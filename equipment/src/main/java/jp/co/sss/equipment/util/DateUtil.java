package jp.co.sss.equipment.util;

import java.time.LocalDate;

/**
 * 日付管理
 * @author 小松原
 */
public class DateUtil {
	private DateUtil() {
	}
	
	/**
	 * 本日日付を取得し返す
	 * @return
	 */
	public static LocalDate getToday() {
		return LocalDate.now();
	}

	/** 
	 * 貸出時にデータ初期値2099の貸出期限初期値を渡す
	 * @return
	 */
	public static LocalDate getDefaultLimitDate() {
		return LocalDate.of(2099, 12, 31);
	}
}
