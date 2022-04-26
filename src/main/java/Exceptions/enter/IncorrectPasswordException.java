package Exceptions.enter;

public class IncorrectPasswordException extends EnterException {
    @Override
    public String getMessage() {
        return "Incorrect password";
    }
}
