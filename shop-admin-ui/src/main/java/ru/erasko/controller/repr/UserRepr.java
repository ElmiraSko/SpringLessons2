package ru.erasko.controller.repr;

import ru.erasko.model.Role;
import ru.erasko.model.User;

import java.io.Serializable;
import java.util.List;

public class UserRepr implements Serializable {

    private Long id;

//    @NotEmpty
    private String name;

//    @NotEmpty
    private String password;

    private Integer age;

    private String phone;

    private String email;

    private String firstName;

    private String lastName;

    private List<Role> roles;

    public UserRepr() {
    }

    public UserRepr(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.age = user.getAge();
        this.phone = user.getPhone();
        this.password = user.getPassword();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.roles = user.getRoles();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "UserRepr{" +
                "id=" + id +
                ", username='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
