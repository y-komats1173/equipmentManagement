package jp.co.sss.equipment.form;

import lombok.Data;

/**
 * 備品検索用フォーム
 */

@Data
public class EquipmentSearchForm {
	
	 /** シリアル番号 */
    private String stockCode;

    /** 備品名 */
    private String name;

    /** カテゴリ */
    private Integer stockType;

    /** 状態（rent / stock） */
    private String status;
    
    /** 所有区分（1:会社保有 / 2:リース品） */
    private Integer ownershipType;
}
