package com.kafkabr.avro;

import static java.lang.System.getProperty;

import java.util.Properties;

import com.kafkabr.e5o.ForwardDebitoExecutadoV1;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 * @author fabiojose
 */
public class App {

    static final Integer QUALQUER_PARTICAO = null;

    public static void main(String[] args) throws Exception {

        System.out.println(" > Registros Avro com Apache Kafka");

        try(KafkaProducer<String, ForwardDebitoExecutadoV1> producer =
                new KafkaProducer<>(criarProducerConfigs())){

            String topico = getProperty("topico", "oc-faturada");
            ForwardDebitoExecutadoV1 evento = new ForwardDebitoExecutadoV1();
            // preencha seu evento . . .

            // ####
            // Registro com estrutura complexa OrdemCompraFaturada, que será
            // serializada para formato de dados Avro
            ProducerRecord<String, ForwardDebitoExecutadoV1> faturada =
                new ProducerRecord<String, ForwardDebitoExecutadoV1>(topico, evento);

            RecordMetadata produzido =
                producer.send(faturada).get();

            System.out.println(" > > Produzido:");
            System.out.println(" > > > partição.: " + produzido.partition());
            System.out.println(" > > > timestmap: " + produzido.timestamp());

        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Criar configurações com base em propriedades de sistema
     */
    private static Properties criarProducerConfigs() {
        Properties props = new Properties();

        // Serializador para chave
        props.put("key.serializer", StringSerializer.class.getName());

        // ########
        // Serializador Avro para OrdemCompraFaturada
        props.put("value.serializer",
            AvroSerializer.class.getName());

        // Servidor Kafka
        props.put("bootstrap.servers",
            getProperty("kafka", "localhost:9092"));

        props.put("schema.registry.url", "http://nao.sera.utilizado:8081");

        // Configurações adicionais
        String cfgs = getProperty("cfg");
        if(null!= cfgs){
            for(String cfg : cfgs.split(",")) {
                String[] kv = cfg.trim().split("=");
                if(kv.length == 2) {
                    System.out.println(" > > cfg " + kv[0] + "=" + kv[1]);
                    props.put(kv[0], kv[1]);
                } else {
                    throw new IllegalArgumentException(
                        "Configurações incorretas: " + cfgs);
                }
            }
        }

        return props;
    }
}
