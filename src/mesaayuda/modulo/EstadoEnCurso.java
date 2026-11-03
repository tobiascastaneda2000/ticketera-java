package mesaayuda.modulo;

final class EstadoEnCurso implements EstadoTicket {
	public void avanzar(Ticket ticket) { ticket.cambiarEstado(new EstadoResuelto()); }
	public void reabrir(Ticket ticket) { throw new IllegalStateException("Solo se puede reabrir un ticket resuelto o cerrado"); }
	public Estado comoEnum() { return Estado.EN_CURSO; }
}
