#Step 0: Choose base
FROM selenium/standalone-chrome:latest

USER root

#Step 1 : Install the pre-requisite
RUN apt-get update

ARG DEBIAN_FRONTEND=noninteractive

#RUN DEBIAN_FRONTEND=noninteractive apt-get install -y --no-install-recommends apt-utils

RUN apt-get install -y --no-install-recommends apt-utils


RUN apt-get install -y curl
RUN apt-get install -y p7zip \
    p7zip-full \
    unace \
    zip \
    unzip \
    bzip2


RUN apt-get install -y openjdk-17-jdk ant maven

RUN java -version
RUN javac -version
RUN mvn -version

# Clean up APT when done.
RUN apt-get clean && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*


ARG JAR_FILE=target/maven-template-repository-1.0-SNAPSHOT.jar

WORKDIR /opt/app

COPY ${JAR_FILE} app.jar

ENTRYPOINT ["java","-jar","app.jar"]
