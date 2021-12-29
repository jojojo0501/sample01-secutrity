package jp.co.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.example.domain.UserEntity;

/**
 * ユーザー情報を扱うリポジトリクラス.
 *
 */
@Repository
public class UserRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * Userオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<UserEntity> USER_ROW_MAPPER = new BeanPropertyRowMapper<>(UserEntity.class);

	/**
	 * ユーザー情報を主キー検索します.
	 * 
	 * @param id ユーザーId
	 * @return
	 */
	public UserEntity load(Integer id) {
		String sql = "SELECT id,name, email, password, zipcode, address, telephone FROM users where id=:id;";

		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		List<UserEntity> userList = template.query(sql, param, USER_ROW_MAPPER);
		if (userList.size() == 0) {
			return null;
		}
		return userList.get(0);
	}

	/**
	 * メールアドレスとパスワードよりユーザーを検索します.
	 * 
	 * @param email    メールアドレス
	 * @param password パスワード
	 * @return ユーザー情報
	 */
	public UserEntity findByEmailAndPassword(String email, String password) {
		StringBuilder sql = new StringBuilder();
		sql.append(
				"SELECT id,name,email,password,zipcode,address,telephone from users WHERE email=:email AND password=:password;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email).addValue("password", password);
		List<UserEntity> userList = template.query(sql.toString(), param, USER_ROW_MAPPER);
		if (userList.size() == 0) {
			return null;
		}
		return userList.get(0);
	};

	/**
	 * メールアドレスをもとにユーザーを検索します.
	 * 
	 * @param email メールアドレス
	 * @return ユーザー情報
	 */
	public UserEntity findByEmail(String email) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id,name,email,password,zipcode,address,telephone from users WHERE email=:email;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
		List<UserEntity> userList = template.query(sql.toString(), param, USER_ROW_MAPPER);
		if (userList.size() == 0) {
			return null;
		}
		return userList.get(0);
	};

	/**
	 * ユーザー登録をする.
	 * 
	 * @param user ユーザー情報
	 * @return 入力するユーザー情報
	 */
	public UserEntity insert(UserEntity usersEnitty) {
		StringBuilder insertSql = new StringBuilder();
		insertSql.append(
				"INSERT INTO users(name,email,password,zipcode,address,telephone) values(:name,:email,:password,:zipcode,:address,:telephone);");
		SqlParameterSource param = new BeanPropertySqlParameterSource(usersEnitty);
		template.update(insertSql.toString(), param);
		return usersEnitty;
	}
}
