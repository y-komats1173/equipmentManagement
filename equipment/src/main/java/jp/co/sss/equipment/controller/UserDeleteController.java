package jp.co.sss.equipment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import jp.co.sss.equipment.service.UserDeleteService;
/**
 * ユーザー削除コントローラ
 */

@Controller
public class UserDeleteController {

	@Autowired
	UserDeleteService userDeleteService;
}
