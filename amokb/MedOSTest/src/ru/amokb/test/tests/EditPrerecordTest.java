package ru.amokb.test.tests;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.firefox.FirefoxDriver;

import ru.amokb.test.lib.MedOSTest;

//test, pass
/**
 * 
 * @author vtsybulin
 *
 */
public class EditPrerecordTest extends MedOSTest {
	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://192.168.7.249:8080";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	@Test
	public void run () {
		System.out.println("Запуск теста: Редактирование чего-то там");
		Date dat=new Date();
		SimpleDateFormat sdfDy=new SimpleDateFormat("dd");
	String un="ebitmaeva", sp="гастроэнтеролог", d="ВОЛОШИНА ОЛЬГА", dy_=sdfDy.format(dat), t="07:00";
	driver.get(baseUrl + "/riams/ecom_login.do");
		
		logIn(un);
		
		driver.findElement(By.linkText("Пред. запись")).click();
		forceWait(4);
		
		//do {
		try {
			forceWait(8);
			driver.findElement(By.id("rdFunction")).findElement(By.xpath("//input[contains(@value, '"+sp+"')]")).click();
			forceWait(8);
			driver.findElement(By.id("rdSpecialist")).findElement(By.xpath("//input[contains(@value, '"+d+"')]")).click();
			forceWait(8);
			driver.findElement(By.cssSelector("#tdDay"+dy_+" > b")).click();
				 
			forceWait(8);
			driver.findElement(By.linkText(t)).click();
		 	 
		} catch(Exception e) { 
 			System.out.println(e.getMessage());
 			System.out.println("Невозможно заполнить направление.");
 			
		}
		//} while(!s.isEmpty());
		
	 	forceWait(4); 
		try {
			driver.findElement(By.id("serviceStreamName")).clear();
			driver.findElement(By.id("serviceStreamName")).sendKeys("1");
		 	forceWait(4); 
			driver.findElement(By.id("serviceStreamName")).sendKeys(Keys.ARROW_UP);
			driver.findElement(By.id("serviceStreamName")).sendKeys(Keys.ENTER);
			driver.findElement(By.id("serviceStreamName")).click();
			 
			driver.findElement(By.id("workPlaceTypeName")).clear();
			driver.findElement(By.id("workPlaceTypeName")).sendKeys("поликлини");
		 	forceWait(4); 
			driver.findElement(By.id("workPlaceTypeName")).sendKeys("ка");
			driver.findElement(By.id("workPlaceTypeName")).sendKeys(Keys.BACK_SPACE);
			 
		 	forceWait(4); 
			driver.findElement(By.id("workPlaceTypeName")).sendKeys(Keys.ENTER);
			 
			forceWait(2); 
			clickByXpath("//*[@id='submitButton']");
			checkErrorMessage();
			logOut();
			System.out.println("Тест завершен успешно");
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			System.out.println("Невозможно заполнить направление.");
		}
	}
	
}
