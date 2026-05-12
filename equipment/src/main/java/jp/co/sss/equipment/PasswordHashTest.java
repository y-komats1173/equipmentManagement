package jp.co.sss.equipment;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * パスワードハッシュ生成テスト
 */
public class PasswordHashTest {

	public static void main(String[] args) {

		BCryptPasswordEncoder encoder =
				new BCryptPasswordEncoder();

		// ハッシュ化したいパスワード一覧
		String[] passwords = {
				"1111",
				"2222",
				"3333",
				"4444",
				"5555"
		};

		// 1件ずつハッシュ化
		for (String password : passwords) {

			String hash = encoder.encode(password);

			System.out.println("元パスワード : " + password);
			System.out.println("ハッシュ    : " + hash);
			System.out.println("-------------------------");
		}
	}
}