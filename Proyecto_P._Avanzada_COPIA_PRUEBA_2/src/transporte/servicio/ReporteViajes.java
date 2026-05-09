package transporte.servicio;

import transporte.modelo.*;
import java.util.ArrayList;

public class ReporteViajes {

    public static void mostrarReporte(ArrayList<Viaje> viajes) {

        if (viajes.isEmpty()) {
            System.out.println("No hay viajes para mostrar");
            return;
        }

        System.out.println("\n===== REPORTE DE VIAJES =====");

        double totalIngresos = 0;
        double totalCostos = 0;

        for (Viaje v : viajes) {

            double ingresos = v.calcularIngresos();
            double costo = v.getCostoViaje();

            totalIngresos += ingresos;
            totalCostos += costo;

            System.out.println("-----------------------------");
            System.out.println("ID: " + v.getId());
            System.out.println("Destino: " + v.getDestino());
            System.out.println("Horario: " + v.getHorario());
            System.out.println("Pasajeros: " + v.getPasajeros().size());
            System.out.println("Ingresos: " + ingresos);
            System.out.println("Costo: " + costo);
            System.out.println("Resultado: " + (ingresos - costo));
            System.out.println("Rentable: " + (v.esRentable() ? "SI" : "NO"));
        }

        System.out.println("=============================");
        System.out.println("TOTAL INGRESOS: " + totalIngresos);
        System.out.println("TOTAL COSTOS: " + totalCostos);
        System.out.println("GANANCIA TOTAL: " + (totalIngresos - totalCostos));
        System.out.println("=============================");
    }
}
