package com.example.db.error;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // 스택 트레이스를 통해 함수명과 클래스명을 가져옴
        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        String className = "Unknown";
        String methodName = "Unknown";
        if (stackTraceElements.length > 2) {
            // 배열의 0번은 getStackTrace, 1번은 현재 메서드, 2번이 호출한 메서드임
            StackTraceElement element = stackTraceElements[2];
            className = element.getClassName();
            methodName = element.getMethodName();
        }

        String errorMessage = String.format("Unauthorized - Please log in. Request URL: %s  Exception occurred in %s: %s", request.getRequestURI(), methodName, authException.getMessage());
        String detailedMessage = String.format("Exception occurred in %s.%s: %s", className, methodName, authException.getMessage());

        response.getWriter().write(String.format("{\"status\": 401, \"message\": \"%s\", \"exception\": \"%s\"}", errorMessage, detailedMessage));
    }
}
