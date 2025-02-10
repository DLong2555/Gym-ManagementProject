package renewal.gym.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Period {

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public void changeStartDate() {
        startDate = LocalDateTime.now();
    }

    public void changeEndDate() {
        endDate = LocalDateTime.now();
    }

}
