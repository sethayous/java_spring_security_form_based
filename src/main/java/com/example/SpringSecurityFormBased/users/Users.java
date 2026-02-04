package com.example.SpringSecurityFormBased.users;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class Users implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private UsersAndRole usersAndRole;

    private boolean isLocked = false;
    private boolean isEnabled = false;

    public Users() {
    }

    public Users(String firstName, String lastName, String email, String password, UsersAndRole usersAndRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.usersAndRole = usersAndRole;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UsersAndRole getUsersAndRole() {
        return usersAndRole;
    }

    public void setUsersAndRole(UsersAndRole usersAndRole) {
        this.usersAndRole = usersAndRole;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Users users = (Users) o;
        return isLocked == users.isLocked && isEnabled == users.isEnabled && Objects.equals(id, users.id) && Objects.equals(firstName, users.firstName) && Objects.equals(lastName, users.lastName) && Objects.equals(email, users.email) && Objects.equals(password, users.password) && usersAndRole == users.usersAndRole;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, password, usersAndRole, isLocked, isEnabled);
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", usersAndRole=" + usersAndRole +
                ", isLocked=" + isLocked +
                ", isEnabled=" + isEnabled +
                '}';
    }
}