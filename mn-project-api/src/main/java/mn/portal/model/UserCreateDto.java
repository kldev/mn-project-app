package mn.portal.model;

import mn.portal.data.users.UserRoleType;

import java.util.ArrayList;
import java.util.List;

public class UserCreateDto {
    private String email;
    private String firstName;
    private String lastName;
    private String carPlate;
    private String password;

    private List<UserRoleType> roles = new ArrayList<>();

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public List<UserRoleType> getRoles() {
        return roles;
    }

    public void addRole(UserRoleType role) {
        if (this.roles == null) this.roles = new ArrayList<>();
        this.roles.add(role);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
