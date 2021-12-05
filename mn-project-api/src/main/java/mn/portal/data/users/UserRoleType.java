package mn.portal.data.users;

public enum UserRoleType {
    Root(1),
    Admin(2),
    Responsible(3),
    Guest(4);

    private int userRoleTypeId;
    UserRoleType(int value) {
        this.userRoleTypeId = value;
    }

    public int getUserRoleTypeId() {
        return this.userRoleTypeId;
    }
}
