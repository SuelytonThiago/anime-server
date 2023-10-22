package com.example.animeserver.rest.dto;

import com.example.animeserver.domain.entities.Anime;
import com.example.animeserver.domain.entities.Category;
import com.example.animeserver.domain.entities.Comment;
import com.example.animeserver.domain.entities.Episode;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
public class AnimeResponse {

    private String name;
    private String synopsis;
    private String date;
    private Integer likes;
    private List<CategoryResponse> categories = new ArrayList<>();
    private Set<CommentResponse> comments = new HashSet<>();
    private Set<EpisodeResponse> episodes = new HashSet<>();

    public static AnimeResponse of(Anime anime){
        return AnimeResponse.builder()
                .name(anime.getName())
                .synopsis(anime.getSynopsis())
                .date(anime.getDate().toString())
                .likes(anime.getAnimeLikes().size())
                .categories(convertCategoryDtoList(anime.getCategories()))
                .comments(convertCommentDtoSet(anime.getAnimeComments()))
                .episodes(convertEpisodeDtoSet(anime.getEpisodes()))
                .build();
    }

    public static List<CategoryResponse> convertCategoryDtoList(List<Category> list){
        return list.stream().map(cat ->{
            return CategoryResponse.of(cat);
        }).collect(Collectors.toList());
    }

    public static Set<CommentResponse> convertCommentDtoSet(Set<Comment> comments){
        return comments.stream().map(comment ->{
            return CommentResponse.of(comment);
        }).collect(Collectors.toSet());
    }

    public static Set<EpisodeResponse> convertEpisodeDtoSet(Set<Episode> episodes){
        return episodes.stream().map(episode ->{
            return EpisodeResponse.of(episode);
        }).collect(Collectors.toSet());
    }
}
