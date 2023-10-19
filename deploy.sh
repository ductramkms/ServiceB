./mvnw install


docker container stop service_b_container
docker container rm service_b_container
docker rmi service_b

docker build -t service_b .
docker run --network service_ab_network --name service_b_container -p 3031:8081 -d service_b

