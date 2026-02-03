package jp.co.sss.equipment.form;

import java.util.Date;

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
	private String equipmentName;
	
	/**メーカー*/
	private String maker;
	
	/**分類*/
	private String ownershipType;
	
	/**リース返却日*/
	private Date leaseReturnDate;
	
	/**備考*/
	private String remarks;
	
	/**貸出可否*/
	private String rentFlag;
}
