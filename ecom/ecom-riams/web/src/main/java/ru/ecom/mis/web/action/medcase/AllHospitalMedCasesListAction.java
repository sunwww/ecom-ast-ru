package ru.ecom.mis.web.action.medcase;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.mis.ejb.form.medcase.hospital.ExtHospitalMedCaseForm;
import ru.ecom.mis.ejb.form.medcase.hospital.HospitalMedCaseForm;
import ru.ecom.mis.ejb.service.medcase.IHospitalMedCaseService;
import ru.ecom.web.actions.parententity.ListAction;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.web.tags.decorator.ITableDecorator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;


public class AllHospitalMedCasesListAction extends ListAction {
	@Override
	public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, 
			HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        
        //aRequest.setAttribute("list", service.listAll(form.getClass(), id));

        String ssl_id = aRequest.getParameter("sslid") ;
        String idString = aRequest.getParameter("id") ;
        Long id ;
        long sslId = -1 ;
        System.out.println("sslid="+ssl_id) ;
        IHospitalMedCaseService service = Injection.find(aRequest).getService(IHospitalMedCaseService.class);
        if (!StringUtil.isNullOrEmpty(ssl_id)){
        	//sslId=Long.valueOf(ssl_id);
        	sslId=Long.parseLong(ssl_id);
        	
        	id = service.getPatient(sslId) ;
        	System.out.println("patientid = "+idString) ;
        } else {
            if(StringUtil.isNullOrEmpty(idString)) {
            	throw new IllegalArgumentException("Нет параметра patientid") ;
            }
            id = Long.parseLong(idString);
        }
        aRequest.setAttribute("id", id);
        //--IParentEntityFormService service = EntityInjection.find(aRequest).getParentEntityFormService();
        //--IEntityForm form = castEntityForm(aForm, aMapping) ;

        //Long id = Long.parseLong(idString) ;
        

		//boolean isMap = isMap(form);
		/*Collection list = isMap 
				? service.listAll(getFormClassname(aMapping), id) 
				: service.listAll(form.getClass(), id);
				*/
        //--Collection list = service.listAll(form.getClass(), id) ;
        Collection list = service.listAll(id) ;
        /*
		aRequest.setAttribute("list"
				, isMap ? ru.ecom.web.actions.entity.ListAction.transormCollection(list, form.getClass()) : list);
        */
        aRequest.setAttribute("list", list) ;
        aRequest.setAttribute("decorator", getDecorator(sslId));
        return aMapping.findForward(SUCCESS) ;
    }

    private ITableDecorator getDecorator(final long aSlsId) {
        return new ITableDecorator() {

        	public String getId(Object aRow) {
        		HospitalMedCaseForm form = (HospitalMedCaseForm) aRow ;
        		return String.valueOf(form.getId());
        	}

        	public String getRowCssClass(Object aRow) {
        		StringBuilder style = new StringBuilder();
        		HospitalMedCaseForm form = (HospitalMedCaseForm) aRow ;
        		if (form instanceof ExtHospitalMedCaseForm) {
        			style.append("otherLpu") ;
        		}
        		if (form.getIsDeniedHospitalizating()) style.append("deniedHospitalizating") ;
        		if (form.getId()==aSlsId) style.append( " current") ; 
        		return style.toString() ;
        	}
        };
    }

}
