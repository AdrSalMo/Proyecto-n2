package transporte.excepciones;

// Excepción personalizada para indicar que un viaje no tiene pasajeros
public class ViajeSinPasajerosException extends Exception {

    // Constructor vacío
    public ViajeSinPasajerosException() {
        super("El viaje no tiene pasajeros registrados");
    }

    // Constructor con mensaje personalizado
    public ViajeSinPasajerosException(String mensaje) {
        super(mensaje);
    }
}