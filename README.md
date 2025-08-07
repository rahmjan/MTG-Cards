# MTG Cards app

### Overview

Application for requesting PNG images of MTG cards. You can find examples of requests
in [examples.http](./examples.http)

### Technologies

- Kotlin
- JVM 21
- Spring Boot
- Gradle
- Docker Compose

### Build & run

#### DB in container + stand alone app:

- Linux: `docker compose -f ./docker-compose/docker-compose.yml up db`
- Win PS: `docker compose -f .\docker-compose\docker-compose.yml up db`
- Win CMD: `docker compose -f ./docker-compose/docker-compose.yml up db`

plus one of these

- `Intellij Idea`
- Linux: `export SPRING_PROFILES_ACTIVE=local && ./gradlew bootRun`
- Win PS: `$env:SPRING_PROFILES_ACTIVE="local"; .\gradlew.bat bootRun`
- Win CMD: `set SPRING_PROFILES_ACTIVE=local && gradlew.bat bootRun`

#### All in Docker Compose:

- Linux: `./gradlew bootJar && docker compose -f .\docker-compose\docker-compose.yml up`
- Win PS: `.\gradlew.bat bootJar; docker compose -f .\docker-compose\docker-compose.yml up`
- Win CMD: `gradlew.bat bootJar && docker compose -f .\docker-compose\docker-compose.yml up`

### Features

- Requests to the [Scryfall API](https://scryfall.com/docs/api) are rate-limited to one request every 100 milliseconds
- Images are processed in parallel using coroutines, with a maximum of 20 concurrent operations
- Successful image responses are cached by card name. If a cached entry exists, it is returned immediately and the
  [Scryfall API](https://scryfall.com/docs/api) is not called
- Cache is cleared every day at 03:00 by the Cron job
