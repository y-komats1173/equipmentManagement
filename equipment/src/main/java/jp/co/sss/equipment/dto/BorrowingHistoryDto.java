package jp.co.sss.equipment.dto;

import lombok.Data;

/**
 * 貸出履歴（一覧表示用DTO）
 */
@Data
public class BorrowingHistoryDto {

    /* シリアルナンバー */
    private String stockCode;
    
    /*カテゴリ*/
    private String categoryName;
    
    /*製品名*/
    private String productName;

    /* 使用者名 */
    private String staffName;

    /* 貸出日 */
    private String startDate;

    /* 返却日 */
    private String returnDate;

    /* 返却予定日 */
    private String limitDate;

    /* 分類（会社所有 / リース） */
    private String ownershipType;

    /* 削除済み表示用（"削除" ） */
    private String deletedLabel;
}