package mesaayuda.modulo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

class HistorialComandosTest {

    private Ticket crearTicket() {
        Usuario usuario = new Usuario(1, "Ana Gómez", "ana@empresa.local");

        return new Ticket(1, "No puedo iniciar sesión", Prioridad.ALTA, Categoria.CUENTA, usuario);
    }

    // Un comando exitoso cambia el ticket y queda registrado en el historial
    @Test
    void comandoExitosoSeAgregaAlHistorial() {
        GestorDeTickets gestor = GestorDeTickets.getInstance();
        int cantidadInicial = gestor.getHistorialComandos().size();

        Ticket ticket = crearTicket();
        ComandoTicket comando = new ComandoAvanzarEstado(ticket);

        gestor.ejecutarComando(comando);

        assertEquals(Estado.EN_CURSO, ticket.getEstado());
        assertEquals(cantidadInicial + 1, gestor.getHistorialComandos().size());
        assertSame(comando, gestor.getHistorialComandos().get(cantidadInicial));
    }

    // Un comando que falla no debe aparecer en el historial
    @Test
    void comandoFallidoNoSeAgregaAlHistorial() {
        GestorDeTickets gestor = GestorDeTickets.getInstance();
        int cantidadInicial = gestor.getHistorialComandos().size();

        Ticket ticket = crearTicket();

        // Cerramos el ticket: ABIERTO -> EN_CURSO -> RESUELTO -> CERRADO
        ticket.transicionarEstado();
        ticket.transicionarEstado();
        ticket.transicionarEstado();

        ComandoTicket comandoInvalido = new ComandoAvanzarEstado(ticket);

        assertThrows(IllegalStateException.class, () -> gestor.ejecutarComando(comandoInvalido));

        assertEquals(cantidadInicial, gestor.getHistorialComandos().size());
    }

    // La lista del historial se puede leer, pero no modificar desde afuera
    @Test
    void historialNoSePuedeModificarDesdeAfuera() {
        GestorDeTickets gestor = GestorDeTickets.getInstance();
        List<ComandoTicket> historial = gestor.getHistorialComandos();

        assertThrows(UnsupportedOperationException.class, () -> historial.clear());
    }
}