package renewal.gym.dto.pay;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentErrorResponse {

    @NotNull
    private Integer code;

    private String message;
}
