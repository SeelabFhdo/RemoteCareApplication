FROM ewolff/docker-java
MAINTAINER Philip Wizenty <philipnils.wizenty@fh-dortmund.de>

# Add microservice jar-file to container
COPY target/eurekaserver-0.0.1-SNAPSHOT.jar .
CMD /usr/bin/java -Xmx400m -Xms400m -jar eurekaserver-0.0.1-SNAPSHOT.jar

# Expose Port for Docker
EXPOSE 8761
