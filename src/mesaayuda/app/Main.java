package mesaayuda.app;

// Colecciones para armar los equipos y recorrer tickets
import java.util.Arrays;
import java.util.List;

import mesaayuda.modulo.Categoria;
import mesaayuda.modulo.ComandoAsignarTecnico;
import mesaayuda.modulo.ComandoAvanzarEstado;
import mesaayuda.modulo.ComandoReabrirTicket;
import mesaayuda.modulo.ComandoTicket;
import mesaayuda.modulo.EstrategiaAsignacionMenorCarga;
import mesaayuda.modulo.EstrategiaAsignacionRotativa;
import mesaayuda.modulo.GestorDeTickets;
import mesaayuda.modulo.NivelSoporte;
import mesaayuda.modulo.NotificadorTecnico;
import mesaayuda.modulo.NotificadorUsuario;
import mesaayuda.modulo.ObservadorTicket;
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

        // Observer: creamos objetos que van a reaccionar a los cambios del ticket
        ObservadorTicket notificadorUsuario = new NotificadorUsuario();
        ObservadorTicket notificadorTecnico = new NotificadorTecnico();

        // Strategy: la regla rotativa reparte los tickets entre los técnicos
        gestor.setEstrategiaAsignacion(new EstrategiaAsignacionRotativa());

        gestor.crearTicket("No puedo iniciar sesión", "La cuenta está bloqueada", Prioridad.ALTA, Categoria.CUENTA,
                solicitante);
        gestor.crearTicket("La computadora no enciende", "No prende", Prioridad.MEDIA, Categoria.HARDWARE, solicitante);
        gestor.crearTicket("No funciona el correo", "No puedo enviar mensajes", Prioridad.MEDIA, Categoria.SOFTWARE,
                solicitante);
        gestor.crearTicket("Problema con la red", "Se corta la conexión", Prioridad.BAJA, Categoria.RED, solicitante);

        List<Ticket> tickets = gestor.getRepositorioDeTickets();
        int primerTicketDeEstaDemo = tickets.size() - 4;

        // Registramos observadores y asignamos los cuatro tickets
        for (int i = primerTicketDeEstaDemo; i < tickets.size(); i++) {
            Ticket ticket = tickets.get(i);
            ticket.agregarObservador(notificadorUsuario);
            ticket.agregarObservador(notificadorTecnico);

            gestor.asignarTicket(ticket, tecnicos);
            System.out.println(ticket);
        }

        // Observer: el cambio de estado también genera una notificación
        Ticket ticketParaAvanzar = tickets.get(primerTicketDeEstaDemo);
        ComandoTicket comando = new ComandoAvanzarEstado(ticketParaAvanzar);
        gestor.ejecutarComando(comando);

        // Strategy: cambiamos la regla para usar menor carga
        gestor.setEstrategiaAsignacion(new EstrategiaAsignacionMenorCarga());

        gestor.crearTicket("No abre el sistema de ventas", "Aparece un error", Prioridad.ALTA, Categoria.SOFTWARE,
                solicitante);

        Ticket ticketParaMenorCarga = gestor.getRepositorioDeTickets().get(gestor.getRepositorioDeTickets().size() - 1);

        ticketParaMenorCarga.agregarObservador(notificadorUsuario);
        ticketParaMenorCarga.agregarObservador(notificadorTecnico);

        gestor.asignarTicket(ticketParaMenorCarga, tecnicos);
        System.out.println("Asignado con menor carga: " + ticketParaMenorCarga);

        // ticketParaAvanzar ya está en EN_CURSO.
        // Avanzamos a RESUELTO.
        ComandoTicket comandoResolver = new ComandoAvanzarEstado(ticketParaAvanzar);
        gestor.ejecutarComando(comandoResolver);

        // Avanzamos de RESUELTO a CERRADO.
        ComandoTicket comandoCerrar = new ComandoAvanzarEstado(ticketParaAvanzar);
        gestor.ejecutarComando(comandoCerrar);

        // Ahora se puede reabrir porque está CERRADO.
        ComandoTicket comandoReabrir = new ComandoReabrirTicket(ticketParaAvanzar);
        gestor.ejecutarComando(comandoReabrir);

        System.out.println("Estado final: " + ticketParaAvanzar.getEstado());
        System.out.println("Comandos ejecutados: " + gestor.getHistorialComandos().size());

        // Command: el comando representa la acción de asignar un técnico
        gestor.crearTicket("Configurar acceso a la VPN", "El usuario necesita conectarse desde su casa",
                Prioridad.MEDIA, Categoria.RED, solicitante);

        Ticket ticketAsignadoPorComando = gestor.getRepositorioDeTickets()
                .get(gestor.getRepositorioDeTickets().size() - 1);

        ticketAsignadoPorComando.agregarObservador(notificadorUsuario);
        ticketAsignadoPorComando.agregarObservador(notificadorTecnico);

        ComandoTicket comandoAsignar = new ComandoAsignarTecnico(ticketAsignadoPorComando, luis);

        gestor.ejecutarComando(comandoAsignar);

        System.out.println("Asignación ejecutada con Command: " + ticketAsignadoPorComando);
        System.out.println("Comandos registrados: " + gestor.getHistorialComandos().size());

        // Buscamos el ticket asignado por su técnico
        List<Ticket> ticketsDeLuis = gestor.buscarTicketsPorTecnico(luis);

        System.out.println("Tickets asignados a Luis:");
        ticketsDeLuis.forEach(System.out::println);

        // Buscamos e imprimimos un ticket por su ID
        Ticket encontrado = gestor.buscarTicketPorId(ticketParaAvanzar.getId());
        System.out.println("Ticket encontrado: " + encontrado);

        // Buscamos e imprimimos los tickets de prioridad alta
        List<Ticket> ticketsAlta = gestor.buscarTicketsPorPrioridad(Prioridad.ALTA);

        System.out.println("Tickets de prioridad alta:");
        ticketsAlta.forEach(System.out::println);

        // Consultas: buscar tickets por texto
        List<Ticket> resultadosTexto = gestor.buscarTicketsPorTexto("sesión");

        System.out.println("Resultados para el texto 'sesión':");
        resultadosTexto.forEach(System.out::println);

        // Consultas: listar tickets sin técnico
        List<Ticket> sinTecnico = gestor.buscarTicketsSinTecnico();

        System.out.println("Tickets sin técnico:");
        sinTecnico.forEach(System.out::println);

        // Consultas: contar tickets abiertos
        int cantidadAbiertos = gestor.contarTicketsPorEstado(mesaayuda.modulo.Estado.ABIERTO);

        System.out.println("Cantidad de tickets abiertos: " + cantidadAbiertos);
    }
}