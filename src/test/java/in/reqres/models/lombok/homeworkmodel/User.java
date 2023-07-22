package in.reqres.models.lombok.homeworkmodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class User {
    Integer id;
    String email;

    @JsonProperty("first_name")
    String firstName;

    @JsonProperty("last_name")
    private String lastName;

    String avatar;
}
