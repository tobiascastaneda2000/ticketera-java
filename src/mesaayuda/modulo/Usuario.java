package mesaayuda.modulo;

public class Usuario {
	int id;
    String nombre;
    String email;
    
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
}
