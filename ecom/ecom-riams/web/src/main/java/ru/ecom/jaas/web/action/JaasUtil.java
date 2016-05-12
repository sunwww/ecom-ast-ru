package ru.ecom.jaas.web.action;

/**
 * Created by IntelliJ IDEA.
 * User: esinev
 * Date: 16.10.2006
 * Time: 0:07:39
 * To change this template use File | Settings | File Templates.
 */
public class JaasUtil {

	public static long[] convertToLongs(String aStr[]) {
		long[] ar = new long[aStr.length] ;
		for (int i = 0; i < aStr.length; i++) {
			String s = aStr[i];
			ar[i] = Long.parseLong(s) ;
		}
		return ar ;
	}
    public static String convertToString(String aStr[]) {
        StringBuilder ret = new StringBuilder() ;
        for (int i = 0; i < aStr.length; i++) {
            String s = aStr[i];
            ret.append(",").append(s) ;
        }
        return ret.length()>0? ret.substring(1):"" ;
    }

}
