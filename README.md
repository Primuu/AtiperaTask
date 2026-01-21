# Atipera Task

Zadanie polega na stworzeniu minimalnego API/Proxy (w strukturze **Controller/Service/Client**), 
które umożliwi zwrot listy wszystkich repozytoriów danego użytkownika 
**z wyłączeniem forków**. Zwrot powinien zawierać:

- **Repository Name**
- **Owner Login**
- dla każdego brancha: **nazwa** + **SHA ostatniego commitu**


Backing API: https://developer.github.com/v3

### Wymagania

- **Java 25**, **Spring Boot 4.0.1**, **Gradle Kotlin**
- **Spring MVC** (`spring-boot-starter-webmvc`) + **RestClient** (`spring-boot-starter-restclient`)
- Brak paginacji (ani w wystawianym endpoint, ani przy konsumowaniu API)
- Brak WebFlux
- Brak security, cache, resilience, retries
- Tylko testy integracyjne korzystające z **WireMock** (bez mock'ów)

---

## Jak uruchomić projekt

- Należy posiadać zainstalowaną **Javę 25**
- Projekt zawiera **Gradle Wrapper**, więc nie trzeba instalować Gradle

### Uruchomienie aplikacji
W katalogu projektu:

```bash
./gradlew bootRun
```

---

## API

### Pobranie listy repozytoriów (bez forków) wraz z branchami
`GET /api/get-repos/{username}`

#### Response (200) - użytkownik istnieje
```json
[
  {
    "repositoryName": "my-repo",
    "ownerLogin": "owner-login",
    "branches": [
      { "name": "main", "lastCommitSha": "a1b2c3..." }
    ]
  }
]
```

#### Response (404) - użytkownik nie istnieje
```json
{
    "status": 404,
    "message": "User with given username not found."
}
```


#### (*) Inne błędy po stronie GitHub API 
GitHub API posiada limity zapytań (szczególnie przy braku autoryzacji). 
Może więc zdarzyć się, że GitHub odrzuci część żądań, co może skutkować błędem po stronie proxy.