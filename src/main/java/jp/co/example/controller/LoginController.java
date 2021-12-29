package jp.co.example.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.example.domain.UserEntity;
import jp.co.example.form.RegisterUserForm;
import jp.co.example.service.UserService;

@Controller
@RequestMapping("/")
public class LoginController {

	@Autowired
	private UserService userService;

	@ModelAttribute
	public RegisterUserForm setUpUserForm() {
		return new RegisterUserForm();
	}

	/**
	 * ユーザー登録画面へ遷移します.
	 * 
	 * @return register.html
	 */
	@RequestMapping(path = "/register")
	public String showResister() {
		return "register";
	}

	/**
	 * ユーザーを新規登録します.
	 * @param form 入力情報
	 * @param result ログイン画面へリダイレクト
	 * @return login.html
	 */
	@RequestMapping(path = "/register/decision")
	public String registerUser(@Validated RegisterUserForm form,BindingResult result) {
		
		// パスワード確認
		if(!form.getPassword().equals(form.getConfirmPassword())){
			result.rejectValue("password", "", "パスワードが一致していません");
			result.rejectValue("confirmationPassword", "", "");
		}
		
		//メールアドレスの重複チェック
		UserEntity existUserEntity = userService.searchUserByEmail(form.getEmail());
		if(existUserEntity != null) {
			result.rejectValue("mailAddress", "", "そのメールアドレスは既に登録されています");
		}
		
		//エラーがある場合、入力画面へ遷移
		if(result.hasErrors()) {
			return showResister();
		}
		
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(form, userEntity);
		userService.insertUser(userEntity);
		return "redirect:/";
	}

	/**
	 * ログイン画面へ遷移します.
	 * 
	 * @return login.html
	 */
	@RequestMapping(path = "/")
	public String showLogin(@RequestParam(required = false) String error) {
		System.err.println("login error:" + error);
		if (error != null) {
			System.err.println("login failed");
		}
		return "login";
	}

//	/**
//	 * ログイン認証を行いトップページへ遷移します.
//	 * 
//	 * @param email    メールアドレス
//	 * @param password パスワード
//	 * @return top.html
//	 */
//	@PostMapping(path = "/login")
//	public String login(String email, String password) {
//		UserEntity user = userService.searchLoginUser(email, password);
//		if (user == null) {
//			return "redirect:/";
//		}
//		return "top";
//	}

//	/**
//	 * ログアウトし、ログイン画面へ遷移します.
//	 * 
//	 * @return login.html
//	 */
//	@GetMapping("/logout")
//	public String logout() {
//		return "redirect:/";
//	}
}