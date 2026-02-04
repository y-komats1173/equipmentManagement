package jp.co.sss.equipment.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;

/**
 * 備品登録フォーム
 * 
 * @author 小松原
 */
@Data
public class EquipmentRegistForm {
	
	/**カテゴリID*/
	private Integer categoryId;
	
	/**備品名称*/
	@NotBlank
    @Size(min = 1, max = 16)
	private String equipmentName;
	
	/*型番*/
	private String model;
	
	/**メーカー*/
	private String maker;
	
	/**分類*/
	private String ownershipType;
	
	/**リース返却日*/
	private String leaseReturnDate;
	
	/**備考*/
	private String remarks;
	
	/**貸出可否*/
	private String rentFlag;
}
