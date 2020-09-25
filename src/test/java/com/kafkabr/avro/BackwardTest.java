package com.kafkabr.avro;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.kafkabr.e5o.BackwardDebitoExecutadoV1;
import com.kafkabr.e5o.BackwardDebitoExecutadoV2;

import org.junit.jupiter.api.Test;

import org.apache.avro.generic.GenericRecord;
import org.junit.jupiter.api.BeforeAll;

/**
 * @author fabiojose
 */
public class BackwardTest {

    private static final String TOPICO = "-configme-";

    private static final AvroSerializer serializer = new AvroSerializer();
    private static final AvroDeserializer deserializer = new AvroDeserializer();

    @BeforeAll
    public static void beforeAll(){
        Map<String, Object> configs = new HashMap<>();

        configs.put("schema.registry.url", "http://nao.sera.utilizado:8081");

        serializer.configure((Map<String, ?>)configs, Boolean.FALSE);
        deserializer.configure((Map<String, ?>)configs, Boolean.FALSE);
    }

    @Test
    public void deve_permitir_apagar_um_campo(){

        // setup
        BackwardDebitoExecutadoV1 v1 = BackwardDebitoExecutadoV1.newBuilder()
            .setValor(-99.37)
            .setConta("893769")
            .setDescricao("Descrição do Débito Executado")
            .setApagarV2("apagar_v2")
            .build();

        byte[] bytesv1 = serializer.serialize(v1);

        // act
        GenericRecord registro = (GenericRecord)
            deserializer.deserialize(TOPICO, bytesv1,
                BackwardDebitoExecutadoV2.SCHEMA$);

        // assert
        assertNull(registro.get("apagar_v2"));
        assertEquals(v1.getValor(), registro.get("valor"));
        assertEquals(v1.getConta(), registro.get("conta"));
        assertEquals(v1.getDescricao(), registro.get("descricao"));
    }

    @Test
    public void deve_permitir_incluir_campo_opcional(){
        // setup
        BackwardDebitoExecutadoV1 v1 = BackwardDebitoExecutadoV1.newBuilder()
            .setValor(-99.37)
            .setConta("893769")
            .setDescricao("Descrição do Débito Executado")
            .setApagarV2("apagar_v2")
            .build();

        byte[] bytesv1 = serializer.serialize(v1);

        // act
        GenericRecord registro = (GenericRecord)
            deserializer.deserialize(TOPICO, bytesv1,
                BackwardDebitoExecutadoV2.SCHEMA$);

        // assert
        assertEquals("-nenhum-", registro.get("metadados"));

        assertEquals(v1.getValor(), registro.get("valor"));
        assertEquals(v1.getConta(), registro.get("conta"));
        assertEquals(v1.getDescricao(), registro.get("descricao"));
    }
}
