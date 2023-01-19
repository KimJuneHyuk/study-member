package com.kjh.studymember.entities.member;

import java.util.Date;

public class EmailVerificationKeyEntity {
    public static  EmailVerificationKeyEntity build() {
        return new EmailVerificationKeyEntity();
    }



    private String userEmail;
    private String key;
    private Date createdAt;
    private Date expiresAt;
    private boolean isExpired;



    public String getUserEmail() {
        return userEmail;
    }

    public EmailVerificationKeyEntity setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public String getKey() {
        return key;
    }

    public EmailVerificationKeyEntity setKey(String key) {
        this.key = key;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public EmailVerificationKeyEntity setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public EmailVerificationKeyEntity setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
        return this;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public EmailVerificationKeyEntity setExpired(boolean expired) {
        isExpired = expired;
        return this;
    }
}
