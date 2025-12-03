# UserTicket Service API

Sistema de gestión de usuarios y tickets desarrollado con Spring Boot, implementando autenticación JWT, cache, y siguiendo principios SOLID.

##  Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 4.0.0**
- **Spring Security + JWT**
- **Spring Data JPA**
- **H2 Database** (en memoria)
- **Caffeine Cache**
- **OpenAPI/Swagger**
- **Lombok**
- **JUnit 5 + Mockito**

##  Requisitos Previos

- Java 17 o superior
- Maven 3.6+


### Opción 1: Ejecución con Maven

```bash

# Compilar el proyecto
mvn clean install

# Ejecutar la aplicación
mvn spring-boot:run
```

##  Documentación de la API

Una vez iniciada la aplicación, accede a:

- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/api-docs
- **H2 Console**: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:ticketdb`
  - Username: `sa`
  - Password: (vacío)

##  Autenticación

La API utiliza JWT (JSON Web Tokens) para autenticación. Todos los endpoints excepto `/api/auth/**` requieren autenticación.

### Flujo de Autenticación

1. **Registrar un usuario**
2. **Iniciar sesión** para obtener el token JWT
3. **Incluir el token** en el header `Authorization: Bearer <token>` en las siguientes peticiones


##  Características Implementadas

### Funcionales
-  CRUD completo de Usuarios
-  CRUD completo de Tickets
-  Paginación de tickets
-  Filtrado por status (ABIERTO/CERRADO)
-  Filtrado por usuario
-  Combinación de filtros
-  Autenticación y autorización JWT
-  Validaciones de campos obligatorios
-  Formato coherente de fechas (LocalDateTime)

### No Funcionales
-  Principios SOLID
-  Manejo de excepciones centralizado
-  Respuestas HTTP estandarizadas
-  DTOs para requests y responses
-  Pruebas unitarias con JUnit y Mockito
-  Pruebas de integración
-  Spring Data JPA con H2
-  Cache con Caffeine (tickets por usuario)
-  Documentación con OpenAPI/Swagger
-  Docker y Docker Compose
-  Manejo de transacciones
-  Optimización de consultas

##  Ejecutar Pruebas

```bash
# Ejecutar todas las pruebas
mvn test

```

##  Modelo de Datos

### Usuario
- `id`: UUID (generado automáticamente)
- `nombres`: String (obligatorio)
- `apellidos`: String (obligatorio)
- `username`: String (obligatorio, único)
- `password`: String (obligatorio, encriptado)
- `fechaCreacion`: LocalDateTime (automático)
- `fechaActualizacion`: LocalDateTime (automático)

### Ticket
- `id`: UUID (generado automáticamente)
- `descripcion`: String (obligatorio, máx 500 caracteres)
- `usuario`: Relación ManyToOne con Usuario
- `status`: Enum (ABIERTO, CERRADO)
- `fechaCreacion`: LocalDateTime (automático)
- `fechaActualizacion`: LocalDateTime (automático)

##  Seguridad

- Contraseñas encriptadas con BCrypt
- Tokens JWT con expiración de 24 horas
- Endpoints protegidos excepto registro y login
- Validación de tokens en cada petición
- CORS configurado

##  Cache

Se implementó cache con Caffeine para optimizar consultas:
- Cache de tickets por usuario
- Tiempo de expiración: 10 minutos
- Tamaño máximo: 500 entradas
- Invalidación automática al crear, actualizar o eliminar tickets

##  Manejo de Errores

La API retorna respuestas HTTP estandarizadas:

- `200 OK`: Operación exitosa
- `201 Created`: Recurso creado
- `204 No Content`: Eliminación exitosa
- `400 Bad Request`: Validación fallida
- `401 Unauthorized`: No autenticado
- `404 Not Found`: Recurso no encontrado
- `500 Internal Server Error`: Error del servidor

