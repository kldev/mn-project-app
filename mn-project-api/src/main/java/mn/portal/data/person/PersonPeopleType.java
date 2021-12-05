package mn.portal.data.person;

public enum PersonPeopleType {
    User(1),
    Guest(2),
    CompanyEmployee(3);

    private int personType;
    PersonPeopleType(int value) {
        this.personType = value;
    }

    public int getPersonType() {
        return this.personType;
    }
}
