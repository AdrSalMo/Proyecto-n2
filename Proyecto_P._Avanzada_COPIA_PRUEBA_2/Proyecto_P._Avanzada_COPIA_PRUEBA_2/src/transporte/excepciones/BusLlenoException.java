package transporte.excepciones;

// Excepción personalizada para indicar que un bus está lleno
public class BusLlenoException extends Exception {

    // Constructor vacío
    public BusLlenoException() {
        super("El bus está lleno");
    }

    // Constructor con mensaje personalizado
    public BusLlenoException(String mensaje) {
        super(mensaje);
    }
}