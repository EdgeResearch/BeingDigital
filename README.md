# Being Digital

<p align = "center">
  <img src = "src/main/resources/static/img/BeingDigital2.png" width="577" alt="Logo">
</p>

<p align = "center">
  Spring Boot Web App for privacy literacy
  <br>
  internship project for
  <br>
  Bachelor Degree in computer science 
  <br>
  at University of Salerno.
</p>

# Project description and introduction

In this section we introduce context information for the project.

## Authors

* **Michela Faella** - *Developer* - [MichelaFaella](https://github.com/MichelaFaella)
* **Lorenzo Sorrentino** - *Developer* - [losor2002](https://github.com/losor2002)

## Documentation

* Project's javadoc can be found in *docs* directory and at the following
  link: [Javadoc Link](https://michelafaella.github.io/BeingDigital/).


# Technical information

In this section we introduce technical information and installing guides!

## Installing and running the project

Follow these steps:

1. Download and start MySQL 8.0.33;
2. Download Java SE 17;
3. Clone this repo;
4. Go into BeingDigital directory;
5. Run `./mwnw (or .\mvnw.cmd if you're on windows) clean package` and wait for maven to build;
6. Run `java -jar target/BeingDigital-1.0.jar –spring.datasource.password="<password_database>"
   –spring.datasource.username="<username_database>"`;
7. Open your browser at `localhost:8080`.

## Built With

* [Java](https://jdk.java.net/17/) - The programming language used for the back-end development.
* [Spring Framework](https://spring.io/) - The java framework used to develop (Spring Web and Spring Data JPA).
* [Maven](https://maven.apache.org/) - Dependency Management.
* [HTML5](https://www.w3schools.com/html/default.asp) - The programming language used for the front-end development.
* [Thymeleaf](https://www.thymeleaf.org/) - Java template to render static pages into dynamic.

# Contributors

<a href="https://github.com/MichelaFaella/BeingDigital/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=MichelaFaella/BeingDigital" />
</a>

Made with [contrib.rocks](https://contrib.rocks).
