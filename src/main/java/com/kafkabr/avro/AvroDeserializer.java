package com.kafkabr.avro;

import java.util.HashMap;
import java.util.Map;

import org.apache.avro.Schema;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;

/**
 * Deserializador Avro com mock para schema registry.
 *
 * @author fabiojose
 */
public class AvroDeserializer extends KafkaAvroDeserializer {

    public AvroDeserializer(){
        super(AvroSerializer.MOCK_REGISTRY);

        Map<String, Object> configs = new HashMap<>();
        configs.put("schema.registry.url", "http://do.not.use:8081");
        configs.put("specific.avro.reader", "false");

        configure(configs, Boolean.FALSE);
    }

    public Object deserialize(Schema schema, byte[] b) {
        return deserialize("none", b, schema);
    }
}
