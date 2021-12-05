package mn.portal.data.users;

import jakarta.inject.Singleton;
import mn.portal.data.entity.auth.UserAuthenticateByPasswordEntity;
import mn.portal.data.entity.auth.UserAuthenticateRoleEntity;
import mn.portal.security.BCryptPasswordEncoderService;

import javax.transaction.Transactional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

@Singleton
public class UserAuthenticationManager {
    private final Connection connection;
    private final BCryptPasswordEncoderService passwordEncoder;
    public UserAuthenticationManager(Connection connection,
                                     BCryptPasswordEncoderService passwordEncoder) {
        this.connection = connection;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Optional<UserAuthenticateByPasswordEntity> getUserByEmail(String email) throws SQLException {
        PreparedStatement ps =
                connection.prepareStatement("select email, password, id from user_accounts where email = ? and active = '1' and deleted = '0' limit 1");
        ps.setString(1, email);

        ResultSet result = ps.executeQuery();
        if (result.next()) {
            UserAuthenticateByPasswordEntity userRecord = new UserAuthenticateByPasswordEntity();
            userRecord.setEmail(result.getString("email"));
            userRecord.setPassword(result.getString("password"));
            userRecord.setId(result.getInt("id"));
            return Optional.of(userRecord);
        }

        return Optional.empty();
    }

    @Transactional
    public ArrayList<UserAuthenticateRoleEntity> getUserRolesById(int userId) throws SQLException {
        ArrayList<UserAuthenticateRoleEntity> rolesList = new ArrayList<UserAuthenticateRoleEntity>();
        PreparedStatement ps =
                connection.prepareStatement("select ut.id as id, ut.name from user_role_types ut "
                        + " inner join user_roles ur on ur.role_id = ut.id and ur.user_id = ?");
        ps.setInt(1, userId);
        ResultSet result = ps.executeQuery();
        while (result.next()) {
            UserAuthenticateRoleEntity addRole = new UserAuthenticateRoleEntity();
            addRole.setRoleId(result.getInt("id"));
            addRole.setRoleName(result.getString("name"));
            rolesList.add(addRole);
        }

        return rolesList;
    }

    public boolean validateProvidedPassword(String dbPasswod, String inputPassword) {
        return passwordEncoder.matches(inputPassword, dbPasswod);
    }
}
