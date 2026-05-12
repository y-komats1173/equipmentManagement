package jp.co.sss.equipment.mapper;

import org.apache.ibatis.annotations.Mapper;

import jp.co.sss.equipment.entity.StaffData;
/**
 * 社員情報テーブルマッパー
 * 
 */ 
@Mapper
public interface EmployeeMapper {
	/**
	 * ログインのためのID、パスワード検索
	 * ハッシュ化したパスワード検索
	 */
	StaffData findByEmpId(Integer empId);
}
