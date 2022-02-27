package ir.chica.task.exception;


public class RecordNotFoundException extends Exception {
    public RecordNotFoundException(Object param) {
        super("Record not found (param: " + param + " )");
    }

}
