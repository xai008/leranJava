package com.test;

import java.io.File;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.dao.MUtil;
import com.model.MonthUrl;

public class TestMFire {

	public static void main(String[] args) throws InterruptedException {
		
				MUtil mUtil=new MUtil();
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
				
				Thread.sleep(400);
				for(int a=2011;a<2016;a++){
					System.out.println(a);
					for(int i=1;i<13;i++){
						MonthUrl murl=new MonthUrl("1","0",a,i,"艾滋病症状");
						System.out.println(murl.getURL());
						mUtil.begin(driver, murl.getURL(),a+"-"+i);
					}	
				}
				driver.close();
				System.out.println("Mission Completed");
		
		
	}

}
