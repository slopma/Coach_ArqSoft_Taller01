# PROYECTO COACH - TALLER 01 SPRING BOOT

## DESCRIPCION DEL PROYECTO:

Sistema de gestión de coaches deportivos desarrollado con Spring Boot. Implementa las 6 actividades del taller:

1. Vista inicial con navegación
2. Formulario de creación
3. Inserción de datos (guarfar los datos del forms)
4. Lista de objetos
5. Vista completa de objeto
6. Eliminación de objetos

## PRERREQUISITOS:

- Java 21 o superior (Yo lo probé con 21 y 24)
- Maven 3.6 o superior
- IDE (IntelliJ IDEA, Eclipse, VS Code)
- Nota: application-dev.properties, application-staging.properties y application.properties NO necesitan cambios.

## INSTALACION Y EJECUCION:

**PASO 1:** Descargar el .zip y decomprimir

**PASO 2:** Compilar e instalar dependencias (si se requiere)
```
mvn clean install
```

**PASO 3:** Ejecutar la aplicación
```
mvn spring-boot:run
```

**PASO 4:** Acceder a la aplicación
- Aplicación principal: _http://localhost:8080_
- Página de inicio: _http://localhost:8080/home_
- Página para crear nuevo coach: _http://localhost:8080/coach/crear_
- Página para listar a los coaches: _http://localhost:8080/coach/listar_
- Página para ver el detalle de un coach: _http://localhost:8080/coach/ver/1_
  * Cabe aclarar que el número final es el ID del coach ya creado, así que es variable


## VALIDACIONES IMPLEMENTADAS:

- Nombre: No vacío y máx 100 caracteres
- Cédula: No vacía, máx 20 caracteres y es única
- Teléfono: No vacío y máx 20 caracteres
- Edad: Entre 18 y 80 años
- Años de experiencia: Entre 0 y 50 años
- Nivel certificación: No vacío y máx 50 caracteres

## TECNOLOGIAS UTILIZADAS:

- Spring Boot 3.2.0
- Spring Web MVC
- Spring Data JPA
- Thymeleaf
- H2 Database
- Bootstrap 5.3
- Font Awesome 6.4 (Biblioteca de iconos para hacer el front más bonito)
- Bean Validation


## AUTOR:

Proyecto desarrollado por Sara López Marín para el Taller 01 - Spring Boot
Clase asignada: Coach
