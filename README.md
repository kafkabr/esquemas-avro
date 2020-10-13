# Avro no Apache Kafka

Entenda como produzir e consumir estruturas complexas com Avro.

## Requerimentos

- Java (JDK) 1.8

## Esquemas

Os esquemas Avro foram criados por estratégia e utilizando o mesmo evento, afim
de contribuir com estudos comparativos.

`BACKWARD`

- [BackwardDebitoExecutado_v1.avsc](./src/main/avro/BackwardDebitoExecutado_v1.avsc)
- [BackwardDebitoExecutado_v2.avsc](./src/main/avro/BackwardDebitoExecutado_v2.avsc)

`FORWARD`

- [ForwardDebitoExecutado_v1.avsc](./src/main/avro/ForwardDebitoExecutado_v1.avsc)
- [ForwardDebitoExecutado_v2.avsc](./src/main/avro/ForwardDebitoExecutado_v2.avsc)

`FULL`

- [FullDebitoExecutado_v1.avsc](./src/main/avro/FullDebitoExecutado_v1.avsc)
- [FullDebitoExecutado_v2.avsc](./src/main/avro/FullDebitoExecutado_v2.avsc)

## Testes Com Avro

Todos os testes que demonstram como Avro funcionam estão nas
classes de teste unitário:

- [BackwardTest.java](./src/test/java/com/kafkabr/avro/BackwardTest.java)
- [ForwardTest.java](./src/test/java/com/kafkabr/avro/ForwardTest.java)
- [FullTest.java](./src/test/java/com/kafkabr/avro/FullTest.java)

## Executar

__Linux__

```bash
./gradlew test --info
```

__Windows__

```powershell
.\gradlew.bat test --info
```
