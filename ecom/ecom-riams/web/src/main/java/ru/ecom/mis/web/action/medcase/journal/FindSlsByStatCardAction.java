package ru.ecom.mis.web.action.medcase.journal;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.nuzmsh.web.struts.BaseAction;

public class FindSlsByStatCardAction  extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
    	//MedcardSearchForm form = (MedcardSearchForm) aForm;
    	//form.validate(aMapping, aRequest) ;
    	//IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class) ;
    	//aRequest.setAttribute("list", service.findSlsByStatCard(form.getNumber()));
    	String onlyYear = aRequest.getParameter("onlyYear") ;
    	String onlyYearH = aRequest.getParameter("onlyYearH") ;
    	if (onlyYear ==null && onlyYearH==null) onlyYearH = (String)aRequest.getSession(true).getAttribute("findSlsByStatCard.onlyYear") ;
        String exactMatch = aRequest.getParameter("exactMatch") ;
        String exactMatchH = aRequest.getParameter("exactMatchH") ;
        if (exactMatch ==null && exactMatchH==null) exactMatchH = (String)aRequest.getSession(true).getAttribute("findSlsByStatCard.exactMatch") ;
        
        /*if (onlyYear==null &&onlyYearH==null 
        		|| (onlyYear.equals("") )
        		||  onlyYearH==null&& (onlyYear.equals("true") 
        				||onlyYear.equals("1") 
        				|| onlyYear.equals("on"))
        */
    	Date cur = new Date() ;
    	SimpleDateFormat FORMAT_2 = new SimpleDateFormat("yyyy") ;
    	aRequest.setAttribute("year",FORMAT_2.format(cur)) ;
    	if ((onlyYear==null && onlyYearH==null) 
    			|| (onlyYearH!=null && onlyYearH.equals("1"))
    			||  ( onlyYear!=null &&onlyYearH==null &&( onlyYear.equals("true") 
    					||onlyYear.equals("1") 
    					|| onlyYear.equals("on"))
    					)) {
    		
    		aRequest.setAttribute("onlyYear",1) ;
    		aRequest.setAttribute("onlyYearS", " and ss.year='"+FORMAT_2.format(cur)+"' ");
    		aRequest.getSession(true).setAttribute("findSlsByStatCard.onlyYear", "1") ;
    	} else {
    		aRequest.setAttribute("onlyYear",0) ;
    		aRequest.setAttribute("onlyYearS", "");
    		aRequest.getSession(true).setAttribute("findSlsByStatCard.onlyYear", "0") ;
    	}
    	if ((exactMatch==null && exactMatchH==null) 
        		|| (exactMatchH!=null && exactMatchH.equals("1"))
        		||  ( exactMatch!=null &&exactMatchH==null &&( exactMatch.equals("true") 
        				||exactMatch.equals("1") 
        				|| exactMatch.equals("on"))
        )) {

        	aRequest.setAttribute("exactMatch",1) ;
        	aRequest.setAttribute("exactMatchS1", " ss.code='");
        	aRequest.setAttribute("exactMatchS2", "' ");
        	aRequest.getSession(true).setAttribute("findSlsByStatCard.exactMatch", "1") ;
        } else {
        	aRequest.setAttribute("exactMatch",0) ;
        	aRequest.setAttribute("exactMatchS1", "  ss.code like '%");
        	aRequest.setAttribute("exactMatchS2", "%'");
        	aRequest.getSession(true).setAttribute("findSlsByStatCard.exactMatch", "0") ;
        }
        return aMapping.findForward("success");
    }
}
