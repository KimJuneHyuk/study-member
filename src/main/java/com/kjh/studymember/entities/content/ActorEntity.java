package com.kjh.studymember.entities.content;

import java.util.Objects;

public class ActorEntity {
    public static final String ATTRIBUTE_NAME = "actor";
    public static final String ATTRIBUTE_NAME_PLURAL = "actors";

    public ActorEntity build() {
        return new ActorEntity();
    }

    private String value;
    private String text;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActorEntity that = (ActorEntity) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public String getValue() {
        return value;
    }

    public ActorEntity setValue(String value) {
        this.value = value;
        return this;
    }

    public String getText() {
        return text;
    }

    public ActorEntity setText(String text) {
        this.text = text;
        return this;
    }
}
