# Spring Boot Transaction Management API

This project implements a Spring Boot 3.x backend API for customer transaction management with a PostgreSQL database. It focuses on CRUD operations and reporting, without security, JWT, Docker, or Flyway.

## Database Setup

1.  **Install PostgreSQL**: If you don't have PostgreSQL installed, download and install it from [https://www.postgresql.org/download/](https://www.postgresql.org/download/).

2.  **Create Database**: Create a new database named `transaction_db` in your PostgreSQL instance. You can do this via `psql` or a GUI tool like pgAdmin.

    ```bash
    CREATE DATABASE transaction_db;
    ```

3.  **Configure `application.properties`**: Update the `src/main/resources/application.properties` file with your PostgreSQL username and password:

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/transaction_db
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    spring.jpa.hibernate.ddl-auto=create-drop
    spring.jpa.show-sql=true
    ```

4.  **Run SQL Scripts**: You can run the `setup.sql` script to create the schema and insert sample data. Open your `psql` terminal and connect to `transaction_db`:

    ```bash
    psql -U your_username -d transaction_db
    ```

    Then, execute the setup script:

    ```sql
    \i src/main/resources/setup.sql
    ```

    Alternatively, Hibernate will auto-generate the schema if `spring.jpa.hibernate.ddl-auto=create-drop` is set (as it is by default in this project). However, to populate with sample data, you will still need to run the `data.sql` script manually or configure Spring Boot to run it on startup (e.g., by placing it in `src/main/resources/data.sql` and ensuring `spring.jpa.defer-datasource-initialization=true` and `spring.sql.init.mode=always` are set in `application.properties`).

## How to Run the Application

1.  **Build the project**: Navigate to the project root directory in your terminal and run:

    ```bash
    ./mvnw clean install
    ```

2.  **Run the application**: After a successful build, run the application using:

    ```bash
    ./mvnw spring-boot:run
    ```

    The application will start on `http://localhost:8080`.

## API Documentation (Basic)

All API endpoints are prefixed with `/api`.

### User Management

-   `POST /api/users` - Create new user
-   `GET /api/users` - Get all users
-   `GET /api/users/{id}` - Get user by ID
-   `PUT /api/users/{id}` - Update user
-   `DELETE /api/users/{id}` - Delete user

### Customer Management

-   `POST /api/customers` - Create new customer
-   `GET /api/customers` - Get all customers with pagination (e.g., `/api/customers?page=0&size=10`)
-   `GET /api/customers/{id}` - Get customer by ID
-   `PUT /api/customers/{id}` - Update customer
-   `DELETE /api/customers/{id}` - Delete customer
-   `GET /api/customers/search?name=&birthdate=&birthplace=` - Search customers

### Product Management

-   `POST /api/products` - Create product
-   `GET /api/products` - Get all products
-   `GET /api/products/{id}` - Get product by ID
-   `PUT /api/products/{id}` - Update product
-   `DELETE /api/products/{id}` - Delete product
-   `POST /api/products/{id}/taxes/{taxId}` - Add tax to product
-   `DELETE /api/products/{id}/taxes/{taxId}` - Remove tax from product
-   `GET /api/products/{id}/taxes` - Get all taxes for a product

### Tax Management

-   `POST /api/taxes` - Create tax
-   `GET /api/taxes` - Get all taxes
-   `GET /api/taxes/{id}` - Get tax by ID
-   `PUT /api/taxes/{id}` - Update tax
-   `DELETE /api/taxes/{id}` - Delete tax

### Transaction Management

-   `POST /api/transactions` - Create new transaction
-   `GET /api/transactions` - Get all transactions
-   `GET /api/transactions/{id}` - Get transaction by ID
-   `DELETE /api/transactions/{id}` - Delete transaction
-   `PUT /api/transactions/{id}/status` - Update payment status only
-   `GET /api/transactions/filter` - Filter transactions (e.g., `/api/transactions/filter?startDate=2024-01-01&paymentStatus=PAID`)

### Reporting

-   `GET /api/reports/customer/{customerId}/spending?startDate=&endDate=` - Customer spending in date range
-   `GET /api/reports/customer/{customerId}/spending/alltime` - Customer total spending all time
-   `GET /api/reports/products/performance` - Total revenue per product all time
-   `GET /api/reports/customer/{customerId}/transactions` - Customer transaction history

## Troubleshooting

-   **Database Connection Issues**: Ensure PostgreSQL is running and your `application.properties` has the correct database URL, username, and password.
-   **Port Already in Use**: If port 8080 is already in use, you can change it in `application.properties` (e.g., `server.port=8081`).
