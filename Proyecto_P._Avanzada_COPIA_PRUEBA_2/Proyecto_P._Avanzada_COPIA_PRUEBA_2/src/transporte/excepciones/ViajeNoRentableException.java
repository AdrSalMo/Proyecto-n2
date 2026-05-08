package transporte.excepciones;

// Excepción personalizada para indicar que un viaje no es rentable
public class ViajeNoRentableException extends Exception {

    // Constructor por defecto
    public ViajeNoRentableException() {
        super("El viaje no es rentable");
    }

    // Constructor con mensaje personalizado
    public ViajeNoRentableException(String mensaje) {
        super(mensaje);
    }
}