package com.hobbygo.api.hobbygoapi.configuration.security.basicAuth;

import com.hobbygo.api.hobbygoapi.security.RepositoryUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private RepositoryUserDetailsService userDetailsService;
    /*
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
*/
    @Autowired
    private CustomBasicAuthenticationEntryPoint customBasicAuthenticationEntryPoint;
    private static String REALM="MY_TEST_REALM";

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
/*
        auth.inMemoryAuthentication().withUser("bill").password("abc123").roles("ADMIN");
        auth.inMemoryAuthentication().withUser("tom").password("abc123").roles("USER");
  */
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(new BCryptPasswordEncoder());

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/v1").permitAll()
                .antMatchers("/api/v1/users").permitAll()
                .antMatchers("/api/v1/users/registrationConfirm").permitAll()
                .antMatchers("/api/v1/distance").permitAll()
                .antMatchers("/api/v1/distanceCount").permitAll()
                .antMatchers("/api/v1/distanceCount").permitAll()
                .antMatchers("/api/v1/users/changePassword").permitAll()
                .antMatchers("/api/v1/users/updatePassword").permitAll()
                //.antMatchers("/api/v1/users/changePassword").hasAuthority("CHANGE_PASSWORD_PRIVILEGE")
                //.antMatchers("/api/v1/users/updatePassword").hasAuthority("CHANGE_PASSWORD_PRIVILEGE")
                //.antMatchers("/api/**").hasRole("OWNER")
                .antMatchers("/api/**").hasRole("USER")
                //.antMatchers("/api/**").hasRole("ADMIN")
                .and().httpBasic().realmName(REALM).authenticationEntryPoint(customBasicAuthenticationEntryPoint)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//We don't need sessions to be created.
    }

    /*
    @Bean
    public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint(){
        return new CustomBasicAuthenticationEntryPoint();
    }
    */

    /* To allow Pre-flight [OPTIONS] request from browser */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
        //web.ignoring().antMatchers(HttpMethod.GET, "/api/users");
    }

}
