package renewal.gym.domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import renewal.gym.domain.Category;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
public class Item {

    @Id @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private int prdPrice;
    private int quantity;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}
