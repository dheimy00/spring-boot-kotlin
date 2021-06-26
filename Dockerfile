FROM gradle:6.5.0-jdk11 AS TEMP_BUILD_IMAGE
RUN chmod +x gradle
RUN  gradle clean build

FROM openjdk:11-jdk
VOLUME /tmp
ADD /build/libs/*-SNAPSHOT.jar produto-service.jar
ENTRYPOINT ["java","-jar","produto-service.jar","--spring.profiles.active=docker"]
