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
 * Создание карты доп. диспансеризации, заведение услуг, удаление карты
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
		String dateStart=getCurrentDate("ddMMyyyy");
		String dateEnd="31052016";
		driver.get(baseUrl + "/riams/ecom_login.do"); 
		 
		logIn("admin");
		
		String ln="СИДОРОВ", fn="ИВАН", id="311546", crd="Н28728";
			
		clickByXpath("//*/img[@alt='На главное меню']",2);
		findElement(By.id("lastname")).clear();
		findElement(By.id("lastname")).sendKeys(ln+" "+fn);
		findElement(By.cssSelector("input[type='submit']")).click();
		clickByXpath("//tr[normalize-space(@class)='list "+id+"']/td[text()='"+crd+"']");
		clickByXpath("//*/a[contains(@title, 'дополнительной диспансеризации')]",2);
		clickByXpath("//*/a[contains(@href, 'entityParentPrepareCreate-extDisp_card')]",2);
		inputText(findElement(By.id("lpuName")), "мариин");
		
		findElement(By.id("lpuLabel")).click();
		inputText(findElement(By.id("socialGroupName")), "1\b1\b1");
		
		findElement(By.id("socialGroupName")).click();
		inputText(findElement(By.id("dispTypeName")), "1\b1\b1");
		
		findElement(By.id("dispTypeName")).click();
		
		
		findElement(By.id("startDate")).sendKeys(dateStart);
		
		findElement(By.id("finishDate")).sendKeys("31052016");
		
		inputText(findElement(By.id("ageGroupName")), "24\b4");
		
		findElement(By.id("ageGroupName")).click();
		inputText(findElement(By.id("workFunctionName")), "тест");
		
		findElement(By.id("workFunctionLabel")).click();
		inputText(findElement(By.id("idcMainName")), "K29.6");

		findElement(By.id("workFunctionName")).click();
		inputText(findElement(By.id("healthGroupName")), "1\b1\b1");
		
		findElement(By.id("healthGroupName")).click();
		clickByXpath("//*/input[@id='submitButton']");
		
		checkErrorMessage(1);
		
	
		driver.get(baseUrl + "/riams/entityParentView-extDisp_card.do?id=11");
		
		//Добавление услуг
		clickByXpath("//*/a[contains(@href, 'js-extDisp_service-edit.do?id=')]",2);
		int cntLab = Integer.valueOf(findElement(By.id("cntExam")).getAttribute("value"));
		int cntVis = Integer.valueOf(findElement(By.id("cntVisit")).getAttribute("value"));
		for (int i=0;i<cntLab;i++) {
			findElement(By.id("examServiceDate"+i)).sendKeys(dateStart);
		}
		for (int i=0;i<cntVis;i++) {
			findElement(By.id("visitServiceDate"+i)).sendKeys(dateEnd);
		}
		checkErrorMessage(1);
		findElement(By.id("submButton")).click();
		hitAltDel("Карта диспансеризации удалена");
		logOut();
		System.out.println("Тест завершен успешно");
		close();
	}
	
}
