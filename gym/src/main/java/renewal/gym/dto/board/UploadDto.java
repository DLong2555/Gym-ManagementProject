package renewal.gym.dto.board;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UploadDto {

    private String uploadFileName;
    private String storeFileName;

    public UploadDto(String uploadFileName, String storeFileName) {
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
    }
}
