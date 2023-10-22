package com.example.animeserver.rest.controllers;

import com.example.animeserver.rest.dto.CommentRequest;
import com.example.animeserver.rest.dto.EpisodeRequest;
import com.example.animeserver.rest.services.EpisodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/episode")
@SecurityRequirement(name = "bearerAuth")
public class EpisodeController {

    @Autowired
    private EpisodeService episodeService;

    @PostMapping("/like/{id}")
    @Operation(summary = "endpoint responsible for adding a like to the episode")
    public ResponseEntity<Void> likeThisEpisode(HttpServletRequest request,
                                                @PathVariable Long id){
        episodeService.likeThisEpisode(id, request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/comment/{id}")
    @Operation(summary = "endpoint responsible for adding a comment to the episode")
    public ResponseEntity<Void> addCommentToEp(HttpServletRequest request,
                                               @PathVariable Long id,
                                               @RequestBody @Valid CommentRequest commentDto){
        episodeService.addCommentToEpisode(request, id, commentDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "endpoint responsible for updating an episode")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestBody @Valid EpisodeRequest request){
        episodeService.update(id,request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "endpoint responsible for deleting an episode")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        episodeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
