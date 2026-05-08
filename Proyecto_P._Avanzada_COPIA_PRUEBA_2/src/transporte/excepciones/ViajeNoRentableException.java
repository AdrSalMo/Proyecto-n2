package transporte.excepciones;

public class ViajeNoRentableException extends TransporteException {

    public ViajeNoRentableException() {
        super("El viaje no es rentable");
    }

    public ViajeNoRentableException(String mensaje) {
        super(mensaje);
    }
}