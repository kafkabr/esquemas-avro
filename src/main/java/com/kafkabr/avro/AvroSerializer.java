package com.kafkabr.avro;

import java.util.HashMap;
import java.util.Map;

import io.confluent.kafka.schemaregistry.client.MockSchemaRegistryClient;
import io.confluent.kafka.serializers.KafkaAvroSerializer;

/**
 * Serializador Avro com mock para schema registry.
 *
 * @author fabiojose
 */
public class AvroSerializer extends KafkaAvroSerializer {

    static final MockSchemaRegistryClient MOCK_REGISTRY =
        new MockSchemaRegistryClient();

    public AvroSerializer() {
        super(MOCK_REGISTRY);

        Map<String, Object> configs = new HashMap<>();
        configs.put("schema.registry.url", "http://do.not.use:8081");

        configure(configs, Boolean.FALSE);
    }

    public byte[] serialize(Object record) {
        return super.serialize("none", record);
    }

}
