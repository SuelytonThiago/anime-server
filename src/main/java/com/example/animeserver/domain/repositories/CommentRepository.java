package com.example.animeserver.domain.repositories;

import com.example.animeserver.domain.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
