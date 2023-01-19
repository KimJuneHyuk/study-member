package com.kjh.studymember.entities.content;

import java.util.Objects;

public class FeatureEntity  {
    public static final String ATTRIBUTE_NAME = "feature";
    public static final String ATTRIBUTE_NAME_PLURAL = "features";


    public FeatureEntity build() {
        return new FeatureEntity();
    }
    private int value;
    private String text;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FeatureEntity that = (FeatureEntity) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public int getValue() {
        return value;
    }

    public FeatureEntity setValue(int value) {
        this.value = value;
        return this;
    }

    public String getText() {
        return text;
    }

    public FeatureEntity setText(String text) {
        this.text = text;
        return this;
    }
}
