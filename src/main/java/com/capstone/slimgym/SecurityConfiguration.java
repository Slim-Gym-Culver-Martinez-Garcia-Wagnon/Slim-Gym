package com.capstone.slimgym;

import com.capstone.slimgym.services.UserDetailsLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private UserDetailsLoader usersLoader;

    public SecurityConfiguration(UserDetailsLoader usersLoader) {
        this.usersLoader = usersLoader;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(usersLoader) // How to find users by their username
                .passwordEncoder(passwordEncoder()) // How to encode and verify passwords
        ;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/profile")
                .permitAll()

                .and()
                    .logout()
                    .logoutSuccessUrl("/login?logout")
                    .permitAll()

                .and()
                    .authorizeRequests()
                    .antMatchers(
                            "/posts/create", "/profile")
                    .authenticated()

                .and()
                  .authorizeRequests()
                  .antMatchers(
                          "/",
                          "/posts",
                          "/posts/{id}",
                          "/posts/create",
                          "/index",
                          "/sign-up",
                          "/gym-page",
                          "/add-gym",
                          "/js/**",
                          "/css/**",
                          "/img/**")
                  .permitAll()
                  .anyRequest().authenticated();

    }
}
