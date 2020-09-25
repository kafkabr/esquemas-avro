# Avro no Apache Kafka

Entenda como produzir e consumir estruturas complexas com Avro.

## Requerimentos

- Java (JDK) 1.8
- Kafka

## Executar

Escolha um dos comandos abaixo para executar. Se for a primeira vez que
você faz isso, levará um tempo até que todas as dependências estejam 
disponíveis.

- `cfg`: é um argumento para configurações adicionais do produtor.

__Linux__

```bash
./gradlew run \
  -Dkafka=localhost:9092 \
  -Dtopico='<NOME DO TOPICO>' \
  -Dcfg='cfg1=v1, cfg2=v2'
```

Exemplo:
```bash
./gradlew run \
  -Dkafka=localhost:9092 \
  -Dtopico='meu-topico' \
  -Dcfg='fetch.min.bytes=256'
```

__Windows__

```powershell
.\gradlew.bat run ^
-Dkafka=localhost:9092 ^
-Dtopico='<NOME DO TOCIPO>' ^
-Dcfg='cfg1=v1, cfg2=v2'
```

Exemplo:
```powershell
.\gradlew.bat run ^
-Dkafka=localhost:9092 ^
-Dtopico='meu-topico' ^
-Dcfg='fetch.min.bytes=256'
```