simple-kundera-embedded-cassandra-app
======================================

A simple proof-of-concept of a Java application with embedded Cassandra integration, using Kundera and Guice.

## Prerequisites ##
- JDK 6
- Maven 3.0.3 or newer

## Test ##

```
mvn clean compile exec:java
```

## TODO ##
- Replace _exec:java_ with tests
- Add more complex query examples
- Add benchmarking (?)

## Note ##
Derivative work from LGPL'ed _cassandra-unit_ library is present in _EmbeddedCassandraServerHelper.java_. But this project is (and will be) Apache 2.0. As of this moment, I'm trying to reach its creator to see if we can agree on some solution.

Ultimately, if we're unable to agree, this will be rewritten.
