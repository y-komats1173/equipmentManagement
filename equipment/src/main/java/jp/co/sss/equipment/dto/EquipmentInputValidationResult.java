package jp.co.sss.equipment.dto;

import java.util.List;

import lombok.Data;

/**
 * 備品入力バリデーション結果DTO
 */
@Data
public class EquipmentInputValidationResult {

	/* エラーメッセージ一覧*/
	private List<String> errorMessages;

	public EquipmentInputValidationResult(
			List<String> errorMessages) {
		this.errorMessages = errorMessages;
	}
}
