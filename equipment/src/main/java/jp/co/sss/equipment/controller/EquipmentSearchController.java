package jp.co.sss.equipment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import jp.co.sss.equipment.dto.EquipmentSearchDto;
import jp.co.sss.equipment.form.EquipmentSearchForm;
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

	    model.addAttribute("categoryList", equipmentCommonService.categoryFind());

	    boolean hasCondition =
	            (form.getStockCode() != null && !form.getStockCode().isBlank()) ||
	            (form.getName() != null && !form.getName().isBlank()) ||
	            form.getStockType() != null ||
	            (form.getStatus() != null && !form.getStatus().isBlank()) ||
	            form.getOwnershipType() != null;

	    if (hasCondition) {
	        List<EquipmentSearchDto> resultList = equipmentSearchService.search(form);
	        model.addAttribute("resultList", resultList);
	    }

	    return "index/search";
	}
}
