package mesaayuda.modulo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ComandoTicketTest {

    private Ticket crearTicket() {
        Usuario usuario = new Usuario(1, "Ana Gómez", "ana@empresa.local");
        return new Ticket(1, "No puedo iniciar sesión", Prioridad.ALTA, Categoria.CUENTA, usuario);
    }

    // Ejecutar el comando avanza el ticket de ABIERTO a EN_CURSO
    @Test
    void comandoAvanzarCambiaElEstado() {
        Ticket ticket = crearTicket();
        ComandoTicket comando = new ComandoAvanzarEstado(ticket);

        comando.ejecutarComando();

        assertEquals(Estado.EN_CURSO, ticket.getEstado());
    }

    // Ejecutar el comando reabrir cambia un ticket resuelto a ABIERTO
    @Test
    void comandoReabrirVuelveAAbierto() {
        Ticket ticket = crearTicket();
        ticket.transicionarEstado(); // ABIERTO -> EN_CURSO
        ticket.transicionarEstado(); // EN_CURSO -> RESUELTO

        ComandoTicket comando = new ComandoReabrirTicket(ticket);
        comando.ejecutarComando();

        assertEquals(Estado.ABIERTO, ticket.getEstado());
    }

    // Un comando debe propagar el error si la transición no está permitida
    @Test
    void comandoAvanzarFallaSiElTicketEstaCerrado() {
        Ticket ticket = crearTicket();
        ticket.transicionarEstado(); // ABIERTO -> EN_CURSO
        ticket.transicionarEstado(); // EN_CURSO -> RESUELTO
        ticket.transicionarEstado(); // RESUELTO -> CERRADO

        ComandoTicket comando = new ComandoAvanzarEstado(ticket);

        assertThrows(IllegalStateException.class, comando::ejecutarComando);
    }

    // El comando asigna al técnico indicado al ticket
    @Test
    void comandoAsignarTecnicoAsignaElTecnico() {
        Ticket ticket = crearTicket();
        Tecnico tecnico = new Tecnico(2, "Luis Pérez", NivelSoporte.N1);
        ComandoTicket comando = new ComandoAsignarTecnico(ticket, tecnico);

        comando.ejecutarComando();

        assertEquals(tecnico, ticket.getTecnico());
    }
}