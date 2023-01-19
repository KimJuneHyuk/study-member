package com.kjh.studymember.entities.member;

import java.util.Objects;

public class TelecomEntity {
    public TelecomEntity build() {
        return new TelecomEntity();
    }

    public static final String ATTRIBUTE_NAME = "telecom";
    public static final String ATTRIBUTE_NAME_PLURAL = "telecoms";

    private String value;
    private String text;

    public TelecomEntity() {
    }

    public TelecomEntity(String value, String text) {
        this.value = value;
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TelecomEntity that = (TelecomEntity) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }


    public String getValue() {
        return value;
    }

    public TelecomEntity setValue(String value) {
        this.value = value;
        return this;
    }

    public String getText() {
        return text;
    }

    public TelecomEntity setText(String text) {
        this.text = text;
        return this;
    }
}
