package main.java.com.exceptions;

public class FailedObjectCreationException extends RuntimeException {

    public FailedObjectCreationException() {
        super();
    }

    public FailedObjectCreationException(String message) {
        super(message);
    }

}