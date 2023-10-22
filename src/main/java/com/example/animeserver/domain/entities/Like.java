package com.example.animeserver.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tb_likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "userLikes_id")
    private Users userLikes;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "likeAnime_id")
    private Anime likeAnime;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "commentLike_id")
    private Comment likeComment;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "episodeLike_id")
    private Episode likeEpisode;

    public static Like of(Users user) {
        return Like.builder()
                .userLikes(user)
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Like likes)) return false;
        return getId().equals(likes.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
