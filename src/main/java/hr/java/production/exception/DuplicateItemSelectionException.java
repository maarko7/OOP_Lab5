package hr.java.production.exception;

/**
 * Izuzetak koji se baca kada se unese duplicirani artikl.
 */
public class DuplicateItemSelectionException extends Exception{

    /**
     * Konstruktor s porukom izuzetka.
     *
     * @param message Poruka izuzetka.
     */
    public DuplicateItemSelectionException(String message) {
        super(message);
    }

    /**
     * Konstruktor s porukom i uzrokom izuzetka.
     *
     * @param message Poruka izuzetka.
     * @param cause   Uzrok izuzetka.
     */
    public DuplicateItemSelectionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Konstruktor s uzrokom izuzetka.
     *
     * @param cause Uzrok izuzetka.
     */
    public DuplicateItemSelectionException(Throwable cause) {
        super(cause);
    }
}
