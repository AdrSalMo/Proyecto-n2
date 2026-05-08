package transporte.excepciones;

/*
 * Clase base para todas las excepciones personalizadas en el sistema de transporte.
 */
public class TransporteException extends Exception {

    public TransporteException(String mensaje) {
        super(mensaje);
    }

    public TransporteException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}