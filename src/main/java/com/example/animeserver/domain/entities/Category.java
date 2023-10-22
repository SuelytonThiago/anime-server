package com.example.animeserver.domain.entities;

import com.example.animeserver.rest.dto.CategoryRequest;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Category( String name) {
        this.id = id;
        this.name = name;
    }

    public static Category of(CategoryRequest request){
        return Category.builder().name(request.getName()).build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category category)) return false;
        return getId().equals(category.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
