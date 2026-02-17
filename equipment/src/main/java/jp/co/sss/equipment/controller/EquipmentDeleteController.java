package jp.co.sss.equipment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import jp.co.sss.equipment.dto.DetailListViewDto;
import jp.co.sss.equipment.service.EquimentDeletervice;
import jp.co.sss.equipment.service.IndexService;

/**
 * 備品削除
 */
@Controller
public class EquipmentDeleteController {
	@Autowired
	IndexService indexService;
	
	@Autowired
	EquimentDeletervice equimentDeletervice;

	/**
	 * 削除確認画面
	 */
	@GetMapping("/equipmentDeleteCheck")
	public String equipmentDeleteCheck(Model model, String serialNo) {
		DetailListViewDto detailList = indexService.serialNoFind(serialNo);
		model.addAttribute("detailName", detailList); //備品名が複数取得されるため１つ目だけ採取しHTMLに反映（１つだけのため）
		model.addAttribute("itemDetail", detailList);
		return "equipmentDelete/equipmentDeleteCheck";
	}
	
	/**
	 * 削除処理　削除完了画面へ遷移
	 */
	@PostMapping("/equipmentDeleteComplete")
	public String equipmentDeleteComplete(String serialNo) {
		//論理削除処理
	    equimentDeletervice.deleteBySerialNo(serialNo);
	    return "equipmentDelete/equipmentDeleteComplete";
	}
}