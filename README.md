# Hotel Management System (HMS)

A Hotel Management System built using Java Spring Boot. 

## Features

- **User Management**: Signup, login, and JWT authentication for users.
- **Property Management**: Add, update, delete, and search properties.
- **Country & City Management**: Admin can add countries and cities.
- **Image Management**: Upload and retrieve property images.
- **Role-Based Access Control**: Different roles such as `OWNER`, `ADMIN` with access restrictions.

## Technologies Used

- **Java 17**
- **Spring Boot**: For building the backend API.
- **Spring Security**: For securing endpoints with JWT authentication and role-based access control.
- **Hibernate/JPA**: For ORM and managing database entities.
- **MySQL**: For storing application data.
- **Amazon S3**: For storing property images.
- **Lombok**: For reducing boilerplate code.

## ER Diagram


Here is the ER Diagram for the project:![E-R Diagram](https://github.com/user-attachments/assets/94263678-993c-4a76-bee1-692767e42565)



## Installation

1. Clone the repository:
    ```bash
    git clone https://github.com/tusharishere/hms.git
    ```

2. Navigate to the project directory:
    ```bash
    cd hms
    ```

3. Configure your MySQL database in the `application.properties` or `application.yml` file:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/hms_db
    spring.datasource.username=root
    spring.datasource.password=yourpassword
    ```

4. Install dependencies and build the project using Maven or Gradle:
    ```bash
    ./mvnw clean install  # For Maven
    ```
    Or
    ```bash
    ./gradlew build  # For Gradle
    ```

5. Run the application:
    ```bash
    ./mvnw spring-boot:run  # For Maven
    ```
    Or
    ```bash
    ./gradlew bootRun  # For Gradle
    ```

## API Endpoints

- **User Authentication**:
  - POST `/api/v1/users/signup` - User signup
  - POST `/api/v1/users/login` - User login
  
- **Property Management**:
  - POST `/api/v1/property/addProperty` - Add a new property
  - PUT `/api/v1/property/{id}` - Update a property
  - DELETE `/api/v1/property` - Delete a property
  - GET `/api/v1/property/{id}` - Get a property by ID
  - GET `/api/v1/property` - Get all properties

- **Country and City Management**:
  - POST `/api/v1/country/addCountry` - Add a new country
  - POST `/api/v1/city/addCity` - Add a new city

- **Image Management**:
  - POST `/api/v1/images/upload/file/{bucketName}/property/{propertyId}` - Upload an image for a property
  - GET `/api/v1/images/property/{propertyId}` - Get all images for a property

## Security

- JWT authentication for all endpoints except login, signup, and public property information.
- Role-based access control (`OWNER`, `ADMIN`) for specific endpoints like adding properties, managing countries/cities, and uploading images.

## Contributing

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Make your changes.
4. Commit your changes (`git commit -am 'Add new feature'`).
5. Push to the branch (`git push origin feature-branch`).
6. Open a pull request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
