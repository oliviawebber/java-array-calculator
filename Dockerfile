FROM openjdk:8-jdk
COPY ./src/main/java /app
EXPOSE 8888
RUN javac /app/*.java
WORKDIR /app
CMD java ArrayClient