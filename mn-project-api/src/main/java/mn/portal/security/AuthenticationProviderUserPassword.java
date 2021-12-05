package mn.portal.security;

import io.micronaut.http.HttpRequest;
import io.micronaut.security.authentication.AuthenticationProvider;
import io.micronaut.security.authentication.AuthenticationRequest;
import io.micronaut.security.authentication.AuthenticationResponse;
import mn.portal.data.entity.auth.UserAuthenticateByPasswordEntity;
import mn.portal.data.entity.auth.UserAuthenticateRoleEntity;
import mn.portal.data.users.UserAuthenticationManager;
import org.reactivestreams.Publisher;

import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Singleton
public class AuthenticationProviderUserPassword  implements AuthenticationProvider{

    private final UserAuthenticationManager userAuthenticationManager;

    public AuthenticationProviderUserPassword(UserAuthenticationManager userAuthenticationManager) {
        this.userAuthenticationManager = userAuthenticationManager;
    }

    @Override
    public Publisher<AuthenticationResponse> authenticate(HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        return Flux.create(emitter -> {

            try {
                Optional<UserAuthenticateByPasswordEntity> userRecord  = userAuthenticationManager.getUserByEmail((String) authenticationRequest.getIdentity());
                if (userRecord.isEmpty())
                {
                    emitter.next(AuthenticationResponse.failure("User not found"));
                    emitter.complete();
                    return;
                }

                UserAuthenticateByPasswordEntity userAuthenticateByPasswordEntity =userRecord.get();

                if (userAuthenticationManager.validateProvidedPassword(userAuthenticateByPasswordEntity.getPassword(), (String) authenticationRequest.getSecret()) == false) {
                    emitter.next(AuthenticationResponse.failure("User email or password invalid"));
                    emitter.complete();
                    return;
                }

                ArrayList<UserAuthenticateRoleEntity> roles = userAuthenticationManager.getUserRolesById(userAuthenticateByPasswordEntity.getId());

                Collection<String> rolesCollection= roles.stream().map(UserAuthenticateRoleEntity::getRoleName).collect(Collectors.toList());
                emitter.next(AuthenticationResponse.success((String) authenticationRequest.getIdentity(), rolesCollection));
                emitter.complete();

            }
            catch (SQLException sqlException) {
                emitter.error(AuthenticationResponse.exception());
                return;
            }
        }, FluxSink.OverflowStrategy.ERROR);
    }
}
