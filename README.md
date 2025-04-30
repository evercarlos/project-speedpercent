## MICROSERVICIO DE CÁLCULO DE PORCENTAJE ```SPEEDPERCENT```

Este microservicio tiene como objetivo calcular el porcentaje entre dos números, obteniendo datos desde un servicio externo. Para mejorar el rendimiento, el resultado es almacenado temporalmente en Redis utilizando caché. Además, se implementan ``hilos virtuales`` (Project Loom - Java 21) para lograr una alta concurrencia con bajo consumo de recursos

### FUNCIONES

- ``Hilos virtuales (java 21): ``:Se implementa la nueva funcionalidad de hilos virtuales de Java 21 (Project Loom), permitiendo una alta concurrencia con menor uso de recursos.
- ``Almacenamiento en Caché``: El porcentaje calculado por el servicio externo se guarda en caché en Redis.
- ``Duración del Cache``: El valor en caché se mantiene durante un período de 30 minutos

### REQUISITOS

- Java 21
- Spring Boot 3.2.5
- Docker

### COMPONENTES

- JAVA: 21
- SPRING BOOT: 3.2.5
- DOCKER
- Redis
- Retry

### INSTALACIÓN

- Clonar el repositorio:

```bash
  git clone https://github.com/evercarlos/project-speedpercent.git
 ```

- Entrar en el directorio del proyecto:
  ```bash
  cd speedpercent
  ```
- Compilar el proyecto
   ```bash
    mvn clean install
  ```
- Construir y levantar los contenedores con Docker Compose
   ```bash
    docker-compose up --build -d
  ```
### URL DEL MICROSERVICIO Y DOCUMENTACIÓN SWAGGER
- http://localhost:9002
### ENDPOINTS DISPONIBLES
1. Lista de historial de llamadas con paginación
   
Realiza una consulta para obtener el historial de las llamadas con paginación.

 ```bash
curl --location 'http://localhost:8092/api/v1/histories/withPagination?page=0&size=10&sort=id%2Casc'
 ```
Respuesta:

```json
{
  "content": [
    {
      "id": 68,
      "date": "2025-04-30T14:16:34",
      "endpoint": "http://speedpercent_mock:8080/calculator/findPercentage",
      "parameterJson": "{\"numberOne\":10.0,\"numberTwo\":20.0}",
      "response": 0,
      "error": "Unable to connect to Redis"
    },
    {
      "id": 69,
      "date": "2025-04-30T14:38:07",
      "endpoint": "http://speedpercent_mock:8080/calculator/findPercentage",
      "parameterJson": "{\"numberOne\":10.0,\"numberTwo\":20.0}",
      "response": 33,
      "error": null
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 40,
    "sort": [
      {
        "direction": "ASC",
        "property": "id",
        "ignoreCase": false,
        "nullHandling": "NATIVE",
        "ascending": true,
        "descending": false
      }
    ],
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "last": true,
  "totalPages": 1,
  "totalElements": 6,
  "first": true,
  "size": 40,
  "number": 0,
  "sort": [
    {
      "direction": "ASC",
      "property": "id",
      "ignoreCase": false,
      "nullHandling": "NATIVE",
      "ascending": true,
      "descending": false
    }
  ],
  "numberOfElements": 6,
  "empty": false
}
```
2. Lista de historial de llamadas sin paginación

Realiza una consulta para obtener el historial de todas las llamadas sin paginación.

 ```bash
curl --location 'http://localhost:8092/api/v1/histories'
 ```
Respuesta:

```json
[
  {
    "id": 68,
    "date": "2025-04-30T14:16:34",
    "endpoint": "http://speedpercent_mock:8080/calculator/findPercentage",
    "parameterJson": "{\"numberOne\":10.0,\"numberTwo\":20.0}",
    "response": 0,
    "error": "Unable to connect to Redis"
  },
  {
    "id": 69,
    "date": "2025-04-30T14:38:07",
    "endpoint": "http://speedpercent_mock:8080/calculator/findPercentage",
    "parameterJson": "{\"numberOne\":10.0,\"numberTwo\":20.0}",
    "response": 33,
    "error": null
  }
]
```

3. Generación de calculo con percentaje dinámico

Realiza una consulta para calcular el porcentaje dinámicamente entre dos números.

 ```bash
curl --location 'http://localhost:9002/api/v1/calculator?numberOne=20&numberTwo=80'
 ```
Respuesta:

 ```json
  110
 ```