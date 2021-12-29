package jp.co.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/afterLogin")
public class AfterLoginController {
	
	/**
	 * トップページに遷移します.
	 * 
	 * @return top.html
	 */
	@RequestMapping(path = "/top")
	public String showTop() {
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
