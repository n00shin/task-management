package ir.chica.task.exception;

public class InvalidException extends Exception{

    public InvalidException(Object param) {
        super("invalid value (param: " + param + " )");

    }
}