package transporte.modelo;

import java.io.Serializable;

public class Conductor extends Persona implements Serializable {

    private static final long serialVersionUID = 1L;

    private String patenteBus; // 🔥 único atributo extra

    // CONSTRUCTOR SEGÚN CSV: rut, nombre, patente
    public Conductor(String rut, String nombre, String patenteBus) {
        super(nombre, rut); // ✔ correcto
        this.patenteBus = patenteBus;
    }

    // ================= GETTER =================

    public String getPatenteBus() {
        return patenteBus;
    }

    // ================= SETTER =================

    public void setPatenteBus(String patenteBus) {
        this.patenteBus = patenteBus;
    }

    @Override
    public String toString() {
        return getNombre() + " | RUT: " + getRut() + " | Bus: " + patenteBus;
    }
}