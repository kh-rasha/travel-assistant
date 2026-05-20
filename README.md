# Travel Assistant API

Detta är ett backendprojekt byggt med Spring Boot för en individuell laboration.  
Applikationen ger rese- och aktivitetsrekommendationer baserat på vädret i en stad.

## Projektidé

Användaren skickar namnet på en stad.  
Applikationen:

1. Hämtar aktuellt väder från WeatherAPI.
2. Väljer en lämplig aktivitetstyp beroende på vädret.
3. Hämtar rekommenderade platser via Geoapify API.
4. Returnerar rekommendationer till användaren.

Exempel:

- Regnigt väder → museum eller inomhusaktiviteter
- Soligt väder → parker eller utomhusaktiviteter
- Molnigt väder → caféer eller sightseeing

---

## Teknologier

- Java 17
- Spring Boot
- Spring WebFlux
- WebClient
- Resilience4j
- WeatherAPI
- Geoapify API
- Swagger / OpenAPI
- Maven
- Lombok

---

## API Endpoint

```http
GET /api/recommendations?location=Gothenburg
```

Exempel:

```text
http://localhost:8080/api/recommendations?location=Gothenburg
```

## Exempel på svar

```json
{
  "location": "Gothenburg",
  "weatherSummary": "Sunny",
  "recommendationType": "park",
  "activities": [
    "Walk in a city park",
    "Visit an outdoor viewpoint",
    "Explore the city center"
  ]
}
```

## Resilience4j

Projektet använder Resilience4j för att göra applikationen mer feltolerant.

Implementerade funktioner:

- Retry
- Circuit Breaker
- Fallback-metoder

Om ett externt API inte fungerar returnerar applikationen fallback-data istället för att krascha.

Externa API:er
### WeatherAPI

Används för att hämta aktuellt väder för en stad.

### Geoapify API

Används för att hitta aktiviteter och platser baserat på stad och aktivitetstyp.

## Konfiguration

API-nycklar ska inte laddas upp till GitHub.

Använd miljövariabler:

```properties
WEATHER_API_KEY=your_weather_api_key_here
GEOAPIFY_API_KEY=your_geoapify_api_key_here
```

I application.properties:

```properties
weather.api.key=${WEATHER_API_KEY:dummy-weather-key}
geoapify.api.key=${GEOAPIFY_API_KEY:dummy-geoapify-key}
```

## Swagger

Swagger UI finns här efter att applikationen startats:

```text
http://localhost:8080/swagger-ui.html
```

## Hur man kör projektet

Klona projektet:

```bash
git clone <repository-url>
```

Gå till projektmappen:

```bash
cd travel-assistant
```

Starta applikationen:

```bash
mvn spring-boot:run
```

Eller kör TravelAssistantApplication direkt från IntelliJ.


Författare

Rasha khatib
Fullstack Java Student