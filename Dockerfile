FROM openjdk:8-jdk-alpine

##dockerVOLUME /tmp
##COPY /build/libs/td-sdx-challenge-*-SNAPSHOT.jar td-sdx-challenge.jar
EXPOSE 8080
ADD /build/libs/td-sdx-challenge-*-SNAPSHOT.jar td-sdx-challenge.jar
ENTRYPOINT ["java", "-jar", "td-sdx-challenge.jar"]