# Alfa Server

## Development environment

1. [Download](https://spring.io/tools/sts/all) and extract Spring Tool Suite (STS).
  > Eclipse Neon or higher can also be used but only if Spring Tool Suite (STS) plugin is installed via Help -> Eclipse Marketplace.
2. Run `mvn clean install -DskipTests` and `mvn eclipse:eclipse` before importing!
3. Import project as *existing Maven project into STS/Eclipse.
4. Run project as *Spring Boot App*.
5. Open your browser and navigate to `localhost:8080/alfa`

## Database setup

1. Install MariaDB server >10
2. Connect to MySQL/MariaDB client and run `CREATE DATABASE alfa DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;` to create *alfa* database
3. Open `application.properties` and update property to `spring.jpa.hibernate.ddl-auto=create` to create tables and insert initial records.
4. Run project as *Spring Boot App*. (You may change the property back to `spring.jpa.hibernate.ddl-auto=none` to start faster.)