package fr.versefactory.template.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(ErrorMessages message, Object... args) {
        super(message.getMessage(args));
    }
}
