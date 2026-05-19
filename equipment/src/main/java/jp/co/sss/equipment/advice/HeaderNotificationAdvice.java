package jp.co.sss.equipment.advice;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import jp.co.sss.equipment.dto.LeaseListDto;
import jp.co.sss.equipment.entity.StaffData;
import jp.co.sss.equipment.service.LeaseService;

/**
 * ヘッダー通知共通
 */
@ControllerAdvice
public class HeaderNotificationAdvice {

	@Autowired
	private LeaseService leaseService;
	
	@Autowired
	private HttpSession session;

	/**
	 * 通知一覧機能
	 */
	@ModelAttribute("alertList")

	public List<LeaseListDto> alertList() {

		StaffData user = (StaffData) session.getAttribute("user");
		if (user == null) {
			return new ArrayList<>();
		}
		List<LeaseListDto> list = leaseService.findLeaseAlerts(user);
		System.out.println("====通知一覧====");
		for (LeaseListDto dto : list) {
			System.out.println(dto.getProductName() + " / " + dto.getAlertType());
		}
		return list;

	}

	/**
	 *通知件数
	 */
	@ModelAttribute("alertCount")
	public int alerCount() {
		StaffData user = (StaffData) session.getAttribute("user");
		if (user == null) {
			return 0;
		}
		
		int count = leaseService.findLeaseAlerts(user).size();
		System.out.println("通知件数:" + count);
		return count;
	}
}
