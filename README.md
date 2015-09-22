# README #

### What is this repository for? ###
* Create, Read, Update, Delete, items(game settings) from a database via UI/API

### How do I get set up? ###
* Configuration:
    * Install Java 7+
    * Install Apache Tomcat web server locally

* Database configuration: app will be connect to a hosted MYSQL database on Google Cloud SQL.

* How to run tests:
    * Gradle script executes tests by default when building project
    * Manually: Go to src/test/java
               * Right click > run all tests | or execute a single unit test

* Deployment instructions:
    * Drop .war file in your local Tomcat's /webapps directory.
        * ex:  C:/apache-tomcat-7.0.63/webapps/java-spring-crud-app-1.0.war
    * Start Tomcat server, visit localhost:8080 to view app

* Repo owner or admin:
      Leon Knights
