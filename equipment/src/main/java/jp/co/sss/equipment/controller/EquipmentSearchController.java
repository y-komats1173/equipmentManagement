package jp.co.sss.equipment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import jp.co.sss.equipment.dto.EquipmentSearchDto;
import jp.co.sss.equipment.form.EquipmentSearchForm;
import jp.co.sss.equipment.form.InventoryForm;
import jp.co.sss.equipment.service.EquipmentCommonService;
import jp.co.sss.equipment.service.EquipmentSearchService;

/**
 * 備品検索コントローラ
 */

@Controller
public class EquipmentSearchController {
	
	@Autowired
	EquipmentCommonService equipmentCommonService;
	
	@Autowired
	EquipmentSearchService equipmentSearchService;
	
	/*
	 * 検索画面
	 */
	@GetMapping("/equipment/search/view")
	public String searchView(
	        @ModelAttribute("searchForm") EquipmentSearchForm form,
	        Model model) {

		// カテゴリ一覧を取得
	    model.addAttribute("categoryList", equipmentCommonService.categoryFind());

	    // 最初は全件
	    List<EquipmentSearchDto> resultList = equipmentCommonService.equipmentAllFind();

	    // 検索条件が1つでも入力されているか判定
	    boolean hasCondition = (form.getStockCode() != null && !form.getStockCode().isBlank()) ||
	            (form.getName() != null && !form.getName().isBlank()) ||
	            form.getStockType() != null ||
	            (form.getStatus() != null && !form.getStatus().isBlank()) ||
	            form.getOwnershipType() != null;

	    // 条件がある場合のみ検索結果で上書き
	    if (hasCondition) {
	        resultList = equipmentSearchService.search(form);
	    }

	    // 棚卸し用フォーム生成
	    InventoryForm inventoryForm = new InventoryForm();
	    inventoryForm.setInventoryList(resultList);

	    model.addAttribute("resultList", resultList);
	    model.addAttribute("inventoryForm", inventoryForm);
	    return "index/search";
	}
}
