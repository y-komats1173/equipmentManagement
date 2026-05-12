package jp.co.sss.equipment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sss.equipment.dto.EquipmentSearchDto;
import jp.co.sss.equipment.form.EquipmentSearchForm;
import jp.co.sss.equipment.mapper.InventoryMapper;

/**
 * 棚卸し用サービス
 */

@Service
public class InventoryService {
	
	@Autowired
	InventoryMapper inventoryMapper;

	/**
	 * 最終確認日の更新
	 * @param list
	 */
    @Transactional
    public void inventoryUpdate(List<EquipmentSearchDto> list) {

        for (EquipmentSearchDto dto : list) {

            if (!dto.isChecked()) {
                continue;
            }
            inventoryMapper.updateConfirmDate(dto.getStockCode());
        }
    }
    
    /**
     * 検索条件が1つでも入力されているか判定
     */
    public boolean hasCondition(EquipmentSearchForm form) {
    	return (form.getStockCode() != null && !form.getStockCode().isBlank()) ||
        (form.getName() != null && !form.getName().isBlank()) ||
        form.getStockType() != null ||
        (form.getStatus() != null && !form.getStatus().isBlank()) ||
        form.getOwnershipType() != null;
    }
}
