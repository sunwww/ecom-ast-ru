package ru.ecom.mis.ejb.uc.privilege.service.cellfunctions;

import java.util.StringTokenizer;

import ru.ecom.mis.ejb.uc.privilege.service.ICellFunction;

public class SplitFunction implements ICellFunction {
	
	public SplitFunction(String aDelimeters, int aPosition) {
		delimeters = aDelimeters ;
		position = aPosition ;
	}

	public String invoke(String aValue) {
		StringTokenizer st = new StringTokenizer(aValue, delimeters) ;
		String ret = null ;
		for(int i=0; i<position && st.hasMoreTokens(); i++) {
			ret = st.nextToken() ;
		}
		return ret ;
	}
	
	private final String delimeters ;
	private final int position ;
}
