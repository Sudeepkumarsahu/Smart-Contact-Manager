package com.scm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig {
    // user create and login using java code with in memory service
@Bean
public UserDetailsService userDetailsService(){
     UserDetails user1 = User
        .withUsername("admin123")
        .password("{noop}123") // {noop} = no password encoder
        .roles("ADMIN", "USER")
        .build();

    UserDetails user2 = User
        .withUsername("sudeep")
        .password("{noop}111")
        .roles("USER")
        .build();

    return new InMemoryUserDetailsManager(user1, user2);
}


}
