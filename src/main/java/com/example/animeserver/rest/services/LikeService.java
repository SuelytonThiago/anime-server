package com.example.animeserver.rest.services;

import com.example.animeserver.domain.entities.*;
import com.example.animeserver.domain.repositories.LikeRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;


    public Like create(HttpServletRequest request){
        var user = userService.findByEmail(jwtService.getSubject(request));
        return Like.of(user);
    }

    public Like save(Like like){
        return likeRepository.save(like);
    }


    public boolean verifyAnime(HttpServletRequest request, Anime anime){
        var user = userService.findByEmail(jwtService.getSubject(request));
        return likeRepository.findByUserLikesAndLikeAnime(user,anime).isEmpty();
    }

    public boolean verifyComment(HttpServletRequest request, Comment comment){
        var user = userService.findByEmail(jwtService.getSubject(request));
        return likeRepository.findByUserLikesAndLikeComment(user,comment).isEmpty();
    }

    public boolean verifyEpisode(HttpServletRequest request, Episode episode){
        var user = userService.findByEmail(jwtService.getSubject(request));
        return likeRepository.findByUserLikesAndLikeEpisode(user,episode).isEmpty();
    }

}
