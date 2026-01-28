package jp.co.sss.equipment.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sss.equipment.dto.BorrowingValidationResult;
import jp.co.sss.equipment.dto.DetailListViewDto;
import jp.co.sss.equipment.entity.StaffData;
import jp.co.sss.equipment.mapper.BorrowingMapper;
import jp.co.sss.equipment.util.DateUtil;

/**
 *備品管理「貸出」サービス
 * @author 小松原
 */
@Service
public class BorrowingService {
	@Autowired
	BorrowingMapper borrowingMapper;

	/**
	 * 貸出画面に現在貸出可能（手元にある）の備品を渡す
	 * @param name
	 * @return ReturnController
	 */
	public List<DetailListViewDto> borrowingFindView(String name) {
		return borrowingMapper.borrowingFind(name); //マッパーの処理を返す
	}

	/**
	 * スタッフデータを取得
	 * @return
	 */
	public List<StaffData> staffDataFind() {
		return borrowingMapper.staffFind();//マッパーの処理を返す
	}

	/**
	 * 入力チェック
	 * @param equipmentIdList
	 * @param serialMap
	 * @param staffNoMap
	 * @param startDateMap
	 * @param limitDateMap
	 * @return
	 */
	public BorrowingValidationResult validateBorrowing(
			List<Integer> equipmentIdList,
			Map<String, String> serialMap,
			Map<String, String> staffNoMap,
			Map<String, String> startDateMap,
			Map<String, String> limitDateMap) {

		List<String> errorMessages = new ArrayList<>(); // エラーメッセージ格納用
		Set<Integer> errorEquipmentIds = new HashSet<>(); // エラー備品ID格納用
		Set<Integer> warningEquipmentIds = new HashSet<>(); // 警告備品ID格納用
		Set<Integer> normalEquipmentIds = new HashSet<>(); // 正常備品ID格納用
		LocalDate today = DateUtil.getToday();

		// 貸出対象選択チェック
		if (equipmentIdList == null || equipmentIdList.isEmpty()) {
			errorMessages.add("　貸出対象が選択されていません");

			//BorrowingValidationResultにインスタンス生成して格納する
			return new BorrowingValidationResult(errorMessages, errorEquipmentIds, warningEquipmentIds,
					normalEquipmentIds);
		}

		// 各備品ごとのチェック
		for (Integer id : equipmentIdList) {

			String key = id.toString();
			String serial = serialMap.get(key);
			String staffStr = staffNoMap.get(key);
			String startStr = startDateMap.get(key);
			String limitStr = limitDateMap.get(key);

			List<String> rowErrors = new ArrayList<>();

			// --- 未入力チェック ---
			if (staffStr == null || staffStr.isBlank()) {
				rowErrors.add("　使用者未選択");
			}
			if (startStr == null || startStr.isBlank()) {
				rowErrors.add("　貸出開始日未入力");
			}
			if (limitStr == null || limitStr.isBlank()) {
				rowErrors.add("　返却予定日未入力");
			}

			// --- 日付パース ---
			LocalDate startDate = null;
			LocalDate limitDate = null;

			if (startStr != null && !startStr.isBlank()) {
				startDate = LocalDate.parse(startStr);
			}
			if (limitStr != null && !limitStr.isBlank()) {
				limitDate = LocalDate.parse(limitStr);
			}

			// --- 日付相関チェック ---
			if (startDate != null && limitDate != null) {
				if (limitDate.isBefore(startDate)) {
					rowErrors.add("　返却予定日が開始日より前です");
				}
			}

			// --- エラーまとめ ---
			if (!rowErrors.isEmpty()) {
				errorEquipmentIds.add(id);
				errorMessages.add("【シリアル：" + (serial != null ? serial : "不明") + "】");
				errorMessages.addAll(rowErrors);
				continue;
			}

			// --- 警告／正常判定 ---
			if (startDate.isAfter(today)) {
				warningEquipmentIds.add(id);
			} else {
				normalEquipmentIds.add(id);
			}
		}
		return new BorrowingValidationResult(errorMessages, errorEquipmentIds, warningEquipmentIds, normalEquipmentIds);
	}

	/**
	 * 貸出更新
	 */
	@Transactional
	public void borrowingEquipment(
			List<Integer> equipmentIdList,
			Map<String, String> staffNoMap,
			Map<String, String> startDateMap,
			Map<String, String> limitDateMap) {
		for (Integer id : equipmentIdList) {
			// キーは ID そのもの（文字列）になっているはず
			String key = id.toString();

			// 備品IDをキーにしてMapから値を取得
			String staffStr = staffNoMap.get(key);
			String startStr = startDateMap.get(key);
			String limitStr = limitDateMap.get(key);

			// 未入力がある場合弾く
			if (staffStr == null || staffStr.isBlank()
					|| startStr == null || startStr.isBlank()
					|| limitStr == null || limitStr.isBlank()) {
				continue;
			}

			LocalDate start = LocalDate.parse(startStr);
			LocalDate limit = LocalDate.parse(limitStr);

			if (!start.isAfter(limit)) {
				int updateCount = borrowingMapper.borrowingUpdate(
						id,
						Integer.valueOf(staffStr),
						start,
						limit);
				if (updateCount == 0) {
					throw new IllegalStateException("他のブラウザで更新済み");
				}
			}
		}
	}
}