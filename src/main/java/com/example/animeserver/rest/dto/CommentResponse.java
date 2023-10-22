package com.example.animeserver.rest.dto;

import com.example.animeserver.domain.entities.Comment;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentResponse {

    private String comment;
    private String username;
    private Integer likes;
    private String date;

    public static CommentResponse of(Comment comment){
        return CommentResponse.builder()
                .comment(comment.getContent())
                .username(comment.getUserComment().getNickName())
                .likes(comment.getCommentLikes().size())
                .date(comment.getDate().toString())
                .build();
    }
}
