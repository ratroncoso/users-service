package cl.rtroncoso.users_service.exception;

public class EmailDuplicatedException extends RuntimeException {

    private int code;

    public EmailDuplicatedException(String message, int code) {
        super(message);
        this.code = code;
    }
}
