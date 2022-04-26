package Exceptions.enter;

public class NoSuchUserException extends EnterException {
    @Override
    public String getMessage() {
        return "No such user";
    }
}
