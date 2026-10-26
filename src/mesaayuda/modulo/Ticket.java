package mesaayuda.modulo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Ticket {

	private final int id;
	private final String titulo;
	private final String descripcion;
	private final Prioridad prioridad;
	private final Categoria categoria;
	private final LocalDateTime fechaCreacion;
	private final Usuario solicitante;
	private Tecnico tecnico;
	private List<Comentario> comentarios = new ArrayList<>();

	public Categoria getCategoria() {
		return categoria;
	}

	public int getId() {
		return id;
	}

	public String getTitulo() {
		return titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public Prioridad getPrioridad() {
		return prioridad;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public Usuario getSolicitante() {
		return solicitante;
	}

	public Tecnico getTecnico() {
		return tecnico;
	}

	public Ticket(int id, String titulo, Prioridad prioridad, Categoria categoria, Usuario solicitante) {
		this(id, titulo, "", prioridad, categoria, solicitante);
	}

	public Ticket(int id, String titulo, String descripcion, Prioridad prioridad, Categoria categoria,
			Usuario solicitante) {
		
		if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }
		
		this.id = id;
		this.titulo = Objects.requireNonNull(titulo.trim(), "Titulo no puede ser null");
		this.descripcion = (descripcion == null) ? "" : descripcion;
		this.prioridad = Objects.requireNonNull(prioridad, "Prioridad no puede ser null");
		this.categoria = Objects.requireNonNull(categoria, "Categoria no puede ser null");
		this.solicitante = Objects.requireNonNull(solicitante, "Solicitante no puede ser null");
		this.fechaCreacion = LocalDateTime.now();
	}

	public List<Comentario> getComentarios() {
		return Collections.unmodifiableList(comentarios);
	}

	public void asignarA(Tecnico t) {
		tecnico = Objects.requireNonNull(t, "Tecnico no puede ser null");
		;
	}

	public void agregarComentario(Comentario c) {
		comentarios.add(c);
	}
	
	@Override
    public String toString() {
        String atiende = tecnico == null ? "sin asignar" : tecnico.getNombre();
        return String.format("#%d [%s] %s (%s) | solicitante: %s | técnico: %s",
                id, prioridad, titulo, categoria, solicitante.getNombre(), atiende);
    }

}
