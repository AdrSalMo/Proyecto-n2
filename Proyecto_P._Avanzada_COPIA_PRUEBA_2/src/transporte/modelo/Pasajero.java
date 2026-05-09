package transporte.modelo;

import java.io.Serializable;

public class Pasajero extends Persona implements Serializable {
    private static final long serialVersionUID = 1L; // Buena práctica para Serializable
    private int id;

    public Pasajero(String nombre, String rut, int id) {
        super(nombre, rut); // Llama al constructor de Persona (Clase base)
        this.id = id;
    }

    @Override
    public String toString() {
        return getNombre() + " (" + getRut() + ") - ID: " + id;
    }
}
