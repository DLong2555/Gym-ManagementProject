package renewal.gym.domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Period {

    private LocalDate startDate;
    private LocalDate endDate;

    public void changeStartDate() {
        this.startDate = LocalDate.now();
    }

    public void changeEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

}
