package entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "user")
@Table(name = "users")
//lombok annotations
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    @Id  // primary key annotation
    private String userId;
    @Column(name = "user_name",nullable = false)
    private String name;
    @Column( nullable = false, unique = true)  // unique constraint annotation for email column
    private String email;
    private String password;
    // @Column(length = 10000)  // not working beacause of SQL length limit
    @Lob   //for storing large text columns
    @Column(columnDefinition = "TEXT")
    private String about;
    // @Column(length = 10000)
    @Lob
    @Column(columnDefinition = "TEXT")
    private String profilePicture;
    private boolean enabled = false;
    private boolean emailVarified = false;
    private boolean phoneVarified = false;

    // self,google, facebook, linkedin, twitter, github, stackoverflow, etc.
    private Providers provider = Providers.SELF;
    private String providerUserId;


    //add more field if needed
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)  // ek user ke pass multiple contacts hai
    private List<Contact> contacts = new ArrayList<>();
    }
