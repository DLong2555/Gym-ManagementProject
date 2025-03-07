package renewal.gym.domain;

public enum PayStatus {

    READY("REDY"),IN_PROGRESS("IN_PROGRESS"),DONE("DONE"),CANCELED("CANCELED"),
    ABORTED("ABORTED"),EXPIRED("EXPIRED");

    private String description;

    PayStatus(String description) {
        this.description = description;
    }
}
