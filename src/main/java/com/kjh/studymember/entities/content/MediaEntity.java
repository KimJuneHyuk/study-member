package com.kjh.studymember.entities.content;

import java.util.Date;
import java.util.Objects;

public class MediaEntity {
    public static final String ATTRIBUTE_NAME = "medium";
    public static final String ATTRIBUTE_NAME_PLURAL = "media";

    public static MediaEntity build() {
        return new MediaEntity();
    }

    private int index;
    private String title;
    private String description;
    private Date publishedYear;
    private boolean isFhd;
    private boolean isUhd;
    private boolean isCommentary;
    private byte[] logoImage;
    private byte[] thumbnailImage;
    private byte[] teaserVideo;
    private String ratingValue;
    private String typeValue;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MediaEntity that = (MediaEntity) o;
        return index == that.index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }

    public int getIndex() {
        return index;
    }

    public MediaEntity setIndex(int index) {
        this.index = index;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public MediaEntity setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public MediaEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public Date getPublishedYear() {
        return publishedYear;
    }

    public MediaEntity setPublishedYear(Date publishedYear) {
        this.publishedYear = publishedYear;
        return this;
    }

    public boolean isFhd() {
        return isFhd;
    }

    public MediaEntity setFhd(boolean fhd) {
        isFhd = fhd;
        return this;
    }

    public boolean isUhd() {
        return isUhd;
    }

    public MediaEntity setUhd(boolean uhd) {
        isUhd = uhd;
        return this;
    }

    public boolean isCommentary() {
        return isCommentary;
    }

    public MediaEntity setCommentary(boolean commentary) {
        isCommentary = commentary;
        return this;
    }

    public byte[] getLogoImage() {
        return logoImage;
    }

    public MediaEntity setLogoImage(byte[] logoImage) {
        this.logoImage = logoImage;
        return this;
    }

    public byte[] getThumbnailImage() {
        return thumbnailImage;
    }

    public MediaEntity setThumbnailImage(byte[] thumbnailImage) {
        this.thumbnailImage = thumbnailImage;
        return this;
    }

    public byte[] getTeaserVideo() {
        return teaserVideo;
    }

    public MediaEntity setTeaserVideo(byte[] teaserVideo) {
        this.teaserVideo = teaserVideo;
        return this;
    }

    public String getRatingValue() {
        return ratingValue;
    }

    public MediaEntity setRatingValue(String ratingValue) {
        this.ratingValue = ratingValue;
        return this;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public MediaEntity setTypeValue(String typeValue) {
        this.typeValue = typeValue;
        return this;
    }
}
