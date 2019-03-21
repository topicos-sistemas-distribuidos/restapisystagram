package br.ufc.great.es.api.demo.model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Classe modelo de Usuário
 * @author armandosoaressousa
 *
 */
@Entity
public class Users extends AbstractModel<Long> implements UserDetails{	
	private static final long serialVersionUID = 1L;
	@Column(length=50)
	private String username;
	@Column(length=255)
	private String password;
	@Column(nullable = false)
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private boolean enabled;
	@Column(length=255)
	private String email;		
	@ManyToMany(fetch = FetchType.EAGER)
	private List<Role> roles = new LinkedList<Role>();
	@OneToOne(cascade=CascadeType.ALL)
	private Person person;
	
	public Users() {
	}
	
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Users(String username, String password, String email) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	/**
	 * Lista as permissoes do usuario
	 * @return lista de permissoes
	 */
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return (Collection<? extends GrantedAuthority>) this.getRoles();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

}