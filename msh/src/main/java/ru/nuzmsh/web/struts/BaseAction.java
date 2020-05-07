package ru.nuzmsh.web.struts;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.nuzmsh.web.messages.ErrorMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action
 */
public abstract class BaseAction extends Action {

	public static final String SUCCESS = "success";
	
    public ActionForward execute(ActionMapping aMapping
            , ActionForm aForm
            , HttpServletRequest aRequest
            , HttpServletResponse aResponse) throws Exception {
        try {
            //aResponse.setHeader();
            ActionForward ret = myExecute(aMapping, aForm, aRequest, aResponse) ;
            if(ret==null) {
                System.out.println("ERROR ret = null");
   //             throw new Exception("Ошибка: ActionForward не должен быть равен NULL") ;
            }
            return ret ;
        } catch (Exception e) {
            if(aMapping.getInput()!=null) {
                setError(aRequest, e);
                return aMapping.getInputForward();
            } else {
                setError(aRequest, e);
                throw e ;
            }
        }
    }

    public abstract ActionForward myExecute(ActionMapping aMapping
            , ActionForm aForm
            , HttpServletRequest aRequest
            , HttpServletResponse aResponse) throws Exception  ;

    protected void setError(HttpServletRequest aRequest, Exception aException) {
        new ErrorMessage(aRequest, aException) ;
    }

    
    protected static long[] getLongArray(HttpServletRequest aRequest, String aParamName, String aParameterTitle) {
    	String[] values = getStringArray(aRequest, aParamName, aParameterTitle) ;
    	long[] ret = new long[values.length] ;
    	for(int i=0; i<values.length; i++) {
    		ret[i] = Long.parseLong(values[i]);
    	}
    	return ret;
    }
    
    protected static String[] getStringArray(HttpServletRequest aRequest, String aParamName, String aParameterTitle) {
        String[] values = aRequest.getParameterValues(aParamName);
        if (values == null || values.length == 0) {
            throw new IllegalArgumentException("Нет параметра " + aParamName + " " + aParameterTitle);
        }
        return values;
    }

    protected static long getLongId(HttpServletRequest aRequest, String aParameTitle) {
        try {
            return Long.parseLong(aRequest.getParameter("id"));
        } catch (Exception e) {
            throw new IllegalArgumentException("Нет параметра id: " + aParameTitle, e);
        }
    }
    
    
}
