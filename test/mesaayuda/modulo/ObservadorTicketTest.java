package mesaayuda.modulo;

// Comprobaciones de JUnit y colecciones para guardar los eventos
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class ObservadorTicketTest {

    // Observador de prueba: registra cada mensaje para poder verificarlo
    private static class ObservadorDePrueba implements ObservadorTicket {
        private final List<String> eventos = new ArrayList<>();

        @Override
        public void actualizar(Ticket ticket, String evento) {
            eventos.add(evento);
        }
    }

    // Crea un ticket de ejemplo para usar en las pruebas
    private Ticket crearTicket() {
        Usuario solicitante =
                new Usuario(1, "Ana Gómez", "ana@empresa.local");

        return new Ticket(
                1,
                "No puedo iniciar sesión",
                Prioridad.ALTA,
                Categoria.CUENTA,
                solicitante
        );
    }

    // Comprueba que asignar un técnico avise a los observadores
    @Test
    void asignarTecnicoNotificaAObservadores() {
        Ticket ticket = crearTicket();
        ObservadorDePrueba observador = new ObservadorDePrueba();
        Tecnico tecnico = new Tecnico(1, "Luis Pérez", NivelSoporte.N1);

        ticket.agregarObservador(observador);
        ticket.asignarA(tecnico);

        assertEquals(1, observador.eventos.size());
        assertTrue(observador.eventos.get(0).contains("Luis Pérez"));
    }

    // Comprueba que avanzar el estado avise con el nuevo estado
    @Test
    void avanzarEstadoNotificaElNuevoEstado() {
        Ticket ticket = crearTicket();
        ObservadorDePrueba observador = new ObservadorDePrueba();

        ticket.agregarObservador(observador);
        ticket.transicionarEstado();

        assertEquals(1, observador.eventos.size());
        assertTrue(observador.eventos.get(0).contains("EN_CURSO"));
    }

    // Comprueba que reabrir un ticket también genere una notificación
    @Test
    void reabrirNotificaElNuevoEstado() {
        Ticket ticket = crearTicket();
        ObservadorDePrueba observador = new ObservadorDePrueba();

        ticket.agregarObservador(observador);

        // ABIERTO -> EN_CURSO -> RESUELTO
        ticket.transicionarEstado();
        ticket.transicionarEstado();

        // La lista contiene los dos avisos de las transiciones previas
        observador.eventos.clear();

        ticket.reabrir();

        assertEquals(1, observador.eventos.size());
        assertTrue(observador.eventos.get(0).contains("ABIERTO"));
    }

   
    
 // Al quitar un observador, deja de recibir los eventos siguientes
    @Test
    void observadorQuitadoNoRecibeNotificaciones() {
        Ticket ticket = crearTicket();
        ObservadorDePrueba observador = new ObservadorDePrueba();

        ticket.agregarObservador(observador);
        ticket.quitarObservador(observador);

        ticket.transicionarEstado();

        assertTrue(observador.eventos.isEmpty());
    }
}