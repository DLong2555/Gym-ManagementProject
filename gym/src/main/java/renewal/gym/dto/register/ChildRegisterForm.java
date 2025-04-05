package renewal.gym.dto.register;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.NumberFormat;

@ToString
@Getter @Setter
public class ChildRegisterForm {

    private Long gymId;
    private String name;
    private String phone;
    private String age;
    private String gender;
    private String gymName;

    @NumberFormat(pattern = "#,###")
    private Integer gymPrice;

    private String orderId;

    public ChildRegisterForm() {
    }

    public ChildRegisterForm(Long gymId, String gymName, Integer gymPrice) {
        this.gymId = gymId;
        this.gymName = gymName;
        this.gymPrice = gymPrice;
    }
}
