package mesaayuda.modulo;

final class EstadoAbierto implements EstadoTicket {
	public void avanzar(Ticket ticket) { ticket.cambiarEstado(new EstadoEnCurso()); }
	public void reabrir(Ticket ticket) { throw new IllegalStateException("El ticket ya está abierto"); }
	public Estado comoEnum() { return Estado.ABIERTO; }
}
