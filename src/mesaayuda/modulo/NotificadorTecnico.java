package mesaayuda.modulo;

public class NotificadorTecnico implements ObservadorTicket{

	@Override
    public void actualizar(Ticket ticket, String evento) {
        Tecnico tecnico = ticket.getTecnico();

        if (tecnico == null) {
            return;
        }

        System.out.println(
                "Aviso al técnico "
                + tecnico.getNombre()
                + ": "
                + evento
        );
    }
}
