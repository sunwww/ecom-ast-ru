package ru.ecom.report.rtf;

import ru.ecom.report.replace.ReplaceHelper;
import ru.ecom.report.replace.IValueGetter;
import ru.ecom.report.replace.IValueInit;
import ru.ecom.report.replace.SetValueException;
import ru.ecom.report.util.ClassLoaderServiceHelper;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import bsh.EvalError;

/**
 * Печать RTF
 */
public class RtfPrintServiceHelper {

	private static void log(String aStr) {
		System.out.println(aStr) ;
	}
    public RtfPrintServiceHelper() {
    	theDrivers.add(new OdtPrintFileDriver(".odt")) ;
    	theDrivers.add(new OdtPrintFileDriver(".ods")) ;
    	theDrivers.add(new RtfPrintFileDriver()) ;
    	theDrivers.add(new TxtPrintFileDriver()) ;
        //theReplaceHelper.setRtfMode(true);
        setRemovedTempFile(true) ;
    }

    /** Каталог для хранения шаблонов */
    public String getTemplateDir() { return theTemplateDir ; }
    public void setTemplateDir(String aTemplateDir) { theTemplateDir = aTemplateDir ; }

    /** Каталог для созданных файлов */
    public String getWorkDir() { return theWorkDir ; }
    public void setWorkDir(String aWorkDir) { theWorkDir = aWorkDir ; }
    
    /** Удаление временных файлов */
	public boolean getRemovedTempFile() {return theRemovedTempFile;}
	public void setRemovedTempFile(boolean aRemovedTempFile) {theRemovedTempFile = aRemovedTempFile;}

    
	public String print(String aKey, IValueInit aValueInit, Map<String, String> aParams) throws RtfPrintException {
    	// определение подходящего драйвера
    	String id = System.currentTimeMillis()+"" ;
    	File templateDir = new File(theTemplateDir) ;
    	IPrintFileDriver driver = findDriver(aKey)
    	   .newInstance(id, templateDir, aKey, theWorkDir) ;
    	theReplaceHelper.setRtfMode(driver.isRtfReplaceMode());
    	
    	
    	DefaultRtfValueGetter valueGetter = new DefaultRtfValueGetter();
         try {
             //IValueInit valueInit = (IValueInit) theClassLoaderService.load(aClass);
             aValueInit.init(aParams, valueGetter);
             //log("Prepare") ;
             driver.prepare() ;
             
             //File templateFile = driver.getInputFile() ; //new File(theTemplateDir+"/"+aKey+".rtf") ;
             //log("InputFile = "+templateFile.getAbsolutePath()) ;
             
            // File outFile = driver.getOutputFile() ; //putFilename() ; //aKey+"-"+System.currentTimeMillis()+".rtf" ;
             //log("outFile = "+outFile.getAbsolutePath()) ;
             //File outFile = new File(theWorkDir+"/"+filename) ;
             
             //driver.print(templateFile, outFile, valueGetter, driver.getEncoding()) ;
             driver.print(theReplaceHelper, valueGetter) ;
             driver.buildFile() ;
             //log("BuildFile") ;
             
             //log("result file = "+driver.getResultFilename()) ;
             return driver.getResultFilename() ; //outFile.getName();
         } catch (Exception e) {
             throw new RtfPrintException("Ошибка печати: "+e.getMessage(),e) ;
         } finally {
             try {
                 valueGetter.clear();
             } catch (EvalError evalError) {
                 evalError.printStackTrace() ;
             }
         }    	
    	
    }    
    /**
     * Добавление значений в текстовый файл
     * @param aKey     ключ
     * @param aClass   IValueInit драйвер
     * @param aParams  значения параметров
     * @return путь к файлы
     * @throws RtfPrintException
     */
    public String print(String aKey, String aClass, Map<String,String> aParams) throws RtfPrintException {
		try {
	        IValueInit valueInit = (IValueInit) theClassLoaderService.load(aClass);
	        return print(aKey, valueInit, aParams) ;
		} catch (Exception e) {
			throw new RuntimeException(e) ;
		}
    }
    /*
    private String _print(String aKey, String aClass, Map<String,String> aParams) throws RtfPrintException {
        DefaultRtfValueGetter valueGetter = new DefaultRtfValueGetter();
        try {
            IValueInit valueInit = (IValueInit) theClassLoaderService.load(aClass);
            valueInit.init(aParams, valueGetter);
            File templateFile = new File(theTemplateDir+"/"+aKey+".rtf") ;
            String filename = aKey+"-"+System.currentTimeMillis()+".rtf" ;
            File outFile = new File(theWorkDir+"/"+filename) ;
            print(templateFile, outFile, valueGetter, "Cp1251") ;
            return outFile.getName();
        } catch (Exception e) {
            throw new RtfPrintException("Ошибка печати: "+e.getMessage(),e) ;
        } finally {
            try {
                valueGetter.clear();
            } catch (EvalError evalError) {
                evalError.printStackTrace() ;
            }
        }
    }

    private String getUniqueFilename(String aKey) {
        String filename = aKey+"-"+System.currentTimeMillis()+".rtf" ;
        return filename ;
    }*/
    
//    public String print(String aKey, IValueInit aValueInit, Map<String, String> aParams) throws RtfPrintException {
//        DefaultRtfValueGetter valueGetter = new DefaultRtfValueGetter();
//
//        try {
//            aValueInit.init(aParams, valueGetter);
//            File templateFile = new File(theTemplateDir+"/"+aKey+".rtf") ;
//            File outFile = new File(theWorkDir+"/"+getUniqueFilename(aKey)) ;
//            print(templateFile,outFile, valueGetter);
//            return outFile.getName() ;
//        } catch (SetValueException e) {
//            throw new RtfPrintException("Ошибка установки значений: "+e.getMessage(),e);
//        } finally {
//            try {
//                valueGetter.clear();
//            } catch (EvalError evalError) {
//                evalError.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//            }
//        }
//    }


    private IPrintFileDriver findDriver(String aKey) {
    	IPrintFileDriver driver = null ;
    	File templateDir = new File(theTemplateDir) ;
    	for(IPrintFileDriver d : theDrivers) {
    		if(d.isAccept(templateDir, aKey)) {
    			driver = d ;
    			break;
    		}
    	}
    	if(driver==null) throw 
    		new IllegalStateException("Нет подходящего драйвера для преобразование "+aKey+" в каталоге "+theTemplateDir) ;
    	return driver ;
    }

    private final List<IPrintFileDriver> theDrivers = new LinkedList<IPrintFileDriver>() ; 
    private final ClassLoaderServiceHelper theClassLoaderService = new ClassLoaderServiceHelper();
    private final ReplaceHelper theReplaceHelper = new ReplaceHelper();
    /** Каталог для хранения шаблонов */
    private String theTemplateDir ;
    /** Каталог для созданных файлов */
    private String theWorkDir ;
	/** Удаление временных файлов */
	private boolean theRemovedTempFile;
}
