package ru.ecom.mis.ejb.form.medcase.interceptor;

import org.json.JSONObject;
import ru.ecom.diary.ejb.service.template.TemplateProtocolServiceBean;
import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.IParentFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.mis.ejb.domain.medcase.Visit;
import ru.ecom.mis.ejb.form.medcase.poly.DirectionMedCaseForm;

import javax.persistence.EntityManager;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;

public class DirectionSaveInterceptor implements IFormInterceptor {

    public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
    	EntityManager manager = aContext.getEntityManager();
    	DirectionMedCaseForm form = (DirectionMedCaseForm)aForm ;
		List<Object[]> rec = manager.createNativeQuery("select pat.lastname||' '||substring(pat.firstname,1,1)||''||substring(coalesce(pat.middlename,' '),1,1) as f1_name , pr.phonenumber " +
				" from patientlistrecord pr left join patient pat on pat.id=pr.patient where pr.patient="+form.getPatient()+" and pr.phoneNumber is not null").getResultList();
		if (rec.size()>0) {
			Object[] r = rec.get(0);
			try {
				Visit vis = (Visit) aEntity;
				EjbEcomConfig config = EjbEcomConfig.getInstance() ;
				String address =config.get("ru.amokb.patientcabinetaddress", null) ;
				if (address!=null){
					String method = "SendSms";
					SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
					String wfName  =vis.getWorkFunctionPlan()!=null?(vis.getWorkFunctionPlan().getWorkFunction()!=null?vis.getWorkFunctionPlan().getWorkFunction().getName():""):"неопределен";
					String date =  format.format(vis.getDatePlan().getCalendarDate());
					String time = vis.getTimePlan().getTimeFrom().toString();
					String message = "Пациент "+r[0].toString()+" записан к "+wfName+" на "+time+" "+date;
					String phone = r[1].toString();
					JSONObject json = new JSONObject();
					json.put("phonenumber",phone);
					json.put("message",message);
					TemplateProtocolServiceBean bean = new TemplateProtocolServiceBean();
					//bean.makePOSTRequest(json.toString(),address,method,null,null,manager);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
    }
}