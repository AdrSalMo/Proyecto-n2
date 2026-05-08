package transporte.excepciones;

// Excepción personalizada para indicar que los datos ingresados no son válidos
public class DatosInvalidosException extends Exception {

    // Constructor vacío
    public DatosInvalidosException() {
        super("Los datos ingresados no son válidos");
    }

    // Constructor con mensaje personalizado
    public DatosInvalidosException(String mensaje) {
        super(mensaje);
    }
}
