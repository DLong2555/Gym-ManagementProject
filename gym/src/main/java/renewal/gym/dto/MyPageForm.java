package renewal.gym.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import renewal.gym.controller.argument.PhoneNumberFormatter;
import renewal.gym.domain.Address;

@Getter @Setter
public class MyPageForm {

    private String name;

    @PhoneNumberFormatter
    private String phoneNumber;

    private Address address;

    @QueryProjection
    public MyPageForm(String name, String phoneNumber, Address address) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }
}
