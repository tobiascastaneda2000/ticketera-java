package mesaayuda.modulo;

public class ComandoAgregarComentario implements ComandoTicket{
	
	private final Ticket ticket;
	private final Comentario comentario;
	
	public ComandoAgregarComentario(Ticket ticket, Comentario comentario) {
		this.ticket = ticket;
		this.comentario = comentario;
	}

	@Override
	public void ejecutarComando() {
		ticket.agregarComentario(comentario);
		
	}

}
