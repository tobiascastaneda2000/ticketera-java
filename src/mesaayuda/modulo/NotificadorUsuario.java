package mesaayuda.modulo;

public class NotificadorUsuario implements ObservadorTicket{

	@Override
	public void actualizar(Ticket ticket, String evento) {
		System.out.println(evento);
		
	}
	
}
