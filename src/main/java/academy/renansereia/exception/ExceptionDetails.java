package academy.renansereia.exception;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalTime;

@Data
@SuperBuilder
public class ExceptionDetails {

    protected String title;
    protected int status;
    protected String details;
    protected String developerMessage;
    protected LocalTime localTime;

}
