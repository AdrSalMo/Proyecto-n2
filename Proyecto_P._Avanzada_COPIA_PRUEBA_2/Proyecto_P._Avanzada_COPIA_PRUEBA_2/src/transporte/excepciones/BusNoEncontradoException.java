package transporte.excepciones;

// Excepción personalizada para indicar que un bus no fue encontrado
public class BusNoEncontradoException extends Exception {

    // Constructor vacío
    public BusNoEncontradoException() {
        super("El bus no fue encontrado en el sistema");
    }

    // Constructor con mensaje personalizado
    public BusNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}