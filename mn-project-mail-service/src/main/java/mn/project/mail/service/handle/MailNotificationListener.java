package mn.project.mail.service.handle;

import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.rabbitmq.annotation.Queue;
import io.micronaut.rabbitmq.annotation.RabbitListener;
import mn.project.mail.service.dto.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Requires(notEnv = Environment.TEST)
@RabbitListener
public class MailNotificationListener {

    private static final Logger LOG = LoggerFactory.getLogger(MailNotificationListener.class);

    @Queue("mn.notification.mail")
    public void sendSms(Message message) {

        LOG.info("Receive at " + new Date().toString());
        LOG.info("Send MAIL Message {}", message.getFrom());

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }
    }
}
