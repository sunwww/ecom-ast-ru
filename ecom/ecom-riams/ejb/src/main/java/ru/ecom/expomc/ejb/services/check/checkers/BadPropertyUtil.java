package ru.ecom.expomc.ejb.services.check.checkers;

import java.util.Collection;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class BadPropertyUtil {

	public static Collection<String> create(String ...aArray) {
    	LinkedList<String> str = new LinkedList<String>() ;
    	for (String string : aArray) {
			str.add(string) ;
		}
    	return str ;
	}
	
	public static Collection<String> create(String aProperty) {
    	LinkedList<String> str = new LinkedList<String>() ;
    	str.add(aProperty) ;
    	return str ;
	}

	public static Collection<String> createTokenized(String aProperty, String aDelims) {
    	LinkedList<String> str = new LinkedList<String>() ;
    	StringTokenizer st = new StringTokenizer(aProperty, aDelims) ;
    	while(st.hasMoreTokens()) {
        	str.add(st.nextToken()) ;
    	}
    	return str ;
	}
	
}
