package com.kjh.studymember.mappers;

import com.kjh.studymember.entities.content.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface IMediaMapper {
    RatingEntity selectRatingByValue(@Param(value = "value") String value);
    TypeEntity selectTypeByValue(@Param(value = "value") String value);
    GenreEntity[] selectGenresByMediaIndex(@Param(value = "mediaIndex") int mediaIndex);
    FeatureEntity[] selectFeaturesByMediaIndex(@Param(value = "mediaIndex") int mediaIndex);
    ActorEntity[] selectActorsByMediaIndex(@Param(value = "mediaIndex") int mediaIndex);
    MediaEntity selectMediaByIndex(@Param(value = "index") int index);


}
