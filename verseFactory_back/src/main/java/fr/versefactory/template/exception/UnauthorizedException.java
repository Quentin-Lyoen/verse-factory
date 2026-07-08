package fr.versefactory.template.exception;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(ErrorMessages message, Object... args) {
        super(message.getMessage(args));
    }
}
