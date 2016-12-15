package com.dao;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.imageio.ImageIO;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
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

	/**
	 * 百度指数的登陆
	 * @param driver
	 * @throws InterruptedException
	 */
	public void login(WebDriver driver) throws InterruptedException {
		WebElement popLogin = driver.findElement(By.partialLinkText("登录"));
		popLogin.click();
		Thread.sleep(500);
		WebElement userName = driver.findElement(By.name("userName"));
		userName.sendKeys("529091459@qq.com");
		WebElement password = driver.findElement(By.name("password"));
		password.sendKeys("19930519XAI123");
	}

	/**
	 * 添加cookie，不用这样处理，直接引chrome和firefox原来的就好
	 * @param driver
	 */
	public void addCookie(WebDriver driver) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("./src/broswer.data"));
			String line = null;
			while ((line = br.readLine()) != null) {
				// StringTokenizer str=new StringTokenizer(line,";");
				String[] str = line.split(";");
				String name = str[0];
				String value = str[1];
				// String domain = str[2];
				String path = str[3];
				String dt = str[4];
				Date expiry = null;
				if (!dt.equals("null")) {
					// expiry=new Date(dt);Thu Oct 06 11:27:29 GMT+08:00 2016
					DateFormat df = new SimpleDateFormat("E MMM dd hh:mm:ss zzzz yyyy", Locale.US);
					expiry = df.parse(dt);
				}

				// boolean isSecure = new Boolean(str[5]).booleanValue();
				// Cookie ck = new Cookie(name, value, domain, path, expiry,
				// isSecure);
				Cookie ck = new Cookie(name, value, path, expiry);
				driver.manage().addCookie(ck);

			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 获取网站的cookie
	 * @param driver
	 */
	public void getCookie(WebDriver driver) {
		File file = new File("./src/broswer.data");
		try {
			// delete file if exists
			file.delete();
			file.createNewFile();
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fw);
			for (Cookie ck : driver.manage().getCookies()) {
				bw.write(ck.getName() + ";" + ck.getValue() + ";" + ck.getDomain() + ";" + ck.getPath() + ";"
						+ ck.getExpiry() + ";" + ck.isSecure());
				bw.newLine();
			}
			bw.flush();
			bw.close();
			fw.close();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("cookie write to file");
		}
	}
	
	/**
	 * 获取大图片,在大图片里面切小图片
	 * @param 浏浏览驱动  元素  文件路径和名称格式
	 */
	
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
