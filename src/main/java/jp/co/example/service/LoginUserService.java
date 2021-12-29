package jp.co.example.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jp.co.example.domain.LoginUser;
import jp.co.example.domain.UserEntity;
import jp.co.example.repository.UserRepository;

@Service
public class LoginUserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(email);
		if(userEntity == null) {
			throw new UsernameNotFoundException("そのEmaiは登録されていません");
		}
		Collection<GrantedAuthority> authorityList = new ArrayList<>();
//		authorityList.add(new SimpleGrantedAuthority("ROLE_USER"));
		return new LoginUser(userEntity,authorityList);
	}
}
