package mn.portal.notification.channel;

import java.io.IOException;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import io.micronaut.rabbitmq.connect.ChannelInitializer;

import jakarta.inject.Singleton;


@Singleton
public class ChannelPoolListener extends ChannelInitializer {
    @Override
    public void initialize(Channel channel, String name) throws IOException {
        channel.exchangeDeclare("mn-project", BuiltinExchangeType.TOPIC, true);
        channel.queueBind("mn.notification.*", "mn-project", "");
    }
}
