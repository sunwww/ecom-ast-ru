package ru.ecom.ejb.print;

import ru.ecom.ejb.services.script.IScriptService;

import java.util.Map;

public interface IPrintService {
	 String print(String aKey, String aServiceName,
			String aMethodName, Map<String,String> aParams) ;
	 String print(String aLogin,boolean aIsTxtFirst, String aKey
			 , String aServiceName
			 , String aMethodName, Map<String,String> aParams) ;
	 String print(String aLogin,boolean aIsTxtFirst, String aKey
				,IScriptService aServiceScr, String aServiceName
				, String aMethodName, Map<String,String> aParams) ;
}
