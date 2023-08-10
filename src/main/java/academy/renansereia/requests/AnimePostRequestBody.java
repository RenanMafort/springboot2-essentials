package academy.renansereia.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimePostRequestBody{
    @NotNull(message = "The anime name cannot be null")
    @NotEmpty(message = "The anime name cannot be empty")
    private String name;

}
