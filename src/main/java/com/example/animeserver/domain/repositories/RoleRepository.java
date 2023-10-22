package com.example.animeserver.domain.repositories;

import com.example.animeserver.domain.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
}
