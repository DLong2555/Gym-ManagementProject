package renewal.gym.domain;

public enum BoardCtg {

    ANNOUNCEMENT("공지"), EVENT("활동");

    private final String ctgName;

    private BoardCtg (String ctgName) {
        this.ctgName = ctgName;
    }

    @Override
    public String toString() {
        return this.ctgName;
    }
}
