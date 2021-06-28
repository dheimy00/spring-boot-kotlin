FROM openjdk:11-jdk
VOLUME /tmp
ADD /build/libs/*-SNAPSHOT.jar produto-service.jar
ENTRYPOINT ["java","-jar","produto-service.jar","--spring.profiles.active=docker"]