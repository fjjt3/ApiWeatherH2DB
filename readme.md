# City Weather - Full Stack Application

Full-stack application for **city weather data**. Weather is fetched from the [Open-Meteo](https://open-meteo.com/) API and stored in an H2 database. Features an Angular frontend with Bootstrap styling.

## Project Structure

```
ApiWeatherH2DB/
├── backend/              # Spring Boot API
├── frontend/             # Angular application
├── docker-compose.yml    # Orchestrates both services
└── DEPLOYMENT.md         # Guide for Cloud Deployment (Render)
```

## Features

### Backend (Spring Boot)
- **Weather API integration**: Fetches current weather from Open-Meteo for Málaga, Milano, and Cortina d'Ampezzo
- **H2 database**: Stores weather records in the `city_weather` table
- **REST API**: Endpoints to refresh weather data, list stored records, and query by city
- **Swagger UI**: Interactive API documentation
- **H2 Console**: Web UI to inspect the database
- **CORS enabled**: Allows Angular frontend to access the API

### Frontend (Angular)
- **Dashboard**: Clean, responsive UI displaying weather cards for each city
- **Refresh button**: Manual weather data update
- **Bootstrap styling**: Professional, modern design with animations
- **Weather icons**: Dynamic icons based on temperature
- **Color-coded temperatures**: Visual feedback (cold = blue, moderate = green, hot = orange)
- **Responsive design**: Works on mobile, tablet, and desktop

## Tech Stack

### Backend
- **Java 17**
- **Spring Boot 3** (Web, Data JPA)
- **H2** (in-memory database)
- **Open-Meteo API** (no API key required)
- **Maven**
- **SpringDoc OpenAPI** (Swagger)

### Frontend
- **Angular 19**
- **TypeScript**
- **Bootstrap 5** (CSS framework)
- **Bootstrap Icons**
- **RxJS** (reactive programming)

### DevOps
- **Docker** & **Docker Compose**
- **Nginx** (frontend production server)
- **Render** (Cloud Hosting)

## Prerequisites

### For Development (Recommended)
- **Java 17** or later — [Adoptium](https://adoptium.net/)
- **Maven 3.6+** — [Maven](https://maven.apache.org/download.cgi)
- **Node.js 18+** — [Node.js](https://nodejs.org/)
- **npm 9+** (comes with Node.js)
- **Angular CLI 19** — Install with `npm install -g @angular/cli`
- **Docker Desktop** — [Docker](https://www.docker.com/products/docker-desktop)

### For Production Only (Docker)
- **Docker Desktop** — [Docker](https://www.docker.com/products/docker-desktop)

Check versions:
```bash
java -version
node --version
npm --version
ng version
docker --version
```

---

## 🚀 Quick Start - Development Mode (Recommended)

This is the best way to learn and develop with hot-reload:

### 1. Start the Backend (Docker)
```bash
docker compose up backend
```
Backend will be available at **http://localhost:8080**

### 2. Start the Frontend (Angular CLI)
```bash
cd frontend
npm install        # First time only
ng serve --open
```
Frontend will open automatically at **http://localhost:4200**

### 3. Test the Application
1. Open browser at http://localhost:4200
2. Click "**Actualizar Datos**" button
3. Weather cards will populate with data from Málaga, Milano, and Cortina d'Ampezzo

---

## 🐳 Production Mode (Full Docker Stack)

Run both frontend and backend in containers:

```bash
docker compose up --build
```

- **Frontend**: http://localhost:4200
- **Backend API**: http://localhost:8080

---

## ☁️ Cloud Deployment (Render)

The application is prepared for deployment on **Render.com**.

For step-by-step instructions on how to deploy this full-stack application to the cloud for free, please refer to:

👉 **[DEPLOYMENT.md](./DEPLOYMENT.md)**

---
- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **H2 Console**: http://localhost:8080/h2-console

To stop:
```bash
docker compose down
```

---

## API Endpoints

Base path: `/clima`

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/clima/actualizar` | Fetch weather from Open-Meteo for all configured cities and save to H2. |
| `POST` | `/clima/actualizar/{nombreCiudad}` | Fetch and save weather for one city (e.g. `Málaga`, `Milano`, `Cortina`). |
| `GET`  | `/clima` | List all weather records stored in H2. |
| `GET`  | `/clima/ciudad/{nombreCiudad}` | List weather history for a city. |
| `GET`  | `/clima/ciudades` | List configured city names. |

### Example API Calls

```bash
# Get configured cities
curl http://localhost:8080/clima/ciudades

# Refresh weather for all cities
curl -X POST http://localhost:8080/clima/actualizar

# Get all weather records
curl http://localhost:8080/clima

# Get history for specific city
curl http://localhost:8080/clima/ciudad/Málaga
```

---

## Frontend Development

### Project Structure
```
frontend/src/app/
├── components/
│   ├── dashboard/          # Main dashboard page
│   └── weather-card/       # Reusable weather card
├── services/
│   └── weather.service.ts  # HTTP service for API calls
├── models/
│   └── ciudad-clima.model.ts  # TypeScript interface
└── environments/
    ├── environment.ts      # Development config (localhost:8080)
    └── environment.prod.ts # Production config (Docker network)
```

### Generate New Components
```bash
cd frontend
ng generate component components/my-component
```

### Run Tests
```bash
cd frontend
npm test
```

### Build for Production
```bash
cd frontend
npm run build
```

---

## Database Schema

Table `city_weather`:

| Column       | Type     | Description        |
|-------------|----------|---------------------|
| id          | BIGINT   | Primary key        |
| city_name   | VARCHAR  | City name          |
| latitude    | DOUBLE   | Latitude           |
| longitude   | DOUBLE   | Longitude          |
| temperature | DOUBLE   | Temperature (°C)   |
| humidity    | INT      | Relative humidity (%) |
| wind_speed  | DOUBLE   | Wind speed (km/h)  |
| query_time  | TIMESTAMP| When data was fetched |
| timezone    | VARCHAR  | Timezone (e.g. Europe/Madrid) |

**H2 Console login**
- JDBC URL: `jdbc:h2:mem:testdb`
- User: `sa`
- Password: *(leave empty)*

---

## Configuration

### Backend
Main settings in `backend/src/main/resources/application.properties`:
- **DataSource**: H2 in-memory (`jdbc:h2:mem:testdb`)
- **JPA**: `ddl-auto=create-drop`
- **CORS**: Configured in `WebConfig.java` to allow Angular frontend

### Frontend
Environment files in `frontend/src/environments/`:
- **environment.ts**: Points to `http://localhost:8080/clima` (development)
- **environment.prod.ts**: Points to `http://backend:8080/clima` (Docker)

---

## Troubleshooting

### Backend won't start
```bash
# Check if port 8080 is already in use
docker compose down
docker compose up backend
```

### Frontend compilation errors
```bash
cd frontend
rm -rf node_modules package-lock.json
npm install
ng serve
```

### CORS errors in browser
Make sure `WebConfig.java` in the backend has the correct origins (use `*` for public cloud deployment):
```java
.allowedOrigins("*")
```

### Docker build fails
```bash
# Clean Docker cache
docker system prune -a
docker compose build --no-cache
```

---

## Development Workflow

1. **Backend changes**: Rebuild Docker container
   ```bash
   docker compose build backend
   docker compose up backend
   ```

2. **Frontend changes**: Hot reload automatically with `ng serve`
   - Just save your files and see changes instantly!

3. **Test full stack**: Use Docker Compose
   ```bash
   docker compose up --build
   ```

---

## License

This project is licensed under the [MIT License](LICENSE).

