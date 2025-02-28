package renewal.gym.dto.mypage;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import renewal.gym.controller.argument.PhoneNumberFormatter;
import renewal.gym.domain.Gender;

import java.time.LocalDate;

@Getter @Setter
public class myChildForm {

    private Long id;
    private String name;

    @PhoneNumberFormatter
    private String phoneNumber;

    private Integer age;
    private Gender gender;
    private String gymName;
    private String belt;
    private LocalDate startDate;
    private LocalDate endDate;

    @QueryProjection
    public myChildForm(Long id, String name, String phoneNumber, Integer age, Gender gender, String gymName, String belt, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.gender = gender;
        this.gymName = gymName;
        this.belt = belt;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
