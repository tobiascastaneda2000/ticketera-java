package mesaayuda.modulo;

import java.time.LocalDateTime;

public class Comentario {
	String autor;
    String texto;
    LocalDateTime fecha;
    
    public Comentario(String a, String texto){
    	
    	if (texto == null || texto.isBlank()) {
            throw new IllegalArgumentException("El texto no puede estar vacío");
        }
    	this.autor = a;
    	this.texto = texto;
    	this.fecha = LocalDateTime.now();
   
    }

	public String getAutor() {
		return autor;
	}

	public String getTexto() {
		return texto;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

}
