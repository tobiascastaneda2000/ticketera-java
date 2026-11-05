package mesaayuda.modulo;

public class NotificadorTecnico implements ObservadorTicket{

	@Override
	public void actualizar(Ticket ticket, String evento) {
		System.out.println(evento);
		
	}
}
