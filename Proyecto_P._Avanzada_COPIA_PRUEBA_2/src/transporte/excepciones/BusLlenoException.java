package transporte.excepciones;

// Excepción personalizada para indicar que un bus está lleno
public class BusLlenoException extends Exception {

    public BusLlenoException() {
        super("El bus está lleno");
    }

    public BusLlenoException(String mensaje) {
        super(mensaje);
    }
}