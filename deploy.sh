mvn clean install

docker container stop service_b_container
docker container rm service_b_container
docker rmi service_b

docker build -t service_b .
docker run --name service_b_container -p 8081:8080 -d service_b
