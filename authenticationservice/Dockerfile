FROM ewolff/docker-java
MAINTAINER Philip Wizenty <philipnils.wizenty@fh-dortmund.de>

# Add microservice jar-file to container
COPY target/authenticationservice-0.0.1-SNAPSHOT.jar .
CMD /usr/bin/java -Xmx400m -Xms400m -jar authenticationservice-0.0.1-SNAPSHOT.jar

# Expose Port for Docker
EXPOSE 9999
