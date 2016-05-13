package ru.amokb.test.tests;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.firefox.FirefoxDriver;

import ru.amokb.test.lib.MedOSTest;

//test, pass
/**
 * Создание дополнительного времени
 *
 */
public class CreateOvertimeTest extends MedOSTest {

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://192.168.7.249:8080";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	@Test
	public void run() throws Exception {
		System.out.println("Запуск теста: Создание дополнительного времени");
		String usr="ebitmaeva" //, dct="ВОЛОШИНА ОЛЬГА"
				, d=getCurrentDate("ddMMyyyy"), t="07:00";
		
		driver.get(baseUrl + "/riams/ecom_login.do");
		
		String RR="Тест досрочно остановлен.";
		try {
			if(!logIn(usr)) {
				System.out.println(RR);
				return;
			}
			
			driver.findElement(By.linkText("Создание дополнительного времени")).click();

			String s="379";
			inputText(driver.findElement(By.id("specialistName")), s);
			forceWait(4);
			
			driver.findElement(By.id("specialistName")).sendKeys(Keys.ENTER);
			forceWait(4);
			
			driver.findElement(By.id("date")).sendKeys(d);
			driver.findElement(By.id("times")).sendKeys(t);
			 
			forceWait(2);
			driver.findElement(By.id("btnGenerate")).click();
			checkErrorMessage(); 
			alertClick();
		   checkErrorMessage();
			System.out.println("Доп. время создано");
		} catch(Exception e) {
			System.out.println("Доп. время не создано.");
			System.out.println(RR);
			System.out.println(e.getMessage());
			return;
		}
		
		alertClick() ;
		logOut();
		System.out.println("Тест завершен успешно");
	}
	
	@After
	public void tearDown() throws Exception {
	    /*driver.quit();
	    String verificationErrorString = verificationErrors.toString();
	    if (!"".equals(verificationErrorString)) {
	      fail(verificationErrorString);
	    }*/
	}
}
