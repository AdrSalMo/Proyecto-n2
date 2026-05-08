package transporte.excepciones;

public class BusNoEncontradoException extends TransporteException {

    public BusNoEncontradoException() {
        super("El bus no fue encontrado en el sistema");
    }

    public BusNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}