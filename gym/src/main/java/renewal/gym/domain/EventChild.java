package renewal.gym.domain;



import jakarta.persistence.*;

import static jakarta.persistence.FetchType.LAZY;

@Entity
public class EventChild {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "event_id")
    private Event event;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "child_id")
    private Child child;

}
