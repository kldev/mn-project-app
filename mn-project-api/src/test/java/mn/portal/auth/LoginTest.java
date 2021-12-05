package mn.portal.auth;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.security.authentication.UsernamePasswordCredentials;
import io.micronaut.security.token.jwt.render.BearerAccessRefreshToken;
import io.micronaut.http.client.HttpClient;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

import com.nimbusds.jwt.JWTParser;
import com.nimbusds.jwt.SignedJWT;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import io.micronaut.http.client.annotation.Client;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest
public class LoginTest {
    @Inject
    @Client("/")
    HttpClient client;

    @Test
    void uponSuccessfulAuthenticationUserGetsAccessToken() throws ParseException {
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("root.test@fake.mail", "testRootSecret");
        HttpRequest<?> request = HttpRequest.POST("/api/auth/login", credentials);
        BearerAccessRefreshToken rsp = client.toBlocking().retrieve(request, BearerAccessRefreshToken.class);
        System.out.println("Rsp" + rsp.getAccessToken());
        assertEquals("root.test@fake.mail", rsp.getUsername());
        assertEquals(1, rsp.getRoles().size());
        assertEquals("root", rsp.getRoles().stream().findFirst().get());
        assertNotNull(rsp.getAccessToken());

        assertTrue(JWTParser.parse(rsp.getAccessToken()) instanceof SignedJWT);
    }

    @Test
    void uponBadPasswordRejectAuthentication() {
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("root.test@fake.mail", "notTheGoodPassword");
        HttpRequest<?> request = HttpRequest.POST("/api/auth/login", credentials);

        HttpClientResponseException error = Assertions.assertThrows(HttpClientResponseException.class,
                () -> client.toBlocking().exchange(request)
        );

        assertEquals("User email or password invalid", error.getMessage());
    }

    @Test
    void uponBadEmailRejectAuthentication() {
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("bad.login@fake.mail", "notTheGoodPassword");
        HttpRequest<?> request = HttpRequest.POST("/api/auth/login", credentials);

        HttpClientResponseException error = Assertions.assertThrows(HttpClientResponseException.class,
                () -> client.toBlocking().exchange(request)
        );

        assertEquals("User not found", error.getMessage());
    }

    @Test
    void uponSuccessfulAuthenticationUserGetsAccessTokenForCrmUser() throws ParseException {
        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials("crm1@fake.mail", "crm1@1234");
        HttpRequest<?> request = HttpRequest.POST("/api/auth/login", credentials);
        BearerAccessRefreshToken rsp = client.toBlocking().retrieve(request, BearerAccessRefreshToken.class);
        System.out.println("Rsp" + rsp.getAccessToken());
        assertEquals("crm1@fake.mail", rsp.getUsername());
        assertNotNull(rsp.getAccessToken());

        assertEquals(2, rsp.getRoles().size());
        assertTrue(JWTParser.parse(rsp.getAccessToken()) instanceof SignedJWT);
    }
}
