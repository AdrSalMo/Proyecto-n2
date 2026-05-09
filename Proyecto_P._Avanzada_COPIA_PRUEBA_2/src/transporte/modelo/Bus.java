package transporte.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import transporte.excepciones.BusLlenoException;
import transporte.excepciones.DatosInvalidosException;

public class Bus implements Serializable {
    private static final long serialVersionUID = 1L;

    private String patente;
    private int capacidad;
    private ArrayList<Pasajero> pasajeros;
    private Conductor conductor;

    // Constructor SIN conductor
    public Bus(String patente, int capacidad) throws DatosInvalidosException {
        validarDatos(patente, capacidad);
        this.patente = patente;
        this.capacidad = capacidad;
        this.pasajeros = new ArrayList<>();
        this.conductor = null;
    }

    // Constructor CON conductor
    public Bus(String patente, int capacidad, Conductor conductor) throws DatosInvalidosException {
        validarDatos(patente, capacidad);
        this.patente = patente;
        this.capacidad = capacidad;
        this.conductor = conductor;
        this.pasajeros = new ArrayList<>();
    }

    // ================= VALIDACIÓN =================
    private void validarDatos(String patente, int capacidad) throws DatosInvalidosException {
        if (patente == null || patente.trim().isEmpty()) {
            throw new DatosInvalidosException("La patente no puede estar vacía");
        }
        if (capacidad <= 0 || capacidad > 100) {
            throw new DatosInvalidosException("Capacidad inválida (1-100)");
        }
    }

    // ================= GETTERS =================
    public String getPatente() {
        return patente;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public ArrayList<Pasajero> getPasajeros() {
        return pasajeros;
    }

    public Conductor getConductor() {
        return conductor;
    }

    // ================= SETTERS =================
    public void setConductor(Conductor conductor) {
        this.conductor = conductor;
    }
    
    public void setCapacidad(int nuevaCapacidad) throws DatosInvalidosException {
        // Validación de lógica de negocio
        if (nuevaCapacidad < pasajeros.size()) {
            throw new DatosInvalidosException("No se puede reducir la capacidad a " + nuevaCapacidad + 
                " porque ya hay " + pasajeros.size() + " pasajeros en el bus.");
        }

        // Validación de rangos
        if (nuevaCapacidad <= 0 || nuevaCapacidad > 100) {
            throw new DatosInvalidosException("La capacidad debe estar entre 1 y 100.");
        }

        this.capacidad = nuevaCapacidad;
    }

    // ================= LÓGICA =================
    public void agregarPasajero(Pasajero p) throws BusLlenoException {
        if (pasajeros.size() >= capacidad) {
            throw new BusLlenoException("El bus está lleno");
        }
        pasajeros.add(p);
    }

    public void eliminarPasajero(Pasajero p) {
        pasajeros.remove(p);
    }

    public int getCapacidadDisponible() {
        return capacidad - pasajeros.size();
    }

    public double getPorcentajeOcupacion() {
        if (capacidad == 0) return 0;
        return (double) pasajeros.size() / capacidad * 100;
    }

    // ================= TO STRING =================
    @Override
    public String toString() {
        String nombreConductor = (conductor != null)
                ? conductor.getNombre()
                : "Sin conductor";

        return "Bus " + patente +
               " | Capacidad: " + capacidad +
               " | Conductor: " + nombreConductor +
               " | Ocupación: " + pasajeros.size() + "/" + capacidad +
               " (" + String.format("%.1f%%", getPorcentajeOcupacion()) + "%)";
    }
}
