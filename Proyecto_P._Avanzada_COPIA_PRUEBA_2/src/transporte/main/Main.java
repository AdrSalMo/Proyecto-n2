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

        // Primero intenta cargar lo que se haya editado antes
        empresa.cargarDatos("datos.dat");

        // Si no hay nada guardado (primera ejecución), carga los CSV originales
        if (empresa.cantidadBuses() == 0) {
            try {
                empresa.cargarDesdeCSV();
                System.out.println("Datos base cargados desde CSV.");
            } catch (Exception e) {
                System.out.println("Error en carga inicial: " + e.getMessage());
            }
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
