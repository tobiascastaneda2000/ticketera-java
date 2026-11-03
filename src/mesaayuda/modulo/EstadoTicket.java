package mesaayuda.modulo;

/** Comportamientos disponibles para un ticket según su estado actual. */
interface EstadoTicket {
	void avanzar(Ticket ticket);
	void reabrir(Ticket ticket);
	Estado comoEnum();
}
