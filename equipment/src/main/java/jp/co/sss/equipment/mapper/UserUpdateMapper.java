package jp.co.sss.equipment.mapper;

import org.apache.ibatis.annotations.Mapper;

import jp.co.sss.equipment.form.UserForm;

/**
 * ユーザー情報編集マッパー
 */
@Mapper
public interface UserUpdateMapper {

	/*
	 * ユーザー情報の変更
	 */
	int userUpdate(UserForm updateForm);

}
