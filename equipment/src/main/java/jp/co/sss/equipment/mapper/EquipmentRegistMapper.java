package jp.co.sss.equipment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import jp.co.sss.equipment.entity.StockMaster;
import jp.co.sss.equipment.entity.StockTypeMaster;

/**
 * 備品登録マッパー
 *
 * @author 小松原
 */
@Mapper
public interface EquipmentRegistMapper {

	/**
	 * カテゴリ取得
	 * @return
	 */
	List<StockTypeMaster> categoryFind();

	/**
	 * カテゴリIDからカテゴリ情報を取得
	 * @param categoryId
	 * @return
	 */
	StockTypeMaster findByCategoryId(@Param("categoryId") Integer categoryId);

	/**
	 * 備品登録挿入
	 * @param stockMaster
	 */
	void equipmentRegistInsert(StockMaster stockMaster);
}
