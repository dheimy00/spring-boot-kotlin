FROM adoptopenjdk/openjdk11:alpine as BUILD

# Get gradle distribution 
COPY *.gradle gradle.* gradlew /src/
COPY gradle /src/gradle
WORKDIR /src
RUN chmod +x ./gradlew
RUN ./gradlew --version
COPY . .
RUN ./gradlew --no-daemon shadowJar


FROM openjdk:11-jre-slim
VOLUME /tmp
ADD /build/libs/produto-service-0.0.1-SNAPSHOT.jar produto-service.jar
ENTRYPOINT ["java","-jar","produto-service.jar","--spring.profiles.active=docker"]
