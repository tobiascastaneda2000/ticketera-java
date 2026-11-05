package mesaayuda.modulo;

import java.util.List;

public class EstrategiaAsignacionMenorCarga implements EstrategiaAsignacion{
	public void asignarTecnico(Ticket ticket, List<Tecnico> tecnicos, List<Ticket> tickets) {
		
		if (tecnicos == null || tecnicos.isEmpty()) {
			throw new IllegalStateException("La lista de tecnicos esta vacia. No puede asignar el ticket");
		}
		
		if (tickets == null) {
            throw new IllegalArgumentException("La lista de tickets no puede ser null");
        }
		
		Tecnico elegido = tecnicos.get(0);
        long menorCarga = contarTicketsActivos(elegido, tickets);
		
		for (int i = 1; i < tecnicos.size(); i++) {
            Tecnico candidato = tecnicos.get(i);
            long carga = contarTicketsActivos(candidato, tickets);

            // Solo reemplazamos si la carga es menor.
            // Así, ante un empate, queda elegido el primero de la lista.
            if (carga < menorCarga) {
                elegido = candidato;
                menorCarga = carga;
            }
        }

        ticket.asignarA(elegido);
    }

    private long contarTicketsActivos(Tecnico tecnico, List<Ticket> tickets) {
        return tickets.stream()
                .filter(Ticket::esActivo)
                .filter(t -> tecnico.equals(t.getTecnico()))
                .count();
    }

}
	

/*1. Asignación rotativa: reparte los tickets entre los técnicos en orden. Si hay Ana, Luis y Sol, asigna Ana → Luis → Sol → Ana.
2. Menor carga: asigna el ticket al técnico que tenga menos tickets activos (ABIERTO o EN_CURSO). Si hay empate, elegí al primero de la lista.
Comportamiento esperado:
- Si no hay técnicos disponibles, el sistema debe avisar que no puede asignar el ticket.
- Al asignar, el ticket debe quedar asociado al técnico elegido.
- Los tickets RESUELTO y CERRADO no cuentan como carga activa.
- El gestor debe poder cambiar de estrategia sin modificar la lógica de las estrategias.
Como guía de diseño, ambas estrategias deberían implementar una misma interfaz, por ejemplo EstrategiaAsignacion, con un método que reciba el ticket y la lista de técnicos y devuelva o asigne el técnico elegido. El gestor usa esa interfaz; no necesita saber qué regla concreta se está aplicando.

Probá primero la rotativa con varios tickets y después la de menor carga. Para comprobar la carga, creá tickets en distintos estados y verificá a quién asigna el siguiente.*/