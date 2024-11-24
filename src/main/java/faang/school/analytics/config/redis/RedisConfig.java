package faang.school.analytics.config.redis;

import faang.school.analytics.listener.FollowerEventListener;
import faang.school.analytics.listener.GoalEventListener;
import faang.school.analytics.listener.MentorshipRequestedEventListener;
import faang.school.analytics.listener.PostLikeEventListener;
import faang.school.analytics.listener.ProfileViewEventListener;
import faang.school.analytics.listener.ProjectViewEventListener;
import faang.school.analytics.config.redis.eventconfig.EventRedisConfig;
import faang.school.analytics.config.redis.eventconfig.PremiumBoughtEventRedisConfig;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.Map;

@Setter
@Configuration
@RequiredArgsConstructor
public class RedisConfig {

    private final PremiumBoughtEventRedisConfig premiumConfig;

    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    MessageListenerAdapter followerListener(FollowerEventListener followerEventListener) {
        return new MessageListenerAdapter(followerEventListener);
    }

    @Bean
    MessageListenerAdapter goalListener(GoalEventListener goalEventListener) {
        return new MessageListenerAdapter(goalEventListener);
    }

    @Bean
    MessageListenerAdapter profileViewListener(ProfileViewEventListener profileViewEventListener) {
        return new MessageListenerAdapter(profileViewEventListener);
    }

    @Bean
    MessageListenerAdapter projectViewListener(ProjectViewEventListener projectViewEventListener) {
        return new MessageListenerAdapter(projectViewEventListener);
    }

    @Bean
    MessageListenerAdapter postLikeListener(PostLikeEventListener postLikeEventListener) {
        return new MessageListenerAdapter(postLikeEventListener);
    }

    @Bean
    MessageListenerAdapter mentorshipRequestedListener(MentorshipRequestedEventListener mentorshipRequestedEventListener) {
        return new MessageListenerAdapter(mentorshipRequestedEventListener);
    }

    @Bean
    RedisMessageListenerContainer redisMessageListenerContainer(
            Map<String, MessageListenerAdapter> listenerAdapters,
            Map<String, ChannelTopic> channelTopics) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(jedisConnectionFactory());
        container.addMessageListener(listenerAdapters.get("followerListener"), channelTopics.get("followerEventTopic"));
        container.addMessageListener(listenerAdapters.get("goalListener"), channelTopics.get("goalEventTopic"));
        container.addMessageListener(listenerAdapters.get("profileViewListener"), channelTopics.get("profileViewTopic"));
        container.addMessageListener(listenerAdapters.get("projectViewListener"), channelTopics.get("projectViewEventTopic"));
        container.addMessageListener(listenerAdapters.get("postLikeListener"), channelTopics.get("postLikeTopic"));
        container.addMessageListener(listenerAdapters.get("mentorshipRequestedListener"), channelTopics.get("mentorshipRequestedTopic"));
        return container;
    }

    @Bean(value = "projectViewEventTopic")
    ChannelTopic projectViewEventTopic(@Value("${spring.data.redis.channels.project-view-channel.name}") String name) {
        return new ChannelTopic(name);
    }

    @Bean(value = "followerEventTopic")
    ChannelTopic followerEventTopic(@Value("${spring.data.redis.channels.follower-channel.name}") String name) {
        return new ChannelTopic(name);
    }

    @Bean(value = "goalEventTopic")
    ChannelTopic goalEventTopic(@Value("${spring.data.redis.channels.goal-channel.name}") String name) {
        return new ChannelTopic(name);
    }

    @Bean(value = "profileViewTopic")
    ChannelTopic profileViewTopic(
            @Value("${spring.data.redis.channels.profile-view-channel.name}") String profileViewChannelName) {
        return new ChannelTopic(profileViewChannelName);
    }

    @Bean(value = "postLikeTopic")
    ChannelTopic postLikeTopic(
            @Value("${spring.data.redis.channels.like-channel.name}") String postLikeChannelName) {
        return new ChannelTopic(postLikeChannelName);
    }
    @Bean(value = "mentorshipRequestedTopic")
    ChannelTopic mentorshipRequestedTopic(
            @Value("${spring.data.redis.channels.mentorship-requested-channel.name}") String mentorshipRequestedChannelName) {
        return new ChannelTopic(mentorshipRequestedChannelName);
    }
}
