package fr.versefactory.template.exception;

public class MethodNotAllowedException extends RuntimeException {
    public MethodNotAllowedException(ErrorMessages message, Object... args) {
        super(message.getMessage(args));
    }
}
