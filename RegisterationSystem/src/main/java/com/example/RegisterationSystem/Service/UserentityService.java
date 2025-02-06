package com.example.RegisterationSystem.Service;

import com.example.RegisterationSystem.Model.Userentity;
import com.example.RegisterationSystem.Repository.UserentityRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserentityService implements UserDetailsService {
    //inject repository class
    @Autowired
    private UserentityRepository repo;

    //the only exist method in UserDetails interface for bringing the details of the user
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Userentity> user=repo.findByUsername(username); //search for the coming username in DB,we put optional as the user might be empty
        //if the user exist in the DB,will set the username and user password from Db to user details obj
        //to return to secureconfig file to authenticate
        if(user.isPresent()){
            var userobj=user.get();
            return User.builder()
                    .username(userobj.getUsername())
                    .password(userobj.getPassword())
                    .build();
        }else{
            throw new UsernameNotFoundException(username); //if user not found
        }
    }
}
