package jp.co.sss.equipment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jp.co.sss.equipment.dto.OverdueListDto;

/**
 * 返却遅延マッパー
 */

@Mapper
public interface OverdueMapper {

	/**
	 * 返却期限切れ一覧取得
	 * @return
	 */
	List<OverdueListDto> returnExpiredList();
}
