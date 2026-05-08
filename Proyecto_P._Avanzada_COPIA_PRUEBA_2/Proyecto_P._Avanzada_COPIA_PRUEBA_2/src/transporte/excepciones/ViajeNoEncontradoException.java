package transporte.excepciones;

// Excepción personalizada para indicar que un viaje no fue encontrado
public class ViajeNoEncontradoException extends Exception {

    // Constructor por defecto
    public ViajeNoEncontradoException() {
        super("Viaje no encontrado");
    }

    // Constructor con mensaje personalizado
    public ViajeNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}