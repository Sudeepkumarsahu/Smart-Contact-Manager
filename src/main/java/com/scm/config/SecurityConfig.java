package com.scm.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.scm.services.implementations.SecurityCustomUserDetailService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
public class SecurityConfig {
    // user create and login using java code with in memory service
    // @Bean
    // public UserDetailsService userDetailsService(){
    // UserDetails user1 = User
    // .withUsername("admin123")
    // .password("{noop}123") // {noop} = no password encoder
    // .roles("ADMIN", "USER")
    // .build();
    // UserDetails user2 = User
    // .withUsername("sudeep")
    // .password("{noop}111")
    // .roles("USER")
    // .build();
    // return new InMemoryUserDetailsManager(user1, user2);
    // }

    @Autowired
    private SecurityCustomUserDetailService userDetailService;

    @Autowired
    private QAuthAuthenticationSuccessHandler handler;
    // configuration of authentication provider for spring security

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        // user Details service ka object
        daoAuthenticationProvider.setUserDetailsService(userDetailService);
        // password encoder ka object
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // configuration
        httpSecurity.authorizeHttpRequests(authorize -> {
            // authorize.requestMatchers("/home", "/register", "/services").permitAll();//
            // home,register and services pages is public

            // urls configuration kiye hai ki konse public rahenge aur konse private rahenge
            authorize.requestMatchers("/user/**").authenticated(); // /user se start hone bale protected i.e. needs
                                                                   // login
            authorize.anyRequest().permitAll();// rest all public
        });

        // form default login
        // agar hame kuchh bhi change karna hua to ham yaha aayenge : form login se
        // related
        // httpSecurity.formLogin(Customizer.withDefaults()); //used for default
        httpSecurity.formLogin(formLogin->{
           formLogin.loginPage("/login")
           .loginProcessingUrl("/authenticate")
           .successForwardUrl("/user/dashboard")
        //    .failureForwardUrl("/login?error=true") // if use then postmapping is needed in controller for login page
           .usernameParameter("email") //username
           .passwordParameter("password");
        //    .failureHandler(new AuthenticationFailureHandler() {
        //     @Override
        //     public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
        //             AuthenticationException exception) throws IOException, ServletException {
        //         // TODO Auto-generated method stub
        //         throw new UnsupportedOperationException("Unimplemented method 'onAuthenticationFailure'");
        //     }           
        //    }); 
        //    formLogin.successHandler(new AuthenticationSuccessHandler() {
        //     @Override
        //     public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
        //             Authentication authentication) throws IOException, ServletException {
        //         // TODO Auto-generated method stub
        //         throw new UnsupportedOperationException("Unimplemented method 'onAuthenticationSuccess'");
        //     }
        //    });
        });

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.logout(logoutForm->{
            logoutForm.logoutUrl("/logout");
            logoutForm.logoutSuccessUrl("/login?logout=true");
        });

        //oauth configuration
        httpSecurity.oauth2Login(oauth->{
            oauth.loginPage("/login");
            oauth.successHandler(handler);
        });
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
