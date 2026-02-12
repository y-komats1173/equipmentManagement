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
	
	/**
	 * 個別詳細画面
	 */
	@GetMapping("/individualDetail")
	public String individualDetail(Model model, String serialNo) {
		DetailListViewDto detailList = indexService.serialNoFind(serialNo);
		DetailListViewDto detail = detailList;
		//値チェック
		System.out.println("=========個別詳細画面=========");
		System.out.println("備品名:"+detail.getEquipmentName());
		System.out.println("カテゴリ番号:" + detail.getStockType());
		System.out.println("シリアルNo:"+detail.getParentStockCode());
		System.out.println("使用者："+detail.getStaffName());
		System.out.println("貸出可否："+detail.getRentFlg());
		System.out.println("貸出開始日"+detail.getStartDate());
		System.out.println("返却予定日:"+detail.getLimitDate());
		System.out.println("最終所在確認日："+detail.getConfirmedDate());
		System.out.println("親シリアル："+detail.getConfirmedDate());
		System.out.println("製品名："+detail.getProductName());
		System.out.println("メーカ名："+detail.getMaker());
		System.out.println("分類："+detail.getOwnershipType());
		System.out.println("リース返却日："+detail.getLeaseReturnDate());
		System.out.println("備考："+detail.getRemarks());
		System.out.println("シーケンスID"+detail.getEquipmentId());
		System.out.println("スタッフID"+detail.getStaffNo());
		System.out.println("貸出可否:" +detail.getRentFlag());
		model.addAttribute("detailName", detailList); //備品名が複数取得されるため１つ目だけ採取しHTMLに反映（１つだけのため）
		model.addAttribute("itemDetail", detailList); 
		return "index/individualDetail";
	}
}