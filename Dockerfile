FROM openjdk:11-jdk
VOLUME /tmp
ADD /build/libs/produto-service-0.0.1-SNAPSHOT.jar produto-service.jar
ENTRYPOINT ["java","-jar","produto-service.jar","--spring.profiles.active=docker"]
