package renewal.gym.domain;

import jakarta.persistence.*;
import lombok.Getter;
import renewal.gym.domain.item.Item;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
public class OrderItem {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;


}
