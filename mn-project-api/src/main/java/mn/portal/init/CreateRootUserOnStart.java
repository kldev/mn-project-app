package mn.portal.init;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.context.event.ApplicationEventListener;
import jakarta.inject.Singleton;
import io.micronaut.discovery.event.ServiceReadyEvent;
import mn.portal.configuration.AppAuthConfiguration;
import mn.portal.data.entity.UserAccountEntity;
import mn.portal.data.users.UserAccountManager;
import mn.portal.data.users.UserRoleType;
import mn.portal.model.UserCreateDto;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Singleton
@Requires(notEnv = Environment.TEST)
public class CreateRootUserOnStart implements ApplicationEventListener<ServiceReadyEvent> {
    private static final Logger LOG = LoggerFactory.getLogger(CreateRootUserOnStart.class);
    private final UserAccountManager userAccountManager;
    private final AppAuthConfiguration appAuthConfiguration;

    public CreateRootUserOnStart(UserAccountManager userAccountManager, AppAuthConfiguration appAuthConfiguration) {
        this.userAccountManager = userAccountManager;
        this.appAuthConfiguration = appAuthConfiguration;
    }

    @Transactional
    @Override
    public void onApplicationEvent(ServiceReadyEvent event)  {

        LOG.info("Check root account exists");
        Optional<UserAccountEntity> root = Optional.empty();
        try {
            root = userAccountManager.getByEmail(appAuthConfiguration.getRootEmail());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (root.isEmpty()) {

            LOG.info("Create startup root account");

            UserCreateDto userCreateDto = new UserCreateDto();
            userCreateDto.setEmail(appAuthConfiguration.getRootEmail());
            userCreateDto.addRole(UserRoleType.Root);
            userCreateDto.setFirstName("Root");
            userCreateDto.setLastName("Fake");
            userCreateDto.setPassword(appAuthConfiguration.getRootStartupPassword());
            userAccountManager.createUser(userCreateDto, Optional.empty());
        }
    }
}

