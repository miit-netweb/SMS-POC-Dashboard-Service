package Microservice.dashboard_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import Microservice.dashboard_service.entity.BillingStatus;
import Microservice.dashboard_service.entity.EmailStatus;
import Microservice.dashboard_service.entity.EnrollmentStatus;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, EmailStatus> emailStatusRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, EmailStatus> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(EmailStatus.class));
        return template;
    }

    @Bean
    public RedisTemplate<String, BillingStatus> billingStatusRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, BillingStatus> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(BillingStatus.class));
        return template;
    }

    @Bean
    public RedisTemplate<String, EnrollmentStatus> enrollmentStatusRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, EnrollmentStatus> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(EnrollmentStatus.class));
        return template;
    }
}
