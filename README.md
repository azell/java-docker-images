# docker-images

Java 8/11 for Docker

* Use 80% of container memory for the Java heap
* Terminate the process on OutOfMemory exception
* Configure the [Amazon Corretto Crypto Provider](https://github.com/corretto/amazon-corretto-crypto-provider)

To build:

```console
$ docker build -t azell/java-docker:8 java-docker
$ docker build -t azell/java-newrelic:8 java-newrelic
$ docker build -f java-docker/Dockerfile.11 -t azell/java-docker:11 .
$ docker build -f java-newrelic/Dockerfile.11 -t azell/java-newrelic:11 .
```

To verify Java heap usage:

```console
$ docker run --rm -m=512m gcr.io/distroless/java:8 -XshowSettings:vm -version
VM settings:
    Max. Heap Size (Estimated): 123.75M
    Ergonomics Machine Class: server
    Using VM: OpenJDK 64-Bit Server VM

openjdk version "1.8.0_222"
OpenJDK Runtime Environment (build 1.8.0_222-8u222-b10-1~deb9u1-b10)
OpenJDK 64-Bit Server VM (build 25.222-b10, mixed mode)
$ docker run --rm -m=512m azell/java-docker:8 -XshowSettings:vm -version
Picked up JAVA_TOOL_OPTIONS: -XX:+ExitOnOutOfMemoryError     -XX:MinRAMPercentage=80.0     -XX:MaxRAMPercentage=80.0
VM settings:
    Max. Heap Size (Estimated): 396.38M
    Ergonomics Machine Class: server
    Using VM: OpenJDK 64-Bit Server VM

openjdk version "1.8.0_222"
OpenJDK Runtime Environment (build 1.8.0_222-8u222-b10-1~deb9u1-b10)
OpenJDK 64-Bit Server VM (build 25.222-b10, mixed mode)
```

Note that heap usage increased from __123.75M__ to __396.38M__ given 512M total.

To verify Amazon Corretto Crypto Provider configuration:

```console
$ javac -source 8 -target 8 VerifyAmazonCorrettoCryptoProvider.java
$ jar -cfm verifyACCP.jar manifest.txt VerifyAmazonCorrettoCryptoProvider.class
$ docker run --rm -v "$PWD":/app azell/java-docker:8 -Djava.security.properties=/accp/amazon-corretto-crypto-provider.security /app/verifyACCP.jar
Picked up JAVA_TOOL_OPTIONS: -XX:+ExitOnOutOfMemoryError     -XX:MinRAMPercentage=80.0     -XX:MaxRAMPercentage=80.0
provider: AmazonCorrettoCryptoProvider
$ docker run --rm -v "$PWD":/app azell/java-newrelic:8 -Djava.security.properties=/accp/amazon-corretto-crypto-provider.security /app/verifyACCP.jar
Picked up JAVA_TOOL_OPTIONS: -XX:+ExitOnOutOfMemoryError     -XX:MinRAMPercentage=80.0     -XX:MaxRAMPercentage=80.0 -javaagent:/agent/newrelic-agent.jar
Sep 30, 2019 02:08:28 +0000 [1 1] com.newrelic INFO: New Relic Agent: Loading configuration file "/agent/./newrelic.yml"
Sep 30, 2019 02:08:28 +0000 [1 1] com.newrelic INFO: Using default collector host: collector.newrelic.com
Sep 30, 2019 02:08:28 +0000 [1 1] com.newrelic ERROR: license_key is empty in the config. Not starting New Relic Agent.
provider: AmazonCorrettoCryptoProvider
```

Note that the provider string should match __AmazonCorrettoCryptoProvider__.

New Relic notes:

* set __NEW_RELIC_APP_NAME__
* set __NEW_RELIC_LICENSE_KEY__
* set __NEW_RELIC_LOG=stdout__
