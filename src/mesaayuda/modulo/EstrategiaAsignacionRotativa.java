package mesaayuda.modulo;

import java.util.List;

public class EstrategiaAsignacionRotativa implements EstrategiaAsignacion{
	int posicion = 0;
	
	public void asignarTecnico(Ticket ticket, List<Tecnico> tecnicos, List<Ticket> tickets) {
		
		if (tecnicos == null || tecnicos.size() == 0) {
			throw new IllegalStateException("La lista de tecnicos esta vacia. No puede asisnar el ticket");
		}
		
		
		int indice = posicion % tecnicos.size();
		
		Tecnico elegido = tecnicos.get(indice);
		posicion++;
		
		ticket.asignarA(elegido);
	}
}
