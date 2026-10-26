package mesaayuda.app;

import mesaayuda.modulo.Categoria;
import mesaayuda.modulo.Comentario;
import mesaayuda.modulo.NivelSoporte;
import mesaayuda.modulo.Prioridad;
import mesaayuda.modulo.Tecnico;
import mesaayuda.modulo.Ticket;
import mesaayuda.modulo.Usuario;

// Prueba de aceptación del modelo: tres comprobaciones
public class Main {

    public static void main(String[] args) {
        Usuario ana = new Usuario(1, "Ana Gómez", "ana@empresa.local");
        Tecnico luis = new Tecnico(1, "Luis Pérez", NivelSoporte.N1);

        // 1. Caso válido
        Ticket ticket = new Ticket(1, "No puedo iniciar sesión", "Dice que mi cuenta está bloqueada",
                Prioridad.ALTA, Categoria.CUENTA, ana);
        ticket.asignarA(luis);
        ticket.agregarComentario(new Comentario("Luis Pérez", "Reviso la cuenta en AD."));
        System.out.println(ticket);
        ticket.getComentarios().forEach(System.out::println);

        // 2. Título vacío: tiene que lanzar IllegalArgumentException
        try {
            new Ticket(2, "  ", "", Prioridad.BAJA, Categoria.OTROS, ana);
            System.out.println("ERROR: debió fallar con título vacío");
        } catch (IllegalArgumentException e) {
            System.out.println("OK título vacío: " + e.getMessage());
        }

        // 3. La lista de comentarios no se puede modificar desde afuera
        try {
            ticket.getComentarios().clear();
            System.out.println("ERROR: la lista no debería poder modificarse");
        } catch (UnsupportedOperationException e) {
            System.out.println("OK lista de solo lectura");
        }
    }
}