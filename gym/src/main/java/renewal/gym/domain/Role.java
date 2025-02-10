package renewal.gym.domain;

public enum Role {
    ADMIN("Role_관리자"), MANAGER("Role_관장"), USER("Role_유저");

    private final String roleName;

    private Role(String roleName) {
        this.roleName = roleName;
    }
}
