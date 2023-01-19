package com.kjh.studymember.entities.member;

import java.util.Date;
import java.util.Objects;

public class UserEntity {
    public static final String ATTRIBUTE_NAME = "user";

    public static UserEntity build() {
        return new UserEntity();
    }

    private String email;
    private String password;
    private String name;
    private Date createdAt;
    private boolean isEmailVerified;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity user = (UserEntity) o;
        return Objects.equals(email, user.email);
    }
    @Override
    public final int hashCode() {
        return Objects.hash(email);
    }


    public String getEmail() {
        return email;
    }

    public UserEntity setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getName() {
        return name;
    }

    public UserEntity setName(String name) {
        this.name = name;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public UserEntity setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public boolean isEmailVerified() {
        return isEmailVerified;
    }

    public UserEntity setEmailVerified(boolean emailVerified) {
        isEmailVerified = emailVerified;
        return this;
    }
}
