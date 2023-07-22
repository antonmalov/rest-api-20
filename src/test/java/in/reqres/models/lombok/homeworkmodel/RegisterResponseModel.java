package in.reqres.models.lombok.homeworkmodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class RegisterResponseModel {
    Integer id;
    String token;
}
