package com.kjh.studymember.entities.content;

import java.util.Objects;

public class TypeEntity {
    public static final String ATTRIBUTE_NAME = "type";
    public static final String ATTRIBUTE_NAME_PLURAL = "types";


    public TypeEntity build() {
        return new TypeEntity();
    }
    private String value;
    private String text;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeEntity that = (TypeEntity) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public String getValue() {
        return value;
    }

    public TypeEntity setValue(String value) {
        this.value = value;
        return this;
    }

    public String getText() {
        return text;
    }

    public TypeEntity setText(String text) {
        this.text = text;
        return this;
    }
}
