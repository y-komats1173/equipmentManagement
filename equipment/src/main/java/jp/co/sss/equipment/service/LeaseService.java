package jp.co.sss.equipment.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.sss.equipment.dto.LeaseListDto;
import jp.co.sss.equipment.entity.StaffData;
import jp.co.sss.equipment.form.LeaseSearchForm;
import jp.co.sss.equipment.mapper.LeaseMapper;

/**
 * リース一覧表示用サービス
 */
@Service
public class LeaseService {

	@Autowired
	LeaseMapper leaseMapper;

	/**
	 * リース製品取得
	 */
	public List<LeaseListDto> leaseListSertch(LeaseSearchForm form) {

		List<LeaseListDto> leaseList = leaseMapper.leaseListSertch(form);

		LocalDate today = LocalDate.now();

		for (LeaseListDto dto : leaseList) {

			if (dto.getLeaseReturnDate() == null || dto.getLeaseReturnDate().isEmpty()) {
				continue;
			}

			LocalDate leaseReturnDate = LocalDate.parse(dto.getLeaseReturnDate());

			if (leaseReturnDate.isBefore(today)) {
				dto.setRowClass("return-over");

			} else if (!leaseReturnDate.isAfter(today.plusDays(7))) {
				dto.setRowClass("return-week");

			} else if (!leaseReturnDate.isAfter(today.plusMonths(1))) {
				dto.setRowClass("return-month");
			}
		}

		return leaseList;
	}

	/**
	 * 通知する情報を取得
	 *
	 * マスター：全通知を取得
	 * その他ユーザー：自分が借りている備品のみ取得
	 */
	/**
	 * 通知する情報を取得
	 */
	public List<LeaseListDto> findLeaseAlerts(StaffData user) {

		if (user == null) {
			return new ArrayList<>();
		}

		/* マスター */
		if (user.getAuthNo() == 1) {
			return leaseMapper.findLeaseAlerts(null);
		}

		/* 一般ユーザー */
		return leaseMapper.findUserLeaseAlerts(
				user.getStaffNo()
		);
	}
}