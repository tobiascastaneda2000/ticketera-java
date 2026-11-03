package mesaayuda.modulo;

public class Usuario {
	private final int id;
    private final String nombre;
    private final String email;
    
    public Usuario(int i , String n, String e) {
    	this.id = i;
    	this.nombre =n;
    	this.email = e;
    }

	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public String getEmail() {
		return email;
	}
	
	public void reportarProblema(String t, String d, Categoria c, Prioridad p) {
		GestorDeTickets gestor = GestorDeTickets.getInstance();
		gestor.crearTicket(t, d, p, c, this);
		
	}
}
