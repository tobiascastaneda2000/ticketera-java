package mesaayuda.modulo;

import java.time.LocalDateTime;

public class Comentario {
	String autor;
    String texto;
    LocalDateTime fecha;
    
    public Comentario(String a, String t){
    	this.autor = a;
    	this.texto = t;
    	this.fecha = LocalDateTime.now();
    }
}
