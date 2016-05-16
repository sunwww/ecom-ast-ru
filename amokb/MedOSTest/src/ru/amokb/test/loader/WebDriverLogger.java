package ru.amokb.test.loader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebDriverLogger extends AbstractWebDriverEventListener {
	private static final Logger l=LoggerFactory.getLogger(WebDriverLogger.class);
	private String theLogLine="";
	private String theRRLine="";
	
	@Override
	public void afterNavigateTo(String arg0, WebDriver arg1) { 
		String s="Переход на "+arg0;
		l.info(s);
		writeLogLine(s);
	}

	@Override
	public void beforeChangeValueOf(WebElement arg0, WebDriver arg1) {  
		String s="Старое значение: "+elementDescription(arg0);
		l.info(s);
		writeLogLine(s);
	}

	@Override
	public void afterChangeValueOf(WebElement arg0, WebDriver arg1) {  
		String s="Новое значение: "+elementDescription(arg0);
		l.info(s);
		writeLogLine(s);
	}

	@Override
	public void beforeClickOn(WebElement arg0, WebDriver arg1) {   
		String s="Выполняется клик: "+elementDescription(arg0);
		l.info(s);
		writeLogLine(s);
	}

	@Override
	public void onException(Throwable arg0, WebDriver arg1) {   
		String s="Возникло исключение: ";
		l.error(s, arg0);
		writeLogLine(s+arg0.getMessage());
		writeRRLine(arg0.getMessage());
	}
	
	private String elementDescription(WebElement el) {
		String description="Тэг "+el.getTagName();
		if(el.getAttribute("id")!=null) description+=", id="+el.getAttribute("id");
		if(el.getAttribute("name")!=null) description+=", name="+el.getAttribute("name");
		if(el.getAttribute("value")!=null) description+=", value="+el.getAttribute("value");
		String t=el.getText();
		if(!t.isEmpty()) description+=" ('"+t+"')";
		return description;
	}
	
	private void writeLogLine(String aLogString) {
		if(aLogString==null) return;
		if(!aLogString.endsWith("\r\n")) aLogString+="\r\n";
		theLogLine+=aLogString;
	}

	public String getLogLine() { return theLogLine; }
	
	private void writeRRLine(String rrString) {
		if(rrString==null) return;
		if(!rrString.endsWith("\r\n")) rrString+="\r\n";
		theLogLine+=rrString;
	}

	public String getRRLine() { return theRRLine; }
}
