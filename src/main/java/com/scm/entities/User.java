package com.scm.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "user")
@Table(name = "users")
// lombok annotations
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {
   @Id // primary key annotation
   private String userId;
   @Column(name = "user_name", nullable = false)
   private String name;
   @Column(nullable = false, unique = true) // unique constraint annotation for email column
   private String email;
   @Getter(value = AccessLevel.NONE)
   private String password;
   private String phoneNumber;
   // @Column(length = 10000) // not working beacause of SQL length limit
   @Lob // for storing large text columns
   @Column(columnDefinition = "TEXT")
   private String about;
   // @Column(length = 10000)
   @Lob
   @Column(columnDefinition = "TEXT")
   private String profilePicture;
   @Getter(value = AccessLevel.NONE)
   private boolean enabled = false;
   private boolean emailVarified = false;
   private boolean phoneVarified = false;

   // self,google, facebook, linkedin, twitter, github, stackoverflow, etc.
   @Enumerated(value = EnumType.STRING)
   private Providers provider = Providers.SELF;
   private String providerUserId;
   private String emailToken;

   // add more field if needed
   @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true) // ek user ke
                                                                                                          // pass
                                                                                                          // multiple
                                                                                                          // contacts
                                                                                                          // hai
   private List<Contact> contacts = new ArrayList<>();
   @ElementCollection(fetch = FetchType.EAGER)
   private List<String> roleList = new ArrayList<>();

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      // list of roles[user,admin]
      // collection of SimpleGrantedAuthority[roles{admin,user}]
      Collection<SimpleGrantedAuthority> roles = roleList.stream().map(role -> new SimpleGrantedAuthority(role))
            .collect(Collectors.toList());
      return roles;
   }

   // for this project email id is username
   @Override
   public String getUsername() {
      return this.email;
   }

   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   @Override
   public boolean isAccountNonLocked() {
      return true;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return true;
   }

   @Override
   public boolean isEnabled() {
      return this.enabled;
   }

   @Override
   public String getPassword() {
      return this.password;
   }
}
