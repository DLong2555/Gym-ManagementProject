package renewal.gym.dto.register;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class ParentInfoForm {

    private String name;
    private String phoneNumber;

    @QueryProjection
    public ParentInfoForm(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }
}
