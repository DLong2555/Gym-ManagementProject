package renewal.gym.domain;

import lombok.Getter;

@Getter
public enum PayType {

    CARD("카드"), CASH("현금"), QUICK("간편결제");

    private String description;

    PayType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
