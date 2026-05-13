package jp.co.sss.equipment.case1;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginTest {

    private WebDriver driver;

    @Test
    public void loginScreenTest() throws InterruptedException {

        // ChromeDriver自動設定
        WebDriverManager.chromedriver().setup();

        // Chrome起動
        driver = new ChromeDriver();

        // ローカル画面へアクセス
        driver.get("http://localhost:8080/");

        Thread.sleep(10000000);
        
        // タイトル表示
        System.out.println(driver.getTitle());
    }

    @AfterEach
    public void close() {

        if (driver != null) {
            driver.quit();
        }
    }
}