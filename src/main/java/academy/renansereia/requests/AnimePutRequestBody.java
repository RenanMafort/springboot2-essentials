package academy.renansereia.requests;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnimePutRequestBody {
    private long id;
    @JsonProperty(namespace = "name")
    private String name;

}
