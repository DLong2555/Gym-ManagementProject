package renewal.gym.domain;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("admin"), MANAGER("manager"), USER("user");

    private final String value;

    private Role(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
