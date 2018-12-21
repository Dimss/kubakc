package com.redhat.kubakc.config;


import com.redhat.kubakp.model.Square;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
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

    @Bean
    public ConsumerFactory<String, Square> consumerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group1");
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

//    @Bean
//    public ConsumerFactory<String, Square> squareConsumerFactory() {
//        Map<String, Object> config = new HashMap<>();
//
//        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
//        config.put(ConsumerConfig.GROUP_ID_CONFIG, "group1");
//        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
//        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
//
//        return new DefaultKafkaConsumerFactory<>(
//                config,
//                new StringDeserializer(),
//                new JsonDeserializer<>(Square.class)
//        );
//    }
//
//    @Bean
//    public ConcurrentKafkaListenerContainerFactory<String, Square> squareKafkaListenerFactory() {
//        ConcurrentKafkaListenerContainerFactory<String, Square> factory = new ConcurrentKafkaListenerContainerFactory<>();
//        factory.setConsumerFactory(squareConsumerFactory());
//        return factory;
//    }

}