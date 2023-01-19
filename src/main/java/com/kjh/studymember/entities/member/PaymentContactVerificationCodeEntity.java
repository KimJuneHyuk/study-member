package com.kjh.studymember.entities.member;

import java.util.Date;
import java.util.Objects;

public class PaymentContactVerificationCodeEntity {
    public static PaymentContactVerificationCodeEntity build() {
        return new PaymentContactVerificationCodeEntity();
    }

    private String contactFirst;
    private String contactSecond;
    private String contactThird;
    private String code;
    private String salt;
    private Date createdAt = new Date();
    private Date expiresAt;
    private boolean isExpired = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentContactVerificationCodeEntity that = (PaymentContactVerificationCodeEntity) o;
        return Objects.equals(contactFirst, that.contactFirst) &&
                Objects.equals(contactSecond, that.contactSecond) &&
                Objects.equals(contactThird, that.contactThird) &&
                Objects.equals(code, that.code) &&
                Objects.equals(salt, that.salt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contactFirst, contactSecond, contactThird, code, salt);
    }

    public PaymentContactVerificationCodeEntity() {
    }

    public PaymentContactVerificationCodeEntity(String contactFirst, String contactSecond, String contactThird, String code, String salt, Date createdAt, Date expiresAt, boolean isExpired) {
        this.contactFirst = contactFirst;
        this.contactSecond = contactSecond;
        this.contactThird = contactThird;
        this.code = code;
        this.salt = salt;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.isExpired = isExpired;
    }

    public String getContactFirst() {
        return contactFirst;
    }

    public PaymentContactVerificationCodeEntity setContactFirst(String contactFirst) {
        this.contactFirst = contactFirst;
        return this;
    }

    public String getContactSecond() {
        return contactSecond;
    }

    public PaymentContactVerificationCodeEntity setContactSecond(String contactSecond) {
        this.contactSecond = contactSecond;
        return this;
    }

    public String getContactThird() {
        return contactThird;
    }

    public PaymentContactVerificationCodeEntity setContactThird(String contactThird) {
        this.contactThird = contactThird;
        return this;
    }

    public String getCode() {
        return code;
    }

    public PaymentContactVerificationCodeEntity setCode(String code) {
        this.code = code;
        return this;
    }

    public String getSalt() {
        return salt;
    }

    public PaymentContactVerificationCodeEntity setSalt(String salt) {
        this.salt = salt;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public PaymentContactVerificationCodeEntity setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public PaymentContactVerificationCodeEntity setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
        return this;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public PaymentContactVerificationCodeEntity setExpired(boolean expired) {
        isExpired = expired;
        return this;
    }
}
