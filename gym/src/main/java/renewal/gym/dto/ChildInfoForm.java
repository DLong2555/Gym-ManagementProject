package renewal.gym.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import renewal.gym.domain.Gender;

import java.time.LocalDateTime;

@ToString
@Getter @Setter
public class ChildInfoForm {

    private String name;
    private Integer age;
    private Gender gender;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private Long memberId;

    @QueryProjection
    public ChildInfoForm(String name, Integer age, Gender gender, LocalDateTime startDate, LocalDateTime endDate, Long memberId) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.startDate = startDate;
        this.endDate = endDate;
        this.memberId = memberId;
    }
}
