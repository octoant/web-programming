package ru.ifmo.web.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.ifmo.web.util.JwtTokenService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Bobur Zakirov
 * @since 12/9/2020 15:56:03
 */
@Component
public class JwtTokenVerifyFilter extends OncePerRequestFilter {
      private UserDetailsService userDetailsService;
      private JwtTokenService jwtTokenService;

      @Autowired
      public void setUserDetailsService(UserDetailsService userDetailsServiceImpl) {
            this.userDetailsService = userDetailsServiceImpl;
      }
      @Autowired
      public void setJwtTokenService(JwtTokenService jwtTokenService) {
            this.jwtTokenService = jwtTokenService;
      }

      @Value("${spring.jwt.authorization.request-header}")
      private String requestHeader;

      @Value("${spring.jwt.authorization.token-prefix}")
      private String tokenPrefix;

      @Override
      protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
              throws ServletException, IOException {
            String authorizationHeader = request.getHeader(requestHeader);
            String jwtToken, userName;

            // if header is null or doesn't start with tokenPrefix
            if (authorizationHeader == null || !authorizationHeader.startsWith(tokenPrefix)) {
                  chain.doFilter(request, response); return;
            }

            // getting token from requestHeader
            jwtToken = authorizationHeader.substring(7);
            // getting username from token
            userName = jwtTokenService.extractUsername(jwtToken);

            // if username or authentication are null
            if (userName == null || SecurityContextHolder.getContext().getAuthentication() != null) {
                  chain.doFilter(request, response); return;
            }

            UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
            if (jwtTokenService.validateToken(jwtToken, userDetails)) {
                  UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                          userDetails, null, userDetails.getAuthorities()
                  );
                  authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                  //setting authenticationToken in spring context authentication
                  SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
            chain.doFilter(request, response);
      }
}
