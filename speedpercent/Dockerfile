FROM eclipse-temurin:21-jre
LABEL maintainer="ecr"

RUN apt-get update && apt-get install -y libfreetype6 fontconfig

RUN mkdir -p /opt/logs
WORKDIR /opt

ADD target/*.jar /opt/

EXPOSE 8080

## for traditional threads
#ENTRYPOINT ["java", "-jar", "/opt/speedpercent-1.0-SNAPSHOT.jar"]
## for virtual threads
ENTRYPOINT ["java", "--enable-preview", "-jar", "/opt/speedpercent-1.0-SNAPSHOT.jar"]
