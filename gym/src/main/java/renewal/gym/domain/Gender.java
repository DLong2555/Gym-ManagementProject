package renewal.gym.domain;

public enum Gender {
    MALE("남자"), FEMALE("여자");

    private final String gender;

    private Gender(String gender) {
        this.gender = gender;
    }
}
