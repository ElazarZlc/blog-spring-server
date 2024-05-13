package com.javabootcamp.finalproject.controlers;

import com.javabootcamp.finalproject.models.Post;
import com.javabootcamp.finalproject.repositories.PostRepository;
import com.javabootcamp.finalproject.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@CrossOrigin
public class PostController {

    private static final Logger log = LoggerFactory.getLogger(PostController.class);
    @Autowired
    PostRepository postRepo;
    @Autowired
    UserRepository userRepo;

    @GetMapping("admin/{user}/posts")
    public ResponseEntity<Object> getAll(@PathVariable String user) {
        try {
            if (userRepo.findById(user).isPresent()) {

                return ResponseEntity.status(HttpStatus.OK).body(postRepo.findAll());
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to do this!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @GetMapping("/posts")
    public ResponseEntity<Object> getAllPublished() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(postRepo.findAllPublishedPosts());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @GetMapping("posts/{id}")
    public ResponseEntity<Object> getPost(@PathVariable int id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(postRepo.findById(id).get());
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @PostMapping("admin/{user}/posts")
    public ResponseEntity<Object> createPost(@PathVariable String user ,@RequestBody Post post){
        try {
            if(userRepo.findById(user).isPresent()){
                return ResponseEntity.status(HttpStatus.CREATED).body(postRepo.save(post));
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to do this!");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PutMapping("/admin/{user}/posts/{id}")
    public ResponseEntity<Object> updatePost(@PathVariable int id,@PathVariable String user, @RequestBody Post newPost){
        try {
            if(userRepo.findById(user).isPresent()){
                Post oldPost = postRepo.findById(id).get();
                oldPost.setTitle(newPost.getTitle());
                oldPost.setDescription(newPost.getDescription());
                oldPost.setData(newPost.getData());
                oldPost.setPublished(newPost.isPublished());

                return ResponseEntity.status(HttpStatus.CREATED).body(postRepo.save(oldPost));
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to do this!");

        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @DeleteMapping("admin/{user}/posts/{id}")
    public ResponseEntity<Object> deletePost(@PathVariable int id, @PathVariable String user){
        try {
            if(userRepo.findById(user).isPresent()) {
                postRepo.deleteById(id);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Post " + id + " deleted");
            }
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("You are not authorized to do this!");
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("**")
    public ResponseEntity<Object> notExistentMapping(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("This page is not exist!");
    }
}
