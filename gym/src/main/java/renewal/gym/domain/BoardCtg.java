package renewal.gym.domain;

public enum BoardCtg {

    Announcement("공지");

    private final String ctgName;

    private BoardCtg (String ctgName) {
        this.ctgName = ctgName;
    }

    @Override
    public String toString() {
        return this.ctgName;
    }
}
