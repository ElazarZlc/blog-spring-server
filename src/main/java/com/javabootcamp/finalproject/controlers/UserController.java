package com.javabootcamp.finalproject.controlers;

import com.javabootcamp.finalproject.models.User;

import com.javabootcamp.finalproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
@RestController
@CrossOrigin
public class UserController {

    @Autowired
    UserRepository userRepo;

    @GetMapping("admin/{user}/users")
    public ResponseEntity<Object> getAll(@PathVariable String user){
        try {
            if(userRepo.findById(user).isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(userRepo.findAll());
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to do this!");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("users")
    public ResponseEntity<Object> createUser(@RequestBody User user){
        if(user.getName() == null || user.getPassword() == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing");
        }
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userRepo.save(user));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }



    @DeleteMapping("admin/{user}/users/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable String id ,@PathVariable String user){
        try {
            if(userRepo.findById(user).isPresent()){
                userRepo.deleteById(id);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Post "+ id + " deleted");
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to do this!");

        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
