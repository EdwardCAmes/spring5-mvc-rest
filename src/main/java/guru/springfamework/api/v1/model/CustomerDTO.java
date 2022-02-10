package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CustomerDTO {

    private Long id;
    private String firstName;
    @JsonProperty("lastName")
    private String lastName;
}
