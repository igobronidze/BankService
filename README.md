#Introduction
Bank Service is app for managing user and cards, it is used for ATM Service as core app

#Requirements
**Java 8+**

**Maven 3.5.4+**

**Docker 1.12+(not required)**

**Postgres 14.0+**

#Dependencies
Bank Service use postgres database for data store

#Configure
There is **application.properties** file in source src/main/resources directory
* It is possible to update properties parameters here(not recommended)
* Replace configuration file location by add command while run app **--spring.config.location=LOCATION**
* Replace individual properties like **--server.port=8081**

Here is configuration for connect Bank Service

**spring.datasource.url=jdbc:postgresql://bank-service-db:5432/databaseName**

**spring.datasource.username=username**

**spring.datasource.password=password**

#Build and deploy
* It is possible to build and deploy application with docker command **docker compose up**.

 for initial run it is needed to define docker_db.config file location(in this file should be db config like in docker_db.config) **docker compose --env-file "docker_db.config" up**
* With maven: run command **mvn clean install** in root directory, it will create jar file in target directory - **bankservice-VERSION.jar** and it is possible to run it with command **java -jar FILE_NAME**

