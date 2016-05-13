package ru.amokb.test.tests;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.firefox.FirefoxDriver;

import ru.amokb.test.lib.MedOSTest;


/**
 * Прием врача поликлиники
 * @author vtsybulin
 *
 */
public class DoctorsReceptionTest extends MedOSTest {
	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://192.168.7.249:8080";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	@Test
	public void run () throws Exception {
		System.out.println("Запуск теста: Прием врача поликлиники");
	String wfCls="379";
	driver.get(baseUrl + "/riams/ecom_login.do"); 
	logIn("ovoloshina");
		//смена р/ф
		driver.findElement(By.linkText("Смена раб.функции")).click();
		driver.findElement(By.xpath("//table/*/tr[normalize-space(@class)='workFunc "+wfCls+"']/td[normalize-space(@class)='2']")).click();
		
		//рабочий календарь
		String s="";
		do {
			try {
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				s="Рабочий календарь";
		      	driver.findElement(By.linkText(s)).click();
		        System.out.println("Календарь");
	      	
	            s="";
		    } catch(Exception e) {
		    	s=e.getMessage();
		    	e.printStackTrace();
		    	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		    }
		} while(!s.equals("")); 
		checkErrorMessage();
		System.out.println("Дата");
		logOut();
		System.out.println("Тест завершен успешно");
		
		
	}
	
}
