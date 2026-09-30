# School Student API

Spring Boot + MongoDB REST API for school and student management.

This is a small secondary Java portfolio project demonstrating a layered REST API. It is a learning project, not a production-grade service.

## Architecture

- Controllers handle HTTP requests and responses.
- Services apply existence and school/student relationship rules.
- Spring Data MongoDB repositories persist `School` and `Student` documents.
- Request records keep input separate from Mongo persistence fields. Responses remain simple entity-shaped JSON.

## Requirements and setup

- Java 17 or newer
- Maven installed (`mvn`); this repository documents installed Maven because its Maven wrapper configuration is incomplete.
- MongoDB running locally or reachable at the configured host and port.

The default local settings use `localhost:27017` and database `schoolDB`. The current `application.properties` contains only safe local defaults; keep real credentials out of it. Git ignore rules cover local property overrides and environment files. To create or refresh the local file from the checked-in example:

```sh
cp src/main/resources/application.example.properties src/main/resources/application.properties
```

Optional environment variables: `MONGO_HOST`, `MONGO_PORT`, `MONGO_DATABASE`, and `SERVER_PORT`. This sample uses host/port settings rather than credentials or a connection URI.

Run the API:

```sh
mvn spring-boot:run
```

Validate and package:

```sh
mvn test
mvn clean package
```

## API

| Method | Path | Behavior |
| --- | --- | --- |
| GET | `/schools` | List schools |
| GET | `/schools/{id}` | Get a school (404 if missing) |
| POST | `/schools` | Create a school (201) |
| DELETE | `/schools/{id}` | Delete a school (204); 404 if missing, 409 if students reference it |
| GET | `/schools/{schoolId}/students` | List a school's students (200, including an empty list; 404 if school is missing) |
| GET | `/students` | List students |
| GET | `/students/{id}` | Get a student (404 if missing) |
| POST | `/students` | Create a student (201); school must exist |
| DELETE | `/students/{id}` | Delete a student (204); 404 if missing |

School requests require nonblank `name` and `address`. Student requests require nonblank `name` and `schoolId`, and an `age` from 1 through 120. Invalid requests return 400. Error responses contain `timestamp`, `status`, `error`, `message`, and `path`. Unknown schools and resources return 404. Deleting a school that still has students returns 409; records are not cascade-deleted.

## Sample requests

Create a school:

```sh
curl -i http://localhost:8080/schools -H 'Content-Type: application/json' \
  -d '{"name":"Green Valley Academy","address":"123 Main St"}'
```

Create a student using the returned school ID:

```sh
curl -i http://localhost:8080/students -H 'Content-Type: application/json' \
  -d '{"name":"Alice","age":16,"schoolId":"<school-id>"}'
```

List students for a school:

```sh
curl http://localhost:8080/schools/<school-id>/students
```

## Limitations

- No authentication or authorization.
- No pagination or update endpoints.
- Responses expose the small entity shape directly.
- Tests use mocked services for HTTP behavior and do not require MongoDB; they do not replace database integration testing.
- No deployment or production operations configuration.
