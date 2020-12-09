package ru.ifmo.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.ifmo.web.filter.JwtTokenVerifyFilter;

/**
 * @author Bobur Zakirov
 * @since 12/9/2020 15:50:40
 */
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
      private UserDetailsService userDetailsService;
      private JwtTokenVerifyFilter jwtTokenVerifyFilter;

      @Autowired @Lazy
      public void setUserDetailsService(UserDetailsService userDetailsServiceImpl) {
            this.userDetailsService = userDetailsServiceImpl;
      }
      @Autowired
      public void setJwtTokenVerifyFilter(JwtTokenVerifyFilter jwtTokenVerifyFilter) {
            this.jwtTokenVerifyFilter = jwtTokenVerifyFilter;
      }

      @Override
      protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
      }

      @Override
      protected void configure(HttpSecurity http) throws Exception {
            http
                    .cors().and().csrf().disable()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .addFilterBefore(jwtTokenVerifyFilter, UsernamePasswordAuthenticationFilter.class)
                    .authorizeRequests()
                    .antMatchers("/auth/*").permitAll()
                    .anyRequest().authenticated();
      }

      @Bean
      public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
      }

      @Override @Bean
      public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
      }
}
