package jp.co.example.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.example.domain.LoginUser;

@Controller
@RequestMapping("/afterLogin")
public class AfterLoginController {
	
	/**
	 * トップページに遷移します.
	 * （引数はログインしたユーザーを取得するための記述）
	 * @return top.html
	 */
	@RequestMapping(path = "/top")
	public String showTop(Model model,@AuthenticationPrincipal LoginUser loginUser) {
		model.addAttribute("loginUserName", loginUser.getUsername());
		return "top";
	}

	/**
	 * コンテンツページに遷移します.
	 * 
	 * @return content.html
	 */
	@GetMapping("/contents")
	public String showContent() {
		return "content";
	}

}
