package mn.portal.init;

import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.discovery.event.ServiceReadyEvent;
import jakarta.inject.Singleton;
import mn.portal.configuration.AppAuthConfiguration;
import mn.portal.data.entity.UserAccountEntity;
import mn.portal.data.users.UserAccountManager;
import mn.portal.data.users.UserRoleType;
import mn.portal.model.UserCreateDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Optional;

@Singleton
@Requires(env = Environment.TEST)
public class SeedDbForTestOnStart implements ApplicationEventListener<ServiceReadyEvent>  {
    private static final Logger LOG = LoggerFactory.getLogger(SeedDbForTestOnStart.class);
    private final UserAccountManager userAccountManager;
    private final AppAuthConfiguration appAuthConfiguration;

    public SeedDbForTestOnStart(UserAccountManager userAccountManager, AppAuthConfiguration appAuthConfiguration) {
        this.userAccountManager = userAccountManager;
        this.appAuthConfiguration = appAuthConfiguration;
    }

    @Override
    public void onApplicationEvent(ServiceReadyEvent event) {
        createRootAccount();
        createCrmAccount();
    }

    private void createRootAccount() {
        LOG.info("Check root account exists");
        Optional<UserAccountEntity> root = Optional.empty();;
        try {
            root = userAccountManager.getByEmail(appAuthConfiguration.getRootEmail());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (root.isEmpty()) {

            LOG.info("Create startup root account: " + appAuthConfiguration.getRootEmail());

            UserCreateDto userCreateDto = new UserCreateDto();
            userCreateDto.setEmail(appAuthConfiguration.getRootEmail());
            userCreateDto.addRole(UserRoleType.Root);
            userCreateDto.setFirstName("Root");
            userCreateDto.setLastName("SeedTest");
            userCreateDto.setPassword(appAuthConfiguration.getRootStartupPassword());
            userAccountManager.createUser(userCreateDto, Optional.empty());

        }


    }
    private void createCrmAccount() {
        Optional<UserAccountEntity> crmUser1 = Optional.empty();;
        try {
            crmUser1 = userAccountManager.getByEmail("crm1@fake.mail");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (crmUser1.isEmpty()) {

            LOG.info("Create crm1 account: crm1@fake.mail");

            UserCreateDto userCreateDto = new UserCreateDto();
            userCreateDto.setEmail("crm1@fake.mail");
            userCreateDto.addRole(UserRoleType.Admin);
            userCreateDto.addRole(UserRoleType.Responsible);
            userCreateDto.setFirstName("John");
            userCreateDto.setLastName("Smith");
            userCreateDto.setPassword("crm1@1234");

            userAccountManager.createUser(userCreateDto, Optional.empty());
        }
    }
}
