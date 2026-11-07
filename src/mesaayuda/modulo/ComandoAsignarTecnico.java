package mesaayuda.modulo;

public class ComandoAsignarTecnico implements ComandoTicket{
	

	private final Ticket ticket;
	private final Tecnico tecnico;
	
	public ComandoAsignarTecnico(Ticket ticket, Tecnico tecnico) {
		this.ticket = ticket;
		this.tecnico = tecnico;
	}
	
	@Override
	public void ejecutarComando() {
		ticket.asignarA(tecnico);
		
	}

}
