package jp.co.sss.equipment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jp.co.sss.equipment.entity.StaffData;
import jp.co.sss.equipment.form.LoginForm;
import jp.co.sss.equipment.mapper.EmployeeMapper;
import jp.co.sss.equipment.util.Constant;
import jp.co.sss.equipment.util.LoginErrorType;

/**
 * ログイン処理
 */
@Service
public class LoginService {
	@Autowired
	private EmployeeMapper employMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	/**
	 * ログイン処理
	 * @param loginForm
	 * @return
	 */
	public LoginResult excute(LoginForm loginForm) {

		//社員IDだけでユーザー情報を取得
		StaffData staffData =
			    employMapper.findByEmpId(loginForm.getEmpId());

		//社員IDが一致しない場合
		if (staffData == null) {
			return LoginResult.failLogin(
					Constant.LOGIN_ERR_MSG,
					LoginErrorType.USER_NOT_FOUND);
		}
		
		//入力されたパスワードとDBのハッシュ化済みパスワードを照合する
		boolean passwordMatch = passwordEncoder.matches(
				loginForm.getEmpPass(),
				staffData.getPassword()
				);
		
		//パスワードが一致しない場合
		if(!passwordMatch) {
			return LoginResult.failLogin(Constant.LOGIN_ERR_MSG,
					LoginErrorType.USER_NOT_FOUND);
		}
		
		//社員ID・パスが正しい場合
		return LoginResult.succeedLogin(staffData);
	}
}