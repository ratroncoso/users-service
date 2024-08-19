package cl.rtroncoso.users_service.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String id) {
        super("No se encuentra usuario id: " + id);
    }
}
