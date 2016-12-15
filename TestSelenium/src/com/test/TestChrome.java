package com.test;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.dao.DaoUtil;
import com.model.TargetURL;

public class TestChrome {

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "E:/0javatest/driver/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("user-data-dir=C:/Users/Administrator/AppData/Local/Google/Chrome/User Data");
		WebDriver driver = new ChromeDriver(options);
		DaoUtil DUtil = new DaoUtil();

		// 最大化
		driver.manage().window().maximize();
		driver.get("http://www.baidu.com");

		Thread.sleep(500);
		TargetURL target = new TargetURL("1", "0", 20160201, 20160302, "汪峰");
		driver.navigate().to(target.getURL());
		System.out.println("start firefox browser succeed...");
		Thread.sleep(500);

		WebElement trendele = driver.findElement(By.id("trend"));
		
		//因为每台电脑的分辨率不同，所以控制跳转到我们要的那个窗口
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", trendele);
		DUtil.createElementImage(driver, trendele, "./result_pic/test.png");
		
		DUtil.moveAction(driver, trendele, target.getdays());

		driver.close();
		System.out.println("Mission Completed");

	}

}
