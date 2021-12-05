package mn.portal.configuration;

import javax.validation.constraints.NotNull;

public interface AppAuthConfiguration {
    @NotNull
    String getRootStartupPassword();

    @NotNull
    String getRootEmail();
}
