package mesaayuda.modulo;

import java.util.List;

public interface EstrategiaAsignacion {
	void asignarTecnico(Ticket ticket, List<Tecnico> tecnicos, List<Ticket> tickets);
}
