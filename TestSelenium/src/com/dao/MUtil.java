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
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MUtil {

	/**
	 * ��ȡͼƬ
	 * 
	 * @param driver
	 * @param element
	 * @param filename
	 */
	
	private static Random random = new Random();
	
	public void createElementImage(WebDriver driver, WebElement element, String filename) {
		// ��ȡ ��ֱ���������ֵ
		Long scrollTop = (Long) ((JavascriptExecutor) driver).executeScript("return document.body.scrollTop");
		Point location = element.getLocation();
		Dimension size = element.getSize();
		BufferedImage originalImage;
		try {
			originalImage = ImageIO
					.read(new ByteArrayInputStream(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)));
			BufferedImage croppedImage = originalImage.getSubimage(location.getX(), (int) (location.getY() - scrollTop),
					size.getWidth(), size.getHeight());
			ImageIO.write(croppedImage, "png", new File(filename));
			System.out.println(filename + "����ɹ���");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * ÿ��ƽ��
	 * 
	 * @param driver
	 * @param element
	 * @param name
	 */
	public void goAction(WebDriver driver, WebElement element, String name) throws InterruptedException {

		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("toLoading")));

		// �������ѡ��PC������
		// wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[@data-value='pc']")));
		// WebElement pc =
		// driver.findElement(By.xpath("//li[@data-value='pc']"));
		// String pcstage = pc.getAttribute("class");
		// while (!pcstage.equals("active")) {
		// pc.click();
		// pcstage = pc.getAttribute("class");
		// wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("toLoading")));
		// }

		// ѡ��ƽ����
		wait.until(ExpectedConditions.elementToBeClickable(By.id("trend-meanline")));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("toLoading")));
		WebElement sb = driver.findElement(By.id("trend-meanline"));
		String sbstage = sb.getAttribute("class");
		while (!sbstage.equals("select")) {
			sb.click();
			sbstage = sb.getAttribute("class");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("toLoading")));
		}
		Actions action = new Actions(driver);
		action.moveToElement(element, 52, 350).perform();
		WebElement tbv = driver.findElement(By.id("trendBarVal"));
		while (!tbv.isDisplayed()) {
//			int b = (random.nextInt(10)) * 2 + 330;
//			int a = (random.nextInt(10)) * 2 + 52;
//			action.moveToElement(element,a, b).perform();
			for(int b=320;b<351;b++){
				action.moveToElement(element,52, b).perform();
			}
//			System.out.println(a);
//			System.out.println(b);
			
		}
		Thread.sleep(1000);

		if ((tbv.getLocation().getX()) != 0 && (tbv.getLocation().getY()) != 0) {
			createElementImage(driver, tbv, name + ".png");
		}
	}

	
	public boolean doesWebElementExist(WebDriver driver, By selector) {
		try {
			driver.findElement(selector);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}

	}

	/**
	 * �ƶ���ȡÿ��ͼƬ
	 * 
	 * @param driver
	 *            ����driver
	 * @param element
	 *            Ҫ�������Ǹ�element
	 */
	public void moveAction(WebDriver driver, WebElement element, int day, String name) throws InterruptedException {
		Dimension size = element.getSize();
		int yOffset = size.getHeight() / 2;
		float movewidth = size.getWidth() / day;
		
		float xOffset = 0;
		Actions action = new Actions(driver);
		
		while(!doesWebElementExist(driver,By.id("viewbox"))){
			action.moveToElement(element, (int) xOffset, yOffset).perform();
			Thread.sleep(500);
			action.moveToElement(element, (int) (xOffset + (random.nextInt(20)) * movewidth), yOffset).perform();
		}
		

		while (xOffset < size.getWidth()) {
			action.moveToElement(element, (int) xOffset, yOffset).perform();
			try {
				WebElement viewele = driver.findElement(By.id("viewbox"));
				WebElement viewetd = driver.findElement(By.xpath("//td[@class='view-value']"));
				String dayid = driver.findElement(By.xpath("//div[@class='view-table-wrap']")).getText();
				while(!viewele.isDisplayed()){
					action.moveToElement(element, (int) (size.getWidth() /(random.nextInt(6)+1)), yOffset).perform();
					Thread.sleep(500);
					action.moveToElement(element, (int) xOffset, yOffset).perform();				
				}
				Thread.sleep(800);
				if ((viewele.getLocation().getX()) != 0 && (viewele.getLocation().getY()) != 0) {
					createElementImage(driver, viewetd, "./result_pic/" + name + String.valueOf(dayid) + ".png");
				}else{
					action.moveToElement(element, (int) xOffset, yOffset+random.nextInt(6)+5).perform();
					Thread.sleep(500);
					((JavascriptExecutor) driver).executeScript("arguments[0].style.display='block';", viewele);
					Thread.sleep(800);
					if ((viewele.getLocation().getX()) != 0 && (viewele.getLocation().getY()) != 0) {
						createElementImage(driver, viewetd, "./result_pic/" + name + String.valueOf(dayid) + ".png");
					}
				}
				xOffset = xOffset + movewidth;
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	/**
	 * ������ȡ�·����� ��ȡtrend��ǩ��ܲ����ƶ����ñ�ǩ
	 * 
	 * @param driver
	 * @param url
	 *            ���ʵ�ַ
	 * @param name
	 *            ����ͼƬ����
	 */
	public void begin(WebDriver driver, String url, String name) throws InterruptedException {
		driver.navigate().to(url);
		WebElement trendele = driver.findElement(By.id("trend"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", trendele);
		Thread.sleep(500);
		goAction(driver, trendele, name);

	}

	/**
	 * ��ȡÿ������
	 * 
	 * @param driver
	 * @param url
	 *            ·��
	 * @param name
	 *            ��������
	 * @param day
	 *            ��������
	 * @throws InterruptedException
	 */
	public void begin(WebDriver driver, String url, String name, int day) throws InterruptedException {
		driver.navigate().to(url);
		Thread.sleep(500);
		WebElement trendele = driver.findElement(By.id("trend"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", trendele);
		moveAction(driver, trendele, day, name);

	}

}
