package ru.ecom.mis.ejb.form.medcase.transfusion.interceptor;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.mis.ejb.domain.medcase.BloodTransfusion;
import ru.ecom.mis.ejb.domain.medcase.TransfusionMonitoring;
import ru.ecom.mis.ejb.domain.medcase.TransfusionReagent;
import ru.ecom.mis.ejb.form.medcase.transfusion.BloodTransfusionForm;
import ru.ecom.mis.ejb.form.medcase.transfusion.TransfusionMonitoringForm;
import ru.ecom.mis.ejb.form.medcase.transfusion.TransfusionReagentForm;
import ru.nuzmsh.util.PropertyUtil;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import java.lang.reflect.Method;
import java.util.List;

public class BloodTransfusionViewInterceptor implements IFormInterceptor{

	public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
		BloodTransfusionForm form = (BloodTransfusionForm)aForm ;
		BloodTransfusion entity = (BloodTransfusion)aEntity ;
		EntityManager manager = aContext.getEntityManager() ;
		long id = form.getId() ;
		
		//try {
			//Мониторинг
			saveForm(manager,"from TransfusionMonitoring where transfusion_id='"+id+"' and hourAfterTransfusion='"+0+"'"
					,"getPulseRate,getBloodPressureLower,getBloodPressureTop,getTemperature,getUrineColor,getDiuresis".split(",")
					,form.getMonitorForm0(),TransfusionMonitoring.class,TransfusionMonitoringForm.class);
			saveForm(manager,"from TransfusionMonitoring where transfusion_id='"+id+"' and hourAfterTransfusion='"+1+"'"
					,"getPulseRate,getBloodPressureLower,getBloodPressureTop,getTemperature,getUrineColor,getDiuresis".split(",")
					,form.getMonitorForm1(),TransfusionMonitoring.class,TransfusionMonitoringForm.class);
			saveForm(manager,"from TransfusionMonitoring where transfusion_id='"+id+"' and hourAfterTransfusion='"+2+"'"
					,"getPulseRate,getBloodPressureLower,getBloodPressureTop,getTemperature,getUrineColor,getDiuresis".split(",")
					,form.getMonitorForm2(),TransfusionMonitoring.class,TransfusionMonitoringForm.class);
			
			//Реактивы
			saveForm(manager,"from TransfusionReagent where transfusion_id='"+id+"' and numberReagent='"+1+"'"
					,"getReagent,getSeries,getExpirationDate".split(",")
					,form.getReagentForm1(),TransfusionReagent.class,TransfusionReagentForm.class);
			saveForm(manager,"from TransfusionReagent where transfusion_id='"+id+"' and numberReagent='"+2+"'"
					,"getReagent,getSeries,getExpirationDate".split(",")
					,form.getReagentForm2(),TransfusionReagent.class,TransfusionReagentForm.class);
		//}catch(Exception e) {
		//	e.printStackTrace();
		//	//throw new IllegalStateException(e.toString());
		//}
		
		StringBuilder biolTest = new StringBuilder() ;
		if (form.getIsIllPatientsBT()!=null && form.getIsIllPatientsBT()) {
			//biolTest.append("Проба на гемолиз (проба Бакстера). Перелито 30 мл. компонента крови струйно, взято 3 мл у реципиента, центрифугирована. Цвет сыворотки: ") ;
			biolTest.append(entity.getSerumColorBT()!=null?entity.getSerumColorBT().getName():"_________") ;
		} else {
			//biolTest.append("Перелито 10 мл. компонента крови со скоростью 40-60 кап. в мин, 3 мин.-наблюдения. Данная процедура выполняется дважды.") ;
			biolTest.append(" PS: ").append(form.getPulseRateBT()) ;
			biolTest.append(" AD: ").append(form.getBloodPressureLowerBT()).append("/").append(form.getBloodPressureTopBT()) ;
			biolTest.append(" ЧДД: ").append(form.getRespiratoryRateBT()) ;
			if (form.getTemperatureBT()!=null) biolTest.append(" t: ").append(form.getTemperatureBT()) ;
			if (entity.getStateBT()!=null) {
				if (entity.getStateBT().getCode()!=null&&entity.getStateBT().getCode().equals("1")) {
					biolTest.append(" Состояние удовлетворительное.") ;
				} else {
					biolTest.append(" Состояние неудовлетворительное. Жалобы:").append(form.getLamentBT()).append(".") ;
				}
			} else {
				biolTest.append(" Состояние: ____________________________. Жалобы _____________________________") ;
			}
		}
		if (form.getIsBreakBT()!=null && form.getIsBreakBT()) biolTest.append(" Переливание прекращено.") ;
		form.setBiologicTest(biolTest.toString()) ;
		//Осложнения complications
		List<Object> list = manager.createNativeQuery(" select list(''||reaction_id) from TransfusionComplication where transfusion_id='" + id + "' group by transfusion_id").getResultList();
		if (!list.isEmpty()) {
			form.setComplications( list.get(0).toString()) ;
		}


	}
	private <E,F> void saveForm(EntityManager aManager, String aAddtionSql,String[] aMethods, F aForm,Class<E> aClassEntity,Class<F> aClassForm)  {
		
		List<E> objs = aManager.createQuery(aAddtionSql).setMaxResults(1).getResultList() ;
		if (!objs.isEmpty()) {
			E obj = objs.get(0);
			for (String method:aMethods) {
				try {
					Method m;
						m = aClassEntity.getMethod(method);
					Class ejbReturnType = m.getReturnType();
					if (ejbReturnType.getAnnotation(Entity.class) != null) {
						Object o = m.invoke(obj);
						if (o!=null) {
							Object id = PropertyUtil.getPropertyValue(o, "id");
							Method formSetterMethod = PropertyUtil.getSetterMethod(aForm
									.getClass(), m);
							formSetterMethod.invoke(aForm, id);
						}
					} else {
						PropertyUtil.copyProperty(aForm, obj, m);
					}
			
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		
	}
	
}