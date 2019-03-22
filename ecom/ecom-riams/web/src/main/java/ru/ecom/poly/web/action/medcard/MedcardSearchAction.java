package ru.ecom.poly.web.action.medcard;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.mis.web.dwr.medcase.HospitalMedCaseServiceJs;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Поиск мед. карт. 
 */
public class MedcardSearchAction extends BaseAction {

     public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception
     {
         MedcardSearchForm form = (MedcardSearchForm) aForm;
         String exactMatch = aRequest.getParameter("exactMatch") ;
         String exactMatchH = aRequest.getParameter("exactMatchH") ;
         if (exactMatch ==null && exactMatchH==null) exactMatchH = (String)aRequest.getSession(true).getAttribute("findSlsByStatCard.exactMatch") ;

         
       //  ISoftConfigService service = Injection.find(aRequest).getService(ISoftConfigService.class);
     	if ((exactMatch==null && exactMatchH==null)
        		|| (exactMatchH!=null && exactMatchH.equals("1"))
        		||  ( exactMatch!=null &&exactMatchH==null &&( exactMatch.equals("true") 
        				||exactMatch.equals("1") 
        				|| exactMatch.equals("on"))
        )) {

        	aRequest.setAttribute("exactMatch",1) ;
        	aRequest.getSession(true).setAttribute("findMedcard.exactMatch", "1") ;
        	aRequest.getSession(true).setAttribute("findMedcardnumberSql", "mc.number = '"+form.getNumber()+"'") ;
        } else {
        	aRequest.setAttribute("exactMatch",0) ;
        	aRequest.getSession(true).setAttribute("findMedcard.exactMatch", "0") ;
        	aRequest.getSession(true).setAttribute("findMedcardnumberSql", "mc.number like '%"+form.getNumber()+"%'") ;
        }
     	
         if (form.getNumber()!=null && !form.getNumber().equals("")) {
        	  
        	 aRequest.setAttribute("fndNumber", form.getNumber());
        	 aRequest.setAttribute("default_lpu", HospitalMedCaseServiceJs.getDefaultParameterByConfig("DEFAULT_LPU_OMCCODE", "", aRequest)) ;
         } else {
        	 aRequest.setAttribute("fndNumber", null) ;
         }
         
         return aMapping.findForward(SUCCESS);
    }
}
