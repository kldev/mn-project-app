package mn.portal.api.system;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import mn.portal.data.entity.UserAccountEntity;
import mn.portal.model.UserCreateDto;

import java.util.Optional;

@Controller("/api/ping")
public class PingController {
    @Secured(SecurityRule.IS_ANONYMOUS)
    @Get("/")
    public HttpResponse<?> create(HttpRequest<UserCreateDto> request) {

        return HttpResponse.ok("Pong");
    }
}

