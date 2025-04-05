package renewal.gym.dto.event;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@ToString
@Getter @Setter
public class ManageEventForm {

    private Long id;
    private String title;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime deadline;

    @QueryProjection
    public ManageEventForm(Long id, String title , LocalDateTime deadline) {
        this.id = id;
        this.title = title;
        this.deadline = deadline;
    }
}
