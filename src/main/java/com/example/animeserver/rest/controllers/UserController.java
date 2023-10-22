package com.example.animeserver.rest.controllers;

import com.example.animeserver.rest.dto.UserRequest;
import com.example.animeserver.rest.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    @Operation(summary = "endpoint responsible for creating a user")
    public ResponseEntity<Void> create(@RequestBody @Valid UserRequest request){
        userService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/delete")
    @Operation(summary = "endpoint responsible for deleting a user")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/update")
    @Operation(summary = "endpoint responsible for updating a user")
    public ResponseEntity<Void> update(@RequestBody @Valid UserRequest userDto,
                                       HttpServletRequest request){
        userService.update(request,userDto);
        return ResponseEntity.noContent().build();
    }
}
