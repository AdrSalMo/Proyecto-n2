package transporte.ui;

import transporte.servicio.Empresa;
import transporte.modelo.Bus;
import transporte.modelo.Viaje;
import transporte.excepciones.*;
import java.util.Map; // Agrega esto si no está

import java.util.Scanner;

/*
 * Clase para manejar la interacción por consola de forma modular.
 */
public class ConsolaUI {

    private Empresa empresa;
    private Scanner sc;

    public ConsolaUI(Empresa empresa) {
        this.empresa = empresa;
        this.sc = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = leerEntero();
            ejecutarOpcion(opcion);
        } while (opcion != 0);
    }

    private void mostrarMenuPrincipal() {
        System.out.println("\n===== MENÚ PRINCIPAL =====");
        System.out.println("1. Gestión de Buses");
        System.out.println("2. Gestión de Viajes");
        System.out.println("3. Reportes");
        System.out.println("4. Datos");
        System.out.println("0. Salir");
        System.out.print("Seleccione opción: ");
    }

    private void ejecutarOpcion(int opcion) {
        try {
            switch (opcion) {
                case 1:
                    menuBuses();
                    break;
                case 2:
                    menuViajes();
                    break;
                case 3:
                    mostrarReporte();
                    break;
                case 4:
                    menuDatos();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void menuBuses() throws Exception {
        int op;
        do {
            System.out.println("\n--- BUSES ---");
            System.out.println("1. Agregar Bus");
            System.out.println("2. Mostrar Buses");
            System.out.println("3. Eliminar Bus");
            System.out.println("0. Volver");
            System.out.print("Opción: ");

            op = leerEntero();

            switch (op) {
                case 1:
                    agregarBus();
                    break;
                case 2:
                    mostrarBuses();
                    break;
                case 3:
                    eliminarBus();
                    break;
            }
        } while (op != 0);
    }

    private void menuViajes() throws Exception {
        int op;
        do {
            System.out.println("\n--- VIAJES ---");
            System.out.println("1. Agregar Viaje");
            System.out.println("2. Mostrar Viajes");
            System.out.println("3. Agregar Pasajero");
            System.out.println("4. Verificar Rentabilidad");
            System.out.println("0. Volver");
            System.out.print("Opción: ");

            op = leerEntero();

            switch (op) {
                case 1:
                    agregarViaje();
                    break;
                case 2:
                    mostrarViajes();
                    break;
                case 3:
                    agregarPasajero();
                    break;
                case 4:
                    verificarRentabilidad();
                    break;
            }
        } while (op != 0);
    }

    private void menuDatos() {
        System.out.println("Gestión de datos aún no implementada.");
    }

    private void mostrarReporte() {
        System.out.println("Mostrar reporte aún no implementado.");
    }

    private void agregarBus() {
        System.out.print("Patente: ");
        String patente = sc.nextLine();
        System.out.print("Capacidad: ");
        int capacidad = leerEntero();

        try {
            empresa.agregarBus(new Bus(patente, capacidad));
            System.out.println("Bus agregado correctamente.");
        } catch (DatosInvalidosException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private void mostrarBuses() {
            // Usamos getBuses() que es el nombre real en tu clase Empresa
            java.util.HashMap<String, Bus> buses = empresa.getBuses(); 

            if (buses.isEmpty()) {
                System.out.println("\nNo hay buses registrados");
            } else {
                for (Bus b : buses.values()) {
                    System.out.println(b);
                }
            }
    }

    private void eliminarBus() {
        System.out.print("Patente: ");
        String patente = sc.nextLine();
        try {
            empresa.eliminarBus(patente);
            System.out.println("Bus eliminado correctamente.");
        } catch (BusNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void agregarViaje() {
        try {
            System.out.print("ID: ");
            int id = leerEntero();
            System.out.print("Destino: ");
            String destino = sc.nextLine();
            System.out.print("Horario: ");
            String horario = sc.nextLine();
            System.out.print("Costo: ");
            double costo = Double.parseDouble(sc.nextLine());
            System.out.print("Precio: ");
            double precio = Double.parseDouble(sc.nextLine());

            System.out.print("Patente del Bus: ");
            String patenteBus = sc.nextLine();
            Bus bus = empresa.buscarBus(patenteBus);

            if (bus == null) {
                System.out.println("Bus no encontrado.");
                return;
            }

            Viaje viaje = new Viaje(id, destino, horario, costo, precio, bus);
            empresa.agregarViaje(viaje);
            System.out.println("Viaje agregado correctamente.");
        } catch (DatosInvalidosException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void mostrarViajes() {
            // Usamos getViajes() que devuelve el ArrayList en tu clase Empresa
            java.util.ArrayList<Viaje> listaViajes = empresa.getViajes(); 

            if (listaViajes.isEmpty()) {
                System.out.println("\nNo hay viajes registrados");
            } else {
                for (Viaje v : listaViajes) {
                    System.out.println(v);
                }
            }
    }

    private void agregarPasajero() {
        System.out.println("Función agregar pasajero aún no implementada.");
    }

    private void verificarRentabilidad() {
        System.out.println("Función verificar rentabilidad aún no implementada.");
    }

    private int leerEntero() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Número inválido. Intente nuevamente: ");
            }
        }
    }
}