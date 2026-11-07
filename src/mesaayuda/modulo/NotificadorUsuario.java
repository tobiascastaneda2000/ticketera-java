package mesaayuda.modulo;

public class NotificadorUsuario implements ObservadorTicket{

	@Override
    public void actualizar(Ticket ticket, String evento) {
        System.out.println(
                "Aviso al solicitante "
                + ticket.getSolicitante().getNombre()
                + ": "
                + evento
        );
    }
	
}
