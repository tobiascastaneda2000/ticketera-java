package mesaayuda.modulo;

final class EstadoResuelto implements EstadoTicket {
	public void avanzar(Ticket ticket) { ticket.cambiarEstado(new EstadoCerrado()); }
	public void reabrir(Ticket ticket) { ticket.cambiarEstado(new EstadoAbierto()); }
	public Estado comoEnum() { return Estado.RESUELTO; }
}
