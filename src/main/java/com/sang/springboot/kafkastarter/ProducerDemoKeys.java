package com.sang.springboot.kafkastarter;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemoKeys {
    private static final Logger log = LoggerFactory.getLogger(ProducerDemoKeys.class.getSimpleName());

    public static void main(String[] args) {
        log.info("Starting Kafka Producer...");

        // Create Producer properties
        Properties properties = new Properties();

        // connect to localhost
        properties.setProperty("bootstrap.servers", "localhost:9092");

        // set key serializer
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());

        // create the Producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        for (int j = 0; j < 10; j++) {

            for (int i = 0; i < 10; i++) {
                String topic = "second-topic";
                String key = "id_" + i;
                String value = "value_" + i;

                log.info("Sending message {} to Kafka", i);

                // create a ProducerRecord
                ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, key, value);

                producer.send(producerRecord, new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                        // executes every time a record is sent successfully or fails
                        if (e == null) {
                            log.info("Key: {} | Partition: {}", key, recordMetadata.partition());
                        } else {
                            log.error("Error sending message", e);
                        }
                    }
                });
            }
        }

        //flush and close the producer
        producer.flush();
        producer.close();

        log.info("Kafka Producer stopped.");
    }
}
