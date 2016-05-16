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

/**
 * Заведение амбулаторной карты, талона на прием. Их удаление
 * 
 */
//test, pass
public class MedcardTalonTest extends MedOSTest {
	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://192.168.7.249:8080";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	@Test
	public void run () {
	    //создание медкарты
		String dctrName = "ВОЛОШИНА ОЛЬГА", dateString = new SimpleDateFormat("ddMMyyyy").format(new Date());
		
		driver.get(baseUrl + "/riams/ecom_login.do"); 
		logIn("admin");
		String ln="СИДОРОВ", fn="ИВАН", id="311546", crd="Н28728";
		driver.findElement(By.xpath("//*/img[@alt='На главное меню']")).click();
		forceWait(16);
		
		driver.findElement(By.id("lastname")).clear();
		driver.findElement(By.id("lastname")).sendKeys(ln+" "+fn);
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		forceWait(8);
		
		driver.findElement(By.xpath("//tr[normalize-space(@class)='list "+id+"']/td[text()='"+crd+"']")).click();
	    driver.findElement(By.xpath("//*/a[contains(@href, 'entityParentPrepareCreate-poly_medcard')]")).click();
	    forceWait(8);
	    driver.findElement(By.id("ideModeMainMenuClose")).click();
	    try {
			driver.findElement(By.id("submitButton")).click();
		    /*forceWait(4);
			driver.findElement(By.id("submitButton")).click();*/
		    System.out.println("Медкарта создана");
		} catch (Exception e1) { e1.printStackTrace(); }
	    forceWait(4);
	    
	    driver.findElement(By.xpath("//*/a[@id='CTRL_2']")).click();
	    forceWait(4);
	    
	    driver.findElement(By.xpath("//*/input[@id='dateFinish']")).sendKeys(dateString);
	    
	    String s=dctrName.substring(0, dctrName.indexOf(" "));
	    inputText(driver.findElement(By.xpath("//*/input[@id='workFunctionExecuteName']")), s);
	    driver.findElement(By.xpath("//*/input[@id='serviceStreamName']")).clear();
	    driver.findElement(By.xpath("//*/input[@id='serviceStreamName']")).sendKeys("1");
	    forceWait(4);
	    driver.findElement(By.xpath("//*/input[@id='serviceStreamName']")).sendKeys(Keys.ENTER);
	    forceWait(4);
		driver.findElement(By.xpath("//*/input[@id='submitButton']")).click();
	    forceWait(4);
	    System.out.println("Талон введён");

	    driver.findElement(By.xpath("//*/a[@id='ALT_1']")).click();
	    forceWait(4);
	    driver.findElement(By.xpath("//*/tr[contains(@class, 'tickets')]/td[contains(@class, '4')][contains(text(), '"+dctrName+"')]")).click();
	    forceWait(4);
	    driver.findElement(By.id("workPlaceTypeName")).sendKeys("1");
	    forceWait(4);
	    driver.findElement(By.id("workPlaceTypeName")).click();
	    forceWait(4);
	    driver.findElement(By.id("visitReasonName")).sendKeys("2");
	    forceWait(4);
	    driver.findElement(By.id("visitReasonName")).click();
	    forceWait(4);
	    driver.findElement(By.id("visitResultName")).sendKeys("1");
	    forceWait(4);
	    driver.findElement(By.id("visitResultName")).click();
	    forceWait(4);
	    inputText(driver.findElement(By.id("concludingMkbName")), "K29.6");
	    forceWait(4);
	    driver.findElement(By.id("concludingActuityName")).sendKeys("2");
	    forceWait(4);
	    driver.findElement(By.id("concludingActuityName")).click();
	    forceWait(4);
		driver.findElement(By.xpath("//*/input[@id='submitButton']")).click();
	    forceWait(4);
	    System.out.println("Талон заполнен");

		  //id="ALT_DEL" //удалить
	    hitAltDel("Талон удалён");
		
		  //id="ALT_DEL" //удалить
	    hitAltDel("Медкарта удалена");
		
		logOut();
	}
	
}
