package com.kjh.studymember.entities.member;

import java.util.Date;
import java.util.Objects;

public class ProfileEntity {
    public static final String ATTRIBUTE_NAME = "profile";
//    복수 네이밍
    public static final String ATTRIBUTE_NAME_PLURAL = "profiles";

    public static ProfileEntity build() {
        return new ProfileEntity();
    }
    private String userEmail;
    private String name;
    private int profileIndex;
    private boolean isKids;
    private Date createdAt;
    private Date lastAccessAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProfileEntity that = (ProfileEntity) o;
        return Objects.equals(userEmail, that.userEmail) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userEmail, name);
    }

    public String getUserEmail() {
        return userEmail;
    }

    public ProfileEntity setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProfileEntity setName(String name) {
        this.name = name;
        return this;
    }

    public int getProfileIndex() {
        return profileIndex;
    }

    public ProfileEntity setProfileIndex(int profileIndex) {
        this.profileIndex = profileIndex;
        return this;
    }

    public boolean isKids() {
        return isKids;
    }

    public ProfileEntity setKids(boolean kids) {
        isKids = kids;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public ProfileEntity setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getLastAccessAt() {
        return lastAccessAt;
    }

    public ProfileEntity setLastAccessAt(Date lastAccessAt) {
        this.lastAccessAt = lastAccessAt;
        return this;
    }
}
