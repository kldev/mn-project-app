package mn.portal.auth;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import mn.portal.configuration.AppAuthConfiguration;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@MicronautTest
public class RootStartupPasswordTest {

    @Inject
    private AppAuthConfiguration appAuthConfiguration;

    @Test
    void initialRootPasswordIsFromConfig() {

        assertNotNull(appAuthConfiguration);
        assertEquals(appAuthConfiguration.getRootStartupPassword(), "testRootSecret");
        assertEquals(appAuthConfiguration.getRootEmail(), "root.test@fake.mail");
    }
}
