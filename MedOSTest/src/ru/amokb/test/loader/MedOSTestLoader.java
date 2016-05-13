package ru.amokb.test.loader;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.support.events.EventFiringWebDriver;

import ru.amokb.test.lib.MedOSTest;
/*import org.openqa.selenium.support.events.WebDriverEventListener;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;*/

public class MedOSTestLoader {
	
	MedOSTestLoader() {}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String fileName="", testFileNameEnd="Test.class", curDT="";
		SimpleDateFormat sdf=new SimpleDateFormat("yyMMdd_hhmmss");
		
		File curDir=new File(fileName);
		String startPath=curDir.getAbsolutePath();
		curDir=new File(startPath);
		startPath=startPath.substring(0, startPath.length()-fileName.length());
		
		File[] files=curDir.listFiles();
		
		TestLoader testLoader=new TestLoader(startPath, ClassLoader.getSystemClassLoader());
		EventFiringWebDriver driver;
		WebDriverLogger wdl=new WebDriverLogger();
		//WebDriverLogger wdl;
		
		//File logFile;
		FileOutputStream fosLog;
		FileOutputStream fosRRLog;
		String s="", logRR="";
		for(File f : files) {
			if(f==null) continue;
			if(f.isDirectory()) continue;
			fileName=f.getName();
			if(fileName.endsWith(testFileNameEnd)) {
				curDT=sdf.format(new Date());
				String fn=fileName.split(".class")[0];
				//wdl=new WebDriverLogger(startPath+"\\"+fn+"_"+curDT+".log");
				try {
					Class<?> cls=testLoader.loadClass(fn);
					MedOSTest tst=(MedOSTest)cls.newInstance();
					
					tst.setUp();
					driver=new EventFiringWebDriver(tst.getDriver());
					driver.register(wdl);
					tst.setDriver(driver);
					
					tst.run();
					
					fosLog=new FileOutputStream(new File(startPath+"\\"+fn+"_"+curDT+".log"));
					fosLog.write(wdl.getLogLine().getBytes());
					fosLog.close();
					
					//s="";
					s=wdl.getRRLine();
					tst.close();
					if(!s.isEmpty()) logRR+="Error of "+fn+": "+s+"\r\n";
				} catch(Exception e) {
					e.printStackTrace();
				} finally {
					
				}
				
				
				try {
					fosRRLog=new FileOutputStream(new File(startPath+"\\RR_"+curDT+".log"));
					fosRRLog.write(logRR.getBytes());
					fosRRLog.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
