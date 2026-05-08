package transporte.modelo;

import java.io.Serializable;

public class Pasajero implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nombre;
    private String rut;
    private int id;

    public Pasajero(String nombre, String rut, int id) {
        this.nombre = nombre;
        this.rut = rut;
        this.id = id;
    }

    public String toString() {
        return nombre + " (" + rut + ")";
    }
}