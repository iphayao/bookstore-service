# BookStore Service

- Require:
  - MySQL

## How to run
After cloned this repo into your machine, follow this step:

### Configuration .yml file
Open application.yml file in `src/main/resource/application.yml` set `url` of your local MySQL database and `username` & `password` of your MySQL user.

### Run Spring Boot
To run application in your local machine use this command, it will compile, test and start this application.

```
$ mvn spring-boot:run
```