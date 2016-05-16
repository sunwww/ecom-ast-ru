package ru.amokb.test.lib;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import javax.imageio.IIOException;

import org.junit.After;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class MedOSTest implements ITest {
	protected WebDriver driver;
	protected String baseUrl;
	Alert alert;
	
	@After
	public void close() {
		driver.close();
	}
	protected String getCurrentDate(String aFormat) {
		Calendar cal = Calendar.getInstance() ;
		SimpleDateFormat format = new SimpleDateFormat(aFormat) ;
		String dateString =format.format(cal.getTime()) ;
		return dateString ;
	}
	
	protected String getCurrentDate() {
		return getCurrentDate("dd.MM.yyyy") ;
	}
	
	protected void clickByXpath(String aNameElement) {
		driver.findElement(By.xpath(aNameElement)).click();
	}
	
	public void setDriver(WebDriver wd) { driver=wd; }
	public WebDriver getDriver(){return driver;}
	
	protected void forceWait(int scnds) { //принудительное ожидание
		driver.manage().timeouts().implicitlyWait(scnds, TimeUnit.SECONDS);
		try{ driver.findElement(By.id("nay3a")); }catch(Exception e) {}
	}
	  
	protected void hitAltDel(String text) {
		//driver.findElement(By.xpath("//*/a[@id='ALT_DEL']")).click();
		clickByXpath("//*/a[@id='ALT_DEL']");
		/*alert=driver.switchTo().alert();
		alert.accept();*/
		alertClick(4);
		forceWait(4);
		System.out.println(text);
	}
	  
	protected boolean inputText(WebElement el, String txt) { return inputText(el, txt, 1); }
	private boolean inputText(WebElement element, String text, int interval) {
		boolean result=true;
		  
		for(int i=0; i<text.length(); i++) {
			element.sendKeys(text.substring(i, i+1));
			forceWait(interval);
		}
		  
		return result;
	}
	
	protected boolean logIn(String n) { return logIn(n, ""); }
	protected boolean logIn(String n, String p) {
		boolean result=true;
		if(p=="") p="1";
		try {
			driver.findElement(By.id("username")).clear();
			driver.findElement(By.id("username")).sendKeys(n);
			driver.findElement(By.id("password")).clear();
			driver.findElement(By.id("password")).sendKeys(p);

			/*if(driver.findElement(By.id("enter"))!=null) */driver.findElement(By.id("enter")).click();
			checkErrorMessage();
			System.out.println("Вход под "+n);
		} catch(Exception e) { 
			result=false;
			System.out.println("Ошибка входа под "+n);
			System.out.println(e.getMessage());
		}
		return result;
	}
	  
	protected void alertClick() { alertClick(8); }
	protected void alertClick(int aTimeOut) {
		try {
			forceWait(aTimeOut);
			System.out.println("alert");
			Alert alrt=driver.switchTo().alert();
			System.out.println(alrt.getText());
			alrt.accept();
			checkErrorMessage();
		} catch (Exception e) {
			 new IIOException("No more alert.");
			 return;
		}
	}
	
	protected boolean findPerson(String ln, String fn, String id, String crd) {
		boolean result=true;
		try {
			//driver.findElement(By.xpath("//*/img[@alt='На главное меню']")).click();
			//clickByXpath("//*/img[@alt='На главное меню']");
			//forceWait(16);
			
			driver.findElement(By.id("lastname")).clear();
			driver.findElement(By.id("lastname")).sendKeys(ln+" "+fn);
			driver.findElement(By.cssSelector("input[type='submit']")).click();
			forceWait(8);
			
			//driver.findElement(By.xpath("//tr[normalize-space(@class)='list "+id+"']/td[text()='"+crd+"']")).click();
			clickByXpath("//tr[normalize-space(@class)='list "+id+"']/td[text()='"+crd+"']");
			//checkErrorMessage();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result=false;
		}
		return result;
	}
	
	protected boolean editPerson(String un, String l, String f, String i, String c) {
		boolean result=true;
		
	    try { 
			if(!logIn(un)) return false;
			
			findPerson(l, f, i, c);
			
		    driver.findElement(By.id("ALT_2")).click();
		    driver.findElement(By.id("submitButton")).click();
		    
		    WebElement we=null;
	    	we=driver.findElement(By.id("PatientDoubleSave")); 
	    	we.click();
		    
		    if(!TFOMSVerify()) return false;
			
			logOut();

		    checkErrorMessage();
		    System.out.println("Сохранён");
	    }
	    catch(Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result=false;
		}
		return result;
	}
	
	protected boolean TFOMSVerify() {
		//ТФОМС
	    driver.findElement(By.linkText("Проверка по ФИО+ДР")).click();
	    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	    //driver.findElement(By.xpath("//input[@id='isPatientPatient']")).click();
	    clickByXpath("//input[@id='isPatientPatient']");
	    //driver.findElement(By.xpath("//input[@id='isPatientAttachment']")).click();
	    clickByXpath("//input[@id='isPatientAttachment']");
	    driver.findElement(By.id("buttonPatientUpdate")).click();
	    forceWait(16);
	    System.out.println("ТФОМС - ОК");
	    
		return true;
	}
	
	protected boolean logOut() {
		boolean result=true;
	    try {
		    //driver.findElement(By.xpath("//a[contains(@href, 'loginExit')]")).click();
		    clickByXpath("//a[contains(@href, 'loginExit')]");
	        System.out.println("Выход");
		} catch(Exception e) { 
			result=false; 
			System.out.println("Ошибка выхода");
			System.out.println(e.getMessage());
		}
	    return result;
	}
	
	protected boolean InsertFromTemplate() {
		//driver.findElement(By.xpath("//*/li[@class='liTemp'][contains(text(), 'ГАСТРОЭНТЕРОЛОГ')]")).click();
		clickByXpath("//*/li[@class='liTemp'][contains(text(), 'ГАСТРОЭНТЕРОЛОГ')]");
		forceWait(4);
		
		//driver.findElement(By.xpath("//*/li[@class='liTemp'][contains(text(), 'ГВ.')]")).click();
		clickByXpath("//*/li[@class='liTemp'][contains(text(), 'ГВ.')]");
		forceWait(4);
		
		driver.findElement(By.id("buttonTempProtOk")).click();
		forceWait(4);
		
		return true;
	}
	
	public void checkErrorMessage () throws Exception{
	    WebElement err = null;
	    try {
		    //err = driver.findElement(By.xpath("//*/div[@class='errorMessage']/a"));
	    	err=driver.findElement(By.xpath(".//*[@id='errorMessageContainer']//*/div"));
		    if(err!=null) {
			    //System.out.println("Error Message: "+err.getText());
			    throw new Exception("Error Message: "+err.getText()) ;
		    }
	    } catch (org.openqa.selenium.NoSuchElementException e) {}
	}
}
