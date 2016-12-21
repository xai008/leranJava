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
		
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("toLoading")));
		
		//这个用来选择PC端数据
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@data-value='pc']")));
		WebElement pc = driver.findElement(By.xpath("//li[@data-value='pc']"));
		String pcstage = pc.getAttribute("class");
		while (!pcstage.equals("active")) {
			pc.click();
			pcstage = pc.getAttribute("class");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("toLoading")));
		}
		
		//选择平均数
		wait.until(ExpectedConditions.elementToBeClickable(By.id("trend-meanline")));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("toLoading")));
		WebElement sb=driver.findElement(By.id("trend-meanline"));
		String sbstage=sb.getAttribute("class");	
		while(!sbstage.equals("select")){
			sb.click();
			sbstage=sb.getAttribute("class");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("toLoading")));
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
			createElementImage(driver, tbv, name+".png");
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
