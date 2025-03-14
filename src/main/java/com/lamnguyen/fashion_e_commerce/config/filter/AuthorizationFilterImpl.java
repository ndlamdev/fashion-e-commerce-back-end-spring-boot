/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:57 PM - 28/02/2025
 * User: lam-nguyen
 **/

package com.lamnguyen.fashion_e_commerce.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lamnguyen.fashion_e_commerce.config.exception.ApplicationException;
import com.lamnguyen.fashion_e_commerce.domain.ApiResponse;
import com.lamnguyen.fashion_e_commerce.mapper.RoleRepository;
import com.lamnguyen.fashion_e_commerce.mapper.ScopeRepository;
import com.lamnguyen.fashion_e_commerce.repository.mysql.UserRepository;
import com.lamnguyen.fashion_e_commerce.util.property.ApplicationProperty;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthorizationFilterImpl extends OncePerRequestFilter {
    UserRepository userRepository;
    RoleRepository roleRepository;
    ScopeRepository scopeRepository;
    ApplicationProperty applicationProperty;

    @Override
    protected void doFilterInternal(@NonNull  HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain) throws ServletException, IOException {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var user = userRepository.findByEmail(authentication.getName());
        if(user.isEmpty()){
            forbidden(response);
            return;
        }
        var path = request.getServletPath();
        var method = request.getMethod();
        if (scopeRepository.existsByControllerAndMethodAndUsersContains(path, method, user.get())) {
            filterChain.doFilter(request, response);
            return;
        }
        var authorities = authentication.getAuthorities();
        for (var authority : authorities) {
            if (!authority.getAuthority().startsWith(applicationProperty.getRolePrefix())) continue;
            var role = roleRepository.findByNameAndUsersContains(authority.getAuthority().substring(5), user.get()).orElseThrow(() -> ApplicationException.createException("Role not found!"));
            for (var scope : role.getScopes())
                if (scope.getController().equals(path) && scope.getMethod().equals(method)) {
                    filterChain.doFilter(request, response);
                    return;
                }
        }

        forbidden(response);
    }

    private void forbidden(HttpServletResponse response) throws IOException {
        var writer = response.getWriter();
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(writer, ApiResponse.<String>builder()
                .code(HttpServletResponse.SC_FORBIDDEN)
                .error("Forbidden")
                .build());
        writer.flush();
    }

    @Override
    protected boolean shouldNotFilter(@NonNull HttpServletRequest request)  {
        var path = request.getServletPath();
        return  applicationProperty.getWhiteList().contains(path);
    }
}
