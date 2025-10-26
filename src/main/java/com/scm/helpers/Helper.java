package com.scm.helpers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class Helper {
    public static String getEmailOfloggedInUser(Authentication authentication) {
        // AuthenticationPrincipal principal = (AuthenticationPrincipal)
        // authentication.getPrincipal();
        // if email and password se login
        if (authentication instanceof OAuth2AuthenticationToken) {
            var aOAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
            var clientId = aOAuth2AuthenticationToken.getAuthorizedClientRegistrationId();
            var oauth2user = (OAuth2User) authentication.getPrincipal();
            String userName = "";

            if (clientId.equalsIgnoreCase("google")) {
                // signin with google
                System.out.println("Getting email from google");
                userName = oauth2user.getAttribute("email").toString();
            } else if (clientId.equalsIgnoreCase("github")) {
                // signin with github
                System.out.println("Getting email from github");
                userName = oauth2user.getAttribute("email") != null ? oauth2user.getAttribute("email").toString()
                        : oauth2user.getAttribute("login").toString() + "@gmail.com";

            }
            return userName;
        } else {
            System.out.println("getting data from local database");
            return authentication.getName();
        }
    }

    public static String getLinkForEmailVerification(String emailToken) {
        String link = "http://localhost:8081/auth/verify-email?token=" + emailToken;
        return link;
    }
}
