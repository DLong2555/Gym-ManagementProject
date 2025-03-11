package renewal.gym.dto.board;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter @Setter
public class BoardWriteForm {

    private Long gymId;
    private String title;
    private String content;
//    private String ctg;

}
