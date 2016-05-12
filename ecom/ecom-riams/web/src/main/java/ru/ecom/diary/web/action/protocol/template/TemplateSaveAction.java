package ru.ecom.diary.web.action.protocol.template;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.nuzmsh.web.struts.BaseAction;

public class TemplateSaveAction extends BaseAction{
	
	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	//Преобразование текстового массива в числовой. 
	//Элемент массива p12 ->12
	public static long[] getLongs(String[] aStr) {
        try {
            long[] ar = new long[aStr.length];
            for (int i = 0; i < aStr.length; i++) {
                String s = aStr[i];
                s = s.substring(1) ;
                ar[i] = Long.parseLong(s);
            }
            return ar;
        } catch (Exception e) {
        	e.printStackTrace() ;
            return new long[0] ;
        }
	}
	
	
}