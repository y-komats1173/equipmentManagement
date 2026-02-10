package jp.co.sss.equipment.form;

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
	
	/*入力タイプ*/
	private String inputType;
	
	/**備品名称*/
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
