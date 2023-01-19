package com.kjh.studymember.entities.content;

import java.util.Objects;

public class GenreEntity {
    public static final String ATTRIBUTE_NAME = "genre";
    public static final String ATTRIBUTE_NAME_PLURAL = "genres";


    public GenreEntity build() {
        return new GenreEntity();
    }

    private int value;
    private String text;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenreEntity that = (GenreEntity) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public int getValue() {
        return value;
    }

    public GenreEntity setValue(int value) {
        this.value = value;
        return this;
    }

    public String getText() {
        return text;
    }

    public GenreEntity setText(String text) {
        this.text = text;
        return this;
    }
}
