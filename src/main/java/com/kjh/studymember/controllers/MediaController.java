package com.kjh.studymember.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjh.studymember.IResult;
import com.kjh.studymember.emums.CommonResult;
import com.kjh.studymember.entities.content.*;
import com.kjh.studymember.services.MediaService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;


@Controller(value = "com.kjh.studymember.controllers.MediaController")
@RequestMapping(value = "/media")
public class MediaController {
    private final MediaService mediaService;

    @Autowired
    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @RequestMapping(value = "logo-image", method = RequestMethod.GET, produces = "image/webp")
    @ResponseBody
    public ResponseEntity<byte[]> getLogoImage(@RequestParam(value = "mid", required = true) int mediaIndex,
                                               HttpServletResponse response) {
        MediaEntity media = this.mediaService.getMedia(mediaIndex);
        if (media == null || media.getIndex() < 1) {
            response.setStatus(404);
            return null;
        }
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.OK;
        headers.add("Content-Length", String.valueOf(media.getLogoImage().length));
        headers.add("Content-Type", "image/webp");
        return new ResponseEntity<>(media.getLogoImage(), headers, status);
    }

    @RequestMapping(value = "thumbnail-image", method = RequestMethod.GET, produces = "image/webp")
    @ResponseBody
    public ResponseEntity<byte[]> getThumbnailImage(@RequestParam(value = "mid", required = true) int mediaIndex,
                                                    HttpServletResponse response) {
        MediaEntity media = this.mediaService.getMedia(mediaIndex);
        if (media == null || media.getIndex() < 1) {
            response.setStatus(404);
            return null;
        }
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.OK;
        headers.add("Content-Length", String.valueOf(media.getThumbnailImage().length));
        headers.add("Content-Type", "image/webp");
        return new ResponseEntity<>(media.getThumbnailImage(), headers, status);
    }

    @RequestMapping(value = "teaser-video", method = RequestMethod.GET, produces = "video/webm")
    @ResponseBody
    public ResponseEntity<byte[]> getTeaserVideo(@RequestParam(value = "mid", required = true) int mediaIndex,
                                                 HttpServletResponse response) {
        MediaEntity media = this.mediaService.getMedia(mediaIndex);
        if (media == null || media.getIndex() < 1) {
            response.setStatus(404);
            return null;
        }
        HttpHeaders headers = new HttpHeaders();
        HttpStatus status = HttpStatus.OK;
        headers.add("Content-Length", String.valueOf(media.getTeaserVideo().length));
        headers.add("Content-Type", "video/webm");
        return new ResponseEntity<>(media.getTeaserVideo(), headers, status);
    }

    @RequestMapping(value = "info", method = RequestMethod.PATCH, produces = "application/json")
    @ResponseBody
    public String patchInfo(@RequestParam(value = "mid", required = true) int mediaIndex) throws JsonProcessingException, InterruptedException {
        Thread.sleep(500);
        Enum<? extends IResult> result;
        JSONObject responseJson = new JSONObject();

        MediaEntity media = this.mediaService.getMedia(mediaIndex);
        if (media == null || media.getIndex() < 1) {
            result = CommonResult.FAILURE;
        } else {
            ObjectMapper objectMapper = new ObjectMapper();

            JSONObject mediaJson = new JSONObject(objectMapper.writeValueAsString(media));
            mediaJson.remove("logoImage");
            mediaJson.remove("thumbnailImage");
            mediaJson.remove("teaserVideo");
            responseJson.put(MediaEntity.ATTRIBUTE_NAME, mediaJson);

            ActorEntity[] actors = this.mediaService.getActors(media);
            JSONArray actorsJson = new JSONArray(objectMapper.writeValueAsString(actors));
            responseJson.put(ActorEntity.ATTRIBUTE_NAME_PLURAL, actorsJson);

            FeatureEntity[] features = this.mediaService.getFeatures(media);
            JSONArray featuresJson = new JSONArray(objectMapper.writeValueAsString(features));
            responseJson.put(FeatureEntity.ATTRIBUTE_NAME_PLURAL, featuresJson);

            GenreEntity[] genres = this.mediaService.getGenres(media);
            JSONArray genresJson = new JSONArray(objectMapper.writeValueAsString(genres));
            responseJson.put(GenreEntity.ATTRIBUTE_NAME_PLURAL, genresJson);

            RatingEntity rating = this.mediaService.getRating(media);
            JSONObject ratingJson = new JSONObject(objectMapper.writeValueAsString(rating));
            responseJson.put(RatingEntity.ATTRIBUTE_NAME, ratingJson);

            TypeEntity type = this.mediaService.getType(media);
            JSONObject typeJson = new JSONObject(objectMapper.writeValueAsString(type));
            responseJson.put(TypeEntity.ATTRIBUTE_NAME, typeJson);

            result = CommonResult.SUCCESS;
        }

        responseJson.put(IResult.ATTRIBUTE_NAME, result.name().toLowerCase());
        return responseJson.toString();
    }
}

