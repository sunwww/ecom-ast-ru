package ru.amokb.test.tests;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import ru.amokb.test.lib.MedOSTest;


/**
 * Создание предварительного направления
 *
 */
//test, pass
public class CreatePrerecordTest extends MedOSTest {

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://192.168.7.249:8080";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	@Test
	public void run() throws Exception {
		System.out.println("Запуск теста: Создание дополнительного времени");
		String usr="ebitmaeva" ,s="гастроэнтеролог" ,d="ВОЛОШИНА ОЛЬГА" //, dct="ВОЛОШИНА ОЛЬГА"
				, currentDate=getCurrentDate("ddMMyyyy"), t="07:00";
		
		driver.get(baseUrl + "/riams/ecom_login.do");
		logIn(usr);
		String RR="Тест досрочно остановлен.";
		try {
			
			
			findElement(By.linkText("Создание дополнительного времени")).click();

			String specName="379";
			WebElement specialistName = findElement(By.id("specialistName"));  
			inputText(specialistName, specName);
			
			
			specialistName.sendKeys(Keys.ENTER);
			findElement(By.id("date")).sendKeys(currentDate);
			findElement(By.id("times")).sendKeys(t);
			 
			forceWait(2);
			findElement(By.id("btnGenerate")).click();
			alertClickCheckText("СОЗДАНЫ");
		   checkErrorMessage(1);
			System.out.println("Доп. время создано");
		} catch(Exception e) {
			System.out.println("Доп. время не создано.");
			System.out.println(RR);
			e.printStackTrace();
			System.out.println(e.getMessage());
			return;
		}
		
	//	alertClick() ;
		checkErrorMessage();
		System.out.println("Создание дополнительного времени завершено");
		
		System.out.println("Запуск теста: Создание предварительного направления");
		Date dat=new Date();
		SimpleDateFormat sdfDy=new SimpleDateFormat("dd");
		
String dy_=sdfDy.format(dat),  pc="Н28728", pl="СИДОРОВ", pf="ИВАН", pm="ВЛАДИМИРОВИЧ" ;

			try {
				
				findElement(By.linkText("Пред. запись")).click();
				findElement(By.id("rdFunction")).findElement(By.xpath("//input[contains(@value, '"+s+"')]")).click();
				findElement(By.id("rdSpecialist")).findElement(By.xpath("//input[contains(@value, '"+d+"')]")).click();
				findElement(By.cssSelector("#tdDay"+dy_+" > b")).click();
				//S="";
			} catch(Exception e) {
				e.printStackTrace();
				forceWait(2);
			}			 
			 //время
			forceWait(4);
			try {
				findElement(By.id("rdTime")).findElement(By.xpath("//input[contains(@value, '#"+t+"')]")).click();
					//пациент
				WebElement lastname =findElement(By.id("lastname")); 
				WebElement firstname =findElement(By.id("firstname")); 
				WebElement middlnename =findElement(By.id("middlename")); 
				lastname.clear();
				lastname.sendKeys(pl);

				firstname.clear();
				firstname.sendKeys(pf);

				middlnename.clear();
				middlnename.sendKeys(pm);
				findElement(By.id("submitStep1Go2Button"),2).click();
				//findElement(By.id("submitStep1Go2Button"),2).click();
				 
				clickByXpath("//input[contains(@value, '#"+pc+"#"+pl+"#"+pf+"')]");		
				 
				findElement(By.id("btnRecord")).click();
				checkErrorMessage(1);
				System.out.println("Записан");
			} catch (Exception e) {
			 	System.out.println("Указанное свободное время отсутствует.");
			 	System.out.println(e.getMessage());
			 	
			} 
			logOut();
			System.out.println("Тест: \"Создание предварительного направления\" завершен успешно");
			System.out.println("Тесты: \"Добавление времени и создание предварительного направления\" завершены успешно");
			
			}
}
