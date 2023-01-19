package com.kjh.studymember.services;

import com.kjh.studymember.entities.content.*;
import com.kjh.studymember.mappers.IMediaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "com.kjh.studymember.services.ContentService")
public class MediaService {
    private final IMediaMapper mediaMapper;


    @Autowired
    public MediaService(IMediaMapper mediaMapper) {
        this.mediaMapper = mediaMapper;
    }

    public ActorEntity[] getActors(MediaEntity media) {
        return this.mediaMapper.selectActorsByMediaIndex(media.getIndex());
    }
    public FeatureEntity[] getFeatures(MediaEntity media) {
        return this.mediaMapper.selectFeaturesByMediaIndex(media.getIndex());
    }
    public GenreEntity[] getGenres(MediaEntity media) {
        return this.mediaMapper.selectGenresByMediaIndex(media.getIndex());
    }
    public RatingEntity getRating(MediaEntity media) {
        return this.mediaMapper.selectRatingByValue(media.getRatingValue());
    }
    public TypeEntity getType(MediaEntity media) {
        return this.mediaMapper.selectTypeByValue(media.getTypeValue());
    }
    public MediaEntity getMedia(int mediaIndex) {
        return this.mediaMapper.selectMediaByIndex(mediaIndex);
    }
}
