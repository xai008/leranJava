package com.dao;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MUtil {
	public void createElementImage(WebDriver driver, WebElement element, String filename) {
		//获取 垂直方向滚动的值
		Long scrollTop=  (Long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollTop"); 
		Point location = element.getLocation();
		Dimension size = element.getSize();
		BufferedImage originalImage;
		try {
			originalImage = ImageIO.read(new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
			BufferedImage croppedImage = originalImage.getSubimage(location.getX(), (int) (location.getY()-scrollTop), size.getWidth(),
					size.getHeight());
			ImageIO.write(croppedImage, "png", new File(filename));
			System.out.println(filename + "保存成功过");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public void goAction(WebDriver driver, WebElement element,String name) throws InterruptedException {
		
		
//		WebElement la=driver.findElement(By.className("toLoading"));
//		String stage2=la.getAttribute("style");	
//		System.out.println(stage2);
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("toLoading")));
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("toLoading")));
		
		WebElement sb=driver.findElement(By.id("trend-meanline"));
		String stage=sb.getAttribute("class");	
		while(!stage.equals("select")){
			sb.click();
			stage=sb.getAttribute("class");
		}
		Actions action = new Actions(driver);
		action.moveToElement(element, 52, 330).perform();		
		WebElement tbv=driver.findElement(By.id("trendBarVal"));
		while(!tbv.isDisplayed()){
			Random random=new Random();
			int b=(random.nextInt(10))*5+330;
			action.moveToElement(element, 52,b).perform();
		}
		Thread.sleep(1000);
		
		if((tbv.getLocation().getX())!=0 && (tbv.getLocation().getY())!=0 ){
			createElementImage(driver, tbv, "./result_pic/"+name+".png");
		}
	}
	
	
	
	
	
	/**
	 * 获取trend标签框架并且移动到该标签
	 * @param driver
	 * @param url  访问地址
	 * @param name 保存图片名字
	 * @throws InterruptedException
	 */
	public void begin(WebDriver driver,String url,String name) throws InterruptedException{
		driver.navigate().to(url);
		WebElement trendele=driver.findElement(By.id("trend"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", trendele);
		Thread.sleep(500);
		goAction(driver, trendele,name);
		
		
	}
	
	
	
	
}
