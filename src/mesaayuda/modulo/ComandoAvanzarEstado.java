package mesaayuda.modulo;

public class ComandoAvanzarEstado implements ComandoTicket {
	
	private final Ticket ticket;

    public ComandoAvanzarEstado(Ticket ticket) {
        this.ticket = ticket;
    }

	@Override
	public void ejecutarComando() {
		ticket.transicionarEstado();
		
	}

}
