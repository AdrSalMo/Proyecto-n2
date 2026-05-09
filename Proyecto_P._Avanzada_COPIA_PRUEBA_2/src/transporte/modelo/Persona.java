package transporte.modelo;

public abstract class Persona {

    private String nombre;
    private String rut;


    public Persona(String nombre, String rut) {
        this.nombre = nombre;
        this.rut = rut;
    }
    
    public Persona(String nombre) {
        this.nombre = nombre;
        this.rut = "Sin RUT";
    }

    public String getNombre() {
        return nombre;
    }

    public String getRut() {
        return rut;
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.isEmpty()) {
            this.nombre = nombre;
        }
    }
    public void setRut(String rut) {
        this.rut = rut;
    }
}
