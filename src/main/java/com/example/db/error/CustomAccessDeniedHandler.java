package com.example.db.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        String errorMessage = String.format("Access Denied - You do not have permission to access this resource. Request URL: %s", request.getRequestURI());
        response.getWriter().write(String.format("{\"status\": 403, \"message\": \"%s\", \"exception\": \"%s\"}", errorMessage, accessDeniedException.getMessage()));

    }
}
