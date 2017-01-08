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
		// �ǵø���firefox��������λ��
		System.setProperty("webdriver.firefox.marionette", "E:/0javatest/driver/geckodriver.exe");
		System.setProperty("webdriver.firefox.bin", "D:/Program Files/Mozilla Firefox/firefox.exe");

		File profileDir = new File("C:/Users/Administrator/AppData/Roaming/Mozilla/Firefox/Profiles/lxn0j4mo.default");
		FirefoxProfile profile = new FirefoxProfile(profileDir);
		WebDriver driver = new FirefoxDriver(profile);

		// ���
		driver.manage().window().maximize();
		driver.navigate().to("http://www.baidu.com");
		System.out.println("start firefox browser succeed...");

		Thread.sleep(400);
		
		String targetN ="����ס��,���žƵ�,���žƵ�Ԥ��,����,���Ŷĳ�,���Ź���,�����ز�";
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
		// �ǵø���firefox��������λ��
		System.setProperty("webdriver.firefox.marionette", "E:/0javatest/driver/geckodriver.exe");
		System.setProperty("webdriver.firefox.bin", "D:/Program Files/Mozilla Firefox/firefox.exe");

		File profileDir = new File("C:/Users/Administrator/AppData/Roaming/Mozilla/Firefox/Profiles/lxn0j4mo.default");
		FirefoxProfile profile = new FirefoxProfile(profileDir);
		WebDriver driver = new FirefoxDriver(profile);

		// ���
		driver.manage().window().maximize();
		driver.navigate().to("http://www.baidu.com");
		System.out.println("start firefox browser succeed...");

		Thread.sleep(400);

		String targetN = "����������";
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
