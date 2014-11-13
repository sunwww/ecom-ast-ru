package ru.ecom.ejb.print;

import java.util.Map;

import ru.ecom.ejb.services.script.IScriptService;

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
