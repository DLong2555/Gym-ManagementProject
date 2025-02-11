package renewal.gym.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@Getter @Setter
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class NaverSearchResultForm {

    private Long gymId;
    private String title;
    private String address;
    private double mapx;
    private double mapy;

    public NaverSearchResultForm(Long gymId, String title, String address, double mapx, double mapy) {
        this.gymId = gymId;
        this.title = title;
        this.address = address;
        this.mapx = mapx;
        this.mapy = mapy;
    }
}
