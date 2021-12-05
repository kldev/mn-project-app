package mn.portal.security;

import io.micronaut.core.annotation.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;
import jakarta.inject.Singleton;

@Singleton
public class BCryptPasswordEncoderService implements PasswordEncoder {

    PasswordEncoder delegate = new BCryptPasswordEncoder();

    @Override
    public String encode(@NotBlank CharSequence rawPassword) {
        if (rawPassword.length() < 5) throw new IllegalArgumentException("Raw password must be at last 5 characters");
        return delegate.encode(rawPassword);
    }
    @Override
    public boolean matches(@NotBlank @NonNull CharSequence rawPassword,
                           @NotBlank @NonNull String encodedPassword) {

        return delegate.matches(rawPassword, encodedPassword);
    }
}
