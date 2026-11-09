package mesaayuda.modulo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

class ConsultasTicketsTest {

    private Usuario crearUsuario() {
        return new Usuario(1, "Ana Gómez", "ana@empresa.local");
    }

    private Ticket crearTicket(
            String titulo,
            String descripcion,
            Prioridad prioridad) {

        GestorDeTickets gestor = GestorDeTickets.getInstance();

        gestor.crearTicket(
                titulo,
                descripcion,
                prioridad,
                Categoria.SOFTWARE,
                crearUsuario()
        );

        List<Ticket> repositorio = gestor.getRepositorioDeTickets();
        return repositorio.get(repositorio.size() - 1);
    }

    // Busca texto en el título sin distinguir mayúsculas
    @Test
    void buscaPorTextoEnTituloSinDistinguirMayusculas() {
        GestorDeTickets gestor = GestorDeTickets.getInstance();
        Ticket ticket = crearTicket(
                "Error al iniciar sesión",
                "La cuenta no abre",
                Prioridad.ALTA
        );

        List<Ticket> resultados = gestor.buscarTicketsPorTexto("SESIÓN");

        assertTrue(resultados.contains(ticket));
    }

    // Busca texto en la descripción
    @Test
    void buscaPorTextoEnDescripcion() {
        GestorDeTickets gestor = GestorDeTickets.getInstance();
        Ticket ticket = crearTicket(
                "Problema de acceso",
                "La VPN no conecta",
                Prioridad.MEDIA
        );

        List<Ticket> resultados = gestor.buscarTicketsPorTexto("vpn");

        assertTrue(resultados.contains(ticket));
    }

    // Busca texto en los comentarios asociados al ticket
    @Test
    void buscaPorTextoEnComentarios() {
        GestorDeTickets gestor = GestorDeTickets.getInstance();
        Ticket ticket = crearTicket(
                "Problema técnico",
                "La computadora funciona",
                Prioridad.BAJA
        );
        ticket.agregarComentario(
                new Comentario("Luis", "Revisé el disco rígido")
        );

        List<Ticket> resultados = gestor.buscarTicketsPorTexto("disco");

        assertTrue(resultados.contains(ticket));
    }

    // Rechaza texto vacío o formado solo por espacios
    @Test
    void textoVacioLanzaExcepcion() {
        GestorDeTickets gestor = GestorDeTickets.getInstance();

        assertThrows(
                IllegalArgumentException.class,
                () -> gestor.buscarTicketsPorTexto("   ")
        );
    }

    // Encuentra los tickets que no tienen técnico asignado
    @Test
    void buscaTicketsSinTecnico() {
        GestorDeTickets gestor = GestorDeTickets.getInstance();
        Ticket ticket = crearTicket(
                "Ticket sin asignar",
                "Esperando técnico",
                Prioridad.MEDIA
        );

        assertTrue(gestor.buscarTicketsSinTecnico().contains(ticket));
    }

    // Cuenta cuántos tickets hay en un estado
    @Test
    void cuentaTicketsPorEstado() {
        GestorDeTickets gestor = GestorDeTickets.getInstance();
        Ticket ticket = crearTicket(
                "Ticket abierto",
                "Descripción",
                Prioridad.MEDIA
        );

        assertEquals(
                1,
                gestor.buscarTicketsPorEstado(Estado.ABIERTO)
                        .stream()
                        .filter(encontrado -> encontrado == ticket)
                        .count()
        );
    }

    // El estado null no es válido para contar
    @Test
    void estadoNullNoSePuedeContar() {
        GestorDeTickets gestor = GestorDeTickets.getInstance();

        assertThrows(
                IllegalArgumentException.class,
                () -> gestor.contarTicketsPorEstado(null)
        );
    }
}