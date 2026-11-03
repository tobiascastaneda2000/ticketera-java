package mesaayuda.modulo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GestorDeTickets {

	private static GestorDeTickets instancia; // Variable estática para almacenar la instancia
	
	private final List<Ticket> repositorioDeTickets = new ArrayList<>();
	private int siguienteId = 1;
	
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


}
