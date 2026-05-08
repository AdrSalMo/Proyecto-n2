package transporte.excepciones;

// Excepción personalizada para indicar que un viaje no tiene pasajeros
public class ViajeSinPasajerosException extends Exception {

    public ViajeSinPasajerosException() {
        super("El viaje no tiene pasajeros registrados");
    }

    public ViajeSinPasajerosException(String mensaje) {
        super(mensaje);
    }
}