package com.dao;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

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

import com.model.TargetURL;


public class DaoUtil {

	public void createElementImage(WebDriver driver, WebElement element, String filename) {
		//获取 垂直方向滚动的值
		Long scrollTop=  (Long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollTop"); 
		Point location = element.getLocation();
		Dimension size = element.getSize();
		BufferedImage originalImage;
		try {
			originalImage = ImageIO.read(new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
//			File Image = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
//			FileUtils.copyFile(Image, new File("d:\\1.png"));
			BufferedImage croppedImage = originalImage.getSubimage(location.getX(), (int) (location.getY()-scrollTop), size.getWidth(),
					size.getHeight());
			ImageIO.write(croppedImage, "png", new File(filename));
			System.out.println(filename + "保存成功过");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	
	/**
	 * 移动获取图片  
	 * @param driver 参数driver
	 * @param element 要操作的那个element
	 * @throws InterruptedException
	 */
	public void moveAction(WebDriver driver, WebElement element,int day) throws InterruptedException {
		Dimension size = element.getSize();
		int yOffset = size.getHeight() / 2;
		float movewidth = size.getWidth() / day;

		float xOffset =0;
		Actions action = new Actions(driver);
		action.moveToElement(element, (int) xOffset, yOffset).perform();
		Thread.sleep(1000);
		action.moveToElement(element, (int) (xOffset+2*movewidth), yOffset).perform();
		Thread.sleep(1000);
		
		while (xOffset < size.getWidth()) {	
			action.moveToElement(element, (int) xOffset, yOffset).perform();
			try{
				WebElement viewele = driver.findElement(By.id("viewbox"));
				WebElement viewetd = driver.findElement(By.xpath("//td[@class='view-value']"));
				String dayid = driver.findElement(By.xpath("//div[@class='view-table-wrap']")).getText();
				if (!viewele.isDisplayed()) {
					action.moveToElement(element, (int)(size.getWidth()/2), yOffset).perform();
					action.moveToElement(element, (int) xOffset, yOffset).perform();
				}else{
					Thread.sleep(1200);
					if((viewele.getLocation().getX())!=0 && (viewele.getLocation().getY())!=0 ){
						createElementImage(driver, viewetd, "./result_pic/"+String.valueOf(dayid)+".png");
					}
					xOffset = xOffset + movewidth;	
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}

	}
	
	
	/**
	 * 开始获取
	 * @param driver
	 * @param target
	 * @throws InterruptedException 
	 */
	public void begin(WebDriver driver,TargetURL target) throws InterruptedException{
		driver.navigate().to(target.getURL());
		Thread.sleep(500);
		WebElement trendele=driver.findElement(By.id("trend"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", trendele);
		moveAction(driver, trendele,target.getdays());	
		
	}

}
