[![Build Status](https://travis-ci.org/pro100boy/TestSEVEN.svg?branch=master)](https://travis-ci.org/pro100boy/TestSEVEN)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/df57db59e5934d70a666260a027bce9e)](https://www.codacy.com/app/gpg/TestSEVEN?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=pro100boy/TestSEVEN&amp;utm_campaign=Badge_Grade)

## Java Enterprise application with authorization and role-based access ##

Application have 3 tabs: Companies, Users, Reports.

The system have 3 roles:

- ADMIN (login: `admin@gmail.com`, password: `admin`)
- COMPANY_OWNER
- COMPANY_EMPLOER

For the ADMIN role all tabs are available. ADMIN can CRUD companies, users, but **only view** all reports of companies. ADMIN have no company and can change own profile into DB directly.

The COMPANY_OWNER have access to all the tabs with the next restrictions:
1) On '**Сompanies**' tab he can only view and edit his own company.
2) On '**Users**' tab he can view, add, edit and delete his company's employees.
3) On '**Reports**' tab he can generate and view reports for his company.

The COMPANY_EMPLOER have access to '**Users**' and '**Reports**' tabs with the next restrictions:
1) On '**Users**' tab he can view and edit only his own profile.
2) On '**Reports**' tab he can generate and view reports for his company.

When creating a company, the system automatically creates a user, then system generates and sends a registering data to the user's mail. `SMTP` settings introduce in `application.yml` file (for `gmail` service).

Initial DB parameters (can be changed in a file `application.yml`):
- login: `root`
- password: `root`
- database name: `sevendb` 

To start the application:
- build project in Maven (run `mvn clean install` command in the terminal)
- run command `java -Dfile.encoding="UTF-8" -jar target\TestSEVEN.jar`. Application will run into Tomcat container, port `8888` (can be changed in a file `application.yml`).
- in browser (for example, Chrome) open the URL: `localhost:8888`

The list of users will be shown in '**Users**' tab. Each user has password `password` and email as login.

When first start the application will create tables with test data in the database. Set parameter `initialize: false` to prevent the tables from being overwritten the next time you start the application.

Technologies:

- Java 8
- Spring Boot
- Spring Security
- Spring MVC
- Spring Data JPA 
- Hibernate ORM 
- Hibernate Validator 
- SLF4J 
- Json Jackson
- Maven
- Thymeleaf, Bootstrap, jQuery (+plugins), AJAX
- MySQL db
- Lombok
- JUnit, Spring Security Test, Mockito (tests for classes containing business logic and for REST controllers)
- Greenmail for SMTP testing

