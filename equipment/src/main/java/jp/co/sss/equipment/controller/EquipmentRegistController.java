package jp.co.sss.equipment.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
	public String equipmentRegistCheck(@Valid @ModelAttribute EquipmentRegistForm registform, BindingResult result, Model model) {
//		if(result.hasErrors()) {
//			return "equipmentRegist/equipmentRegistInput";
//		}else {
			//入力情報の取得
			StockTypeMaster stockTypeMaster = equipmentRegistService.categoryFindCheck(registform.getCategoryId());
			model.addAttribute("categoryName", stockTypeMaster.getName());
			System.out.println(stockTypeMaster.getName()+"カテゴリID:"+registform.getCategoryId());
			System.out.println(registform.getEquipmentName());
			System.out.println(registform.getModel());
			System.out.println(registform.getMaker());
			System.out.println(registform.getOwnershipType());
			System.out.println(registform.getLeaseReturnDate());
			System.out.println(registform.getRemarks());
			System.out.println(registform.getRentFlag());
			model.addAttribute("equipmentRegistForm", registform);
//		}
		return "equipmentRegist/EquipmentRegistCheck";
	}
	
	/**
	 * 備品登録入力画面へ戻る
	 */
	@PostMapping("/equipmentRegistBack")
	public String equipmentRegistBack(@ModelAttribute EquipmentRegistForm registform, Model model) {
		System.out.println(registform.getCategoryId());
		System.out.println(registform.getEquipmentName());
		System.out.println(registform.getModel());
		System.out.println(registform.getMaker());
		System.out.println(registform.getOwnershipType());
		System.out.println(registform.getLeaseReturnDate());
		System.out.println(registform.getRemarks());
		System.out.println(registform.getRentFlag());
		List<StockTypeMaster>categoryList = equipmentRegistService.categoryFind();
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("equipmentRegistForm", registform);
		return "equipmentRegist/equipmentRegistInput";
	}
	
	/**
	 * 備品登録処理
	 */
	@PostMapping("/equipmentRegistComplete")
	public String equipmentRegistComplete(EquipmentRegistForm registform) {
		System.out.println("登録処理実行");
		System.out.println(registform.getCategoryId());
		System.out.println(registform.getEquipmentName());
		System.out.println(registform.getModel());
		System.out.println(registform.getMaker());
		System.out.println(registform.getOwnershipType());
		System.out.println(registform.getLeaseReturnDate());
		System.out.println(registform.getRemarks());
		System.out.println(registform.getRentFlag());
		equipmentRegistService.equipmentRegistInsert(registform);
		return "equipmentRegist/equipmentRegistComplete";
	}
}
