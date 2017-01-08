package com.test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class TestChrome {

	public static void main(String[] args) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver", "E:/0javatest/driver/chromedriver.exe");
		ChromeOptions options = new ChromeOptions();
		options.addArguments("user-data-dir=C:/Users/Administrator/AppData/Local/Google/Chrome/User Data");
		WebDriver driver = new ChromeDriver(options);
		

		// ×î´ó»¯
		driver.manage().window().maximize();
		driver.get("http://www.baidu.com");

		Thread.sleep(500);
		

	}

}
