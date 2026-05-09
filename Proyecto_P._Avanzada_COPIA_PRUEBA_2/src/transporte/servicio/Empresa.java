package transporte.servicio;

import transporte.modelo.*;
import transporte.excepciones.*;
import transporte.modelo.Viaje;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collection; // Importante para getListaConductores
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Empresa {

    private String nombre;
    private ArrayList<Viaje> viajes;
    private HashMap<String, Bus> buses;
    private HashMap<String, ArrayList<Viaje>> viajesPorDestino;
    private HashMap<String, Conductor> todosLosConductores; // Mapa maestro de conductores

    public Empresa(String nombre) {
        this.nombre = nombre;
        this.viajes = new ArrayList<>();
        this.buses = new HashMap<>();
        this.viajesPorDestino = new HashMap<>();
        this.todosLosConductores = new HashMap<>(); // Inicialización vital
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

    public HashMap<String, ArrayList<Viaje>> getViajesPorDestino() {
        return viajesPorDestino;
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

    public void agregarBus(Bus b) throws DatosInvalidosException {
        if (buses.containsKey(b.getPatente())) {
            throw new DatosInvalidosException("Ya existe un bus con la patente: " + b.getPatente());
        }
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
    
    public void editarCapacidadBus(String patente, int nuevaCapacidad) throws Exception {
        // Buscamos el bus en el mapa de la empresa
        Bus b = buscarBus(patente); 

        if (b == null) {
            throw new Exception("El bus con patente " + patente + " no existe.");
        }

        if (nuevaCapacidad <= 0) {
            throw new Exception("La capacidad debe ser mayor a cero.");
        }

        // Aplicamos el cambio al objeto
        b.setCapacidad(nuevaCapacidad);
    }
    
    // version ventanaprincipal
    public void editarViaje(int id, String destino, String horario, double costo, double precio, String patente) 
            throws ViajeNoEncontradoException {

        Viaje v = buscarViaje(id);

        if (v == null) {
            throw new ViajeNoEncontradoException("Viaje no encontrado con ID: " + id);
        }

        // Actualizamos solo si el usuario ingresó algo (no es null ni vacío)
        if (destino != null && !destino.trim().isEmpty()) v.setDestino(destino);
        if (horario != null && !horario.trim().isEmpty()) v.setHorario(horario);

        // Solo actualiza si son valores válidos (mayores a 0)
        if (costo >= 0) v.setCostoViaje(costo);
        if (precio >= 0) v.setPrecioPasaje(precio);

        // Lógica para cambiar el bus por su patente
        if (patente != null && !patente.trim().isEmpty()) {
            Bus nuevoBus = buscarBus(patente);
            if (nuevoBus != null) {
                v.setBus(nuevoBus);
            }
        }
    }

    // version main
    public void editarViaje(int id, String destino, double costo, double precio) 
            throws ViajeNoEncontradoException {
        this.editarViaje(id, destino, null, costo, precio, null);
    }

    // ================= CONDUCTOR (CORREGIDO) =================
    
    public void contratarConductor(Conductor c) {
        if (c != null && c.getRut() != null) {
            todosLosConductores.put(c.getRut(), c);
        }
    }

    public Collection<Conductor> getListaConductores() {
        return todosLosConductores.values();
    }
    
    public Conductor buscarConductor(String rut) {
        return todosLosConductores.get(rut);
    }

    // ================= VIAJES =================

    public void agregarViaje(Viaje v) throws DatosInvalidosException {
        for (Viaje viajeExistente : viajes) {
            if (viajeExistente.getId() == v.getId()) {
                throw new DatosInvalidosException("Ya existe un viaje con el ID: " + v.getId());
            }
        }
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
        if (v == null) throw new ViajeNoEncontradoException();
        viajes.remove(v);
        guardarDatos("datos.dat");
    }

    // ================= CSV (CORREGIDO) =================

    public void cargarDesdeCSV() {
        try {
            buses.clear();
            viajes.clear();
            todosLosConductores.clear(); // Limpiar también conductores

            cargarBuses("buses.csv");
            cargarConductores("conductores.csv");
            cargarViajes("viajes.csv");
            cargarPasajeros("pasajeros.csv");

            System.out.println("Datos cargados correctamente desde CSV");
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
            
            // 1. Registrar en el mapa global de la empresa
            contratarConductor(c);

            // 2. Vincular con el bus si existe
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

    // ================= SERIALIZACIÓN (CORREGIDO) =================

    public void guardarDatos(String archivo) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(archivo))) {
            oos.writeObject(viajes);
            oos.writeObject(buses);
            oos.writeObject(todosLosConductores); // Guardar conductores independientes
            System.out.println("Datos guardados en " + archivo);
        } catch (Exception e) {
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void cargarDatos(String archivo) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
            viajes = (ArrayList<Viaje>) ois.readObject();
            buses = (HashMap<String, Bus>) ois.readObject();
            todosLosConductores = (HashMap<String, Conductor>) ois.readObject(); // Cargar conductores
            
            System.out.println("Datos cargados desde binario");
        } catch (Exception e) {
            System.out.println("Error al cargar binario: " + e.getMessage());
        }
    }
}
