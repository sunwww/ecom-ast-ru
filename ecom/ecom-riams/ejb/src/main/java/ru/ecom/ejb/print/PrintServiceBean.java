package ru.ecom.ejb.print;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import ru.ecom.ejb.services.file.IJbossGetFileLocalService;
import ru.ecom.ejb.services.script.IScriptService;
import ru.ecom.ejb.services.util.ConvertSql;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.report.replace.IValueGetter;
import ru.ecom.report.replace.IValueInit;
import ru.ecom.report.replace.SetValueException;
import ru.ecom.report.rtf.RtfPrintServiceHelper;

@Stateless
@Local(IPrintService.class)
@Remote(IPrintService.class)
public class PrintServiceBean implements IPrintService {

	public String print(String aKey
			, String aServiceName
			, String aMethodName, Map<String,String> aParams) {
		return print("user",false, aKey, aServiceName, aMethodName, aParams);		
	}
	public String print(String aLogin,boolean aIsTxtFirst, String aKey
			, String aServiceName
			, String aMethodName, Map<String,String> aParams) {
		// получение данных
		EjbInjection theInjection = EjbInjection.getInstance();
		IScriptService serv = theInjection.getLocalService(IScriptService.class) ;
		return print(aLogin, aIsTxtFirst, aKey,serv ,aServiceName,aMethodName, aParams);
	}
	public String print(String aLogin,boolean aIsTxtFirst, String aKey
			,IScriptService aServiceScr, String aServiceName
			, String aMethodName, Map<String,String> aParams) {
	
		try {
			Map<String,Object> values = (Map<String, Object>)aServiceScr.invoke(aServiceName, aMethodName,new Object[] {aParams});
			// печать
            EjbEcomConfig config = EjbEcomConfig.getInstance() ;
            //Long maxLengthLine = ConvertSql.parseLong(config.get("text.line.length.max", "77")) ;
            RtfPrintServiceHelper service = new RtfPrintServiceHelper(aIsTxtFirst);
            String workDir =config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
            boolean removedTemp =config.get("tomcat.data.dir.removedtemp", "1").equals("1")?true:false;
            
            service.setWorkDir(config.get("tomcat.data.dir",workDir!=null ? workDir : "/opt/tomcat/webapps/rtf"));
            service.setTemplateDir(System.getProperty("jboss.server.data.dir"));
            //service.set
            //System.out.println("removedTemp = "+removedTemp) ;
            service.setRemovedTempFile(removedTemp);
            service.setLogin(aLogin) ;
            return service.print(aKey, new ValueInit(values), new HashMap<String, String>()) ;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	public static class ValueInit implements IValueInit {

		public ValueInit(Map<String, Object> values) {
			theValues = values ;
		}
		public void init(Map<String, String> aParams, IValueGetter aGetter) throws SetValueException {
			for (Map.Entry<String, Object> entry : theValues.entrySet()) {
				aGetter.set(entry.getKey(), entry.getValue());
			}
		}
		private final Map<String, Object> theValues ;
		
	}

	
	private @EJB
	IJbossGetFileLocalService theJbossGetFileLocalService;

	//private EjbInjection theInjection = EjbInjection.getInstance();
}
