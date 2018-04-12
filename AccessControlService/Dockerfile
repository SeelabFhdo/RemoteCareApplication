FROM java
MAINTAINER Philip Wizenty <philipnils.wizenty@fh-dortmund.de>

# update sources
RUN echo "upgrade apt sources"
RUN apt-get update
RUN apt-get upgrade -y

# Add microservice jar-file to container
ADD target/AccessControlService-1.jar .
CMD /usr/bin/java -Xmx400m -Xms400m -jar AccessControlService-1.jar

# Expose Port for Docker
EXPOSE <Used Ports in Microservice>