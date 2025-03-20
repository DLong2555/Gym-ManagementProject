package renewal.gym.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

@Getter @Setter
public class RegularPaymentForm {

    private Long id;
    private String name;

    private String gymName;

    @NumberFormat(pattern = "#,###")
    private Integer price;

    @QueryProjection
    public RegularPaymentForm(Long id, String name, String gymName, Integer price) {
        this.id = id;
        this.name = name;
        this.gymName = gymName;
        this.price = price;
    }
}
