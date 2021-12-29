package jp.co.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.example.domain.UserEntity;
import jp.co.example.repository.UserRepository;

/**
 * ユーザー情報を扱うサービスクラス.
 *
 */
@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/**
	 * メールアドレス、パスワードをもとにユーザーを検索します.
	 * @param email メールアドレス
	 * @param password　パスワード
	 * @return ユーザー情報
	 */
	public UserEntity searchLoginUser(String email,String password) {
		UserEntity user = userRepository.findByEmailAndPassword(email, password);
		return user;
	}
	
	/**
	 * メールアドレスをもとにユーザーを検索します.
	 * @param email メールアドレス
	 * @return ユーザー情報
	 */
	public UserEntity searchUserByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	/**
	 * ユーザー登録を行います.
	 * @param userEntity ユーザー情報
	 * @return 登録後のユーザー情報
	 */
	public void insertUser(UserEntity userEntity) {
		String afterEncodePassword = passwordEncoder.encode(userEntity.getPassword());
		userEntity.setPassword(afterEncodePassword);
		userRepository.insert(userEntity);
	}
}
