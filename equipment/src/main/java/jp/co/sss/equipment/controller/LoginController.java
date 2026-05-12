package jp.co.sss.equipment.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jp.co.sss.equipment.entity.StaffData;
import jp.co.sss.equipment.form.LoginForm;
import jp.co.sss.equipment.service.IndexService;
import jp.co.sss.equipment.service.LoginResult;
import jp.co.sss.equipment.service.LoginService;
import jp.co.sss.equipment.service.OtpAuthService;

/**
 * ログイン処理
 */
@Controller
public class LoginController {
	@Autowired
	HttpSession session;

	@Autowired
	LoginService loginService;

	@Autowired
	IndexService indexService;

	@Autowired
	OtpAuthService otpAuthService;

	/**
	 * ログイン画面の表示
	 * @author 小松原
	 * @return　templates/index/index ログイン画面
	 */
	@GetMapping("/")
	public String loginForm(Model model) {
		//		session.invalidate();
		model.addAttribute("loginForm", new LoginForm());
		return "login";
	}

	/**
	 * ログイン処理
	 */
	@PostMapping("/login")
	public String login(@Valid @ModelAttribute LoginForm loginForm, BindingResult result, HttpSession session,
			Model model) {

		//エラーチェック
		if (result.hasErrors()) {
			return "login";
		}

		//ログイン処理
		LoginResult loginResult = loginService.excute(loginForm);

		if (loginResult.isLogin()) {
			// OTP発行
			otpAuthService.issueOtp(
					loginResult.getLoginUser().getStaffNo(),
					loginResult.getLoginUser().getMail());

			// 一時保持
			session.setAttribute("otpUser", loginResult.getLoginUser());
			return "redirect:/oneTime";
		}

		// これが必要ログイン失敗時
		model.addAttribute("errMessage", loginResult.getErrorMsg());
		return "login";
	}

	/**
	 * トップメニュー
	 * @return
	 */
	@GetMapping("/topMenu")
	public String topMenu() {
		return "topMenu";
	}

	/**
	 * ログアウト処理
	 * @return
	 */
	@GetMapping("/user/logout")
	public String logout(HttpSession session) {
		if (session != null) {
			session.invalidate();
		}
		return "redirect:/";
	}

	/**
	 * ワンタイムパスコード入力画面
	 */
	@GetMapping("/oneTime")
	public String onetime() {
		return "oneTime";
	}

	/*
	 * ワンタイムパスコード確認
	 */
	@PostMapping("/otpCheck")
	public String otpCheck(String otp, HttpSession session, Model model) {

		StaffData user = (StaffData) session.getAttribute("otpUser");

		if (user == null) {
			return "redirect:/";
		}

		boolean result = otpAuthService.verifyOtp(
				user.getStaffNo(),
				otp);

		if (result) {

			// ログイン確定
			session.setAttribute("user", user);

			// OTP用ユーザー削除
			session.removeAttribute("otpUser");

			// 権限判定
			if (user.getAuthNo() == 99) {
				// 一般ユーザー
				return "redirect:/index";
			}

			// 管理者
			return "redirect:/topMenu";
		}

		model.addAttribute("error", "コードが違います");
		return "oneTime";
	}
}