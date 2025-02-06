package com.example.RegisterationSystem.Controller;

import com.example.RegisterationSystem.Model.Userentity;
import com.example.RegisterationSystem.Repository.UserentityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterationController {
    @Autowired
    private UserentityRepository userentityRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @PostMapping(value = "/api/signup",consumes = "application/json")
    public ResponseEntity<?> createUser(@RequestBody Userentity user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Userentity savedUser = userentityRepository.save(user);
            return ResponseEntity.ok(savedUser);
        } catch (DataIntegrityViolationException e) {
           //catches dublicates
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Username already exists. Please choose another one.");
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred.");
        }
    }
}
