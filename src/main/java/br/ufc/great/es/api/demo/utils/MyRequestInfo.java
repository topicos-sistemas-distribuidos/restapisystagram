package br.ufc.great.es.api.demo.utils;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import br.ufc.great.es.api.demo.model.Users;
import br.ufc.great.es.api.demo.service.UsersService;

@Component
@Scope(value="session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyRequestInfo {

    private Users user;
	private UsersService userService;
	private String acesso;
	
	@Autowired
	public void setUserService(UsersService userServices){
		this.userService = userServices;
	}

	public UserDetails currentUserDetails(){
	    SecurityContext securityContext = SecurityContextHolder.getContext();
	    Authentication authentication = securityContext.getAuthentication();
	    if (authentication != null) {
	        Object principal = authentication.getPrincipal();
	        return principal instanceof UserDetails ? (UserDetails) principal : null;
	    }
	    return null;
	}
	
    public Users getCurrentUser() {
    	String username="";
    	
    	if (user == null) {	              	
    		Object principal = currentUserDetails();
    		
    		if (principal != null) {
    			username = ((UserDetails)principal).getUsername();
    			user = userService.getUserByUserName(username);
    			System.out.println("user name: " + username);
    		}
        	
        }
        
    	return user;
    }
    
    /**
     * Checa se uma determinada regra existe na lista de permissoes do usuário logado
     * @param role
     * @return
     */
    public boolean hasRole(String role) {	
    	boolean hasRole = false;

    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	
    	if (authentication != null) {
    		
        	Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) authentication.getAuthorities();

        	if (authorities != null) {  

        		for (GrantedAuthority authority : authorities) {
        			hasRole = authority.getAuthority().equals(role);
        			if (hasRole) {
        				break;
        			}
        		}

        		return hasRole;
        	}	
    	}
    	else {
    		return false;
    	}
		
    	return hasRole;
    }  
    
    /**
     * Verifica se o usuário logou como administrador
     */
    private String checkAccessControl() {
 	   if (hasRole("ADMIN")) {
 		   return acesso = "ADMIN";
 	   }else {
 		   return acesso = "";
 	   }
    }

	public String getAcesso() {
		return checkAccessControl();
	}
    
}