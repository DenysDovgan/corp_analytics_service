package faang.school.analytics.config.redis.eventconfig;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.listener.ChannelTopic;

@Configuration
public class GoalEventRedisConfig extends AbstractEventRedisConfig {

    public GoalEventRedisConfig(
        @Value("${spring.data.redis.channels.goal-channel.name}") String topicName,
        @Qualifier("goalEventListener") MessageListener eventListener
    ) {
        super(topicName, eventListener);
    }

    @Override
    @Bean("goalChannel")
    public ChannelTopic getTopic() {
        return this.topic;
    }

    @Override
    @Bean("goalAdapter")
    public MessageListener getAdapter() {
        return this.adapter;
    }
}
