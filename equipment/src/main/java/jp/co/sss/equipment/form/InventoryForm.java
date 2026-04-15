package jp.co.sss.equipment.form;

import java.util.List;

import jp.co.sss.equipment.dto.EquipmentSearchDto;
import lombok.Data;

/**
 * 棚卸し用フォーム
 */

@Data
public class InventoryForm {
	
	/**棚卸しデータ*/
	private List<EquipmentSearchDto> inventoryList;
}