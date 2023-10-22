package com.example.animeserver.rest.dto;

import com.example.animeserver.domain.entities.Comment;
import com.example.animeserver.domain.entities.Episode;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
public class EpisodeResponse {

    private String nameEp;
    private Integer likes;
    private String url;
    private String date;

    private Set<CommentResponse> comments = new HashSet<>();

    public static EpisodeResponse of(Episode episode){
        return EpisodeResponse.builder()
                .nameEp(episode.getNameEp())
                .likes(episode.getEpisodeLikes().size())
                .url(episode.getUrl())
                .date(episode.getDate().toString())
                .comments(convertCommentDtoSet(episode.getEpisodeComments()))
                .build();
    }

    private static Set<CommentResponse> convertCommentDtoSet(Set<Comment> comments){
        return comments.stream().map(comment -> {
            return CommentResponse.of(comment);
        }).collect(Collectors.toSet());
    }

}
