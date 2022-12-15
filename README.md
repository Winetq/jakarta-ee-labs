# Jakarta EE Tools and Applications

Laboratories created during "Jakarta EE tools and applications" lectures at Gdansk University of Technology.

[![Java v11][shield-java]](https://openjdk.java.net/projects/jdk/11/)
[![Jakarta EE v8][shield-jakarta]](https://jakarta.ee/specifications/platform/8/)

## Requirements

The list of tools required to build and run the project:

* Open JDK 11.x
* Apache Maven 3.5.x

## Building

In order to build project use:

```bash
mvn clean package
```

## Running

In order to run using Open Liberty Application server use:

```bash
mvn -P liberty liberty:dev
```

[shield-java]: https://img.shields.io/badge/Java-11-blue.svg
[shield-jakarta]: https://img.shields.io/badge/Jakarta_EE-8-blue.svg
