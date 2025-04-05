package renewal.gym.domain;

import lombok.Getter;

@Getter
public enum Gender {
    MALE("남자"), FEMALE("여자");

    private final String value;

    private Gender(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
