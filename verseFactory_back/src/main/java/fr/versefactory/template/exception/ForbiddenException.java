package fr.versefactory.template.exception;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(ErrorMessages message, Object... args) {
        super(message.getMessage(args));
    }
}
