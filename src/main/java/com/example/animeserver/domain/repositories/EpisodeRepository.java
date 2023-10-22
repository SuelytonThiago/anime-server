package com.example.animeserver.domain.repositories;

import com.example.animeserver.domain.entities.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepository extends JpaRepository<Episode,Long> {
}
