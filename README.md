# Docker
```
cd spring-quartz
mvn clean install -DskipTests   
docker build -t spring-quartz .
cd ../demo
mvn clean install -DskipTests   
docker build -t demo .

docker-compose up -d
```