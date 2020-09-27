package com.kafkabr.avro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;

import com.kafkabr.e5o.ForwardDebitoExecutadoV1;
import com.kafkabr.e5o.ForwardDebitoExecutadoV2;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * @author fabiojose
 */
public class ForwardTest {

    private static final AvroSerializer serializer =
        new AvroSerializer();

    private static final AvroDeserializer deserializer =
        new AvroDeserializer();

    @BeforeAll
    public static void beforeAll(){
        Map<String, Object> configs = new HashMap<>();

        configs.put("schema.registry.url", "http://nao.sera.utilizado:8081");
        configs.put("specific.avro.reader", "true");

        serializer.configure((Map<String, ?>)configs, Boolean.FALSE);
        deserializer.configure((Map<String, ?>)configs, Boolean.FALSE);
    }

    @Test
    public void deve_permitir_apagar_campo_requerido() {

        // setup
        ForwardDebitoExecutadoV2 v2 = ForwardDebitoExecutadoV2.newBuilder()
            .setValor(-99.37)
            .setConta("893769")
            .setDescricao("Descrição do Débito Executado")
            .setMetadados("local:sp")
            .build();

        byte[] bytesV2 = serializer.serialize(v2);

        // act
        ForwardDebitoExecutadoV1 actual = (ForwardDebitoExecutadoV1)
            deserializer.deserialize(
                    ForwardDebitoExecutadoV1.getClassSchema(), bytesV2);

        // assert
        assertNotNull(actual);
        assertEquals("-apagado_v2-", actual.getApagarV2());

        assertThrows(NullPointerException.class, () ->
            actual.get("metadados"));

    }

    @Test
    public void deve_permitir_incluir_campo_opcional() {

    }
}
