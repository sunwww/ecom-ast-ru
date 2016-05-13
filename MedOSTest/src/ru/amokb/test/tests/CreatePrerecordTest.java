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
		System.out.println("Запуск теста: Создание предварительного направления");
		Date dat=new Date();
		SimpleDateFormat sdfDy=new SimpleDateFormat("dd");
		
String usr="ebitmaeva", s="гастроэнтеролог", d="ВОЛОШИНА ОЛЬГА", dy_=sdfDy.format(dat), t="07:00", pc="Н28728", pl="СИДОРОВ", pf="ИВАН", pm="ВЛАДИМИРОВИЧ" ;
driver.get(baseUrl + "/riams/ecom_login.do");
logIn(usr);
			try {
				forceWait(2);
				driver.findElement(By.linkText("Пред. запись")).click();
				forceWait(2);
				driver.findElement(By.id("rdFunction")).findElement(By.xpath("//input[contains(@value, '"+s+"')]")).click();
				forceWait(2);
				driver.findElement(By.id("rdSpecialist")).findElement(By.xpath("//input[contains(@value, '"+d+"')]")).click();
				forceWait(2);
				driver.findElement(By.cssSelector("#tdDay"+dy_+" > b")).click();
				//S="";
			} catch(Exception e) {
				e.printStackTrace();
				forceWait(2);
			}
			//}while(!S.isEmpty());
			 
			 //время
			forceWait(4);
			try {
				driver.findElement(By.id("rdTime")).findElement(By.xpath("//input[contains(@value, '#"+t+"')]")).click();
					//пациент
				driver.findElement(By.id("lastname")).clear();
				driver.findElement(By.id("lastname")).sendKeys(pl);

				driver.findElement(By.id("firstname")).clear();
				driver.findElement(By.id("firstname")).sendKeys(pf);

				driver.findElement(By.id("middlename")).clear();
				driver.findElement(By.id("middlename")).sendKeys(pm);
				 
				forceWait(10);
				driver.findElement(By.id("submitStep1Go2Button")).click();
				forceWait(4);
				driver.findElement(By.id("submitStep1Go2Button")).click();
				forceWait(4);
				 
				clickByXpath("//input[contains(@value, '#"+pc+"#"+pl+"#"+pf+"')]");		
				 
				forceWait(2);
				driver.findElement(By.id("btnRecord")).click();
				checkErrorMessage();
				System.out.println("Записан");
			} catch (Exception e) {
			 	System.out.println("Указанное свободное время отсутствует.");
			 	System.out.println(e.getMessage());
			 	
			} 
			logOut();
			System.out.println("Тест завершен успешно");
			}
}
