package fr.versefactory.template.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(ErrorMessages message, Object... args) {
        super(message.getMessage(args));
    }
}
