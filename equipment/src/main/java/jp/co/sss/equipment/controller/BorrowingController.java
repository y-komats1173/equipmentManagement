package jp.co.sss.equipment.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.co.sss.equipment.dto.DetailListViewDto;
import jp.co.sss.equipment.entity.StaffData;
import jp.co.sss.equipment.service.BorrowingService;
import jp.co.sss.equipment.service.IndexService;
import jp.co.sss.equipment.util.DateUtil;

/**
 *備品管理「貸出」
 * @author 小松原
 */
@Controller
public class BorrowingController {
	@Autowired //DIの導入
	BorrowingService borrowingService;

	@Autowired
	IndexService indexService;

	/**
	 * 貸出画面
	 * @author 小松原
	 * @param model
	 * @param name
	 * @return　templates/index/returnView 貸出画面
	 */
	//	@GetMapping("/borrowingView")
	//	public String borrowingView(Model model, String name) {
	//		List<DetailListViewDto> borrowingList = borrowingService.borrowingFindView(name); //備品名を取得する　サービス層で処理
	//		List<DetailListViewDto> detailName = indexService.detailFind(name);//貸出中の備品を取得する
	//		List<StaffData> staffList = borrowingService.staffDataFind(); //スタッフ情報を取得する
	//		//model.addAttribute("detailName", detailName.get(0)); //備品名をひとつ取得し、HTMLに表示させる
	//		if (!detailName.isEmpty()) {
	//		    model.addAttribute("detailName", detailName.get(0));
	//		} else {
	//		    model.addAttribute("detailName", null); // または空オブジェクトを作る
	//		}
	//		model.addAttribute("itemDetail", borrowingList);//貸出中の備品をHTMLのテーブルに表示させる
	//		model.addAttribute("staffName", staffList);//スタッフ情報をhtmlのドロップダウンに表示させる
	//
	//		//日付を渡す 文字列として渡す
	//		model.addAttribute("today", DateUtil.getToday().toString());
	//		model.addAttribute("defaultLimit", DateUtil.getDefaultLimitDate().toString());
	//		return "borrowing/borrowingView";//templatesフォルダーのhtmlを表示させる
	//	}

	@GetMapping("/borrowingView")
	public String borrowingView(Model model, String name) {
		List<DetailListViewDto> detailNameList = indexService.detailFind(name);

		DetailListViewDto detailName = null;
		if (!detailNameList.isEmpty()) {
			detailName = detailNameList.get(0);
		} else {
			detailName = new DetailListViewDto(); // 空のオブジェクト作っとく
			detailName.setEquipmentName(""); // null より "" が安全
		}
		model.addAttribute("detailName", detailName);

		List<DetailListViewDto> borrowingList = borrowingService.borrowingFindView(name);
		List<StaffData> staffList = borrowingService.staffDataFind();

		model.addAttribute("itemDetail", borrowingList);
		model.addAttribute("staffName", staffList);
		model.addAttribute("today", DateUtil.getToday().toString());
		model.addAttribute("defaultLimit", DateUtil.getDefaultLimitDate().toString());

		//シーケンスidが取得できていない
		//デバッグ
		int num = 0;
		for (DetailListViewDto i : detailNameList) {
			num++;
			System.out.println("===========" + num + "===========");
			System.out.println("===========" + "貸出" + "===========");
			System.out.println("備品名:" + i.getEquipmentName());
			System.out.println("シリアル:" + i.getParentStockCode());
			System.out.println("使用者:" + i.getStaffName());
			System.out.println("貸出可否:" + i.getRentFlg());
			System.out.println("貸出開始日:" + i.getStartDate());
			System.out.println("返却予定日:" + i.getLimitDate());
			System.out.println("最終所在確認" + i.getConfirmedDate());
			System.out.println("備考:" + i.getRemarks());
			System.out.println("シーケンスID: " + i.getEquipmentId());
			System.out.println("スタッフID:" + i.getStaffNo());
		}
		return "borrowing/borrowingView";
	}

	/**
	 * 貸出処理
	 * @param equipmentIdList
	 * @param name
	 * @param model
	 * @return
	 */
	@PostMapping("/borrowingProcess")
	public String borrowingProcess(
			@RequestParam(value = "equipmentIdList", required = false) List<Integer> equipmentIdList,
			@RequestParam Map<String, String> staffNoMap,
			@RequestParam Map<String, String> startDateMap,
			@RequestParam Map<String, String> limitDateMap,
			@RequestParam(value = "name", required = false) String name,
			RedirectAttributes redirectAttributes) {

		System.out.println("=== borrowingProcess called ===");
		System.out.println("equipmentIdList: " + equipmentIdList);
		System.out.println("staffNoMap: " + staffNoMap);
		System.out.println("startDateMap: " + startDateMap);
		System.out.println("limitDateMap: " + limitDateMap);
		System.out.println("name: " + name);

		if (equipmentIdList != null && !equipmentIdList.isEmpty()) {
			// Service に Map も含めてまとめて渡す
			borrowingService.borrowingEquipment(equipmentIdList, staffNoMap, startDateMap, limitDateMap);
		}
		List<DetailListViewDto> detailNameList = indexService.detailFind(name);
		if (!detailNameList.isEmpty()) {
			redirectAttributes.addFlashAttribute("detailName", detailNameList.get(0));
		}
		redirectAttributes.addFlashAttribute("name", name);
		return "redirect:/borrowingView";
	}
}