package ru.ecom.mis.ejb.form.workcalendar.interceptor;


import ru.ecom.ejb.sequence.service.SequenceHelper;
import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.jaas.ejb.service.SoftConfigServiceBean;
import ru.ecom.mis.ejb.domain.workcalendar.WorkCalendarHospitalBed;
import ru.ecom.mis.ejb.form.workcalendar.WorkCalendarHospitalBedForm;

import javax.persistence.EntityManager;
import java.util.List;

public class WorkCalendarHospitalBedSave implements IFormInterceptor {
    public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
		WorkCalendarHospitalBedForm form = (WorkCalendarHospitalBedForm)aForm ;
		WorkCalendarHospitalBed hospitalBed = (WorkCalendarHospitalBed) aEntity;
		if (form.getInternalCode()==null||form.getInternalCode().equals("")){//Генерация номера направления
			EntityManager manager = aContext.getEntityManager();
			String defaultLpuId = SoftConfigServiceBean.getDefaultParameterByConfig("DEFAULT_LPU",null,manager);
			if (defaultLpuId!=null) {
				List<Object> list = manager.createNativeQuery("select to_char(current_date,'yyyy')||''||lpu.codef from mislpu lpu where lpu.id="+defaultLpuId+" " +
						" and lpu.codef is not null and lpu.codef!=''").getResultList();
				if (list!=null) { //Есть код
					String code = list.get(0).toString();
					SequenceHelper sequenceHelper = SequenceHelper.getInstance();
					String number= sequenceHelper.startUseNextValueNoCheck("OMC#DIRECTIONID#"+code,"",manager);
					while (number.length()<6){
						number="0"+number;
					}
					code +=number;
					form.setInternalCode(code);
					hospitalBed.setInternalCode(code);
					manager.persist(hospitalBed);
				}
			}
		}
	}
}



