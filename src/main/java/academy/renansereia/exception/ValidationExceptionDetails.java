package academy.renansereia.exception;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class ValidationExceptionDetails extends ExceptionDetails{
    private final String fields;
    private final String fieldsMessages;
}
