package com.kjh.studymember.entities.content;

import java.util.Objects;

public class RatingEntity {
    public static final String ATTRIBUTE_NAME = "rating";
    public static final String ATTRIBUTE_NAME_PLURAL = "ratings";

    public RatingEntity build() {
        return new RatingEntity();
    }
    private String value;
    private String text;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RatingEntity that = (RatingEntity) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public String getValue() {
        return value;
    }

    public RatingEntity setValue(String value) {
        this.value = value;
        return this;
    }

    public String getText() {
        return text;
    }

    public RatingEntity setText(String text) {
        this.text = text;
        return this;
    }
}
