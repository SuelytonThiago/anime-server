package com.example.animeserver.rest.services;

import com.example.animeserver.domain.entities.Episode;
import com.example.animeserver.domain.repositories.EpisodeRepository;
import com.example.animeserver.rest.dto.CommentRequest;
import com.example.animeserver.rest.dto.EpisodeRequest;

import com.example.animeserver.rest.services.exceptions.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class EpisodeService {

    @Autowired
    private EpisodeRepository episodeRepository;

    @Autowired
    private LikeService likeService;

    @Autowired
    private CommentService commentService;


    public Episode createNewEp(EpisodeRequest request){
        return Episode.of(request);
    }

    public Episode save(Episode episode){
        return episodeRepository.save(episode);
    }

    public Episode findById(Long id){
        return episodeRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Episode is not found"));
    }

    public void deleteById(Long id){
        episodeRepository.delete(findById(id));
    }

    @Transactional
    public void likeThisEpisode(Long idEpisode, HttpServletRequest request){
        var episode = findById(idEpisode);
        if(likeService.verifyEpisode(request,episode)){
            var like = likeService.create(request);
            like.setLikeEpisode(episode);
            episode.getEpisodeLikes().add(likeService.save(like));
            episodeRepository.save(episode);
        }
    }

    @Transactional
    public void addCommentToEpisode(HttpServletRequest request, Long idEpisode, CommentRequest commentDto){
        var comment = commentService.create(request,commentDto);
        var episode = findById(idEpisode);
        comment.setCommentEpisode(episode);
        episode.getEpisodeComments().add(commentService.save(comment));
        episodeRepository.save(episode);
    }

    public void update(Long id,EpisodeRequest request){
        var episode = findById(id);
        updateData(episode,request);
        episodeRepository.save(episode);
    }

    private void updateData(Episode episode, EpisodeRequest request) {
        episode.setNameEp(request.getNameEp());
        episode.setUrl(request.getUrl());
    }

}
