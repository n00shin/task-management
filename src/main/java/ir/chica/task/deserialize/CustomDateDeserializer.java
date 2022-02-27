package ir.chica.task.deserialize;


import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@JsonComponent
public class CustomDateDeserializer extends JsonDeserializer {
    private static SimpleDateFormat simpleDateFormat =
            new SimpleDateFormat( "dd-MM-yyyy hh:mm:ss" );

    public CustomDateDeserializer() {
        this( null );
    }

    public CustomDateDeserializer(Class<?> c) {
        super();
    }

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext
            deserializationContext) throws IOException, JacksonException {
        String date = jsonParser.getText();
        try {
            return simpleDateFormat.parse( date );
        } catch (ParseException e) {
            throw new RuntimeException( e );
        }
    }
}