package com.example.animeserver.rest.controllers;

import com.example.animeserver.domain.entities.Anime;
import com.example.animeserver.rest.dto.*;
import com.example.animeserver.rest.services.AnimeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/anime")
@SecurityRequirement(name = "bearerAuth")
public class AnimeController {

    @Autowired
    private AnimeService animeService;

    @GetMapping
    @Operation(summary = "endpoint responsible for picking up all recently added animes")
    public ResponseEntity<List<AnimeResponse>> searchRecentlyAdded(){
        return ResponseEntity.ok(animeService.searchRecentlyAdded());
    }

    @GetMapping("/findAll")
    @Operation(summary = "endpoint responsible for getting all animes")
    public ResponseEntity<List<Anime>> findAll(){
        return ResponseEntity.ok(animeService.findAll());
    }

    @GetMapping("/category")
    @Operation(summary = "endpoint responsible for getting all anime by category")
    public ResponseEntity<List<AnimeResponse>>findByCategory(@RequestParam String categoryName){
        return ResponseEntity.ok(animeService.findByCategory(categoryName));
    }

    @GetMapping("/name")
    @Operation(summary = "endpoint responsible for getting all anime by name")
    public ResponseEntity<AnimeResponse>findByName (@RequestParam String name){
        return ResponseEntity.ok(animeService.findByName(name));
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "endpoint responsible for deleting an anime by id")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        animeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/addCategory/{idAnime}")
    @Operation(summary = "endpoint responsible for adding a category to an anime providing the anime id and the category name")
    public ResponseEntity<Void> addCategoryToAnime(@PathVariable Long idAnime,
                                                   @RequestParam String categoryName){
        animeService.addCategory(categoryName,idAnime);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/removeCategory/{idAnime}")
    @Operation(summary = "endpoint responsible for removing a category from an anime by providing the anime id and the category name")
    public ResponseEntity<Void> removeCategoryToAnime(@PathVariable Long idAnime,
                                                      @RequestParam String categoryName){
        animeService.removeCategory(categoryName,idAnime);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "endpoint responsible for updating an anime")
    public ResponseEntity<Void>update(@PathVariable Long id,
                                      @RequestBody @Valid AnimeRequest request){
        animeService.update(id,request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/add")
    @Operation(summary = "endpoint responsible for adding an anime")
    public ResponseEntity<Void> create(@Valid @RequestBody AnimeRequest request){
        animeService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/comment/{idAnime}")
    @Operation(summary = "endpoint responsible for adding a comment to the anime")
    public ResponseEntity<Void>addCommentToAnime(HttpServletRequest request,
                                                 @PathVariable Long idAnime,
                                                 @RequestBody @Valid CommentRequest comment){
        animeService.addCommentToAnime(request, idAnime, comment);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/like/{idAnime}")
    @Operation(summary = "endpoint responsible for adding a like to the anime")
    public ResponseEntity<Void>likeThisAnime(HttpServletRequest request,@PathVariable Long idAnime){
        animeService.likeThisAnime(idAnime,request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/newEp/{id}")
    @Operation(summary = "endpoint responsible for adding an episode to the anime")
    public ResponseEntity<Void> addNewEp(@PathVariable Long id,
                                         @RequestBody @Valid EpisodeRequest request){
        animeService.addNewEpisodeToAnime(id,request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }


}
