package mesaayuda.modulo;

public class Tecnico {
	int id;
    String nombre;
	NivelSoporte nivel;
    
    public Tecnico(int i, String n, NivelSoporte niv) {
    	this.id = i;
    	this.nombre = n;
    	this.nivel = niv;
    }
    
    public String getNombre() {
		return nombre;
	}

	public int getId() {
		return id;
	}

	public NivelSoporte getNivel() {
		return nivel;
	}
}
