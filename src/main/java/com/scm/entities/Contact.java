package com.scm.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Contact {
    @Id
    private String contactId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String picture;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String description;
    private boolean favorite = false;
    private String websiteLink;
    private String linkedInLink;
    // private List<String> socialMediaLinks = new ArrayList<>();
    @ManyToOne
    private User user; // User who created this contact

    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)  
    private List<SocialLink> links = new ArrayList<>();

}
