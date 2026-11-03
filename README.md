# Mesa de ayuda

Aplicación de consola en Java para gestionar solicitudes de soporte técnico. El proyecto sirve como práctica progresiva de programación orientada a objetos y patrones de diseño vistos en el curso de Fundación Pescar.

## Enunciado

Una empresa necesita registrar problemas reportados por sus usuarios, asignarlos a técnicos, conversar sobre cada caso y seguir su ciclo de vida. Cada ticket tiene un identificador, título, descripción, prioridad, categoría, fecha de creación, solicitante, técnico opcional, comentarios y estado.

La aplicación debe permitir crear y consultar tickets, asignarlos, agregar comentarios y cambiar su estado con reglas válidas. A medida que se incorporen patrones, el sistema debe poder elegir cómo asignar técnicos, notificar cambios, decorar tickets con funcionalidades opcionales y guardar/restaurar borradores.

## Cómo leer el proyecto

- `Usuario`, `Tecnico`, `Comentario` y `Ticket` son clases del dominio: representan conceptos del problema.
- `enum` como `Prioridad` y `Categoria` limitan valores a opciones conocidas.
- `Ticket` protege sus datos y valida su construcción; su lista de comentarios se expone como solo lectura.
- `GestorDeTickets` centraliza la creación y guarda los tickets en memoria.
- `Main` contiene una demostración ejecutable de los comportamientos actuales.

## Patrones practicados

### Singleton — `GestorDeTickets`

`getInstance()` entrega la instancia única del gestor. El constructor es privado para evitar crear gestores por fuera. El repositorio se inicializa al construir el gestor y se expone como lista de solo lectura.

### State — estados de `Ticket`

El estado actual del ticket es un objeto que implementa `EstadoTicket`. Cada estado decide qué significa avanzar o reabrir. La ruta normal es `ABIERTO → EN_CURSO → RESUELTO → CERRADO`; desde `RESUELTO` o `CERRADO` se puede reabrir. Una acción inválida lanza `IllegalStateException`.

Para seguir el flujo, mirá `Ticket.transicionarEstado()`: delega en el estado actual. Luego abrí `EstadoAbierto` y seguí cómo crea el siguiente estado. Así, las reglas quedan repartidas entre estados en vez de crecer en una cadena de `if/else`.

## Guía para ejecutarlo

Es un proyecto Java sencillo importable en Eclipse como proyecto existente. Ejecutá `mesaayuda.app.Main`.

También se puede compilar desde una terminal con un JDK instalado:

```bash
javac -d bin src/mesaayuda/modulo/*.java src/mesaayuda/app/Main.java
java -cp bin mesaayuda.app.Main
```

No necesita dependencias externas por ahora.

## Ruta de práctica

1. **Modelo y encapsulamiento (hecho):** entidades, atributos privados, constructores y asociaciones.
2. **Singleton y State (hecho):** gestor único y ciclo de vida explícito del ticket.
3. **Strategy (siguiente):** reemplazá el `enum Estrategia` por estrategias intercambiables para asignar un ticket: rotación entre técnicos y técnico con menos tickets abiertos. El gestor debería usar una estrategia sin conocer sus detalles.
4. **Observer:** avisá al solicitante y al técnico cuando cambie el estado, sin acoplar las notificaciones a `Ticket`.
5. **Factory Method:** centralizá la creación de tickets según categoría o canal de ingreso.
6. **Decorator:** agregá capacidades opcionales como seguimiento prioritario o SLA sin llenar `Ticket` de banderas.
7. **Command:** representá acciones como asignar, resolver y reabrir para poder ejecutarlas desde una interfaz y eventualmente deshacerlas.
8. **Memento y Builder:** guardá/restaurá borradores y facilitá la construcción de tickets complejos sin constructores largos.

En cada etapa, intentá primero explicar el problema en tus palabras, dibujá las clases principales y recién después codificá. No hace falta memorizar cada línea: buscá identificar quién crea objetos, quién delega comportamiento y quién conoce a quién.

## Ejercicios actuales

- Ejecutá `Main` y seguí la salida del caso válido y las dos validaciones.
- Agregá una transición `ABIERTO → EN_CURSO → RESUELTO → CERRADO` y consultá `getEstado()` en cada paso.
- Probá avanzar un ticket cerrado y confirmá que se rechaza.
- Reabrí uno resuelto y uno cerrado; verificá que vuelven a `ABIERTO`.
- Usá `Usuario.reportarProblema(...)` y luego consultá `GestorDeTickets.getInstance().getRepositorioDeTickets()`.
- Dibujá el State en UML: `Ticket` mantiene un `EstadoTicket` y delega en sus implementaciones concretas.

## Alcance actual

Los datos viven en memoria y se pierden al cerrar el programa. Todavía no hay interfaz gráfica, base de datos ni sistema de autenticación; primero se practica el diseño del dominio.
