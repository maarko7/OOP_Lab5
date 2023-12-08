package hr.java.production.exception;

public class IllegalPaperFormatException extends RuntimeException{

    public IllegalPaperFormatException(String message) {
        super(message);
    }
}
