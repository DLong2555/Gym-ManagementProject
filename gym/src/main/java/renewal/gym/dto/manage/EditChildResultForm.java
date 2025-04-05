package renewal.gym.dto.manage;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class EditChildResultForm {

    private List<Long> successIds = new ArrayList<>();
    private List<Long> failIds = new ArrayList<>();
}
