package ru.amokb.test.tests;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

import ru.amokb.test.lib.MedOSTest;
/**
 * Создание листа нетрудоспособности
 *
 */
//test, passed
public class CreateDisabilityTest extends MedOSTest {
	//private WebDriver driver;
	
	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://192.168.7.249:8080";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	@Test
	public void run () throws Exception {	
		System.out.println("Запуск теста: Создание листа нетрудоспособности");
		driver.get(baseUrl + "/riams/ecom_login.do"); 
		logIn("admin");
		String ln="СИДОРОВ", fn="ИВАН", id="311546", crd="Н28728";
		String dat=getCurrentDate("ddMMyyyy"), dct="ВОЛОШИНА ОЛЬГА";
		driver.findElement(By.xpath("//*/img[@alt='На главное меню']")).click();
		forceWait(16);
		
		driver.findElement(By.id("lastname")).clear();
		driver.findElement(By.id("lastname")).sendKeys(ln+" "+fn);
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		forceWait(8);
		
		clickByXpath("//tr[normalize-space(@class)='list "+id+"']/td[text()='"+crd+"']");
		
		clickByXpath("//*/a[contains(@title, 'нетрудоспособности')]");
		forceWait(4);
		clickByXpath("//*/a[contains(@href, 'entityParentPrepareCreate-dis_document')]");
		forceWait(4);
		inputText(driver.findElement(By.id("disabilityReasonName")), "01\b1");
		forceWait(4);
		driver.findElement(By.id("dateFrom")).sendKeys(dat);
		forceWait(4);
		inputText(driver.findElement(By.id("workFunctionName")), dct.substring(0, 8));
		forceWait(4);
		clickByXpath("//*/input[@id='submitButton']");
		forceWait(4);
		checkErrorMessage();
		logOut();				
		System.out.println("ЛН введён");
		System.out.println("Тест завершен успешно");
	}
}
