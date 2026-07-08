package fr.versefactory.template.exception;

public class NotImplementedException extends RuntimeException {
    public NotImplementedException(ErrorMessages message, Object... args) {
        super(message.getMessage(args));
    }
}
