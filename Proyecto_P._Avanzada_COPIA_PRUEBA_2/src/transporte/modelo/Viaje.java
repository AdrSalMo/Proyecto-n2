package transporte.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import transporte.excepciones.*;

public class Viaje implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String destino;
    private String horario; 
    private double costoViaje;
    private double precioPasaje;
    private ArrayList<Pasajero> pasajeros;
    private Bus bus;

    
    public Viaje(int id, String destino, String horario, double costo, double precio, Bus bus) throws DatosInvalidosException { 
    
    // Se valida aquí para que el catch de la UI tenga sentido
    if (id <= 0) throw new DatosInvalidosException("El ID del viaje debe ser positivo.");
    if (costo < 0 || precio < 0) throw new DatosInvalidosException("Los montos no pueden ser negativos.");
    if (destino == null || destino.trim().isEmpty()) throw new DatosInvalidosException("El destino es obligatorio.");

    this.id = id;
    this.destino = destino;
    this.costoViaje = costo;
    this.precioPasaje = precio;
    this.bus = bus;
    this.horario = horario;
    this.pasajeros = new ArrayList<>();
}

    // ================= GETTERS =================

    public int getId() {
        return id;
    }

    public String getDestino() {
        return destino;
    }

    public String getHorario() {
        return horario;
    }

    public double getCostoViaje() {
        return costoViaje;
    }

    public double getPrecioPasaje() {
        return precioPasaje;
    }

    public ArrayList<Pasajero> getPasajeros() {
        return pasajeros;
    }

    public Bus getBus() {
        return bus;
    }

    // ================= SETTERS =================

    public void setDestino(String destino) {
        if (destino != null && !destino.trim().isEmpty()) {
            this.destino = destino;
        }
    }

    public void setHorario(String horario) {
        if (horario != null && !horario.trim().isEmpty()) {
            this.horario = horario;
        }
    }

    public void setCostoViaje(double costoViaje) {
        if (costoViaje >= 0) {
            this.costoViaje = costoViaje;
        }
    }

    public void setPrecioPasaje(double precioPasaje) {
        if (precioPasaje >= 0) {
            this.precioPasaje = precioPasaje;
        }
    }

    // ================= LÓGICA =================

    // agregar pasajero con datos
    public void agregarPasajero(String nombre, String rut, int id) throws BusLlenoException {
        Pasajero p = new Pasajero(nombre, rut, id);
        agregarPasajero(p);
    }

    // agregar pasajero objeto
    public void agregarPasajero(Pasajero p) throws BusLlenoException {
    
    if (this.bus.getPasajeros().size() >= this.bus.getCapacidad()) {
        throw new BusLlenoException("No se puede agregar: El bus " + bus.getPatente() + " alcanzó su límite.");
    }
    pasajeros.add(p);
    }

    public void eliminarPasajero(Pasajero p) {
        pasajeros.remove(p);
    }

    public double calcularIngresos() {
        return pasajeros.size() * precioPasaje;
    }

    public boolean esRentable() {
        return calcularIngresos() >= costoViaje;
    }

    public void verificarRentabilidad() throws ViajeNoRentableException {
        if (!esRentable()) {
            throw new ViajeNoRentableException(
                "Viaje no rentable. Ingresos: " + calcularIngresos() +
                " | Costo: " + costoViaje
            );
        }
    }

    // ================= toString =================

    @Override
    public String toString() {
        return "Viaje " + id +
               " | Destino: " + destino +
               " | Horario: " + horario +
               " | Pasajeros: " + pasajeros.size() +
               " | Ingresos: " + calcularIngresos() +
               " | Costo: " + costoViaje +
               " | Rentable: " + (esRentable() ? "SI" : "NO");
    }
}