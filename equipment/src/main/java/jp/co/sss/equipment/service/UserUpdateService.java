package jp.co.sss.equipment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sss.equipment.dto.PasswordCheckDto;
import jp.co.sss.equipment.entity.StaffData;
import jp.co.sss.equipment.form.UserForm;
import jp.co.sss.equipment.mapper.StaffCommonMapper;
import jp.co.sss.equipment.mapper.UserUpdateMapper;

/**
 * ユーザー情報編集サービス
 */
@Service
public class UserUpdateService {

	@Autowired
	UserUpdateMapper userUpdateMapper;

	@Autowired
	StaffCommonMapper staffCommonMapper;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	StaffCommonService staffCommonService;

	/**
	 * 更新処理
	 */
	@Transactional
	public int userUpdate(UserForm updateForm) {

		// 新しいパスワードが未入力なら、DBの既存ハッシュを保持する
		if (updateForm.getPassword() == null || updateForm.getPassword().isBlank()) {

			StaffData dbUser = staffCommonMapper.staffFindIndividual(updateForm.getOldStaffNo());
			updateForm.setPassword(dbUser.getPassword());

		} else {

			// 更新確定時だけ新しいパスワードをハッシュ化する
			String hashPassword = passwordEncoder.encode(updateForm.getPassword());
			updateForm.setPassword(hashPassword);
		}

		return userUpdateMapper.userUpdate(updateForm);
	}

	/**
	 * パスワードチェック
	 */
	public PasswordCheckDto checkPasswordUpdate(UserForm form, StaffData loginUser) {

		PasswordCheckDto dto = new PasswordCheckDto();

		// 新しいパスワード未入力ならチェック不要
		if (form.getPassword() == null || form.getPassword().isEmpty()) {
			return dto;
		}

		// 自分以外のパスワード変更は禁止
		if (!loginUser.getStaffNo().equals(form.getOldStaffNo())) {
			dto.setPasswordChangeNotAllowed(true);
			return dto;
		}

		// 現在のパスワード未入力
		if (form.getCurrentPassword() == null || form.getCurrentPassword().isEmpty()) {
			dto.setCurrentPasswordRequired(true);
			return dto;
		}

		// DBのハッシュ済みパスワードを取得
		StaffData dbUser = staffCommonMapper.staffFindIndividual(form.getOldStaffNo());

		// 現在のパスワード照合
		boolean passwordMatch = passwordEncoder.matches(
				form.getCurrentPassword(),
				dbUser.getPassword());

		if (!passwordMatch) {
			dto.setCurrentPasswordInvalid(true);
		}

		// 確認パスワード未入力
		if (form.getCheckPassword() == null || form.getCheckPassword().isEmpty()) {
			dto.setCheckPasswordRequired(true);
		}

		// 確認パスワード不一致
		else if (!form.getPassword().equals(form.getCheckPassword())) {
			dto.setCheckPasswordInvalid(true);
		}

		return dto;
	}

	/**
	 * メールアドレス重複チェック
	 *
	 * 自分自身のアドレスならOK
	 * 他ユーザー使用中ならtrue
	 */
	public boolean mailDuplicateCheck(UserForm form) {

		// DBの自分情報取得
		StaffData dbUser = staffCommonMapper.staffFindIndividual(form.getOldStaffNo());

		// メール変更なし
		if (dbUser.getMail().equals(form.getMail())) {
			return false;
		}

		// 他ユーザー重複チェック
		return staffCommonService.addressCheck(
				form.getMail());
	}
}