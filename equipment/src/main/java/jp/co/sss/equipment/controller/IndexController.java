package jp.co.sss.equipment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jp.co.sss.equipment.dto.DetailListViewDto;
import jp.co.sss.equipment.dto.EquipListViewDto;
import jp.co.sss.equipment.service.IndexService;

/**
 * 備品一覧
 * @author 小松原 2025/12/04
 */
@Controller
public class IndexController {
	@Autowired
	IndexService indexService;

	/**
	 * 一覧画面
	 * @author 小松原
	 * @param model
	 * @param name
	 * @return　templates/index/index 一覧画面
	 */
	@GetMapping("/index") //右のURLを入力するとページが遷移される
	public String index(Model model) {//メソッド　model→viewに渡すデータ
		List<EquipListViewDto> indexlist = indexService.indexFind();//サービス層のindexFindメソッドを呼び出し値をリストに返す
		model.addAttribute("items", indexlist);//indexに情報を渡す　”items”をindex.htmlのth:eactに一致させる
		return "index/index"; //tenmplatsファルダーのindexのindex.htmlが呼出される
	}

	/**
	 * 詳細画面
	 * @author 小松原
	 * @param model
	 * @param name
	 * @return　templates/index/detail 詳細画面
	 */
	@GetMapping("/detail")
	public String detail(Model model, String name) { //String name HTMLから検索したい値を引数として用意
		List<DetailListViewDto> detailList = indexService.detailFind(name);//サービス層のdetailFindメソッドを呼び出し値をリストに返す
		model.addAttribute("detailName", detailList.get(0)); //備品名が複数取得されるため１つ目だけ採取しHTMLに反映（１つだけのため）
		model.addAttribute("itemDetail", detailList); 
		
		//値取得チェック
		for (DetailListViewDto dto : detailList) {
			System.out.println("備品名：" + dto.getEquipmentName());
			System.out.println("カテゴリ番号：" + dto.getStockType());
			System.out.println("シリアルナンバー：" + dto.getParentStockCode());
			System.out.println("使用者：" + dto.getStaffName());
			System.out.println("貸出可否：" + dto.getRentFlg());
			System.out.println("貸出開始日：" + dto.getStartDate());
			System.out.println("返却予定日：" + dto.getLimitDate());
			System.out.println("最終所在確認：" + dto.getConfirmedDate());
			System.out.println("親シリアル：" + dto.getDestinationSerial());
			System.out.println("製品名：" + dto.getProductName());
			System.out.println("メーカー名：" + dto.getMaker());
			System.out.println("分類：" + dto.getOwnershipType());
			System.out.println("リース返却予定日：" + dto.getLeaseReturnDate());
			System.out.println("備考：" + dto.getRemarks());
			System.out.println("シーケンスid：" + dto.getEquipmentId());
			System.out.println("スタッフid：" + dto.getStaffNo());
			System.out.println("---------------------------");
		}
		
		return "index/detail";
	}
	
	/**
	 * 「貸出」「返却」の画面から戻るボタンを押下したとき
	 */
	@GetMapping("/detailBack")
	public String detailBack(Model model, String name) { //String name HTMLから検索したい値を引数として用意
		List<DetailListViewDto> detailList = indexService.detailFind(name);//サービス層のdetailFindメソッドを呼び出し値をリストに返す
		model.addAttribute("detailName", detailList.get(0)); //備品名が複数取得されるため１つ目だけ採取しHTMLに反映（１つだけのため）
		model.addAttribute("itemDetail", detailList); 
		return "index/detail";
	}
}