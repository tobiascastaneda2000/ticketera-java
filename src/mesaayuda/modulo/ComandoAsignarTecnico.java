package mesaayuda.modulo;

import java.util.Objects;

public class ComandoAsignarTecnico implements ComandoTicket{
	

	private final Ticket ticket;
	private final Tecnico tecnico;
	
	public ComandoAsignarTecnico(Ticket ticket, Tecnico tecnico) {
		
		this.ticket = Objects.requireNonNull(ticket, "Ticket no puede ser null");
		this.tecnico = Objects.requireNonNull(tecnico, "Tecnico no puede ser null");;
	}
	
	@Override
	public void ejecutarComando() {
		
		if(ticket.getEstado() == Estado.CERRADO) {
			throw new IllegalStateException("El estado del ticket no peude ser CERRADO");
		}
		
		ticket.asignarA(tecnico);
		
	}

}
