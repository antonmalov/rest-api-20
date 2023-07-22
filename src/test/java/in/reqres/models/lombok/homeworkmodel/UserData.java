package in.reqres.models.lombok.homeworkmodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import in.reqres.models.lombok.homeworkmodel.User;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserData {
    User data;
}
