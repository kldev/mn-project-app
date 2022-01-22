package mn.portal.notification;

import io.micronaut.rabbitmq.annotation.Binding;
import io.micronaut.rabbitmq.annotation.RabbitClient;
import mn.portal.notification.dto.Message;

@RabbitClient("mn-project")
public interface NotificationProducer {

    @Binding()
    void sendMessage(Message message);
}
