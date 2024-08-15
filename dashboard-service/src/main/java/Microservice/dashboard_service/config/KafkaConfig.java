package Microservice.dashboard_service.config;

import Microservice.dashboard_service.dto.BillingStatusDto;
import Microservice.dashboard_service.dto.EmailStatusDto;
import Microservice.dashboard_service.dto.EnrollmentStatusDto;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Bean
    public NewTopic jsonNewTopic() {
        return TopicBuilder.name("email-status").build();
    }

    @Bean
    public NewTopic billingStatusTopic(){
        return TopicBuilder.name("billing-status").build();
    }

    @Bean
    public NewTopic enrollmentStatusTopic(){
        return TopicBuilder.name("enrollment-status").build();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public ConsumerFactory<String, EmailStatusDto> consumerFactory() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, "emails");
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
        configs.put(JsonDeserializer.TRUSTED_PACKAGES, "*");  // Allow all packages, or specify if necessary
        configs.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "Microservice.dashboard_service.dto.EmailStatusDto");  // Fully qualified class name
        configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        configs.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        configs.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "5000");
        configs.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, "false");  // Optional, depends on whether you use type info headers
        return new DefaultKafkaConsumerFactory<>(configs);
    }


    @Bean
    public ConsumerFactory<String, BillingStatusDto> consumerFactoryBilling() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, "billings");
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
        configs.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        configs.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "Microservice.dashboard_service.dto.BillingStatusDto");
        configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        configs.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        configs.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "5000");
        configs.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, "false");
        return new DefaultKafkaConsumerFactory<>(configs);
    }
    @Bean
    public ConsumerFactory<String, EnrollmentStatusDto> consumerFactoryEnrollment() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        configs.put(ConsumerConfig.GROUP_ID_CONFIG, "enrollments");
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class.getName());
        configs.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        configs.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "Microservice.dashboard_service.dto.EnrollmentStatusDto");
        configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        configs.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        configs.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "5000");
        configs.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, "false");
        return new DefaultKafkaConsumerFactory<>(configs);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, EmailStatusDto> kafkaListenerContainerFactoryEmail() {
        ConcurrentKafkaListenerContainerFactory<String, EmailStatusDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        factory.setConcurrency(3);  // Number of concurrent consumers
        factory.getContainerProperties().setPollTimeout(3000);
        return factory;
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, BillingStatusDto> kafkaListenerContainerFactoryBilling() {
        ConcurrentKafkaListenerContainerFactory<String, BillingStatusDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryBilling());
        factory.setConcurrency(3);  // Number of concurrent consumers
        factory.getContainerProperties().setPollTimeout(3000);
        return factory;
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, EnrollmentStatusDto> kafkaListenerContainerFactoryEnrollment() {
        ConcurrentKafkaListenerContainerFactory<String, EnrollmentStatusDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactoryEnrollment());
        factory.setConcurrency(3);  // Number of concurrent consumers
        factory.getContainerProperties().setPollTimeout(3000);
        return factory;
    }
}

 