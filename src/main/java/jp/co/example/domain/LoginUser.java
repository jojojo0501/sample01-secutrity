package jp.co.example.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class LoginUser extends User {

	private static final long serialVersionUID = 1L;
	private final UserEntity userEntity;

	public LoginUser(UserEntity userEntity, Collection<GrantedAuthority> authorityList) {
		super(userEntity.getEmail(), userEntity.getPassword(), authorityList);
		this.userEntity = userEntity;
	}

	public UserEntity getUserEntity() {
		return userEntity;
	}
}
