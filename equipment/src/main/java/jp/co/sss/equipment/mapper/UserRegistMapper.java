package jp.co.sss.equipment.mapper;

import org.apache.ibatis.annotations.Mapper;

import jp.co.sss.equipment.entity.StaffData;

/**
 *ユーザー登録マッパー 
 */

@Mapper
public interface UserRegistMapper {

	/**
	 * ユーザーの登録処理
	 * @param staffData
	 */
	void userRegistInsert(StaffData staffData);
}
