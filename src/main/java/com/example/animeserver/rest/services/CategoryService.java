package com.example.animeserver.rest.services;

import com.example.animeserver.domain.entities.Category;
import com.example.animeserver.domain.repositories.CategoryRepository;
import com.example.animeserver.rest.dto.CategoryRequest;
import com.example.animeserver.rest.dto.CategoryResponse;
import com.example.animeserver.rest.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public void create(CategoryRequest request){
        categoryRepository.save(Category.of(request));
    }

    public Category findById(Long id){
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("category not found"));
    }

    public Category findByName(String name){
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new ObjectNotFoundException("category not found"));
    }

    public List<CategoryResponse> findAll(){
        return categoryRepository.findAll().stream()
                .map(cat -> CategoryResponse.of(cat))
                .collect(Collectors.toList());
    }

    public void deleteById(Long id){
        categoryRepository.delete(findById(id));
    }


}
