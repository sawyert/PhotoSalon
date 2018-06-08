FROM sgrio/java-oracle:server_jre_9

ADD target/photosalon-api-0.0.1-SNAPSHOT.jar /data/photosalon-api-0.0.1-SNAPSHOT.jar
ADD photo-salon-api.yaml /data/photo-salon-api.yaml

#RUN java -jar /data/photosalon-api-0.0.1-SNAPSHOT.jar db migrate /data/photo-salon-api.yaml
CMD java -jar /data/photosalon-api-0.0.1-SNAPSHOT.jar server

EXPOSE 8080