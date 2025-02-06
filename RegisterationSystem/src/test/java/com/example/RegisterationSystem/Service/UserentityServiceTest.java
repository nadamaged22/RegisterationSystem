package com.example.RegisterationSystem.Service;

import com.example.RegisterationSystem.Model.Userentity;
import com.example.RegisterationSystem.Repository.UserentityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class UserentityServiceTest {

    @Mock
    private UserentityRepository userentityRepository;

    @InjectMocks
    private UserentityService userentityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsername_UserExists_ReturnsUserDetails() {
        Userentity user = new Userentity();
        user.setUsername("john_doe");
        user.setPassword("password123");

        when(userentityRepository.findByUsername("john_doe")).thenReturn(Optional.of(user));

        UserDetails userDetails = userentityService.loadUserByUsername("john_doe");

        assertNotNull(userDetails);
        assertEquals("john_doe", userDetails.getUsername());
        assertEquals("password123", userDetails.getPassword());
    }

    @Test
    void loadUserByUsername_UserDoesNotExist_ThrowsException() {

        when(userentityRepository.findByUsername("non_existent_user")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userentityService.loadUserByUsername("non_existent_user");
        });
    }
}
