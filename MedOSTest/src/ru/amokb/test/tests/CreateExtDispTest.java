package ru.amokb.test.tests;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

import ru.amokb.test.lib.MedOSTest;

//tested, passed
/**
 * Создание карты доп. диспансеризации
 *
 */
public class CreateExtDispTest extends MedOSTest {
	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://192.168.7.249:8080";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	@Test
	public void run() throws Exception {
		System.out.println("Запуск теста: Создание карты доп. диспансеризации");
		String dat=getCurrentDate("ddMMyyyy");
		driver.get(baseUrl + "/riams/ecom_login.do"); 
		logIn("admin");
		
		String ln="СИДОРОВ", fn="ИВАН", id="311546", crd="Н28728";
		clickByXpath("//*/img[@alt='На главное меню']");
		forceWait(16);
		
		driver.findElement(By.id("lastname")).clear();
		driver.findElement(By.id("lastname")).sendKeys(ln+" "+fn);
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		forceWait(8);
		
		clickByXpath("//tr[normalize-space(@class)='list "+id+"']/td[text()='"+crd+"']");
		clickByXpath("//*/a[contains(@title, 'дополнительной диспансеризации')]");
		forceWait(4);
		clickByXpath("//*/a[contains(@href, 'entityParentPrepareCreate-extDisp_card')]");
		forceWait(4);
		inputText(driver.findElement(By.id("lpuName")), "мариин");
		forceWait(4);
		driver.findElement(By.id("lpuLabel")).click();
		inputText(driver.findElement(By.id("socialGroupName")), "1\b1\b1");
		forceWait(4);
		driver.findElement(By.id("socialGroupName")).click();
		inputText(driver.findElement(By.id("dispTypeName")), "1\b1\b1");
		forceWait(4);
		driver.findElement(By.id("dispTypeName")).click();
		forceWait(4);
		driver.findElement(By.id("startDate")).sendKeys(dat);
		forceWait(4);
		driver.findElement(By.id("finishDate")).sendKeys("31052016");
		forceWait(4);
		inputText(driver.findElement(By.id("ageGroupName")), "24\b4");
		forceWait(4);
		driver.findElement(By.id("ageGroupName")).click();
		inputText(driver.findElement(By.id("workFunctionName")), "тест");
		forceWait(4);
		driver.findElement(By.id("workFunctionLabel")).click();
		inputText(driver.findElement(By.id("idcMainName")), "K29.6");
		forceWait(4);
		driver.findElement(By.id("workFunctionName")).click();
		inputText(driver.findElement(By.id("healthGroupName")), "1\b1\b1");
		forceWait(4);
		driver.findElement(By.id("healthGroupName")).click();
		clickByXpath("//*/input[@id='submitButton']");
		forceWait(4);
		checkErrorMessage();
		hitAltDel("Карта диспансеризации удалена");
		logOut();
		System.out.println("Тест завершен успешно");
	}
	
}
