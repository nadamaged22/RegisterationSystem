package com.example.RegisterationSystem.Security;

import com.example.RegisterationSystem.Service.UserentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //tells spring boot that this class holds important configurations for our app
@EnableWebSecurity //active spring security
public class SecurityConfig {
    //this method will take httpsecurity obj which rep the core of httpsecurity config of spring
    //we will use it to define login forms ,authorization and more
    @Autowired
    private UserentityService entityService;

    @Bean
    public UserDetailsService userDetailsService(){
        return entityService;
    }
    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setUserDetailsService(entityService);
        provider.setPasswordEncoder(passwordEncoder());//as when comparing in login it compare correctly
        return provider;

    }
    //to encode the password
    //return obj of type passwordEncoder
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();

    }


    @Bean
    public SecurityFilterChain securityfilterChain(HttpSecurity httpSecurity) throws  Exception{
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(httpform->{
                    httpform.loginPage("/api/login").permitAll();
                    httpform.defaultSuccessUrl("/api/index",true);
                })
                //this method alows us to define authauntication rules included who can access and other
                .authorizeHttpRequests(register->{
                    register.requestMatchers("/api/signup","/CSS/**","/JS/**").permitAll();
                    //by adding this anyone can access the register page withought going to the login page
                    register.anyRequest().authenticated();

                }).build();
    }


}
