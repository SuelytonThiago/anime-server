package com.example.animeserver.rest.controllers;

import com.example.animeserver.rest.dto.CategoryRequest;
import com.example.animeserver.rest.dto.CategoryResponse;
import com.example.animeserver.rest.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;


    @GetMapping
    @Operation(summary = "endpoint responsible for getting all categories")
    public ResponseEntity<List<CategoryResponse>> findAll(){
        return ResponseEntity.ok(categoryService.findAll());
    }

    @PostMapping("/add")
    @Operation(summary = "endpoint responsible for creating a category")
    public ResponseEntity<Void> create(@RequestBody @Valid CategoryRequest request){
        categoryService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "endpoint responsible for deleting a category")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
