package com.redhat.kubakc.config;


import com.redhat.kubakp.model.Square;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;


import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Value("${kafka.bootstrap.server}")
    private String bootstarpServers;
    @Value("${kafka.bootstrap.server.port}")
    private String bootstrapServersPort;
    @Value("${kafka.consumer.group.id}")
    private String consumerGroupId;

    @Bean
    public ConsumerFactory<String, Square> consumerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, String.format("%s:%s", bootstarpServers, bootstrapServersPort));
        config.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        final JsonDeserializer<Square> squareDeserializer = new JsonDeserializer<>();
        squareDeserializer.addTrustedPackages("com.redhat.kubakp.model");

        return new DefaultKafkaConsumerFactory<>(
                config,
                new StringDeserializer(),
                squareDeserializer
        );
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Square> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Square> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;

    }
}