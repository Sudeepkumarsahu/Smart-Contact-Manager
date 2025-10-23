package com.scm.config;

import java.io.IOException;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.scm.helpers.Message;
import com.scm.helpers.MessageType;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class AuthFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (exception instanceof DisabledException) {
            // Set session message first
            session.setAttribute("message",
                    Message.builder()
                            .content("Your account is disabled. Contact admin.")
                            .type(MessageType.red)
                            .build());
            response.sendRedirect("/login");
        } else {
            response.sendRedirect("/login?error=true");
        }
    }
}
