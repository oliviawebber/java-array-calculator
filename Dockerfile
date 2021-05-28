FROM openjdk:8-jre
ADD build/Client.class Client.class
ADD build/MetricHandler.class MetricHandler.class
EXPOSE 8888
ENTRYPOINT ["java","Client"]