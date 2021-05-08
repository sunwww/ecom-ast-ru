package ru.ecom.report.rtf;

import bsh.EvalError;
import ru.ecom.report.replace.IValueInit;
import ru.ecom.report.replace.ReplaceHelper;
import ru.ecom.report.util.ClassLoaderServiceHelper;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Печать RTF
 */
public class RtfPrintServiceHelper {

	public RtfPrintServiceHelper() {
    	theDrivers.add(new OdtPrintFileDriver(".odt")) ;
    	theDrivers.add(new OdtPrintFileDriver(".ods")) ;
    	theDrivers.add(new RtfPrintFileDriver()) ;
    	theDrivers.add(new TxtPrintFileDriver()) ;
        setRemovedTempFile(true) ;
	}

    public RtfPrintServiceHelper(boolean aIsTxtFirst) {
    	
    	if (aIsTxtFirst) theDrivers.add(new TxtPrintFileDriver()) ;
    	theDrivers.add(new OdtPrintFileDriver(".odt")) ;
    	theDrivers.add(new OdtPrintFileDriver(".ods")) ;
    	theDrivers.add(new RtfPrintFileDriver()) ;
    	if (!aIsTxtFirst) theDrivers.add(new TxtPrintFileDriver()) ;
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
    	
    	driver.setLogin(theLogin) ;
    	DefaultRtfValueGetter valueGetter = new DefaultRtfValueGetter();
         try {
             aValueInit.init(aParams, valueGetter);
             driver.prepare() ;
             driver.print(theReplaceHelper, valueGetter) ;
             driver.buildFile(getRemovedTempFile()) ;
			 //Milamesher #120 24092018 вставка qr-кода
			 try {
			 	if (driver instanceof OdtPrintFileDriver) {
					OdtPrintFileDriver d = (OdtPrintFileDriver)driver;
					ru.ecom.report.QRCode.QRCodeServiceBean bean = new ru.ecom.report.QRCode.QRCodeServiceBean();
					bean.createInsertQRCode(d.getQR_text(), d.getQR_w(), d.getQR_h(), "PNG", theWorkDir + "/" + driver.getResultFilename(), d.getExtension(), d.getReplaceString());
				}
			 }
			 catch (Exception e) {
			 	e.printStackTrace();
			 }
		return driver.getResultFilename() ;
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
     */
    public String print(String aKey, String aClass, Map<String,String> aParams) {
		try {
	        IValueInit valueInit = (IValueInit) theClassLoaderService.load(aClass);
	        return print(aKey, valueInit, aParams) ;
		} catch (Exception e) {
			throw new RuntimeException(e) ;
		}
    }
    


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
    		new IllegalStateException("Нет подходящего шаблона для преобразование "+aKey+" в каталоге "+theTemplateDir) ;
    	return driver ;
    }

    private final List<IPrintFileDriver> theDrivers = new LinkedList<>() ;
    private final ClassLoaderServiceHelper theClassLoaderService = new ClassLoaderServiceHelper();
    private final ReplaceHelper theReplaceHelper = new ReplaceHelper();
    /** Каталог для хранения шаблонов */
    private String theTemplateDir ;
    /** Каталог для созданных файлов */
    private String theWorkDir ;
	/** Удаление временных файлов */
	private boolean theRemovedTempFile;
	
	/** Логин */
	public String getLogin() {return theLogin;}
	public void setLogin(String aLogin) {theLogin = aLogin;}

	/** Логин */
	private String theLogin;
}