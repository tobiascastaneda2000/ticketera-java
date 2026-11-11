package mesaayuda.modulo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ComandoAsignarTecnicoTest {

    private Ticket crearTicket() {
        Usuario usuario = new Usuario(1, "Ana Gómez", "ana@empresa.local");

        return new Ticket(1, "Problema de acceso", Prioridad.ALTA, Categoria.CUENTA, usuario);
    }

    // El comando asigna el técnico y queda registrado en el gestor
    @Test
    void asignarTecnicoSeEjecutaYSeGuardaEnHistorial() {
        GestorDeTickets gestor = GestorDeTickets.getInstance();
        int cantidadInicial = gestor.getHistorialComandos().size();

        Ticket ticket = crearTicket();
        Tecnico tecnico = new Tecnico(1, "Luis Pérez", NivelSoporte.N1);
        ComandoAsignarTecnico comando = new ComandoAsignarTecnico(ticket, tecnico);

        gestor.ejecutarComando(comando);

        assertEquals(tecnico, ticket.getTecnico());
        assertEquals(cantidadInicial + 1, gestor.getHistorialComandos().size());
    }

    // Un ticket cerrado no puede reasignarse ni agrega el comando al historial
    @Test
    void noPermiteAsignarTecnicoATicketCerrado() {
        GestorDeTickets gestor = GestorDeTickets.getInstance();
        int cantidadInicial = gestor.getHistorialComandos().size();

        Ticket ticket = crearTicket();
        ticket.transicionarEstado(); // ABIERTO -> EN_CURSO
        ticket.transicionarEstado(); // EN_CURSO -> RESUELTO
        ticket.transicionarEstado(); // RESUELTO -> CERRADO

        Tecnico tecnico = new Tecnico(1, "Luis Pérez", NivelSoporte.N1);
        ComandoAsignarTecnico comando = new ComandoAsignarTecnico(ticket, tecnico);

        assertThrows(IllegalStateException.class, () -> gestor.ejecutarComando(comando));

        assertEquals(cantidadInicial, gestor.getHistorialComandos().size());
    }

    // El constructor rechaza un técnico null
    @Test
    void tecnicoNullNoEsValido() {
        Ticket ticket = crearTicket();

        assertThrows(NullPointerException.class, () -> new ComandoAsignarTecnico(ticket, null));
    }
}