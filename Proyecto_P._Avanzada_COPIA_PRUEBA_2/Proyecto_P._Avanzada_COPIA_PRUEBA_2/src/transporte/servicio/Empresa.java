package transporte.servicio;

import transporte.modelo.*;
import transporte.excepciones.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;

public class Empresa {

    private String nombre;
    private ArrayList<Viaje> viajes;
    private HashMap<String, Bus> buses;

    public Empresa(String nombre) {
        this.nombre = nombre;
        this.viajes = new ArrayList<>();
        this.buses = new HashMap<>();
    }

    // ================= GETTERS =================

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Viaje> getViajes() {
        return viajes;
    }

    public HashMap<String, Bus> getBuses() {
        return buses;
    }

    public int cantidadBuses() {
        return buses.size();
    }

    public int cantidadViajes() {
        return viajes.size();
    }

    // ================= UTIL =================

    private String repetir(String texto, int veces) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < veces; i++) {
            sb.append(texto);
        }
        return sb.toString();
    }

    // ================= BUS =================

    public void agregarBus(Bus b) {
        buses.put(b.getPatente(), b);
    }

    public Bus buscarBus(String patente) {
        return buses.get(patente);
    }

    public void eliminarBus(String patente) throws BusNoEncontradoException {
        if (!buses.containsKey(patente)) {
            throw new BusNoEncontradoException("Bus no encontrado");
        }
        buses.remove(patente);
    }

    public void mostrarBuses() {
        if (buses.isEmpty()) {
            System.out.println("\nNo hay buses registrados");
            return;
        }

        System.out.println("\nLISTA DE BUSES");
        System.out.println(repetir("=", 80));

        for (Bus b : buses.values()) {
            System.out.println(b);
        }

        System.out.println(repetir("=", 80));
    }

    // ================= VIAJES =================

    public void agregarViaje(Viaje v) {
        viajes.add(v);
    }

    public Viaje buscarViaje(int id) {
        for (Viaje v : viajes) {
            if (v.getId() == id) return v;
        }
        return null;
    }

    public void eliminarViaje(int id) throws ViajeNoEncontradoException {
        Viaje v = buscarViaje(id);

        if (v == null) {
            throw new ViajeNoEncontradoException("Viaje no encontrado");
        }

        viajes.remove(v);
    }

    public void mostrarViajes() {
        if (viajes.isEmpty()) {
            System.out.println("\nNo hay viajes registrados");
            return;
        }

        System.out.println("\nLISTA DE VIAJES");
        System.out.println(repetir("=", 100));

        for (Viaje v : viajes) {
            System.out.println(v);
        }

        System.out.println(repetir("=", 100));
    }

    // ================= EDITAR VIAJE =================

    public void editarViaje(int id, String destino, double costo, double precio)
            throws ViajeNoEncontradoException {

        Viaje v = buscarViaje(id);

        if (v == null) {
            throw new ViajeNoEncontradoException("Viaje no encontrado");
        }

        if (destino != null && !destino.trim().isEmpty()) {
            v.setDestino(destino);
        }

        if (costo >= 0) {
            v.setCostoViaje(costo);
        }

        if (precio >= 0) {
            v.setPrecioPasaje(precio);
        }
    }

    // ================= REASIGNACIÓN =================

    public void reasignarPasajeros(int idViaje) throws ViajeNoEncontradoException {

        Viaje original = buscarViaje(idViaje);

        if (original == null) {
            throw new ViajeNoEncontradoException("Viaje no encontrado");
        }

        if (original.esRentable()) {
            System.out.println("El viaje ya es rentable");
            return;
        }

        boolean reasignado = false;

        for (Pasajero p : new ArrayList<>(original.getPasajeros())) {

            Viaje mejorViaje = null;

            for (Viaje v : viajes) {

                if (v != original &&
                    v.getDestino().equalsIgnoreCase(original.getDestino()) &&
                    !v.getHorario().equalsIgnoreCase(original.getHorario())) {

                    if (v.getBus().getCapacidadDisponible() > 0) {

                        if (v.esRentable()) {
                            mejorViaje = v;
                            break;
                        }

                        if (mejorViaje == null ||
                            v.getPrecioPasaje() > mejorViaje.getPrecioPasaje()) {
                            mejorViaje = v;
                        }
                    }
                }
            }

            if (mejorViaje != null) {
                try {
                    mejorViaje.agregarPasajero(p);
                    original.eliminarPasajero(p);
                    reasignado = true;
                } catch (BusLlenoException e) {
                    // ignorar
                }
            }
        }

        if (reasignado) {
            System.out.println("Reasignación realizada");

            if (original.getPasajeros().isEmpty()) {
                viajes.remove(original);
                System.out.println("Viaje eliminado por quedar vacío");
            }

        } else {
            System.out.println("No se pudo reasignar pasajeros");
        }
    }

    // ================= CSV =================

    public void cargarDesdeCSV() {
        try {
            buses.clear();
            viajes.clear();

            cargarBuses("buses.csv");
            cargarConductores("conductores.csv");
            cargarViajes("viajes.csv");
            cargarPasajeros("pasajeros.csv");

            System.out.println("Datos cargados correctamente");

        } catch (Exception e) {
            System.out.println("Error al cargar CSV: " + e.getMessage());
        }
    }

    private void cargarBuses(String archivo) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(archivo));
        String linea;

        br.readLine();

        while ((linea = br.readLine()) != null) {
            String[] d = linea.split(",");

            String patente = d[0];
            int capacidad = Integer.parseInt(d[1]);

            agregarBus(new Bus(patente, capacidad));
        }

        br.close();
    }

    private void cargarConductores(String archivo) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(archivo));
        String linea;

        br.readLine();

        while ((linea = br.readLine()) != null) {
            String[] d = linea.split(",");

            String rut = d[0];
            String nombre = d[1];
            String patente = d[2];

            Conductor c = new Conductor(nombre, rut, patente);

            Bus bus = buscarBus(patente);

            if (bus != null) {
                bus.setConductor(c);
            }
        }

        br.close();
    }

    private void cargarViajes(String archivo) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(archivo));
        String linea;

        br.readLine();

        while ((linea = br.readLine()) != null) {
            String[] d = linea.split(",");

            int id = Integer.parseInt(d[0]);
            String destino = d[1];
            String horario = d[2];
            double costo = Double.parseDouble(d[3]);
            double precio = Double.parseDouble(d[4]);
            String patente = d[5];

            Bus bus = buscarBus(patente);

            if (bus != null) {
                agregarViaje(new Viaje(id, destino, horario, costo, precio, bus));
            }
        }

        br.close();
    }

    private void cargarPasajeros(String archivo) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader(archivo));
        String linea;

        br.readLine();

        while ((linea = br.readLine()) != null) {
            String[] d = linea.split(",");

            int idViaje = Integer.parseInt(d[0]);
            String nombre = d[1];
            String rut = d[2];
            int idP = Integer.parseInt(d[3]);

            Viaje v = buscarViaje(idViaje);

            if (v != null) {
                v.agregarPasajero(nombre, rut, idP);
            }
        }

        br.close();
    }

    // ================= SERIALIZACIÓN =================

    public void guardarDatos(String archivo) {
        try (java.io.ObjectOutputStream oos =
             new java.io.ObjectOutputStream(new java.io.FileOutputStream(archivo))) {

            oos.writeObject(viajes);
            oos.writeObject(buses);

            System.out.println("Datos guardados correctamente");

        } catch (Exception e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void cargarDatos(String archivo) {
        try (java.io.ObjectInputStream ois =
             new java.io.ObjectInputStream(new java.io.FileInputStream(archivo))) {

            viajes = (ArrayList<Viaje>) ois.readObject();
            buses = (HashMap<String, Bus>) ois.readObject();

            System.out.println("Datos cargados correctamente");

        } catch (Exception e) {
            System.out.println("Error al cargar: " + e.getMessage());
        }
    }
}