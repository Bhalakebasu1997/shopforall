package com.store.entities;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/*
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
*/

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User  {
	
	@Id
	@Column(name="User_Id")
	private String userId;
	
	
	@Column(name="User_Name")
	private String name;
	
	@Column(name="User_email", unique=true)
	private String email;
	
	@Column(name="User_about", length=50)
	private String about;
	
	@Column(name="User_Password" , length=500)
	private String password;
	
	@Column()
	private String gender;
	
	
	private String imageName;
	
	//private String roles;


	// this is must be implementions
	/*@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public String getPassword() {
		return this.password;

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

	@Override
	public boolean isEnabled() {
		return true;
	}*/

}
