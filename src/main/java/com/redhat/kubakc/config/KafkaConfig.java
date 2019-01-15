package com.redhat.kubakc.config;


import com.redhat.kubak.square.Square;
import com.redhat.kubakc.service.MetadataService;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
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


@Configuration
@EnableKafka
public class KafkaConfig {

    @Autowired
    private MetadataService metadataService;
    @Value("${kafka.bootstrap.server}")
    private String bootstarpServers;
    @Value("${kafka.bootstrap.server.port}")
    private String bootstrapServersPort;
    @Value("${kafka.consumer.group.id}")
    private String consumerGroupId;
    @Value("${app.kafka.offset.reset}")
    private String offsetRestConfig;

    @Bean
    public ConsumerFactory<String, Square> consumerFactory() {
        Map<String, Object> config = new HashMap<>();

        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, String.format("%s:%s", bootstarpServers, bootstrapServersPort));
        // For presentation reason, if it's a 4th instance in STS,
        // do not use consumer ID from ENVS, use hardcoded value
        if (metadataService.getHostname().equals("kubakc-4")) consumerGroupId = "kubakc-4";
        config.put(ConsumerConfig.GROUP_ID_CONFIG, consumerGroupId);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, offsetRestConfig);

        final JsonDeserializer<Square> squareDeserializer = new JsonDeserializer<>();
        squareDeserializer.addTrustedPackages("com.redhat.kubak.square");

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