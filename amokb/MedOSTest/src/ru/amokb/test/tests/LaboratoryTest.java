package ru.amokb.test.tests;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

import ru.amokb.test.lib.MedOSTest;

public class LaboratoryTest extends MedOSTest {

	@Test
	public void run() {
		String dateString = getCurrentDate() ;
		
		intake("opotapova",dateString) ;
		transfer("ltsyganova",dateString) ;
		checkAnalisis("aponomarev",dateString) ;
		deleteCheckAnalisis("pivanov",dateString) ;
	}
		
	private void intake(String aUsername, String aDate)	 {
		System.out.println("Забор биоматериала") ; 
		
		logIn(aUsername);
		clickByXpath("//*/a[@id='mainMenuLaboratoryJournal']") ;
		clickByXpath("//*/a[@id='mainMenuLaboratoryJournal']");
		forceWait(4);
		clickByXpath("//*/a[@id='pres_intake']");
		forceWait(4);
		driver.findElement(By.xpath("//*/input[@name='typeGroup'][@value='2']")).click();
		forceWait(4);
		driver.findElement(By.xpath("//*/input[@name='typeIntake'][@value='2']")).click();
		forceWait(4);
		driver.findElement(By.xpath("//*/input[@name='typeTransfer'][@value='3']")).click();
		forceWait(4);
		driver.findElement(By.xpath("//*[@id='beginDate']")).clear();
		driver.findElement(By.xpath("//*[@id='beginDate']")).sendKeys(aDate);
		//forceWait(4);
		driver.findElement(By.xpath("//*/input[@type='submit'][@value='Отобразить данные']")).click();
		forceWait(4);
		driver.findElement(By.xpath("//*/table//tr[2]//input[@type='button'][@value='Прием']")).click();
		forceWait(4);
		driver.findElement(By.id("BiomatbutOK")).click();
		forceWait(4);
		driver.findElement(By.xpath("//*/a[contains(@href, 'ecom_loginExit')]")).click();
		forceWait(4);
		logOut();
	}
	private void transfer(String aUsername, String aDate) {
	    System.out.println("Биоматериал забран");

	    
	    
		logIn(aUsername);
	    driver.findElement(By.xpath("//*/a[@id='mainMenuLaboratoryJournal']")).click();
	    forceWait(4);
	    driver.findElement(By.id("pres_transfer")).click();
	    forceWait(4);
	    driver.findElement(By.xpath("//*/input[@name='typeIntake'][@value='1']")).click();
	    forceWait(4);
	    driver.findElement(By.xpath("//*/input[@name='typeTransfer'][@value='2']")).click();
	    forceWait(4);
	    inputText(driver.findElement(By.id("departmentName")), "НЕЙРОХИР");
	    forceWait(4);
	    driver.findElement(By.id("beginDate")).clear();
	    forceWait(4);
	    driver.findElement(By.id("beginDate")).clear();
	    forceWait(4);
	    driver.findElement(By.id("endDate")).click();
	    forceWait(4);
	    //driver.findElement(By.id("beginDate")).sendKeys(s);
	    inputText(driver.findElement(By.id("beginDate")), aDate);
	    //forceWait(4);
	    driver.findElement(By.xpath("//*/input[@type='submit']")).click();
	    forceWait(4);
	    driver.findElement(By.xpath("//*/table//tr[3]//input[@type='checkbox']")).click();
	    forceWait(4);
	    driver.findElement(By.xpath("//*/a[contains(text(), 'Переданы ')]")).click();
	    forceWait(4);
	    driver.findElement(By.xpath("//*//div[@id='BioIntakeRootPane']//tr[2]//input[1]")).click();
	    forceWait(4);
	    driver.findElement(By.xpath("//*/input[@value='Принять']")).click();
	    forceWait(4);
		logOut();
	    System.out.println("Биоматериал принят");
	}
	private void checkAnalisis(String aUsername,String aDate) {
	    
	    
		
		logIn(aUsername);
	    driver.findElement(By.xpath("//*/a[@id='mainMenuLaboratoryJournal']")).click();
	    forceWait(4);
	    driver.findElement(By.id("pres_cabinet")).click();
	    forceWait(4);
	    driver.findElement(By.xpath("//*/input[@name='typeCabinet'][@value='2']")).click();
	    forceWait(4);
	    driver.findElement(By.xpath("//*/input[@name='typeResult'][@value='1']")).click();
	    forceWait(4);
	    driver.findElement(By.id("beginDate")).clear();
	    forceWait(4);
	    driver.findElement(By.id("beginDate")).clear();
	    driver.findElement(By.id("beginDate")).sendKeys(aDate);
	    forceWait(4);
	    driver.findElement(By.xpath("//*/input[@type='submit']")).click();
	    forceWait(4);
	    driver.findElement(By.xpath("//*/table//tr[2]//input[@value='Ан.+Рез.']")).click();
	    forceWait(4);
	    driver.findElement(By.id("paramOK")).click();
		logOut();
	    System.out.println("Результаты подтверждены");

	}
	private void deleteCheckAnalisis(String aUsername,String aDate) {
	    
	    
	    
		
		logIn(aUsername);
	    forceWait(60);
	    driver.findElement(By.xpath("//*/a[@id='mainMenuLaboratoryJournal']")).click();
	    forceWait(4);
	    driver.findElement(By.id("pres_journal")).click();
	    forceWait(4);
	    driver.findElement(By.xpath("//*/input[@name='typeIntake'][@value='1']")).click();
	    forceWait(4);
	    driver.findElement(By.xpath("//*/input[@name='typeTransfer'][@value='1']")).click();
	    forceWait(4);
	    driver.findElement(By.xpath("//*/input[@name='typeCancel'][@value='2']")).click();
	    forceWait(4);
	    inputText(driver.findElement(By.id("departmentName")), "нейрохи");
	    forceWait(4);
	    driver.findElement(By.id("beginDate")).clear();
	    forceWait(4);
	    driver.findElement(By.id("beginDate")).clear();
	    driver.findElement(By.id("beginDate")).sendKeys(aDate);
	    forceWait(4);
	    driver.findElement(By.xpath("//*/input[@type='submit']")).click();
	    forceWait(4);
	    driver.findElement(By.xpath("//*/table//tr[2]//td[1]/input")).click();
	    forceWait(4);
	    logOut();
	    System.out.println("Результаты аннулированы");
		return ;
	}

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://192.168.7.249:8080";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
}
