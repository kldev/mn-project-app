package mn.portal.api.users;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import mn.portal.data.entity.UserAccountEntity;
import mn.portal.data.users.UserAccountManager;
import mn.portal.data.users.UserRoleType;
import mn.portal.model.UserCreateDto;

import java.util.Optional;


@Controller("/api/v1/users")
public class CreateUserController {

    private final UserAccountManager userAccountManager;

    public CreateUserController(UserAccountManager userAccountManager) {
        this.userAccountManager = userAccountManager;
    }

    @Secured(SecurityRule.IS_AUTHENTICATED)
    @Post("/create")
    public HttpResponse<UserAccountEntity> create(HttpRequest<UserCreateDto> request) {

        UserAccountEntity newUser = this.userAccountManager.createUser(request.getBody().get(), Optional.empty());
        return HttpResponse.ok(newUser);
    }
}
