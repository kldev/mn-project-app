package mn.portal.auth;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;

import mn.portal.data.entity.auth.UserAuthenticateByPasswordEntity;
import mn.portal.data.entity.auth.UserAuthenticateRoleEntity;
import mn.portal.data.users.UserAuthenticationManager;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

@MicronautTest
public class UserRoleTest {
    final UserAuthenticationManager userAuthenticationManager;

    public UserRoleTest(UserAuthenticationManager userAuthenticationManager) {
        this.userAuthenticationManager = userAuthenticationManager;
    }

    @Test
    void getCrmAccountRoles() throws SQLException {
        Optional<UserAuthenticateByPasswordEntity>  user = userAuthenticationManager.getUserByEmail("crm1@fake.mail");
        ArrayList<UserAuthenticateRoleEntity> roles = userAuthenticationManager.getUserRolesById(user.get().getId());

        Assert.assertEquals(2, roles.size());
        Assert.assertTrue(roles.stream().anyMatch(r -> r.getRoleName().equals( "admin")));
        Assert.assertTrue(roles.stream().anyMatch(r -> r.getRoleName().equals("responsible")));
    }


}
