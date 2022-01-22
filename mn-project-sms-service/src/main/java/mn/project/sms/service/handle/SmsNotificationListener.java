package mn.project.sms.service.handle;

import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;
import io.micronaut.rabbitmq.annotation.Queue;
import io.micronaut.rabbitmq.annotation.RabbitListener;
import mn.project.sms.service.dto.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

@Requires(notEnv = Environment.TEST)
@RabbitListener
public class SmsNotificationListener {

    private static final Logger LOG = LoggerFactory.getLogger(SmsNotificationListener.class);

    @Queue("mn.notification.sms")
    public void sendSms(Message message) {

        LOG.info("Receive at " + new Date().toString());
        LOG.info("Send SMS Message {}", message.getFrom());
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }

    }
}
