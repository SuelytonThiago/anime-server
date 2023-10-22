package com.example.animeserver.domain.repositories;
import com.example.animeserver.domain.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface LikeRepository extends JpaRepository<Like,Long> {

    Optional<Like> findByUserLikesAndLikeAnime(Users user, Anime anime);
    Optional<Like> findByUserLikesAndLikeComment(Users user, Comment comment);
    Optional<Like> findByUserLikesAndLikeEpisode(Users user, Episode episode);
}
