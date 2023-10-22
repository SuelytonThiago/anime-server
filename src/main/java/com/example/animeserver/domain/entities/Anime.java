package com.example.animeserver.domain.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_animes")
public class Anime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String synopsis;
    private LocalDate date;

    @ManyToMany
    @JoinTable(name = "tb_anime_categories",
    joinColumns = @JoinColumn(name = "anime_id"),
    inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "likeAnime",cascade = CascadeType.REMOVE)
    private List<Like> animeLikes = new ArrayList<>();

    @OneToMany(mappedBy = "episodeAnime",cascade = CascadeType.REMOVE)
    private Set<Episode> episodes = new HashSet<>();

    @OneToMany(mappedBy = "commentAnime",cascade = CascadeType.REMOVE)
    private Set<Comment> animeComments = new HashSet<>();

    public Anime(Long id, String name, String synopsis) {
        this.id = id;
        this.name = name;
        this.synopsis = synopsis;
        this.date = LocalDate.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Anime anime)) return false;
        return getId().equals(anime.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
