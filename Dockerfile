FROM gradle:6.5.0-jdk11 AS TEMP_BUILD_IMAGE
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME
COPY build.gradle.kts settings.gradle.kts $APP_HOME

COPY gradle $APP_HOME/gradle
COPY --chown=gradle:gradle . /home/gradle/src
USER root
RUN chown -R gradle /home/gradle/src

RUN ./gradlew build || return 0
COPY . .
RUN ./gradlew clean build

FROM openjdk:11-jdk
VOLUME /tmp
ADD /build/libs/*-SNAPSHOT.jar produto-service.jar
ENTRYPOINT ["java","-jar","produto-service.jar","--spring.profiles.active=docker"]
