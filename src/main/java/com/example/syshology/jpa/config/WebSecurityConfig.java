package com.example.syshology.jpa.config;

import com.example.syshology.jpa.config.jwt.JwtAuthenticationEntryPoint;
import com.example.syshology.jpa.config.jwt.JwtRequestFilter;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final UserDetailsService jwtUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        // 정적 파일 무시
        web.ignoring().antMatchers("/resources/css/**", "/resources/js/**", "/resources/images/**"  );
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        // This example does not require the use of CSRF
        httpSecurity.headers().frameOptions().disable().and().csrf().disable()
                // Authentication pages do not require permissions
                .authorizeRequests().antMatchers("/authenticate", "/members/new", "/h2/**", "/test/**").permitAll().
                //Other pages
                 anyRequest().authenticated().and().
                //Login Page Simulator Client
                formLogin().loginPage("/login").permitAll().and().
                // store user's state.
                 exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().
                sessionManagement()
                //Do not use session
               .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //Verify that the request is correct
        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
    @Bean
    Hibernate5Module hibernate5Module() {
        Hibernate5Module hibernate5Module = new Hibernate5Module();
        hibernate5Module.configure(Hibernate5Module.Feature.FORCE_LAZY_LOADING,
                true);
        return hibernate5Module;
    }
}