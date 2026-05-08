package transporte.excepciones;

// Excepción personalizada para indicar que un viaje no fue encontrado
public class ViajeNoEncontradoException extends Exception {

    public ViajeNoEncontradoException() {
        super("Viaje no encontrado");
    }

    public ViajeNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}