package ru.erasko.model;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Это поле обязательное для заполнения")
    @Column(length = 32, nullable = false)
    private String name;

    @Min(value = 16, message = "Минимальное значение поля 10 лет")
    @Max(value = 110, message = "Максимальное значение поля 110 лет")
    @Column(nullable = false)
    private Integer age;

    @NotBlank(message = "Email введен не верно")
    @Column(length = 64, nullable = false)
    private String email;

    @NotBlank(message = "Это поле обязательное для заполнения")
    @Column(length = 128, nullable = false)
    private String password;

    @Column(length = 12, nullable = true)
    private String phone;

    @OneToMany(mappedBy = "user")
    private List<Product> productList;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    public User() {
        System.out.println("Конструктор по умолчанию у Юзера");
    }

    public User(Long id, String name, Integer age, String password) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.password = password;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getRoleName() {
        String str = "";
        for (Role role: this.roles) {
            str = role.getName() + " ";
        }
        return str;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
