package com.kjh.studymember.entities.member;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Objects;

public class PaymentEntity {
    public PaymentEntity build() {
        return new PaymentEntity();
    }

    private int index;
    private String userEmail;
    private String name;
    private String contactTelecom;
    private String contactFirst;
    private String contactSecond;
    private String contactThird;
    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date    birth;
    private int     gender;
    private String addressPostal;
    private String addressPrimary;
    private String addressSecondary;
    private String cardNumberFirst;
    private String cardNumberSecond;
    private String cardNumberThird;
    private String cardNumberFourth;
    private String cardNumberExpM;
    private String cardNumberExpY;
    private String cardNumberCvc;
    private Date    createdAt = new Date();
    private boolean isDefault;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentEntity that = (PaymentEntity) o;
        return index == that.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }

    public int getIndex() {
        return index;
    }

    public PaymentEntity setIndex(int index) {
        this.index = index;
        return this;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public PaymentEntity setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        return this;
    }

    public String getName() {
        return name;
    }

    public PaymentEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getContactTelecom() {
        return contactTelecom;
    }

    public PaymentEntity setContactTelecom(String contactTelecom) {
        this.contactTelecom = contactTelecom;
        return this;
    }

    public String getContactFirst() {
        return contactFirst;
    }

    public PaymentEntity setContactFirst(String contactFirst) {
        this.contactFirst = contactFirst;
        return this;
    }

    public String getContactSecond() {
        return contactSecond;
    }

    public PaymentEntity setContactSecond(String contactSecond) {
        this.contactSecond = contactSecond;
        return this;
    }

    public String getContactThird() {
        return contactThird;
    }

    public PaymentEntity setContactThird(String contactThird) {
        this.contactThird = contactThird;
        return this;
    }

    public Date getBirth() {
        return birth;
    }

    public PaymentEntity setBirth(Date birth) {
        this.birth = birth;
        return this;
    }

    public int getGender() {
        return gender;
    }

    public PaymentEntity setGender(int gender) {
        this.gender = gender;
        return this;
    }

    public String getAddressPostal() {
        return addressPostal;
    }

    public PaymentEntity setAddressPostal(String addressPostal) {
        this.addressPostal = addressPostal;
        return this;
    }

    public String getAddressPrimary() {
        return addressPrimary;
    }

    public PaymentEntity setAddressPrimary(String addressPrimary) {
        this.addressPrimary = addressPrimary;
        return this;
    }

    public String getAddressSecondary() {
        return addressSecondary;
    }

    public PaymentEntity setAddressSecondary(String addressSecondary) {
        this.addressSecondary = addressSecondary;
        return this;
    }

    public String getCardNumberFirst() {
        return cardNumberFirst;
    }

    public PaymentEntity setCardNumberFirst(String cardNumberFirst) {
        this.cardNumberFirst = cardNumberFirst;
        return this;
    }

    public String getCardNumberSecond() {
        return cardNumberSecond;
    }

    public PaymentEntity setCardNumberSecond(String cardNumberSecond) {
        this.cardNumberSecond = cardNumberSecond;
        return this;
    }

    public String getCardNumberThird() {
        return cardNumberThird;
    }

    public PaymentEntity setCardNumberThird(String cardNumberThird) {
        this.cardNumberThird = cardNumberThird;
        return this;
    }

    public String getCardNumberFourth() {
        return cardNumberFourth;
    }

    public PaymentEntity setCardNumberFourth(String cardNumberFourth) {
        this.cardNumberFourth = cardNumberFourth;
        return this;
    }

    public String getCardNumberExpM() {
        return cardNumberExpM;
    }

    public PaymentEntity setCardNumberExpM(String cardNumberExpM) {
        this.cardNumberExpM = cardNumberExpM;
        return this;
    }

    public String getCardNumberExpY() {
        return cardNumberExpY;
    }

    public PaymentEntity setCardNumberExpY(String cardNumberExpY) {
        this.cardNumberExpY = cardNumberExpY;
        return this;
    }

    public String getCardNumberCvc() {
        return cardNumberCvc;
    }

    public PaymentEntity setCardNumberCvc(String cardNumberCvc) {
        this.cardNumberCvc = cardNumberCvc;
        return this;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public PaymentEntity setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
        return this;
    }
    public boolean isDefault() {
        return isDefault;
    }

    public PaymentEntity setDefault(boolean aDefault) {
        isDefault = aDefault;
        return this;
    }
}
