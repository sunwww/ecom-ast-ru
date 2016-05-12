package ru.ecom.mis.web.action.patient;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import ru.ecom.mis.ejb.service.medcase.IPolyclinicMedCaseService;
import ru.ecom.mis.ejb.service.patient.IPatientService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.messages.ErrorMessage;
import ru.nuzmsh.web.struts.BaseAction;

public class FindPersonBySocialCardAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        String lname = getAttributeSocialCard(aRequest,"l") ;
        String mname = getAttributeSocialCard(aRequest,"m") ;
        String fname = getAttributeSocialCard(aRequest,"f") ;
        String spec_lastname = getAttributeSocialCard(aRequest,"sl") ;
        String spec_middlename = getAttributeSocialCard(aRequest,"sm") ;
        String spec_firstname = getAttributeSocialCard(aRequest,"sf") ;
        IPolyclinicMedCaseService servicePol = Injection.find(aRequest).getService(IPolyclinicMedCaseService.class) ;
        String fio = new StringBuilder().append(lname).append(" ").append(fname).append(" ").append(mname).toString() ;
        String specfio = servicePol.getFioBySpec() ;
        String specfioByCard = new StringBuilder().append(spec_lastname).append(" ").append(spec_firstname).append(" ").append(spec_middlename).toString() ;
        if (specfio==null || specfio.equals("") || specfioByCard==null || !specfio.equals(specfioByCard)) {
        	if (spec_lastname==null) {
        		new ErrorMessage(aRequest, "Вставьте карточку специалиста") ;
        	} if (specfio.equals("")) {
        		new ErrorMessage(aRequest, "Нет соответствия рабочей функции и пользователя") ;
        		
        	} else {
        		new ErrorMessage(aRequest, "Не совпадают ФИО специалиста зарегистрированного в системе с ФИО специалиста по карточке") ;
        	}
        } else {
            IPatientService service = Injection.find(aRequest).getService(IPatientService.class);
//          IEntityFormService entityService = EntityInjection.find(aRequest).getEntityFormService();
          aRequest.setAttribute("list"
                  , service.findPatient(null, null,fio));
        }
        //form.validate(aMapping, aRequest) ;
        return aMapping.findForward("success");
    }
    private String getAttributeSocialCard(HttpServletRequest aRequest, String aKey) {
    	String ret = aRequest.getParameter(aKey) ;
    	
    	if (ret==null || ret.equals("")) {
    		ret = (String)aRequest.getSession().getAttribute("SocialCard_"+aKey) ; 
    	} else {
    			ret =  getLat(ret);
    	}
    	aRequest.getSession().setAttribute("SocialCard_"+aKey, ret) ;
    	return ret ;
    }
    private String getLat(String aCode) {
    	StringBuilder decode = new StringBuilder() ;
    	for (int i=0;  i<aCode.length();i++) {
    		String ret =theMap.get(String.valueOf(aCode.charAt(i))) ;
    		decode.append(ret) ;
    	}
    	
    	return decode.toString() ;
    }
    
    public Map<String,String> enrusCreate() {
    	Map <String,String> map = new HashMap<String, String>();
    	map.put( "Q" ,"Й") ;
    	map.put("W","Ц" ) ;
    	map.put("E","У"  ) ;
    	map.put(  "R","К" ) ;
    	map.put("T","Е"  ) ;
    	map.put( "A","Ф" ) ;
    	map.put(  "S","Ы") ;
    	map.put("D","В"  ) ;
    	map.put("F","А" ) ;
    	map.put("G","П"  ) ;
    	map.put("Z","Я"  ) ;
    	map.put("X","Ч"  ) ;
    	map.put("C","С"  ) ;
    	map.put(  "V","М" ) ;
    	map.put("B","И" ) ;
    	map.put("Y","Н"  ) ;
    	map.put( "U","Г"  ) ;
    	map.put( "I","Ш"  ) ;
    	map.put( "O","Щ"  ) ;
    	map.put("P","З" ) ;
    	map.put( "H","Р" ) ;
    	map.put("J","О"  ) ;
    	map.put("K","Л"  ) ;
    	map.put( "L","Д" ) ;
    	map.put("N","Т" ) ;
    	map.put( "M","Ь" ) ;
    	map.put( "~","Ё" ) ;
    	map.put( "[","Х" ) ;
    	map.put( "]","Ъ" ) ;
    	map.put( ";","Ж" ) ;
    	map.put( "'","Э" ) ;
    	map.put( "<","Б" ) ;
    	map.put( ">","Ю" ) ;
    	return map ;
    }
    Map<String, String> theMap = enrusCreate();
}