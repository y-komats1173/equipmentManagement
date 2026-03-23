package jp.co.sss.equipment.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jp.co.sss.equipment.entity.AuthMaster;
import jp.co.sss.equipment.entity.StaffData;
import jp.co.sss.equipment.form.UserForm;
import jp.co.sss.equipment.service.StaffCommonService;
import jp.co.sss.equipment.service.UserRegistService;
import jp.co.sss.equipment.service.UserUpdateService;
import jp.co.sss.equipment.util.BeanCopy;

/**
 * ユーザー情報編集コントローラ
 */

@Controller
public class UserUpdateController {
	
	@Autowired
	StaffCommonService staffCommonService;
	
	@Autowired
	UserRegistService userRegistService;
	
	@Autowired
	UserUpdateService userUpdateService;
	
	/**
	 * 編集画面遷移
	 */
	@GetMapping("user/update/input/{staffNo}")
	public String updateInput(@PathVariable Integer staffNo, Model model) {
		List<AuthMaster> authList = staffCommonService.authFind();
		model.addAttribute("authList", authList);

		// ユーザーの個別詳細を取得
		StaffData staffData = staffCommonService.staffFindIndividual(staffNo);

		// entity → form にコピー
		UserForm form = BeanCopy.userCopyForm(staffData);
		model.addAttribute("userRegistForm", form);

		System.out.println(staffData);
		System.out.println(form);

		return "userUpdate/input";
	}
	
	/**
	 * 編集確認画面
	 */
	@PostMapping("user/update/check")
	public String updateCheck(
	        @Valid @ModelAttribute("userRegistForm") UserForm registForm,
	        BindingResult result,
	        Model model) {

	    // 権限情報の取得
	    List<AuthMaster> authList = staffCommonService.authFind();
	    model.addAttribute("authList", authList);

	    // 入力エラー
	    if (result.hasErrors()) {
	        return "userUpdate/input";
	    }

	    // 元のIDと変更後IDを比較
	    Integer oldStaffNo = registForm.getOldStaffNo();

	    if (oldStaffNo != null
	            && !oldStaffNo.equals(registForm.getStaffNo())
	            && staffCommonService.idCheck(registForm.getStaffNo())) {

	        result.rejectValue("staffNo", null, "このIDはすでに使用されています");
	        return "userUpdate/input";
	    }

	    // 権限IDから権限情報を取得
	    if (registForm.getAuth() != null) {
	        AuthMaster authMaster = staffCommonService.authFindById(registForm.getAuth());
	        model.addAttribute("authMaster", authMaster);
	    }

	    model.addAttribute("userRegistForm", registForm);

	    return "userUpdate/check";
	}
	
	/**
	 * 完了画面（UPDATE 処理）
	 */
	@PostMapping("user/update/complete")
	public String updateComplete(UserForm updateForm, Model model) {
		userUpdateService.userUpdate(updateForm);
		return "userUpdate/complete";
	}
}
