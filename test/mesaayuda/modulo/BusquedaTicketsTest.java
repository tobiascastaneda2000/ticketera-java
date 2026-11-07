package mesaayuda.modulo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class BusquedaTicketsTest {

    private Usuario crearUsuario() {
        return new Usuario(1, "Ana Gómez", "ana@empresa.local");
    }

    private Ticket crearTicketEnGestor(String titulo) {
        GestorDeTickets gestor = GestorDeTickets.getInstance();

        gestor.crearTicket(
                titulo,
                "Descripción de prueba",
                Prioridad.MEDIA,
                Categoria.SOFTWARE,
                crearUsuario()
        );

        // crearTicket agrega al final del repositorio
        List<Ticket> tickets = gestor.getRepositorioDeTickets();
        return tickets.get(tickets.size() - 1);
    }

    // Busca un ticket existente por su ID
    @Test
    void buscaTicketPorId() {
        GestorDeTickets gestor = GestorDeTickets.getInstance();
        Ticket ticket = crearTicketEnGestor("Busqueda ID");

        Ticket encontrado = gestor.buscarTicketPorId(ticket.getId());

        assertEquals(ticket, encontrado);
    }

    // Un ID inexistente produce IllegalArgumentException
    @Test
    void idInexistenteLanzaExcepcion() {
        GestorDeTickets gestor = GestorDeTickets.getInstance();

        assertThrows(
                IllegalArgumentException.class,
                () -> gestor.buscarTicketPorId(-999)
        );
    }

    // La búsqueda por estado incluye el ticket que coincide
    @Test
    void buscaTicketsPorEstado() {
        GestorDeTickets gestor = GestorDeTickets.getInstance();
        Ticket ticket = crearTicketEnGestor("Busqueda estado");

        List<Ticket> encontrados =
                gestor.buscarTicketsPorEstado(Estado.ABIERTO);

        assertTrue(encontrados.contains(ticket));
    }

    // La búsqueda por técnico devuelve tickets asignados a esa instancia
    @Test
    void buscaTicketsPorTecnico() {
        GestorDeTickets gestor = GestorDeTickets.getInstance();
        Ticket ticket = crearTicketEnGestor("Busqueda técnico");
        Tecnico tecnico =
                new Tecnico(8, "Luis Pérez", NivelSoporte.N1);

        ticket.asignarA(tecnico);

        List<Ticket> encontrados =
                gestor.buscarTicketsPorTecnico(tecnico);

        assertTrue(encontrados.contains(ticket));
    }

    // No se puede buscar por un estado null
    @Test
    void estadoNullLanzaExcepcion() {
        GestorDeTickets gestor = GestorDeTickets.getInstance();

        assertThrows(
                IllegalArgumentException.class,
                () -> gestor.buscarTicketsPorEstado(null)
        );
    }

    // No se puede buscar por un técnico null
    @Test
    void tecnicoNullLanzaExcepcion() {
        GestorDeTickets gestor = GestorDeTickets.getInstance();

        assertThrows(
                IllegalArgumentException.class,
                () -> gestor.buscarTicketsPorTecnico(null)
        );
    }
}
