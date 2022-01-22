package mn.portal.api.system;

import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.security.annotation.Secured;
import io.micronaut.security.rules.SecurityRule;
import mn.portal.model.UserCreateDto;
import mn.portal.notification.NotificationProducer;
import mn.portal.notification.dto.Message;

import java.util.stream.IntStream;

@Controller("/api/test")
public class TestController {

    private NotificationProducer notificationProducer;

    public TestController(NotificationProducer notificationProducer) {
        this.notificationProducer = notificationProducer;
    }

    @Secured(SecurityRule.IS_ANONYMOUS)
    @Get("/")
    public HttpResponse<?> create(HttpRequest<UserCreateDto> request) {


        IntStream.range(0, 100).boxed()
                .forEach(x -> {

                    Message m = new Message();
                    m.setFrom("from@fake.mail");
                    m.setBody("demo");
                    notificationProducer.sendMessage(m);
                });


        return HttpResponse.ok("Pong");
    }
}
