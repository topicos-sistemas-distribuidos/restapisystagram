package br.ufc.great.es.api.demo.security.aux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.ufc.great.es.api.demo.service.UsersService;


/**
 * Classe de configuração do Spring Security
 * @author armandosoaressousa
 *
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {    
    @Autowired
	private UsersService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);

    	http.authorizeRequests()                		
                .antMatchers("/register").permitAll()
                .antMatchers("/sessionExpired").permitAll()
                .antMatchers("/invalidSession").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/accesscontrol/**").hasAnyAuthority("ADMIN")
                .antMatchers("/users").hasAnyAuthority("ADMIN")
                .antMatchers("/users/").hasAnyAuthority("ADMIN")
                .antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
                .antMatchers("/dashboard/admin").hasAnyAuthority("ADMIN")
                .antMatchers("/dashboard/user").hasAnyAuthority("USER")
                .anyRequest().authenticated()
                .and()
            .formLogin()
            	.loginPage("/login")
            	.permitAll()
            	.defaultSuccessUrl("/")
            	.failureUrl("/login?error")                                          
                .and()
            .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .deleteCookies("auth_code", "JSESSIONID")
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/login")
                .permitAll();   
           	
    	http.sessionManagement()
    		.maximumSessions(2)
    		.expiredUrl("/sessionExpired");
    	
    	http.sessionManagement().invalidSessionUrl("/invalidSession");   
    	
    	http.sessionManagement()
    	  .sessionFixation().migrateSession();
    	
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	auth.userDetailsService(this.userService)
    	.passwordEncoder(new BCryptPasswordEncoder());
    }
}