package jp.co.sss.equipment.dto;

import lombok.Data;

/**
 * ユーザー情報をビューに表示する
 */
@Data
public class StaffViewDto {
	
	/**スタッフID*/
	private Integer staffNo;
	
	/**名前*/
	private String name;
	
	/**メールアドレス*/
	private String mail;
	
	/**権限id*/
	private Integer authNo;
	
	/**権限名*/
	private String authName;

}
