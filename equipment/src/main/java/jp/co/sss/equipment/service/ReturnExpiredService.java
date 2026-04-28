package jp.co.sss.equipment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import jp.co.sss.equipment.dto.DetailListViewDto;
import jp.co.sss.equipment.mapper.ReturnMapper;

/**
 * 返却期限切れ通知コンポーネント
 */
@Component
public class ReturnExpiredService {

	@Autowired
	private ReturnMapper returnMapper;

	@Autowired
	private MailService mailService;

	private int i = 0;

	/**
	 *返却期限が切れた人にメールを送信
	 */
	//@Scheduled(cron = "0 0 9 * * ?") // 毎日9時
	@Scheduled(fixedRate = 1000000)
	public void notifyOverdue() {
		System.out.println("=====" + i + "回目=====");
		System.out.println("notifyOverdue()実行");

		//未返却かつ期限切れデータ取得
		List<DetailListViewDto> overdueList = returnMapper.equipmentReturnOver();

		for (DetailListViewDto dto : overdueList) {
			if (dto.getMail() == null || dto.getMail().isBlank()) {
				continue;
			}

			//メールの送信
			mailService.sendMail(
					"******@bg.co.jp",
					dto.getMail(),
					"返却期限超過のお知らせ",
					dto.getStaffName() + "様\n返却期限を過ぎています。");
		}
		System.out.println("期限切れメールを送信");
		i++;
	}
}
