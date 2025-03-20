package renewal.gym.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@DiscriminatorValue("EVENT")
public class Event extends Board{

    private Integer price;
    private LocalDateTime deadline;

    @OneToMany(mappedBy = "event")
    private List<EventChild> eventChild = new ArrayList<>();

    public Event(Manager manager, Gym gym, String title, String content, Integer price, LocalDateTime deadline) {
        super(manager, gym, title, content);
        this.price = price;
        this.deadline = deadline;
    }

    public void updateEvent(String title, String content, Integer price, LocalDateTime deadline) {
        super.updateBoard(title, content);
        this.price = price;
        this.deadline = deadline;
    }
}
