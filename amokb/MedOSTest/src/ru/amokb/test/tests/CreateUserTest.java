package ru.amokb.test.tests;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import ru.amokb.test.lib.MedOSTest;

/**
 * Создание пользователя
 *
 */
//tested, pass
public class CreateUserTest extends MedOSTest {

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://192.168.7.249:8080";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	@Test
	public void run() throws Exception  {
		System.out.println("Запуск теста: Создание пользователя");
		driver.get(baseUrl + "/riams/ecom_login.do"); 
		logIn("admin");
String user_name = "testuser_"+getCurrentDate("HHmm");
	    driver.findElement(By.xpath("//*/a[@id='mainMenuConfig']")).click();
	//    forceWait(4);
	    
	    driver.findElement(By.xpath(".//div[@class='menu']//a[contains(@href,'entityList-secuser.do')]")).click();
	    forceWait(2);
	    driver.findElement(By.xpath("//*/a[@id='ALT_N']")).click();
	    forceWait(2);
	    driver.findElement(By.xpath("//*[@id='login']")).sendKeys(user_name);
	   forceWait(2);
	    driver.findElement(By.xpath("//*[@id='fullname']")).sendKeys("Test User");
	   forceWait(2);
	    driver.findElement(By.xpath("//*[@id='password']")).sendKeys("1");
	    forceWait(2);
	    driver.findElement(By.xpath("//*[@id='changePasswordAtLogin']")).click();
	    forceWait(2);
	    driver.findElement(By.xpath("//*[@id='submitButton']")).click();
	      forceWait(2);
	    checkErrorMessage();
	    driver.findElement(By.xpath("//*[@title='Активация']")).click();
	    System.out.println("Пользователь "+user_name+" создан");
	    logOut();
	    System.out.println("Тест завершен успешно");
	    
	}
}
