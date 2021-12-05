package mn.portal.configuration;

import io.micronaut.context.annotation.ConfigurationProperties;
import io.micronaut.context.annotation.Requires;
import io.micronaut.core.annotation.NonNull;

import javax.validation.constraints.NotBlank;

@Requires(property = AppAuthConfigurationProperties.PREFIX + ".rootStartupPassword")
@ConfigurationProperties(AppAuthConfigurationProperties.PREFIX)
public class AppAuthConfigurationProperties implements AppAuthConfiguration {
    public static final String PREFIX = "app.auth";

    @NonNull
    @NotBlank
    private String rootStartupPassword = "";

    @NonNull
    @NotBlank
    private String rootEmail = "";

    @NonNull
    public String getRootStartupPassword() {
        return rootStartupPassword;
    }

    public void setRootStartupPassword(@NonNull String rootStartupPassword) {
        this.rootStartupPassword = rootStartupPassword;
    }

    @Override
    @NonNull
    public String getRootEmail() {
        return rootEmail;
    }

    public void setRootEmail(@NonNull String rootEmail) {
        this.rootEmail = rootEmail;
    }
}
