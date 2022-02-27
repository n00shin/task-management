package ir.chica.task.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionAdvice {
    @ResponseBody
    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String NotFoundHandler(RecordNotFoundException exception) {
        return exception.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(InvalidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String invalidValue(InvalidException invalidExcept) {
        return invalidExcept.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(BlogAPIException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    String invalidValue(BlogAPIException invalidExcept) {
        return invalidExcept.getMessage();

    }
}