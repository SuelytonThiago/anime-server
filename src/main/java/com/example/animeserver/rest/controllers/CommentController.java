package com.example.animeserver.rest.controllers;

import com.example.animeserver.rest.dto.CommentRequest;
import com.example.animeserver.rest.services.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@SecurityRequirement(name = "bearerAuth")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "endpoint responsible for deleting a comment")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        commentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/like/{id}")
    @Operation(summary = "endpoint responsible for adding a like to the comment")
    public ResponseEntity<Void> likeThisComment(HttpServletRequest request,
                                                @PathVariable Long id){
        commentService.likeThisComment(id,request);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update/{id}")
    @Operation(summary = "endpoint responsible for updating a comment")
    public ResponseEntity<Void> update(@PathVariable Long id,
                                       @RequestBody @Valid CommentRequest commentRequest){
        commentService.update(id,commentRequest);
        return ResponseEntity.noContent().build();
    }
}
