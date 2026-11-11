# Mesa de ayuda

Proyecto de práctica para construir una mesa de ayuda y aprender diseño orientado a objetos, patrones de diseño y desarrollo web con Java. El repositorio contiene el modelo de dominio original y un backend web inicial en Spring Boot. La interfaz React y los endpoints de tickets todavía están planificados.

## Objetivo

Una empresa necesita registrar problemas reportados por sus usuarios, asignarlos a técnicos, agregar comentarios y seguir el ciclo de vida de cada ticket. El proyecto avanza por etapas: primero las reglas del dominio y sus patrones; luego una API y persistencia; finalmente una interfaz web.

## Estructura del repositorio

```text
java-gestion-tickets/
├── src/                       # Aplicación Java de consola y modelo de dominio
│   └── mesaayuda/
│       ├── app/                # Main de demostración
│       └── modulo/             # Tickets, gestor y patrones
├── test/                      # Pruebas JUnit del modelo de dominio
│   └── mesaayuda/modulo/
├── backend/                   # Proyecto Maven con Spring Boot
│   ├── pom.xml
│   └── src/
│       ├── main/java/mesaayuda/api/
│       ├── main/resources/
│       └── test/java/mesaayuda/api/
└── frontend/                  # Reservado para la interfaz React
```

`src` y `test` en la raíz pertenecen al proyecto de consola original. Spring Boot usa su propia estructura Maven bajo `backend/src/main` y `backend/src/test`. No mezcles las carpetas de ambos proyectos.

## Dominio y patrones implementados

- **Singleton — `GestorDeTickets`:** un punto de acceso al repositorio en memoria, asignación de IDs, consultas y ejecución de comandos.
- **State — `Ticket` y `EstadoTicket`:** controla el ciclo `ABIERTO → EN_CURSO → RESUELTO → CERRADO`. Se puede reabrir desde resuelto o cerrado; las transiciones inválidas se rechazan.
- **Strategy — `EstrategiaAsignacion`:** asignación rotativa o por menor cantidad de tickets activos.
- **Observer — `ObservadorTicket`:** los notificadores reaccionan a asignaciones, comentarios y cambios de estado.
- **Command — `ComandoTicket`:** representa acciones como avanzar estado, reabrir y asignar técnico. `GestorDeTickets` registra comandos ejecutados correctamente.

El gestor también ofrece búsquedas por ID, estado, técnico, prioridad y texto, búsqueda de tickets sin técnico y conteo por estado.

## Aplicación de consola

El `Main` original demuestra el modelo, las estrategias, notificaciones y comandos. En Eclipse, ejecutá `mesaayuda.app.Main` desde el proyecto Java original.

Las pruebas del dominio están en `test/mesaayuda/modulo`. En Eclipse, elegí una clase de test y ejecutá **Run As → JUnit Test**.

## Backend Spring Boot

El backend se creó con Java 21, Maven y Spring Boot. Por ahora es un esqueleto que arranca el servidor web y configura JPA con H2; todavía no expone endpoints para tickets ni guarda entidades propias.

### Ejecutar desde Eclipse

Importá `backend` como proyecto Maven y ejecutá `MesaAyudaApiApplication` con **Run As → Java Application** (o **Spring Boot App**, si aparece). Spring inicia Tomcat en el puerto `8080`.

### Ejecutar desde Windows

Desde una terminal ubicada en `backend`:

```bat
mvnw.cmd spring-boot:run
```

Para compilar y ejecutar las pruebas del backend:

```bat
mvnw.cmd clean test
```

El Maven Wrapper (`mvnw.cmd`) usa la versión de Maven configurada para el proyecto; no es necesario instalar Maven globalmente. La primera ejecución puede descargar dependencias.

### Base de datos actual

H2 está configurada en modo memoria (`jdbc:h2:mem`). Spring Boot la inicia al arrancar y los datos se pierden al detener la aplicación. Todavía no hay entidades, repositorios de tickets ni persistencia del dominio.

## Próximos pasos

1. Agregar un endpoint de salud `GET /api/health` y probar la respuesta.
2. Definir la entidad persistente de ticket y su repositorio JPA.
3. Crear `GET /api/tickets` y `POST /api/tickets`.
4. Conectar la creación y listado de tickets con la interfaz React en `frontend`.
5. Como mejora posterior, cambiar H2 por PostgreSQL y documentar cómo iniciarlo.

## Tecnologías

- Java 21
- Spring Boot, Spring Web, Spring Data JPA y Jakarta Validation
- Maven Wrapper
- H2 (en memoria, etapa inicial)
- JUnit 5
- React (planificado)
