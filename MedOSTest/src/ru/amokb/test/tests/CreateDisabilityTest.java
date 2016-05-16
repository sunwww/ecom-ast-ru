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
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@Test
	public void run () throws Exception {	
		System.out.println("Запуск теста: Создание листа нетрудоспособности");
		driver.get(baseUrl + "/riams/ecom_login.do"); 
		logIn("admin");
		String ln="СИДОРОВ", fn="ИВАН", id="311546", crd="Н28728";
		String dat=getCurrentDate("ddMMyyyy"), dct="ВОЛОШИНА ОЛЬГА";
		findElement(By.xpath("//*/img[@alt='На главное меню']"),16).click();
		findElement(By.id("lastname")).clear();
		findElement(By.id("lastname")).sendKeys(ln+" "+fn);
		findElement(By.cssSelector("input[type='submit']")).click();
		clickByXpath("//tr[normalize-space(@class)='list "+id+"']/td[text()='"+crd+"']",8);
		clickByXpath("//*/a[contains(@title, 'нетрудоспособности')]");
		clickByXpath("//*/a[contains(@href, 'entityParentPrepareCreate-dis_document')]");
		checkErrorMessage(1);
		inputText(findElement(By.id("disabilityReasonName")), "01\b1");
		findElement(By.id("dateFrom")).sendKeys(dat);
		inputText(findElement(By.id("workFunctionName")), dct.substring(0, 8));
		clickByXpath("//*/input[@id='submitButton']");
		checkErrorMessage(1);
		logOut();				
		System.out.println("ЛН введён");
		System.out.println("Тест завершен успешно");
		
	}
}
