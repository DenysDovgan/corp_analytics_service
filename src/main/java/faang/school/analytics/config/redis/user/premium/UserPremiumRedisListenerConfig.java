package faang.school.analytics.config.redis.user.premium;

import faang.school.analytics.service.user.premium.listener.RedisPremiumBoughtEventSubscriber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class UserPremiumRedisListenerConfig {
    @Value("${app.user-premium-redis-config.premium_bought_event_topic}")
    private String premiumBoughtEventTopic;

    @Bean
    public ChannelTopic premiumBoughtEventTopic() {
        return new ChannelTopic(premiumBoughtEventTopic);
    }

    @Bean
    public MessageListenerAdapter premiumBoughtEventListener(RedisPremiumBoughtEventSubscriber messageListener) {
        return new MessageListenerAdapter(messageListener);
    }

    @Bean
    public RedisMessageListenerContainer redisContainer(JedisConnectionFactory connectionFactory,
                                                        ChannelTopic premiumBoughtEventTopic,
                                                        MessageListenerAdapter premiumBoughtEventListener) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(premiumBoughtEventListener, premiumBoughtEventTopic);

        return container;
    }
}