package jp.co.sss.equipment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jp.co.sss.equipment.entity.StockTypeMaster;
import jp.co.sss.equipment.form.EquipmentRegistForm;
import jp.co.sss.equipment.service.EquipmentRegistService;

/**
 * 備品登録コントローラー
 *
 * @author 小松原
 */
@Controller
public class EquipmentRegistController {
	@Autowired
	EquipmentRegistService equipmentRegistService;
	/**
	 * 備品入力画面表示
	 */
	@GetMapping("/equipmentRegistInput")
	public String equipmentRegistInput(Model model, @ModelAttribute EquipmentRegistForm form) {
		//ログイン機能追加後は、セッションチェックを実装
		//カテゴリの取得
		List<StockTypeMaster>categoryList = equipmentRegistService.categoryFind();
		for (StockTypeMaster category : categoryList) {
			System.out.println(category);
		}
		model.addAttribute("categoryList", categoryList);
		return "equipmentRegist/equipmentRegistInput";
	}
	
	/**
	 * 備品登録確認画面
	 */
	@PostMapping("/equipmentRegistCheck")
	public String equipmentRegistCheck(Model model,EquipmentRegistForm form) {
		return "equipmentRegist/EquipmentRegistCheck";
	}
	
	/**
	 * 備品登録処理
	 */
	@PostMapping("/equipmentRegistComplete")
	public String equipmentRegistComplete() {
		return "equipmentRegist/equipmentRegistComplete";
	}
}
