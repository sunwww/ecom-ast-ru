package ru.ecom.ejb.print;

import java.util.Map;

public interface IPrintService {
	 String print(String aKey, String aServiceName,
			String aMethodName, Map<String,String> aParams) ;
	 String print(String aLogin, String aKey
				, String aServiceName
				, String aMethodName, Map<String,String> aParams) ;
}
