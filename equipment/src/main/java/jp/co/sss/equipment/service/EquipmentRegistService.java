package jp.co.sss.equipment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.equipment.entity.StockTypeMaster;
import jp.co.sss.equipment.mapper.EquipmentRegistMapper;

/**
 * 備品登録サービス
 *
 * @author 小松原
 */
@Service
public class EquipmentRegistService {
	@Autowired
	EquipmentRegistMapper equipmentRegistMapper;
/*
 * 備品登録時に使用するDB操作
 * カテゴリ検索
 */
	public List<StockTypeMaster> categoryFind() {	
		return equipmentRegistMapper.categoryFind();
	}
}
