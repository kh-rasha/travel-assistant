# Travel Assistant API

A reactive Spring Boot backend application that provides travel recommendations using WeatherAPI and OpenAI APIs.

---

# Features

* Get real-time weather information for any city
* Generate AI-powered travel advice
* Reactive programming with Spring WebFlux
* Swagger/OpenAPI documentation
* Resilience4j Retry & Circuit Breaker
* Global exception handling
* Environment variable support for API keys
* Fallback responses when external APIs fail

---

# Tech Stack

* Java 17
* Spring Boot 3
* Spring WebFlux
* WebClient
* OpenAI API
* WeatherAPI
* Resilience4j
* Swagger / OpenAPI
* Maven
* Lombok

---

# Project Structure

```text
src/main/java/com/rasha/travel_assistant
│
├── client
│   ├── OpenAiClient.java
│   └── WeatherClient.java
│
├── config
│   └── WebClientConfig.java
│
├── controller
│   └── TravelController.java
│
├── dto
│   ├── TravelResponse.java
│   └── WeatherResponse.java
│
├── exception
│   └── GlobalExceptionHandler.java
│
├── service
│   └── TravelService.java
│
└── TravelAssistantApplication.java
```

---

# API Endpoint

## Get Travel Advice

```http
GET /api/travel?city=London
```

### Example Request

```http
http://localhost:8080/api/travel?city=Stockholm
```

### Example Response

```json
{
  "city": "Stockholm",
  "weatherSummary": "18.2°C, Sunny",
  "travelAdvice": "Perfect weather for sightseeing and walking tours."
}
```

---

# Swagger Documentation

After running the application:

```text
http://localhost:8080/swagger-ui.html
```

---

# Environment Variables

Create a `.env` file or use environment variables:

```env
OPENAI_API_KEY=your_openai_api_key
WEATHER_API_KEY=your_weather_api_key
```

---

# application.properties

```properties
spring.application.name=travel-assistant
server.port=8080

openai.api.key=${OPENAI_API_KEY:dummy-openai-key}
weather.api.key=${WEATHER_API_KEY:dummy-weather-key}
```

---

# Resilience4j Configuration

The application includes:

* Retry mechanism
* Circuit breaker
* Fallback responses

This improves stability when external APIs are unavailable or rate-limited.

---

# Running the Project

## Clone the repository

```bash
git clone <your-repository-url>
cd travel-assistant
```

---

## Build the project

```bash
mvn clean install
```

---

## Run the application

```bash
mvn spring-boot:run
```

Or run `TravelAssistantApplication.java` directly from IntelliJ IDEA.

---

# Testing the API

You can test using:

* Swagger UI
* Browser
* Postman
* curl

Example:

```bash
curl "http://localhost:8080/api/travel?city=Paris"
```

---

# Error Handling

The application handles:

* External API failures
* Missing API keys
* OpenAI rate limits
* Invalid requests

Using:

* Global exception handler
* Fallback methods
* Resilience4j

---

# Notes

* OpenAI free accounts may return:

```text
429 Too Many Requests
```

if billing/quota is not enabled.

* Weather API may return:

```text
401 Unauthorized
```

if the API key is invalid.

---

# Future Improvements

* Add caching
* Add unit tests
* Add Docker support
* Add authentication
* Save travel history in database
* Frontend integration

---

# Author

Rasha khatib 

Fullstack Java Student — Göteborg 
