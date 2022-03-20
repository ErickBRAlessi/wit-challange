# Wit Challange

## Description:

Development of a Rest API that provides the basic funcionalities of a calculator.

Fully Specs can be found on the pdf: ***./Challenge JAVA_2020.pdf***

There is a collection that can be imported on Postman for calling the Rest module. ***./Rest.postman_collection.json***

### Modules

- **Rest** (Responsable for handle HTTP Request and call Calculator module. Also, It makes basic validations)
- **Calculator** (Responsable for process math operations)

## Build:

### Starting RabbitMQ

From the root of the repository (wit-challange)

Run:

``
sudo docker-compose -f RabbitMQ/docker-compose.yml up -d
``

RabbitMQ will be running on background.

### Compiling the Aplication:

It can be runned from the IDE or directly from maven (terminal) as It is described bellow:

#### Running Rest Project

``
mvn -f rest/pom.xml clean
``

``
mvn -f rest/pom.xml spring-boot:run
``

#### Running Calculator Project

``
mvn -f calculator/pom.xml clean
``

``
mvn -f calculator/pom.xml spring-boot:run
``

## Main Techs

- Java 11 (adopt-openjdk-11)
- SpringBoot 2.5.3
- Maven
- RabbitMQ (implemented RPC - Remote Procedure Call)
- Lombok
- MDC (Mapped Diagnostic Context) for logging