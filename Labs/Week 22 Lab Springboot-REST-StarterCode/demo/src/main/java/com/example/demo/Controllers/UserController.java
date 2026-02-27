package com.example.demo.Controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Models.User;
import com.example.demo.Services.UserService;

@RestController

@CrossOrigin(origins = "http://localhost:5173")
public class UserController {
    @Autowired
	UserService userService;
    
    // Get All Users
    @GetMapping("/user")
    public List<User> getUsers() {
        return userService.getUsers();
    }
    
    // Allows for the creation of new json entities (Users)
    @PostMapping("/user")
    public ResponseEntity<Object> postMethodName(@RequestBody UserPostDTO UserBTO) {
        //TODO: process POST request
       
        /// Validate the input data  
        if(UserBTO.getName()==null|| UserBTO.getName().isEmpty()||
            UserBTO.getEmail()==null|| UserBTO.getEmail().isEmpty()||
            UserBTO.getPassword()==null|| UserBTO.getPassword().isEmpty()||
            UserBTO.getUserType()==UserType.NONE){


                return ResponseEntity.badRequest().build();

            }

            // Create a new User entity from the DTO
        User newUser = new User(
                     UserBTO.getName(), 
                     UserBTO.getEmail(),
                     UserBTO.getPassword(), 
                     UserBTO.getUserType()

       ); 




       // Call the service layer to save the new user 
       User createdUser = userService.addUser(newUser);
       return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
    



    // Get User by ID
    @GetMapping("/user/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long id) {
        var user = userService.findByID(id);
        if(user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    } 


    // Delete User by ID
    @GetMapping("/user/delete/{id}") 
    public ResponseEntity<Object> deleteUserById(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }
    


    // Get User by Email
    @GetMapping("/user/email")  
    public ResponseEntity<Object> getUserByEmail(@RequestParam String email) {
        var user = userService.findByEmail(email);
        if(user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
