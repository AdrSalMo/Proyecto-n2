package transporte.excepciones;

// Excepción personalizada para indicar que los datos ingresados no son válidos
public class DatosInvalidosException extends Exception {

    public DatosInvalidosException() {
        super("Los datos ingresados no son válidos");
    }

    public DatosInvalidosException(String mensaje) {
        super(mensaje);
    }
}