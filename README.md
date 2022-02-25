# cl-ea-price-kotlin

A [Chainlink](https://chain.link/) compatible [External Adapter](https://docs.chain.link/docs/external-adapters/) that supports a REST API and AWS Lambda Handler.

This adapter interfaces with the [CryptoCompare](https://min-api.cryptocompare.com/data/price) data source.

This is a [Gradle](https://gradle.org/) project built using [OpenJDK 15](https://openjdk.java.net/projects/jdk/15/) with [Micronaut](https://micronaut.io/).

## Building the application

1. Build the JAR:
```
./gradlew clean build
```

### Run with Docker
1. Create the Docker image:
```
docker build -t cl-ea-price-kotlin .
```
2. Get your image ID:
```
docker images
```
3. Start the Docker container:
```
docker run -p 7080:7080 <image_id> 
```
### Run without Docker
1. To start the application, run the main class:
```
Main.kt
```

## Sending requests
1. Send requests as follows:
```
POST: http://localhost:7080
Headers: Content-Type application/json
Body:
{
    "id": 123,
    "data": {
        "from": "ETH",
        "to": "CAD"
    }
}
```
Response:
```
{
    "jobRunId": 123,
    "data": "{\"CAD\":3325.15}"
}
```
## Running the tests
1. Run the tests using:
```
./gradlew clean build
```

## Caching (Recommended)

A cache is enabled by default. The adapter does not support an API key (this may be added in a future release).

1. A [Caffeine](https://github.com/ben-manes/caffeine) cache is enabled by default.
2. It supports the following configuration options in the application.yml:
```
micronaut.caches.prices.initial-capacity
micronaut.caches.prices.maximum-size
micronaut.caches.prices.maximum-weight
micronaut.caches.prices.expire-after-write
micronaut.caches.prices.expire-after-access
```
For more information on each property, take a look at the [documentation](https://micronaut-projects.github.io/micronaut-cache/latest/api/io/micronaut/cache/CacheConfiguration.html).

## Logging
1. The project uses [Logback](https://logback.qos.ch/), which can be configured in:
```
/src/resources/logback.xml
```
2. To enable DEBUG logs, update:
```
<root level="info">
```
To:
```
<root level="debug">
```
