package com.example.animeserver.domain.repositories;

import com.example.animeserver.domain.entities.Anime;
import com.example.animeserver.domain.entities.Category;
import com.example.animeserver.domain.entities.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AnimeRepository extends JpaRepository<Anime,Long> {

    @Query(value = "select * from tb_animes c where c.date > :date ",nativeQuery = true)
    List<Anime> findByRecentlyAdded(@Param("date") LocalDate date);

    Optional<Anime> findByName(String name);

    List<Anime> findByCategories(Category category);




}
