package com.example.animeserver.domain.config;


import com.example.animeserver.domain.entities.*;
import com.example.animeserver.domain.enums.Roles;
import com.example.animeserver.domain.repositories.AnimeRepository;
import com.example.animeserver.domain.repositories.CategoryRepository;
import com.example.animeserver.domain.repositories.RoleRepository;
import com.example.animeserver.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Arrays;

@Configuration
public class Config implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private AnimeRepository animeRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {
        Users users = new Users(null,"joe","joe@gmail.com",encoder.encode("senha123"));
        userRepository.save(users);

        Users adm = new Users(null,"adm","adm@gmail.com",encoder.encode("senha123"));
        userRepository.save(adm);

        Role role = new Role(null,Roles.ROLE_USER);
        roleRepository.save(role);

        Role roleAdm = new Role(null,Roles.ROLE_ADMIN);
        roleRepository.save(roleAdm);

        users.getRoles().add(role);
        adm.getRoles().add(roleAdm);
        userRepository.saveAll(Arrays.asList(users,adm));

        Category category = new Category(null,"isekai");
        Category category1 = new Category(null,"ecchi");
        categoryRepository.saveAll(Arrays.asList(category,category1));

        Anime anime = new Anime(null,"naruto","o jinchuriki da raposa de nove caldas");
        animeRepository.save(anime);

        anime.getCategories().add(category1);
        animeRepository.save(anime);


    }
}
