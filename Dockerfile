FROM gradle:jdk15 as builder
USER root
COPY ./src /app/src
COPY ./build.gradle.kts settings.gradle.kts /app/
RUN gradle -p /app clean
FROM adoptopenjdk/openjdk15:alpine-jre
COPY build/libs/*-all.jar /app/app.jar
RUN apk add --no-cache --update \
    curl \
    bash \
    tini
COPY docker_entrypoint.sh /docker_entrypoint.sh
RUN chmod +x /docker_entrypoint.sh
WORKDIR /app
ENTRYPOINT [ "/sbin/tini", "--", "/docker_entrypoint.sh"]
CMD ["java", "-jar", "/app/app.jar"]
