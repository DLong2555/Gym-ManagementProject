package renewal.gym.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;


import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Gym {

    @Id @GeneratedValue
    @Column(name = "gym_id")
    private Long id;

    private String gymName;
    private Integer gymPrice;
    private String gymPhoneNum;
    private Address address;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "manager_id")
    private Manager manager;

    @OneToMany(mappedBy = "gym")
    private List<Child> children = new ArrayList<>();

    public Gym(String gymName, Integer gymPrice, String gymPhoneNum, Address address, Manager manager) {
        this.gymName = gymName;
        this.gymPrice = gymPrice;
        this.gymPhoneNum = gymPhoneNum;
        this.address = address;
        this.manager = manager;
    }

    public void updateGym(String gymName, Integer gymPrice, String gymPhoneNum, Address address) {
        this.gymName = gymName;
        this.gymPrice = gymPrice;
        this.gymPhoneNum = gymPhoneNum;
        this.address = address;
    }



}
