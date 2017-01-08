package com.test;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.dao.MUtil;
import com.model.MonthUrl;

public class TestFireFox {

	public static void main(String[] args) throws InterruptedException {
		 Month();
//		Day();
	}

	public static void Day() throws InterruptedException {
		MUtil mUtil = new MUtil();
		// 记得更换firefox的配置与位置
		System.setProperty("webdriver.firefox.marionette", "E:/0javatest/driver/geckodriver.exe");
		System.setProperty("webdriver.firefox.bin", "D:/Program Files/Mozilla Firefox/firefox.exe");

		File profileDir = new File("C:/Users/Administrator/AppData/Roaming/Mozilla/Firefox/Profiles/lxn0j4mo.default");
		FirefoxProfile profile = new FirefoxProfile(profileDir);
		WebDriver driver = new FirefoxDriver(profile);

		// 最大化
		driver.manage().window().maximize();
		driver.navigate().to("http://www.baidu.com");
		System.out.println("start firefox browser succeed...");

		Thread.sleep(400);
		
		String targetN ="澳门住宿,澳门酒店,澳门酒店预订,澳门,澳门赌场,澳门攻略,澳门特产";
		String[] tn = targetN.split(",");
		for (String tName : tn) {
			System.out.println(tName);
			for (int a = 2011; a < 2016; a++) {
				System.out.println(a);
				for (int i = 1; i < 13; i++) {
					MonthUrl murl = new MonthUrl("1", "0", a, i, tName);
					System.out.println(murl.getURL());
					mUtil.begin(driver, murl.getURL(), tName, murl.getDay());
				}
			}
		}
		driver.close();
		System.out.println("Mission Completed");
	}

	public static void Month() throws InterruptedException {

		MUtil mUtil = new MUtil();
		// 记得更换firefox的配置与位置
		System.setProperty("webdriver.firefox.marionette", "E:/0javatest/driver/geckodriver.exe");
		System.setProperty("webdriver.firefox.bin", "D:/Program Files/Mozilla Firefox/firefox.exe");

		File profileDir = new File("C:/Users/Administrator/AppData/Roaming/Mozilla/Firefox/Profiles/lxn0j4mo.default");
		FirefoxProfile profile = new FirefoxProfile(profileDir);
		WebDriver driver = new FirefoxDriver(profile);

		// 最大化
		driver.manage().window().maximize();
		driver.navigate().to("http://www.baidu.com");
		System.out.println("start firefox browser succeed...");

		Thread.sleep(400);

		String targetN = "澳门旅游塔";
		String[] tn = targetN.split(",");
		for (String tName : tn) {
			System.out.println(tName);
			for (int a = 2011; a < 2016; a++) {
				System.out.println(a);
				for (int i = 1; i < 13; i++) {
					MonthUrl murl = new MonthUrl("1", "0", a, i, tName);
					System.out.println(murl.getURL());
					mUtil.begin(driver, murl.getURL(), "./result_pic/" + tName + "-" + a + "-" + i);
				}
			}
		}

		driver.close();
		System.out.println("Mission Completed");
	}

}
