package mesaayuda.modulo;

final class EstadoCerrado implements EstadoTicket {
	public void avanzar(Ticket ticket) { throw new IllegalStateException("Un ticket cerrado no puede avanzar"); }
	public void reabrir(Ticket ticket) { ticket.cambiarEstado(new EstadoAbierto()); }
	public Estado comoEnum() { return Estado.CERRADO; }
}
