package jp.co.sss.equipment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.co.sss.equipment.entity.StockMaster;
import jp.co.sss.equipment.entity.StockTypeMaster;
import jp.co.sss.equipment.form.EquipmentUpdateForm;
import jp.co.sss.equipment.service.EquimentUpdateService;
import jp.co.sss.equipment.service.EquipmentRegistService;
import jp.co.sss.equipment.util.BeanCopy;

/**
 * 備品編集
 */
@Controller
public class EquipmentUpdateController {
	@Autowired
	EquimentUpdateService equimentUpdateService;
	
	@Autowired
	EquipmentRegistService equipmentRegistService;

	/*
	 * 備品編集入力画面表示
	 */
	@GetMapping("/equipmentUpdateInput")
	public String equimentUpdateInput(Model model, String serialNo) {
		
		List<StockTypeMaster> categoryList = equipmentRegistService.categoryFind();
		for (StockTypeMaster category : categoryList) {
			System.out.println(category);
		}
		
		StockMaster stockMaster = equimentUpdateService.equimentFind(serialNo);


	    EquipmentUpdateForm form = BeanCopy.copyDtoToUpdateForm(stockMaster);

		model.addAttribute("categoryList", categoryList);
	    model.addAttribute("equipmentUpdateForm", form);
	    StockMaster updateform =equimentUpdateService.equimentFind(serialNo);
		
		
	
		System.out.println("=======form=======");
		System.out.println("カテゴリ"+form.getCategoryId());
		//分類
		System.out.println("分類"+form.getOwnershipType());
		//貸出可否
		System.out.println("貸出可否"+form.getRentFlag());

		
		//ログイン機能追加後は、セッションチェックを実装
		return "equipmentUpdate/equipmentUpdateInput";
	}
}
