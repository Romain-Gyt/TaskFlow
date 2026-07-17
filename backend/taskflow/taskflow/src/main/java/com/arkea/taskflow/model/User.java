package com.arkea.taskflow.model;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

@Entity
@Table(name = "users")
public class User {

    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[\\w.+-]+@[\\w-]+\\.[a-zA-Z]{2,}$");

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    protected User() {}
    private User(Builder builder) {
        this.email = builder.email;
        this.password = builder.password;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void changeEmail(String newEmail){
        this.email = validate(newEmail);
    }

    public void changePassword(String newHashedPassword){
        this.password = Objects.requireNonNull(newHashedPassword,"Password is Required");
    }

    private String validate(String email){
        Objects.requireNonNull(email,"email is required");
        if(!EMAIL_PATTERN.matcher(email).matches()){
            throw new IllegalArgumentException("Invalid email format: " + email);
        }
        return email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + email + '\'' +
                '}';
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
       private String email;
       private String password;

       private Builder() {}
        public Builder email(String email) {
           this.email = email;
           return this;
        }
        public Builder password(String password) {
           this.password = password;
           return this;
        }

        public User build() {
            Objects.requireNonNull(email, "email is required");
            Objects.requireNonNull(password, "password is required");
           return new User(this);
        }
    }
}
