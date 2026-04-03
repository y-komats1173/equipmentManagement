package jp.co.sss.equipment.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 *ユーザー情報削除マッパー 
 */

@Mapper
public interface UserDeleteMapper {

	//ユーザー削除
	void userDelete(@Param("staffNo")Integer staffNo);

}
