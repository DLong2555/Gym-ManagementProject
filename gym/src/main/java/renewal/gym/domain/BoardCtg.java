package renewal.gym.domain;

public enum BoardCtg {

    Announcement("공지"), FreeTalk("자유"), Questions("문의"), Trade("거래");

    private final String ctgName;

    private BoardCtg(String ctgName) {
        this.ctgName = ctgName;
    }
}
