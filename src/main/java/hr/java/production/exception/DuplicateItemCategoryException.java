package hr.java.production.exception;

public class DuplicateItemCategoryException extends RuntimeException{

    public DuplicateItemCategoryException(String message) {
        super(message);
    }

    public DuplicateItemCategoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateItemCategoryException(Throwable cause) {
        super(cause);
    }
}
