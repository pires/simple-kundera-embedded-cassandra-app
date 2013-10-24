simple-kundera-embedded-cassandra-app
======================================

A simple proof-of-concept of a Java application with embedded Cassandra integration, using Kundera and Guice.

## Prerequisites ##
- JDK 6
- Maven 3.0.3 or newer
- Java EE 7 container (I recommend Glassfish 4, more especifically 4.0.1-b04, nightly as of Sep 25, 2013).

*Note* that this app won't work with a prior version of Java EE container.

## Test ##

```
mvn clean compile exec:java
```

## TODO ##
- Replace _exec:java_ with tests
- Add more complex query examples
- Add benchmarking (?)
