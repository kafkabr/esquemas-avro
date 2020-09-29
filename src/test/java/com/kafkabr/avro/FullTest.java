package com.kafkabr.avro;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;

import com.kafkabr.e5o.FullDebitoExecutadoV1;
import com.kafkabr.e5o.FullDebitoExecutadoV2;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * @author fabiojose
 */
public class FullTest {

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
    public void deve_permitir_incluir_campo_opcional(){

        // setup
        FullDebitoExecutadoV1 v1 = FullDebitoExecutadoV1.newBuilder()
            .setValor(-99.37)
            .setConta("893769")
            .setDescricao("Descrição do Débito Executado")
            .build();

        byte[] bytesV1 = serializer.serialize(v1);

        // act
        FullDebitoExecutadoV2 actual = (FullDebitoExecutadoV2)
            deserializer.deserialize(
                FullDebitoExecutadoV2.getClassSchema(), bytesV1);

        // assert
        assertEquals("-nenhum-", actual.getMetadados());

        assertEquals(v1.getValor(), actual.getValor());
        assertEquals(v1.getConta(), actual.getConta());
        assertEquals(v1.getDescricao(), actual.getDescricao());

    }

    @Test
    public void deve_permitir_apagar_campo_opcional() {

        // setup
        FullDebitoExecutadoV2 v2 = FullDebitoExecutadoV2.newBuilder()
            .setValor(-99.37)
            .setConta("893769")
            .setDescricao("Descrição do Débito Executado")
            .build();

        byte[] bytesV2 = serializer.serialize(v2);

        // act
        FullDebitoExecutadoV1 actual = (FullDebitoExecutadoV1)
            deserializer.deserialize(
                FullDebitoExecutadoV1.getClassSchema(), bytesV2);

        // assert
        assertEquals("-apagado_v2-", actual.getApagarV2());

        assertEquals(v2.getValor(), actual.getValor());
        assertEquals(v2.getConta(), actual.getConta());
        assertEquals(v2.getDescricao(), actual.getDescricao());
    }
}
