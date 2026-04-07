package jp.co.sss.equipment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import jp.co.sss.equipment.dto.EquipmentSearchDto;
import jp.co.sss.equipment.form.EquipmentSearchForm;

/**
 * 備品検索マッパー
 */
@Mapper
public interface EquipmentSearchMapper {

	/*
	 * 備品条件検索
	 */
	List<EquipmentSearchDto> search(EquipmentSearchForm form);;

}
