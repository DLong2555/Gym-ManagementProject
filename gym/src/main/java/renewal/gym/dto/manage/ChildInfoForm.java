package renewal.gym.dto.manage;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;
import renewal.gym.domain.Gender;

import java.time.LocalDate;

@ToString
@Getter @Setter
public class ChildInfoForm {

    private Long id;
    private String name;
    private Integer age;
    private Gender gender;
    private String belt;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    public ChildInfoForm(Long id, String name, Integer age, Gender gender, String belt, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.belt = belt;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
