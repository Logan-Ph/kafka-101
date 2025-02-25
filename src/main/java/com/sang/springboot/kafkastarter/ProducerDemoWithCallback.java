package com.sang.springboot.kafkastarter;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerDemoWithCallback {
    private static final Logger log = LoggerFactory.getLogger(ProducerDemoWithCallback.class.getSimpleName());

    public static void main(String[] args) {
        log.info("Starting Kafka Producer...");

        // Create Producer properties
        Properties properties = new Properties();

        // connect to localhost
        properties.setProperty("bootstrap.servers", "localhost:9092");

        // set key serializer
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());

        properties.setProperty("batch.size", "400");

        properties.setProperty("partitioner.class", RoundRobinPartitioner.class.getName());

        // create the Producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);


        for (int i = 0; i < 30; i++) {
            for (int j = 0; j < 10; j++) {

                log.info("Sending message {} to Kafka", i);
                int finalI = i;
                producer.send(new ProducerRecord<>("second-topic", "key" + i, "value" + i), new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                        // executes every time a record is sent successfully or fails
                        if (e == null) {
                            log.info("Key: {} | Partition: {}", finalI, recordMetadata.partition());
                        } else {
                            log.error("Error sending message", e);
                        }
                    }
                });
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //flush and close the producer
        producer.flush();
        producer.close();

        log.info("Kafka Producer stopped.");
    }
}
