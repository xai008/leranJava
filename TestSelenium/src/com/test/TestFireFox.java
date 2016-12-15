package com.test;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.dao.DaoUtil;
import com.model.TargetURL;

public class TestFireFox {
	public static void main(String[] args) throws InterruptedException {

		DaoUtil DUtil = new DaoUtil();
		//记得更换firefox的配置与位置
		System.setProperty("webdriver.firefox.marionette", "E:/0javatest/driver/geckodriver.exe");
		System.setProperty("webdriver.firefox.bin", "D:/Program Files/Mozilla Firefox/firefox.exe");
		
		File profileDir = new File("C:/Users/Administrator/AppData/Roaming/Mozilla/Firefox/Profiles/lxn0j4mo.default");
		FirefoxProfile profile = new FirefoxProfile(profileDir);
		WebDriver driver = new FirefoxDriver(profile);
		
		//最大化
		driver.manage().window().maximize();
		driver.navigate().to("http://www.baidu.com");
		System.out.println("start firefox browser succeed...");
		
		Thread.sleep(500);
		TargetURL target =new TargetURL("1", "0", 20160701, 20160801, "艾滋病初期症状");
		

		//i《12-开始月份
		for(int i=0;i<6;i++){
			DUtil.begin(driver, target);
			target.setStarttime(target.getStarttime()+100);
			target.setEndtime(target.getEndtime()+100);
			System.out.println(i);
		}
		//特殊处理12月的，假如是不12月结尾就不需要
//		target.setStarttime(target.getStarttime());
//		target.setEndtime(target.getEndtime()+8800);
//		DUtil.begin(driver, target);
		
		driver.close();
		System.out.println("Mission Completed");
		
	}
}
