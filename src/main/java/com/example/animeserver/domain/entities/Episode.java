package com.example.animeserver.domain.entities;

import com.example.animeserver.rest.dto.EpisodeRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tb_episodes")
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nameEp;
    private LocalDate date;
    private String url;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "episodeAnime_id")
    private Anime episodeAnime;

    @OneToMany(mappedBy = "likeEpisode", cascade = CascadeType.REMOVE)
    private List<Like> episodeLikes = new ArrayList<>();

    @OneToMany(mappedBy = "commentEpisode", cascade = CascadeType.REMOVE)
    private Set<Comment> episodeComments = new HashSet<>();

    public static Episode of(EpisodeRequest request){
        return Episode.builder()
                .nameEp(request.getNameEp())
                .date(LocalDate.now())
                .url(request.getUrl())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Episode episode)) return false;
        return getId().equals(episode.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
