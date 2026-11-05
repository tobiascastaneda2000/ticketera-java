package mesaayuda.modulo;

// Imports de JUnit y colecciones para preparar cada escenario
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

class EstrategiaAsignacionTest {

    private Usuario crearSolicitante() {
        return new Usuario(1, "Ana Gómez", "ana@empresa.local");
    }

    private Tecnico crearTecnico(int id, String nombre) {
        return new Tecnico(id, nombre, NivelSoporte.N1);
    }

    private Ticket crearTicket(int id, String titulo) {
        return new Ticket(
                id,
                titulo,
                Prioridad.MEDIA,
                Categoria.SOFTWARE,
                crearSolicitante()
        );
    }

    // Prueba que la rotativa respete el orden y vuelva al primer técnico
    @Test
    void rotativaReparteEnOrdenYVuelveAlPrimero() {
        EstrategiaAsignacionRotativa estrategia =
                new EstrategiaAsignacionRotativa();

        Tecnico ana = crearTecnico(1, "Ana");
        Tecnico luis = crearTecnico(2, "Luis");
        Tecnico sol = crearTecnico(3, "Sol");

        List<Tecnico> tecnicos = Arrays.asList(ana, luis, sol);
        List<Ticket> tickets = Collections.emptyList();

        Ticket ticket1 = crearTicket(1, "Problema 1");
        Ticket ticket2 = crearTicket(2, "Problema 2");
        Ticket ticket3 = crearTicket(3, "Problema 3");
        Ticket ticket4 = crearTicket(4, "Problema 4");

        estrategia.asignarTecnico(ticket1, tecnicos, tickets);
        estrategia.asignarTecnico(ticket2, tecnicos, tickets);
        estrategia.asignarTecnico(ticket3, tecnicos, tickets);
        estrategia.asignarTecnico(ticket4, tecnicos, tickets);

        assertEquals(ana, ticket1.getTecnico());
        assertEquals(luis, ticket2.getTecnico());
        assertEquals(sol, ticket3.getTecnico());
        assertEquals(ana, ticket4.getTecnico());
    }

    // Prueba que la rotativa rechace una lista vacía de técnicos
    @Test
    void rotativaFallaCuandoNoHayTecnicos() {
        EstrategiaAsignacionRotativa estrategia =
                new EstrategiaAsignacionRotativa();

        Ticket ticket = crearTicket(1, "Problema sin técnico");

        assertThrows(
                IllegalStateException.class,
                () -> estrategia.asignarTecnico(
                        ticket,
                        Collections.emptyList(),
                        Collections.emptyList()
                )
        );
    }

    // Prueba que menor carga elija al técnico con menos tickets activos
    @Test
    void menorCargaEligeAlTecnicoConMenosTicketsActivos() {
        EstrategiaAsignacionMenorCarga estrategia =
                new EstrategiaAsignacionMenorCarga();

        Tecnico ana = crearTecnico(1, "Ana");
        Tecnico luis = crearTecnico(2, "Luis");

        Ticket activoDeAna1 = crearTicket(1, "Activo de Ana 1");
        Ticket activoDeAna2 = crearTicket(2, "Activo de Ana 2");
        Ticket activoDeLuis = crearTicket(3, "Activo de Luis");

        activoDeAna1.asignarA(ana);
        activoDeAna2.asignarA(ana);
        activoDeLuis.asignarA(luis);

        List<Ticket> ticketsExistentes =
                Arrays.asList(activoDeAna1, activoDeAna2, activoDeLuis);

        Ticket nuevoTicket = crearTicket(4, "Nuevo problema");

        estrategia.asignarTecnico(
                nuevoTicket,
                Arrays.asList(ana, luis),
                ticketsExistentes
        );

        assertEquals(luis, nuevoTicket.getTecnico());
    }

    // Prueba que RESUELTO y CERRADO no cuenten como carga activa
    @Test
    void menorCargaIgnoraTicketsResueltosYCerrados() {
        EstrategiaAsignacionMenorCarga estrategia =
                new EstrategiaAsignacionMenorCarga();

        Tecnico ana = crearTecnico(1, "Ana");
        Tecnico luis = crearTecnico(2, "Luis");

        Ticket resueltoDeAna = crearTicket(1, "Resuelto de Ana");
        resueltoDeAna.asignarA(ana);
        resueltoDeAna.transicionarEstado();
        resueltoDeAna.transicionarEstado();

        Ticket cerradoDeLuis = crearTicket(2, "Cerrado de Luis");
        cerradoDeLuis.asignarA(luis);
        cerradoDeLuis.transicionarEstado();
        cerradoDeLuis.transicionarEstado();
        cerradoDeLuis.transicionarEstado();

        List<Ticket> ticketsExistentes =
                Arrays.asList(resueltoDeAna, cerradoDeLuis);

        Ticket nuevoTicket = crearTicket(3, "Nuevo problema");

        estrategia.asignarTecnico(
                nuevoTicket,
                Arrays.asList(ana, luis),
                ticketsExistentes
        );

        // Ambos tienen cero tickets activos; debe ganar Ana por estar primero
        assertEquals(ana, nuevoTicket.getTecnico());
    }

    // Prueba que, si hay empate de carga, gane el primero de la lista
    @Test
    void menorCargaEnEmpateEligeAlPrimero() {
        EstrategiaAsignacionMenorCarga estrategia =
                new EstrategiaAsignacionMenorCarga();

        Tecnico ana = crearTecnico(1, "Ana");
        Tecnico luis = crearTecnico(2, "Luis");

        Ticket nuevoTicket = crearTicket(1, "Nuevo problema");

        estrategia.asignarTecnico(
                nuevoTicket,
                Arrays.asList(ana, luis),
                Collections.emptyList()
        );

        assertEquals(ana, nuevoTicket.getTecnico());
    }

    // Prueba que menor carga rechace una lista vacía de técnicos
    @Test
    void menorCargaFallaCuandoNoHayTecnicos() {
        EstrategiaAsignacionMenorCarga estrategia =
                new EstrategiaAsignacionMenorCarga();

        Ticket ticket = crearTicket(1, "Problema sin técnico");

        assertThrows(
                IllegalStateException.class,
                () -> estrategia.asignarTecnico(
                        ticket,
                        Collections.emptyList(),
                        Collections.emptyList()
                )
        );
    }
}