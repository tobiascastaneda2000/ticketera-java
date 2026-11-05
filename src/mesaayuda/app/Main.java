package mesaayuda.app;

// Imports del dominio y de las estrategias que se muestran en la demo
import java.util.Arrays;
import java.util.List;

import mesaayuda.modulo.Categoria;
import mesaayuda.modulo.EstrategiaAsignacionMenorCarga;
import mesaayuda.modulo.EstrategiaAsignacionRotativa;
import mesaayuda.modulo.GestorDeTickets;
import mesaayuda.modulo.NivelSoporte;
import mesaayuda.modulo.Prioridad;
import mesaayuda.modulo.Tecnico;
import mesaayuda.modulo.Ticket;
import mesaayuda.modulo.Usuario;

public class Main {

    public static void main(String[] args) {
        Usuario solicitante = new Usuario(1, "Ana Gómez", "ana@empresa.local");

        Tecnico luis = new Tecnico(1, "Luis Pérez", NivelSoporte.N1);
        Tecnico sol = new Tecnico(2, "Sol Díaz", NivelSoporte.N1);
        Tecnico marco = new Tecnico(3, "Marco Ruiz", NivelSoporte.N2);

        List<Tecnico> tecnicos = Arrays.asList(luis, sol, marco);
        GestorDeTickets gestor = GestorDeTickets.getInstance();

        // Demo de Strategy: la regla rotativa reparte los tickets en orden
        gestor.setEstrategiaAsignacion(new EstrategiaAsignacionRotativa());

        gestor.crearTicket("No puedo iniciar sesión", "La cuenta está bloqueada",
                Prioridad.ALTA, Categoria.CUENTA, solicitante);
        gestor.crearTicket("La computadora no enciende", "No prende",
                Prioridad.MEDIA, Categoria.HARDWARE, solicitante);
        gestor.crearTicket("No funciona el correo", "No puedo enviar mensajes",
                Prioridad.MEDIA, Categoria.SOFTWARE, solicitante);
        gestor.crearTicket("Problema con la red", "Se corta la conexión",
                Prioridad.BAJA, Categoria.RED, solicitante);

        List<Ticket> tickets = gestor.getRepositorioDeTickets();

        // Asignamos los cuatro últimos tickets: Luis, Sol, Marco y nuevamente Luis
        int primerTicketDeEstaDemo = tickets.size() - 4;
        for (int i = primerTicketDeEstaDemo; i < tickets.size(); i++) {
            gestor.asignarTicket(tickets.get(i), tecnicos);
            System.out.println(tickets.get(i));
        }

        // Demo de Strategy: la regla de menor carga elige al técnico con menos tickets activos
        gestor.setEstrategiaAsignacion(new EstrategiaAsignacionMenorCarga());

        gestor.crearTicket("No abre el sistema de ventas", "Aparece un error",
                Prioridad.ALTA, Categoria.SOFTWARE, solicitante);

        Ticket ticketParaMenorCarga =
                gestor.getRepositorioDeTickets().get(
                        gestor.getRepositorioDeTickets().size() - 1
                );

        gestor.asignarTicket(ticketParaMenorCarga, tecnicos);
        System.out.println("Asignado con menor carga: " + ticketParaMenorCarga);
    }
}