package mesaayuda.modulo;

public class ComandoReabrirTicket implements ComandoTicket {
	
	private final Ticket ticket;

    public ComandoReabrirTicket(Ticket ticket) {
        this.ticket = ticket;
    }

	@Override
	public void ejecutarComando() {
		ticket.reabrir();
		
	}

}