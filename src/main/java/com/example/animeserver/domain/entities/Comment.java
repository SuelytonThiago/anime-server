package com.example.animeserver.domain.entities;

import com.example.animeserver.rest.dto.CommentRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String content;
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "userComment_id")
    private Users userComment;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "commentAnime_id")
    private Anime commentAnime;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "commentEpisode_id")
    private Episode commentEpisode;

    @OneToMany(mappedBy = "likeComment", cascade = CascadeType.REMOVE)
    private List<Like> commentLikes = new ArrayList<>();


    public static Comment of(CommentRequest request,Users user){
        Comment comment = new Comment();
        comment.setContent(request.getComment());
        comment.setDate(LocalDate.now());
        comment.setUserComment(user);
        return comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment comment)) return false;
        return getId().equals(comment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
