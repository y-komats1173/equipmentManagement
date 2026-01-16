package jp.co.sss.equipment.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.sss.equipment.dto.DetailListViewDto;
import jp.co.sss.equipment.entity.StaffData;
import jp.co.sss.equipment.mapper.BorrowingMapper;

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
	 * 貸出更新
	 */
	//	@Transactional
	//	public void borrowingEquipment(
	//	    List<Integer> equipmentIdList,
	//	    List<Integer> staffNoList,
	//	    List<LocalDate> startDateList,
	//	    List<LocalDate> limitDateList
	//	) {
	//	    for (int i = 0; i < equipmentIdList.size(); i++) {
	//
	//	        LocalDate start = startDateList.get(i);
	//	        LocalDate limit = limitDateList.get(i);
	//
	//	        // 未入力は処理しない
	//	        if (start == null || limit == null) {
	//	            continue;
	//	        }
	//
	//	        // 開始日 <= 返却日
	//	        if (!start.isAfter(limit)) {
	//	            borrowingMapper.borrowingUpdate(
	//	                equipmentIdList.get(i),
	//	                staffNoList.get(i),
	//	                start,
	//	                limit
	//	            );
	//	        }
	//	    }
	//	}

	@Transactional
	public void borrowingEquipment(
	        List<Integer> equipmentIdList,
	        Map<String, String> staffNoMap,
	        Map<String, String> startDateMap,
	        Map<String, String> limitDateMap) {

	    for (Integer id : equipmentIdList) {

	        String staffKey = "staffNoMap[" + id + "]";
	        String startKey = "startDateMap[" + id + "]";
	        String limitKey = "limitDateMap[" + id + "]";

	        String staffStr = staffNoMap.get(staffKey);
	        String startStr = startDateMap.get(startKey);
	        String limitStr = limitDateMap.get(limitKey);

	        if (staffStr == null || startStr == null || limitStr == null) {
	            continue;
	        }

	        LocalDate start = LocalDate.parse(startStr);
	        LocalDate limit = LocalDate.parse(limitStr);

	        if (!start.isAfter(limit)) {
	            borrowingMapper.borrowingUpdate(
	                    id,
	                    Integer.valueOf(staffStr),
	                    start,
	                    limit
	            );
	        }
	    }
	}
}