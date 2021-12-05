package mn.portal.data.users;


import jakarta.inject.Singleton;
import mn.portal.data.entity.UserAccountEntity;
import mn.portal.data.person.PersonPeopleType;
import mn.portal.model.UserCreateDto;

import java.sql.*;
import java.util.Optional;
import java.util.UUID;

import io.micronaut.transaction.SynchronousTransactionManager;
import mn.portal.security.BCryptPasswordEncoderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.transaction.Transactional;


@Singleton
public class UserAccountManager {
    private static final Logger LOG = LoggerFactory.getLogger(UserAccountManager.class);
    private final Connection connection;
    private final SynchronousTransactionManager<Connection> transactionManager;
    private final BCryptPasswordEncoderService passwordEncoder;

    public UserAccountManager(Connection connection,
                              BCryptPasswordEncoderService passwordEncoder,
                              SynchronousTransactionManager<Connection> transactionManager
                              ) {

        this.connection = connection;
        this.transactionManager = transactionManager;
        this.passwordEncoder = passwordEncoder;
    }

    public UserAccountEntity createUser(UserCreateDto user, Optional<Long> addedByUserId) {
        return transactionManager.executeWrite(status -> {
            final UserAccountEntity addedRecord = new UserAccountEntity();
            try (PreparedStatement ps =
                         connection.prepareStatement("insert into user_accounts (email, password) values (?, ?) returning id")) {
                ps.setString(1, user.getEmail());
                String userPassword = user.getPassword();
                if (userPassword == null || userPassword.isEmpty()) userPassword = UUID.randomUUID().toString();

                ps.setString(2, passwordEncoder.encode(userPassword));
                ResultSet res = ps.executeQuery();
                if (res.next()) {
                    addedRecord.setId(res.getLong(1));
                }

                addedRecord.setEmail(user.getEmail());
                addedRecord.setActive(true);
                addedRecord.setDeleted(false);
                saveUserRoles(user, addedByUserId, addedRecord);
                Optional<Long> addedPersonId = createUserPerson(user, addedByUserId, addedRecord);
                if (addedPersonId.isPresent()) {
                    connectPersonAndUser(addedPersonId, addedRecord.getId());
                }
            }
            return addedRecord;
        });
    }

    private void connectPersonAndUser(Optional<Long> addedPersonId, Long userId) throws SQLException {
        PreparedStatement saveUserPerson = connection.prepareStatement("insert into user_persons(user_id, person_id) values(?, ?)");
        saveUserPerson.setLong(1, userId);
        saveUserPerson.setLong(2, addedPersonId.get());
        saveUserPerson.execute();
    }

    private Optional<Long> createUserPerson(UserCreateDto user, Optional<Long> addedByUserId, UserAccountEntity addedRecord) throws SQLException {
        Optional<Long> addedPersonId = null;

        PreparedStatement createUserPerson =
                connection.prepareStatement("insert into person_people (first_name, last_name, person_type_id, added_by_id) values(?, ?, ?, ?) returning id");
        createUserPerson.setString(1, user.getFirstName());
        createUserPerson.setString(2, user.getLastName());
        createUserPerson.setInt(3, PersonPeopleType.User.getPersonType());
        if (addedByUserId.isPresent()) {
            createUserPerson.setLong(4, addedByUserId.get());
        } else {
            createUserPerson.setNull(4, Types.BIGINT);
        }

        ResultSet createPersonResult = createUserPerson.executeQuery();

        if (createPersonResult.next()) {
            addedPersonId = Optional.of(createPersonResult.getLong(1));
        }
        return addedPersonId;
    }

    private void saveUserRoles(UserCreateDto user, Optional<Long> addedByUserId, UserAccountEntity addedRecord) throws SQLException {
        if (user.getRoles().size() > 0) {
            for (UserRoleType role : user.getRoles()) {
                LOG.debug("add role: " + role.getUserRoleTypeId() + " to " + addedRecord.getEmail());
                PreparedStatement addRoleStatement =
                        connection.prepareStatement("insert into user_roles (user_id, role_id, added_by_id) values(?, ?, ?)");
                addRoleStatement.setLong(1, addedRecord.getId());
                addRoleStatement.setInt(2, role.getUserRoleTypeId());
                if (addedByUserId.isPresent()) {
                    addRoleStatement.setLong(3, addedByUserId.get());
                } else {
                    addRoleStatement.setNull(3, Types.BIGINT);
                }
                addRoleStatement.execute();
            }
        }
    }

    @Transactional
    public Optional<UserAccountEntity> getByEmail(String email) throws SQLException {

        PreparedStatement ps =
                connection.prepareStatement("select id, email, active, deleted, create_at from user_accounts where email = ?");
        ps.setString(1, email);

        ResultSet result = ps.executeQuery();
        if (result.next()) {
            UserAccountEntity record = new UserAccountEntity();
            record.setId(result.getLong(1));
            record.setEmail(result.getString(2));
            record.setActive(result.getBoolean(3));
            record.setDeleted(result.getBoolean(4));
            record.setCreatedAt(result.getDate(5));

            return Optional.of(record);
        }

        return Optional.empty();
    }
}
