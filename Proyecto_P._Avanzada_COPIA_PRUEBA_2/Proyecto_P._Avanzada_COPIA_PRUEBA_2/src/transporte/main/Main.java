package transporte.main;

import transporte.servicio.*;
import transporte.modelo.*;
import transporte.excepciones.*;
import transporte.ui.VentanaPrincipal;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Empresa empresa = new Empresa("Mi Empresa");

        try {
            empresa.cargarDesdeCSV();
            System.out.println("Datos iniciales cargados correctamente");
            System.out.println("Buses cargados: " + empresa.getBuses().size());
            System.out.println("Viajes cargados: " + empresa.getViajes().size());
        } catch (Exception e) {
            System.out.println("No se pudieron cargar datos iniciales: " + e.getMessage());
        }

        Scanner sc = new Scanner(System.in);

        // 🔥 SELECTOR INICIAL
        System.out.println("=================================");
        System.out.println(" SISTEMA DE TRANSPORTE");
        System.out.println("=================================");
        System.out.println("1. Ejecutar en consola");
        System.out.println("2. Ejecutar en ventana");
        System.out.print("Seleccione una opción: ");

        int opcionInicial = leerEntero(sc);

        if (opcionInicial == 1) {
            ejecutarConsola(sc, empresa);
        } else if (opcionInicial == 2) {
            new VentanaPrincipal(empresa);
        } else {
            System.out.println("Opción inválida");
        }

        sc.close();
    }

    // ================= CONSOLA =================

    private static void ejecutarConsola(Scanner sc, Empresa empresa) {

        int opcion;

        do {
            mostrarMenuPrincipal();
            opcion = leerEntero(sc);

            try {
                switch (opcion) {
                    case 1:
                        menuBuses(sc, empresa);
                        break;
                    case 2:
                        menuViajes(sc, empresa);
                        break;
                    case 3:
                        ReporteViajes.mostrarReporte(empresa.getViajes());
                        break;
                    case 4:
                        menuDatos(sc, empresa);
                        break;
                    case 0:
                        System.out.println("Saliendo...");
                        break;
                    default:
                        System.out.println("Opción inválida");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (opcion != 0);
    }

    // ================= MENÚ PRINCIPAL =================

    private static void mostrarMenuPrincipal() {
        System.out.println("\n===== MENÚ PRINCIPAL =====");
        System.out.println("1. Gestión de Buses");
        System.out.println("2. Gestión de Viajes");
        System.out.println("3. Reportes");
        System.out.println("4. Datos");
        System.out.println("0. Salir");
        System.out.print("Seleccione opción: ");
    }

    // ================= BUSES =================

    private static void menuBuses(Scanner sc, Empresa empresa) throws Exception {
        int op;
        do {
            System.out.println("\n--- BUSES ---");
            System.out.println("1. Agregar Bus");
            System.out.println("2. Mostrar Buses");
            System.out.println("3. Eliminar Bus");
            System.out.println("0. Volver");
            System.out.print("Opción: ");

            op = leerEntero(sc);

            switch (op) {
                case 1:
                    agregarBus(sc, empresa);
                    break;
                case 2:
                    empresa.mostrarBuses();
                    break;
                case 3:
                    eliminarBus(sc, empresa);
                    break;
            }
        } while (op != 0);
    }

    // ================= VIAJES =================

    private static void menuViajes(Scanner sc, Empresa empresa) throws Exception {
        int op;
        do {
            System.out.println("\n--- VIAJES ---");
            System.out.println("1. Agregar Viaje");
            System.out.println("2. Mostrar Viajes");
            System.out.println("3. Agregar Pasajero");
            System.out.println("4. Verificar Rentabilidad");
            System.out.println("5. Reasignar Pasajeros");
            System.out.println("6. Eliminar Viaje");
            System.out.println("7. Editar Viaje");
            System.out.println("0. Volver");
            System.out.print("Opción: ");

            op = leerEntero(sc);

            switch (op) {
                case 1:
                    agregarViaje(sc, empresa);
                    break;
                case 2:
                    empresa.mostrarViajes();
                    break;
                case 3:
                    agregarPasajero(sc, empresa);
                    break;
                case 4:
                    verificarRentabilidad(sc, empresa);
                    break;
                case 5:
                    reasignar(sc, empresa);
                    break;
                case 6:
                    eliminarViaje(sc, empresa);
                    break;
                case 7:
                    editarViaje(sc, empresa);
                    break;
            }
        } while (op != 0);
    }

    // ================= DATOS =================

    private static void menuDatos(Scanner sc, Empresa empresa) {
        int op;
        do {
            System.out.println("\n--- DATOS ---");
            System.out.println("1. Guardar");
            System.out.println("2. Cargar");
            System.out.println("0. Volver");
            System.out.print("Opción: ");

            op = leerEntero(sc);

            switch (op) {
                case 1:
                    empresa.guardarDatos("datos.dat");
                    break;
                case 2:
                    empresa.cargarDatos("datos.dat");
                    break;
            }
        } while (op != 0);
    }

    // ================= UTIL =================

    private static int leerEntero(Scanner sc) {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.print("Número inválido, intente nuevamente: ");
            }
        }
    }

    // ================= LÓGICA =================

    private static void agregarBus(Scanner sc, Empresa empresa) {
        System.out.print("Patente: ");
        String patente = sc.nextLine();

        System.out.print("Capacidad: ");
        int capacidad = leerEntero(sc);

        try {
            empresa.agregarBus(new Bus(patente, capacidad));
            System.out.println("Bus agregado correctamente");
        } catch (DatosInvalidosException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void agregarViaje(Scanner sc, Empresa empresa) throws Exception {
        System.out.print("ID: ");
        int id = leerEntero(sc);

        System.out.print("Destino: ");
        String destino = sc.nextLine();

        System.out.print("Horario: ");
        String horario = sc.nextLine();

        System.out.print("Costo: ");
        double costo = Double.parseDouble(sc.nextLine());

        System.out.print("Precio pasaje: ");
        double precio = Double.parseDouble(sc.nextLine());

        System.out.print("Patente bus: ");
        String pat = sc.nextLine();

        Bus bus = empresa.buscarBus(pat);
        if (bus == null) throw new BusNoEncontradoException("Bus no encontrado");

        empresa.agregarViaje(new Viaje(id, destino, horario, costo, precio, bus));
        System.out.println("Viaje agregado");
    }

    private static void agregarPasajero(Scanner sc, Empresa empresa) throws Exception {
        System.out.print("ID viaje: ");
        int id = leerEntero(sc);

        Viaje v = empresa.buscarViaje(id);
        if (v == null) throw new ViajeNoEncontradoException("Viaje no encontrado");

        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        System.out.print("RUT: ");
        String rut = sc.nextLine();

        System.out.print("ID pasajero: ");
        int idP = leerEntero(sc);

        v.agregarPasajero(nombre, rut, idP);
        System.out.println("Pasajero agregado");
    }

    private static void verificarRentabilidad(Scanner sc, Empresa empresa) {
        System.out.print("ID viaje: ");
        int id = leerEntero(sc);

        try {
            Viaje v = empresa.buscarViaje(id);

            if (v == null) throw new ViajeNoEncontradoException("Viaje no encontrado");

            v.verificarRentabilidad();
            System.out.println("El viaje ES rentable");

        } catch (ViajeNoRentableException e) {

            System.out.println("X " + e.getMessage());

            System.out.print("¿Desea reasignar pasajeros? (s/n): ");
            String opcion = sc.nextLine();

            if (opcion.equalsIgnoreCase("s")) {
                try {
                    empresa.reasignarPasajeros(id);
                    System.out.println("Pasajeros reasignados correctamente");
                } catch (Exception ex) {
                    System.out.println("Error al reasignar: " + ex.getMessage());
                }
            }

        } catch (ViajeNoEncontradoException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void reasignar(Scanner sc, Empresa empresa) throws Exception {
        System.out.print("ID viaje: ");
        int id = leerEntero(sc);
        empresa.reasignarPasajeros(id);
    }

    private static void eliminarViaje(Scanner sc, Empresa empresa) throws Exception {
        System.out.print("ID viaje: ");
        int id = leerEntero(sc);
        empresa.eliminarViaje(id);
        System.out.println("Viaje eliminado");
    }

    private static void eliminarBus(Scanner sc, Empresa empresa) throws Exception {
        System.out.print("Patente: ");
        String pat = sc.nextLine();
        empresa.eliminarBus(pat);
        System.out.println("Bus eliminado");
    }

    private static void editarViaje(Scanner sc, Empresa empresa) throws Exception {
        System.out.print("ID viaje: ");
        int id = leerEntero(sc);

        System.out.print("Nuevo destino: ");
        String destino = sc.nextLine();

        System.out.print("Nuevo costo: ");
        double costo = Double.parseDouble(sc.nextLine());

        System.out.print("Nuevo precio: ");
        double precio = Double.parseDouble(sc.nextLine());

        empresa.editarViaje(id, destino, costo, precio);
        System.out.println("Viaje editado");
    }
}