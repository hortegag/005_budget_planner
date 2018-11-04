package com.home.budgetplanner.beans;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;

import com.home.budgetplanner.security.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@Order(3)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .formLogin()
                .defaultSuccessUrl("/welcome")
                .and()
                .logout().logoutSuccessUrl("/login")
                .and()
                .authorizeRequests()
                .antMatchers("/webjars/**", "/login").permitAll()
                //.antMatchers("/identificationTypeFlow/**").hasRole("USER") 
                //.antMatchers("/budgetplanner/**").hasRole("USER") 
                //.antMatchers("/peopleTestBootstrap-flow/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login")
                //.failureUrl("/login-error")
                ;
    }
    
    
    //@Autowired
    //public void configureAuth(AuthenticationManagerBuilder auth)
    //        throws Exception {
              
      //        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());;
              
              
        /*
               auth.inMemoryAuthentication()
                .withUser("user").password("{noop}user").roles("USER").and()
                .withUser("userflow").password("{noop}userflow").roles("USER","FLOWS").and()                
                .withUser("admin").password("{noop}admin").roles("USER", "ADMIN");
                */
    //}
    
    
        
    @Bean
    public PasswordEncoder passwordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder;
    }
    
    
    
    
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean()
            throws Exception {
        return super.authenticationManagerBean();
    }


    
}