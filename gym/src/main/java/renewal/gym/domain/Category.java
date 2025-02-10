package renewal.gym.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Category {

    @Id @GeneratedValue
    @Column(name = "cgt_id")
    private Long id;

    private String cgtName;

}
