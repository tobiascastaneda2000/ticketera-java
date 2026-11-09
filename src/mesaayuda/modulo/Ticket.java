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
	private EstadoTicket estado;
	private final List<ObservadorTicket> observadores = new ArrayList<>();

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
	
	// 1. Sin descripción, sin observadores
	public Ticket(int id, String titulo, Prioridad prioridad, Categoria categoria, Usuario solicitante) {
		this(id, titulo, "", prioridad, categoria, solicitante, null);
	}
	
	// 2. Sin descripción, con observadores
	public Ticket(int id, String titulo, Prioridad prioridad, Categoria categoria, Usuario solicitante, List<ObservadorTicket> observadores) {
	    this(id, titulo, "", prioridad, categoria, solicitante, observadores);
	}

	// 3. Con descripción, sin observadores
	public Ticket(int id, String titulo, String descripcion, Prioridad prioridad, Categoria categoria, Usuario solicitante) {
	    this(id, titulo, descripcion, prioridad, categoria, solicitante, null);
	}
	// 4. Con descripción y observadores
	public Ticket(int id, String titulo, String descripcion, Prioridad prioridad, Categoria categoria,
			Usuario solicitante, List<ObservadorTicket> observadores) {
		
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
		this.estado = new EstadoAbierto();
		if (observadores != null) {
			this.observadores.addAll(observadores);
		}
	}

	public List<Comentario> getComentarios() {
		return Collections.unmodifiableList(comentarios);
	}

	public void asignarA(Tecnico t) {
		this.tecnico = Objects.requireNonNull(t, "Tecnico no puede ser null");
		notificarObservadores("Ticket asignado a " + this.tecnico.getNombre());
	}

	public void agregarComentario(Comentario c) {
		comentarios.add(c);
		notificarObservadores("Nuevo Comentario - " + "Autor: " + c.autor + " - Texto: " + c.texto);
	}
	
	public void reabrir() {
		estado.reabrir(this);
		notificarObservadores("El ticket cambió a " + getEstado());
	}
	
	public void transicionarEstado() {
		this.estado.avanzar(this);
		notificarObservadores("El ticket cambió a " + getEstado());
	}

	private void notificarObservadores(String evento) {
		for (ObservadorTicket observador : new ArrayList<>(observadores)) {
			observador.actualizar(this, evento);
		}
	}

	void cambiarEstado(EstadoTicket nuevoEstado) { this.estado = nuevoEstado; }
	
	@Override
    public String toString() {
        String atiende = tecnico == null ? "sin asignar" : tecnico.getNombre();
		return String.format("#%d [%s | %s] %s (%s) | solicitante: %s | técnico: %s",
				id, prioridad, getEstado(), titulo, categoria, solicitante.getNombre(), atiende);
    }

	public Estado getEstado() {
		return estado.comoEnum();
	}
	
	public boolean esActivo() {
		return estado.comoEnum() == Estado.ABIERTO || estado.comoEnum() == Estado.EN_CURSO;
	}
	
	public void agregarObservador(ObservadorTicket observador) {
		this.observadores.add(Objects.requireNonNull(observador, "Observador no puede ser null"));
	}
	
	public void quitarObservador(ObservadorTicket observador) {
		this.observadores.remove(observador);
	}
		
}
