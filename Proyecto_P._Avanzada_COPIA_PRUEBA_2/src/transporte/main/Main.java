package transporte.main;

import transporte.servicio.Empresa;
import transporte.ui.ConsolaUI;
import transporte.ui.VentanaPrincipal;

/*
 * Clase principal para inicializar el sistema de transporte.
 */
public class Main {

    public static void main(String[] args) {

        Empresa empresa = new Empresa("Mi Empresa");

        try {
            empresa.cargarDesdeCSV();
            System.out.println("Datos iniciales cargados correctamente");
            System.out.println("Buses cargados: " + empresa.cantidadBuses());
            System.out.println("Viajes cargados: " + empresa.cantidadViajes());
        } catch (Exception e) {
            System.out.println("No se pudieron cargar los datos iniciales: " + e.getMessage());
        }

        System.out.println("=================================");
        System.out.println(" SISTEMA DE TRANSPORTE ");
        System.out.println("=================================");
        System.out.println("1. Ejecutar en consola");
        System.out.println("2. Ejecutar en ventana");
        System.out.print("Seleccione una opción: ");

        int opcionInicial = new java.util.Scanner(System.in).nextInt();

        if (opcionInicial == 1) {
            new ConsolaUI(empresa).iniciar();
        } else if (opcionInicial == 2) {
            new VentanaPrincipal(empresa);
        } else {
            System.out.println("Opción inválida.");
        }
    }
}