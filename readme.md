# City Weather

Spring Boot application for **city weather data**. Weather is fetched from the [Open-Meteo](https://open-meteo.com/) API and stored in an H2 in-memory database.

## Features

- **Weather API integration**: Fetches current weather from Open-Meteo for Málaga, Milano, and Cortina d'Ampezzo.
- **H2 database**: Stores weather records in the `city_weather` table.
- **REST API**: Endpoints to refresh weather data, list stored records, and query by city.
- **Swagger UI**: Interactive API documentation.
- **H2 Console**: Web UI to inspect the database.

## Tech stack

- **Java 17**
- **Spring Boot 3** (Web, Data JPA)
- **H2** (in-memory)
- **Open-Meteo API** (no API key required)
- **Maven**
- **SpringDoc OpenAPI** (Swagger)
- **JUnit 5 & Mockito** (tests)

## Prerequisites

- **Java 17** or later — [Adoptium](https://adoptium.net/)
- **Maven 3.6+** — [Maven](https://maven.apache.org/download.cgi) (or use the included wrapper `mvnw` / `mvnw.cmd`)

Check versions:

```bash
java -version
mvn -version
```

## How to run

### 1. Clone and go to the project

```bash
git clone <repository-url>
cd demo-proveedores
```

### 2. Build

```bash
mvn clean package
```

Or with the wrapper (no Maven installed):

- **Windows:** `.\mvnw.cmd clean package`
- **Linux/macOS:** `./mvnw clean package`

### 3. Start the application

**Option A – Maven:**

```bash
mvn spring-boot:run
```

**Option B – Maven wrapper (Windows):**

```bash
.\mvnw.cmd spring-boot:run
```

**Option C – JAR:**

```bash
java -jar target/demo-proveedores-0.0.1-SNAPSHOT.jar
```

The app runs at **http://localhost:8080** (default).

### 4. Run with Docker

You can also run the application using Docker Compose. This method ensures you have a consistent environment.

**Prerequisites:** Docker and Docker Compose installed.

1.  **Build and run:**
    ```bash
    docker compose up --build
    ```
    This command builds the Docker image using Maven and starts the container.

2.  **Access the application:**
    Open http://localhost:8080 in your browser.

3.  **Stop:**
    Press `Ctrl+C` or run:
    ```bash
    docker compose down
    ```

## URLs

| Resource        | URL |
|----------------|-----|
| Application    | http://localhost:8080 |
| Swagger UI     | http://localhost:8080/swagger-ui/index.html |
| H2 Console     | http://localhost:8080/h2-console |

**H2 Console login**

- JDBC URL: `jdbc:h2:mem:testdb`
- User: `sa`
- Password: *(leave empty)*

## API endpoints

Base path: `/clima`

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/clima/actualizar` | Fetch weather from Open-Meteo for all configured cities (Málaga, Milano, Cortina) and save to H2. |
| `POST` | `/clima/actualizar/{nombreCiudad}` | Fetch and save weather for one city (e.g. `Málaga`, `Milano`, `Cortina`). |
| `GET`  | `/clima` | List all weather records stored in H2. |
| `GET`  | `/clima/ciudad/{nombreCiudad}` | List weather history for a city. |
| `GET`  | `/clima/ciudades` | List configured city names. |

## Quick test

1. Start the app (see above).
2. Load initial weather for all cities:
   ```bash
   curl -X POST http://localhost:8080/clima/actualizar
   ```
3. List stored weather:
   ```bash
   curl http://localhost:8080/clima
   ```
4. Or open Swagger: http://localhost:8080/swagger-ui/index.html and call the `/clima` endpoints from there.

## Configuration

Main settings in `src/main/resources/application.properties`:

- **DataSource**: H2 in-memory (`jdbc:h2:mem:testdb`).
- **JPA**: `ddl-auto=create-drop`, schema and data from `schema.sql` and `data.sql`.
- **Open-Meteo**: Optional default timezone, e.g. `openmeteo.timezone.default=Europe/Madrid`.

## Database schema

Table `city_weather`:

| Column       | Type     | Description        |
|-------------|----------|--------------------|
| id          | BIGINT   | Primary key        |
| city_name   | VARCHAR  | City name          |
| latitude    | DOUBLE   | Latitude           |
| longitude   | DOUBLE   | Longitude          |
| temperature | DOUBLE   | Temperature (°C)   |
| humidity    | INT      | Relative humidity (%) |
| wind_speed  | DOUBLE   | Wind speed (km/h)  |
| query_time  | TIMESTAMP| When the data was fetched |
| timezone    | VARCHAR  | Timezone (e.g. Europe/Madrid) |

Sample data for Málaga, Milano and Cortina d'Ampezzo is loaded from `data.sql` on startup.

## Tests

Run all tests:

```bash
mvn test
```

Or with wrapper:

```bash
.\mvnw.cmd test
```

## License

This project is licensed under the [MIT License](LICENSE).
