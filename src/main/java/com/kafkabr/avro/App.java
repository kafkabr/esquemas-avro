package com.kafkabr.avro;

import static java.lang.System.getProperty;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.UUID;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 * @author fabiojose
 */
public class App {

    static final Integer QUALQUER_PARTICAO = null;

    public static void main(String[] args) throws Exception {

        System.out.println(" > Registros Avro com Apache Kafka");

        //try(KafkaProducer<String, OrdemCompraFaturada> producer =
        //        new KafkaProducer<>(criarProducerConfigs())){
        //
        //    // Tópico que será consumido
        //    String topico = getProperty("topico", "oc-faturada");
        //    OrdemCompraFaturada ocFaturada = new OrdemCompraFaturada();
        //    ocFaturada.setId(UUID.randomUUID().toString());
        //    ocFaturada.setClienteId("20068945690");
        //    ocFaturada.setValor(235.99);

        //    // ####
        //    // Registro com estrutura complexa OrdemCompraFaturada, que será
        //    // serializada para formato de dados Avro
        //    ProducerRecord<String, OrdemCompraFaturada> faturada =
        //        new ProducerRecord<String,OrdemCompraFaturada>(topico, ocFaturada);

        //    RecordMetadata produzido =
        //        producer.send(faturada).get();

        //    System.out.println(" > > Produzido:");
        //    System.out.println(" > > > partição.: " + produzido.partition());
        //    System.out.println(" > > > timestmap: " + produzido.timestamp());

        //    try(KafkaConsumer<String, OrdemCompraFaturada> consumer =
        //            new KafkaConsumer<>(criarConsumerConfigs())) {

        //        consumer.subscribe(Collections.singletonList(topico));

        //        // ####
        //        // Consumir ordens de compra faturadas
        //        while(true){
        //            ConsumerRecords<String, OrdemCompraFaturada> records =
        //                consumer.poll(Duration.ofSeconds(5));

        //            records.forEach(r -> {
        //                System.out.println(" > > > > Ordem faturada");
        //                System.out.println(" > > > > > " + r.value());
        //            });

        //        }
        //    }catch(Exception e) {
        //        e.printStackTrace();
        //    }
        //}
    }

    /**
     * Criar configurações com base em propriedades de sistema
     */
    private static Properties criarProducerConfigs() {
        Properties props = new Properties();

        // Serializador para chave
        props.put("key.serializer", StringSerializer.class.getName());

        // #########
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

    public static Properties criarConsumerConfigs() {
        Properties props = new Properties();

        // Deserializador para chave
        props.put("key.deserializer", StringDeserializer.class.getName());

        // #########
        // Deserializador Avro para OrdemCompraFaturada
        props.put("value.deserializer",
            AvroDeserializer.class.getName());

        // Servidor Kafka
        props.put("bootstrap.servers",
            getProperty("kafka", "localhost:9092"));

        props.put("group.id", "avro-record");

        props.put("auto.offset.reset", "earliest");

        props.put("schema.registry.url", "http://nao.sera.utilizado:8081");

        return props;
    }

}
