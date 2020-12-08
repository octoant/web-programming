package ru.ifmo.web.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.ifmo.web.detail.UserDetailsServiceImpl;
import ru.ifmo.web.util.JwtTokenService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Bobur Zakirov
 * @since 12/8/2020 01:46:41
 */
@Component
public class JwtTokenVerifyFilter extends OncePerRequestFilter {
      private final UserDetailsServiceImpl userDetailsService;

      private final JwtTokenService jwtTokenService;

      @Autowired
      public JwtTokenVerifyFilter(UserDetailsServiceImpl userDetailsService, JwtTokenService jwtTokenService) {
            this.userDetailsService = userDetailsService;
            this.jwtTokenService = jwtTokenService;
      }

      @Value("${spring.jwt.authorization.request-header}")
      private String requestHeader;

      @Value("${spring.jwt.authorization.token-prefix}")
      private String tokenPrefix;

      @Override
      protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                      FilterChain filterChain) throws ServletException, IOException {

            String authorizationHeader = httpServletRequest.getHeader(requestHeader);
            String jwtToken, userName;

            if (authorizationHeader == null || !authorizationHeader.startsWith(tokenPrefix)) {
                  filterChain.doFilter(httpServletRequest, httpServletResponse);
                  return;
            }

            jwtToken = authorizationHeader.substring(7);
            userName = jwtTokenService.extractUsername(jwtToken);

            if (userName == null || SecurityContextHolder.getContext().getAuthentication() != null) {
                  filterChain.doFilter(httpServletRequest, httpServletResponse);
                  return;
            }

            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            if (jwtTokenService.validateToken(jwtToken, userDetails)) {
                  UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                          userDetails, null, userDetails.getAuthorities()
                  );
                  authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                  SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
      }
}
