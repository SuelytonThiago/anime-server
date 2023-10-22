package com.example.animeserver.rest.services;

import com.example.animeserver.domain.entities.Anime;
import com.example.animeserver.domain.entities.Category;
import com.example.animeserver.domain.repositories.AnimeRepository;
import com.example.animeserver.rest.dto.*;
import com.example.animeserver.rest.services.exceptions.CustomException;
import com.example.animeserver.rest.services.exceptions.ObjectNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimeService {

    @Autowired
    private AnimeRepository animeRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private EpisodeService episodeService;


    @Transactional
    public void create(AnimeRequest request){
        var anime = new Anime(null, request.getName(), request.getSynopsis());
        List<Category> list = convertCategoryList(request.getCategories());
        animeRepository.save(anime);
        anime.getCategories().addAll(list);
        animeRepository.save(anime);
    }

    public Anime findById(Long id){
        return animeRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("anime not found"));
    }

    public List<Anime> findAll(){
        return animeRepository.findAll();
    }


    public List<AnimeResponse> searchRecentlyAdded(){
        return animeRepository.findByRecentlyAdded(LocalDate.now().minusDays(7))
                .stream()
                .map(anime ->{
                    return AnimeResponse.of(anime);
        }).collect(Collectors.toList());
    }

    @Transactional
    public List<AnimeResponse> findByCategory(String categoryName){
        var category = categoryService.findByName(categoryName);
        return animeRepository.findByCategories(category).stream().map(anime ->{
            return AnimeResponse.of(anime);
        }).collect(Collectors.toList());
    }

    public AnimeResponse findByName(String name){
        var anime = animeRepository.findByName(name)
                .orElseThrow(() -> new ObjectNotFoundException("anime not found"));
        return AnimeResponse.of(anime);
    }

    @Transactional
    public void deleteById(Long id){
        try{
            animeRepository.delete(findById(id));
        }
        catch (DataIntegrityViolationException e){
            throw new CustomException("This Anime cannot ben deleted");
        }
    }


    @Transactional
    public void addCategory(String categoryName, Long idAnime){
        var anime = findById(idAnime);
        var category = categoryService.findByName(categoryName);
        anime.getCategories().add(category);
        animeRepository.save(anime);
    }

    @Transactional
    public void removeCategory(String categoryName, Long idAnime){
        var anime = findById(idAnime);
        var category = categoryService.findByName(categoryName);
        anime.getCategories().remove(category);
        animeRepository.save(anime);
    }

    @Transactional
    public void addCommentToAnime(HttpServletRequest request, Long idAnime, CommentRequest commentDto){
       var comment = commentService.create(request,commentDto);
       var anime = findById(idAnime);
       comment.setCommentAnime(anime);
       anime.getAnimeComments().add(commentService.save(comment));
       animeRepository.save(anime);
    }

    @Transactional
    public void likeThisAnime(Long idAnime,HttpServletRequest request){
        var anime = findById(idAnime);
        if(likeService.verifyAnime(request,anime)) {
            var like = likeService.create(request);
            like.setLikeAnime(anime);
            likeService.save(like);
            anime.getAnimeLikes().add(like);
            animeRepository.save(anime);
        }
    }
    @Transactional
    public void addNewEpisodeToAnime(Long idAnime, EpisodeRequest request){
        var anime = findById(idAnime);
        var episode = episodeService.createNewEp(request);
        episode.setEpisodeAnime(anime);
        episodeService.save(episode);
        anime.getEpisodes().add(episode);
        anime.setDate(LocalDate.now());
        animeRepository.save(anime);
    }

    public void update(Long id, AnimeRequest request){
        var anime = findById(id);
        updateData(anime,request);
        animeRepository.save(anime);
    }

    private void updateData(Anime anime, AnimeRequest request) {
        anime.setName(request.getName());
        anime.setSynopsis(request.getSynopsis());
    }

    private List<Category> convertCategoryList(List<CategoryRequest> list){
        return list.stream().map(cat ->{
            return categoryService.findByName(cat.getName());
        }).collect(Collectors.toList());
    }

}
