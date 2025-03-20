package renewal.gym.dto.manage;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import renewal.gym.controller.argument.PhoneNumberFormatter;
import renewal.gym.domain.Gender;

@ToString
@Getter @Setter
public class EventParticipantForm {

    private String name;
    private Integer age;
    private Gender gender;

    @PhoneNumberFormatter
    private String phone;

    @QueryProjection
    public EventParticipantForm(String name, Integer age, Gender gender, String phone) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.phone = phone;
    }
}
