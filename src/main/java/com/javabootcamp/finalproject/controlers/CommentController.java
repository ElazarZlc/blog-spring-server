package com.javabootcamp.finalproject.controlers;

import com.javabootcamp.finalproject.models.Comment;
import com.javabootcamp.finalproject.repositories.CommentRepository;
import com.javabootcamp.finalproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@CrossOrigin
public class CommentController {

    @Autowired
    CommentRepository commentRepo;
    @Autowired
    UserRepository userRepo;

    @GetMapping("admin/{user}/comments")
    public ResponseEntity<Object> getAll(@PathVariable String user){
        try {
            if (userRepo.findById(user).isPresent()) {
                return ResponseEntity.status(HttpStatus.OK).body(commentRepo.findAll());
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to do this!");

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("admin/{user}/comments/{id}")
    public ResponseEntity<Object> getComment(@PathVariable int id) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(commentRepo.findById(id).get());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/posts/{id}/comments")
    public ResponseEntity<Object> getCommentsByPostId(@PathVariable int id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body( commentRepo.findAllByPostId(id));
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @PostMapping("/comments")
    public ResponseEntity<Object> createPost(@RequestBody Comment comment){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(commentRepo.save(comment));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("admin/{user}/comments/{id}")
    public ResponseEntity<Object> updatePost(@PathVariable int id,@PathVariable String user, @RequestBody Comment newComment){
        try {
            if(userRepo.findById(user).isPresent()) {
                Comment oldComment = commentRepo.findById(id).get();
                oldComment.setName(newComment.getName());
                oldComment.setData(newComment.getData());
                return ResponseEntity.status(HttpStatus.CREATED).body(commentRepo.save(oldComment));
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to do this!");
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("admin/{user}/comments/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable int id, @PathVariable String user){
        try {
            if(userRepo.findById(user).isPresent()) {
                commentRepo.deleteById(id);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("comment " + id + " deleted");
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to do this!");
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
