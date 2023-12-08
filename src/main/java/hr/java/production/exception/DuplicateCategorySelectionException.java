package hr.java.production.exception;

/**
 * Izuzetak koji se baca kada se unese duplicirani naziv kategorije.
 */
public class DuplicateCategorySelectionException extends Exception{

    /**
     * Konstruktor s porukom izuzetka.
     *
     * @param message Poruka izuzetka.
     */
    public DuplicateCategorySelectionException(String message) {
        super(message);
    }

    /**
     * Konstruktor s porukom i uzrokom izuzetka.
     *
     * @param message Poruka izuzetka.
     * @param cause   Uzrok izuzetka.
     */
    public DuplicateCategorySelectionException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Konstruktor s uzrokom izuzetka.
     *
     * @param cause Uzrok izuzetka.
     */
    public DuplicateCategorySelectionException(Throwable cause) {
        super(cause);
    }
}
