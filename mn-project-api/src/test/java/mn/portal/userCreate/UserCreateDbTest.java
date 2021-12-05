package mn.portal.userCreate;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import mn.portal.data.entity.UserAccountEntity;
import mn.portal.data.users.UserAccountManager;
import mn.portal.data.users.UserRoleType;
import mn.portal.model.UserCreateDto;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@MicronautTest
public class UserCreateDbTest {

    final UserAccountManager userAccountManager;

    public UserCreateDbTest(UserAccountManager userAccountManager) {
        this.userAccountManager = userAccountManager;
    }

    @Test
    void userManagerCreateUserWithRoles() {
        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setEmail("test.user@fake.mail");
        userCreateDto.setPassword("secret1");
        userCreateDto.addRole(UserRoleType.Root);
        userCreateDto.addRole(UserRoleType.Admin);
        userCreateDto.setFirstName("Test");
        userCreateDto.setLastName("FakeMail");

        UserAccountEntity added =  userAccountManager.createUser(userCreateDto, Optional.empty());

        Assert.assertTrue(added.getId() > 0);

        UserCreateDto guestOne = new UserCreateDto();
        guestOne.setEmail("guest1@fake.mail");
        guestOne.setPassword("secret1");
        guestOne.setFirstName("Alan");
        guestOne.setLastName("Demo");
        guestOne.addRole(UserRoleType.Guest);

        UserAccountEntity questOneAdded = userAccountManager.createUser(guestOne, Optional.of( added.getId()));
        assertNotNull(questOneAdded);

        try {
            userAccountManager.createUser(guestOne, Optional.of( added.getId()));
            assertEquals(1, 0);
        }
        catch (Exception e) {
            assertNotNull(e);
        }

    }
}
