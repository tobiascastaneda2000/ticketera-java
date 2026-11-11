package mesaayuda.modulo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GestorDeTickets {

    private static GestorDeTickets instancia; // Variable estática para almacenar la instancia

    private final List<Ticket> repositorioDeTickets = new ArrayList<>();
    private int siguienteId = 1;
    private EstrategiaAsignacion estrategiaAsignacion;

    private final List<ComandoTicket> historialComandos = new ArrayList<>();

    // Constructor privado
    private GestorDeTickets() {
    }

    // Método estático para obtener la instancia única
    public static synchronized GestorDeTickets getInstance() {
        if (instancia == null) {
            instancia = new GestorDeTickets();
        }
        return instancia;
    }

    public List<Ticket> getRepositorioDeTickets() {
        return Collections.unmodifiableList(repositorioDeTickets);
    }

    public void crearTicket(String t, String d, Prioridad p, Categoria c, Usuario u) {
        Ticket ticket = new Ticket(siguienteId++, t, d, p, c, u);
        this.repositorioDeTickets.add(ticket);
    }

    public void setEstrategiaAsignacion(EstrategiaAsignacion estrategia) {
        if (estrategia == null) {
            throw new IllegalArgumentException("La estrategia no puede ser null");
        }
        this.estrategiaAsignacion = estrategia;
    }

    public void asignarTicket(Ticket ticket, List<Tecnico> tecnicos) {
        if (estrategiaAsignacion == null) {
            throw new IllegalStateException("Primero elegí una estrategia de asignación");
        }

        estrategiaAsignacion.asignarTecnico(ticket, tecnicos, repositorioDeTickets);
    }

    public void ejecutarComando(ComandoTicket comando) {
        if (comando == null) {
            throw new IllegalArgumentException("El comando no puede ser null");
        }

        // Si esto falla, la línea siguiente no se ejecuta y no se guarda el comando
        comando.ejecutarComando();
        historialComandos.add(comando);
    }

    public List<ComandoTicket> getHistorialComandos() {
        return Collections.unmodifiableList(historialComandos);
    }

    public Ticket buscarTicketPorId(int id) {
        return repositorioDeTickets.stream().filter(ticket -> ticket.getId() == id).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No existe un ticket con ID " + id));
    }

    public List<Ticket> buscarTicketsPorEstado(Estado estado) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado no puede ser null");
        }

        // Stream.toList() devuelve una lista que no se puede modificar
        return repositorioDeTickets.stream().filter(ticket -> ticket.getEstado() == estado).toList();
    }

    public List<Ticket> buscarTicketsPorTecnico(Tecnico tecnico) {
        if (tecnico == null) {
            throw new IllegalArgumentException("El técnico no puede ser null");
        }

        return repositorioDeTickets.stream().filter(ticket -> ticket.getTecnico() == tecnico).toList();
    }

    public List<Ticket> buscarTicketsPorPrioridad(Prioridad prioridad) {
        if (prioridad == null) {
            throw new IllegalArgumentException("La prioridad no puede ser null");
        }

        return repositorioDeTickets.stream().filter(ticket -> ticket.getPrioridad() == prioridad).toList();
    }

    // Busqueda por texto
    public List<Ticket> buscarTicketsPorTexto(String texto) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException("El texto no puede ser null");
        }

        return this.repositorioDeTickets.stream()
                .filter(ticket -> ticket.getTitulo().toLowerCase().contains(texto.toLowerCase())
                        || ticket.getDescripcion().toLowerCase().contains(texto.toLowerCase())
                        || ticket.getComentarios().stream().anyMatch(
                                comentario -> comentario.getTexto().toLowerCase().contains(texto.toLowerCase())))
                .toList();
    }

    public List<Ticket> buscarTicketsSinTecnico() {
        return repositorioDeTickets.stream().filter(ticket -> ticket.getTecnico() == null).toList();
    }

    public int contarTicketsPorEstado(Estado estado) {
        if (estado == null) {
            throw new IllegalArgumentException("El estado no puede ser null");
        }
        return this.buscarTicketsPorEstado(estado).size();
    }

}
