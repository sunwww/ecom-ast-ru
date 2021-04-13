package ru.ecom.mis.ejb.form.assessmentcard;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.mis.ejb.domain.assessmentcard.AssessmentCard;
import ru.ecom.mis.ejb.service.disability.IDisabilityService;

import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Milamesher on 12.09.2019.
 */
public class AssessmentCardSaveInterceptor implements IFormInterceptor {
    @Override
    public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
        AssessmentCard card=(AssessmentCard)aEntity;
        AssessmentCardForm cardForm=(AssessmentCardForm)aForm;
       if (card.getTemplate()==8) {  //в бд ещё нет, объектов тоже нет
           JSONObject out = new JSONObject();
           out.put("patientId",card.getPatient());
           out.put("slsId",cardForm.getMedcase());
           out.put("createDate", new SimpleDateFormat("dd.MM.yyyy").format(new Date()));
           out.put("birthday",getBirthday(card.getPatient(),aContext.getEntityManager()));
           out.put("bals",card.getBallSum());

           JSONArray params = new JSONObject(cardForm.getParams()).getJSONArray("params");
           JSONArray array = new JSONArray();
           for (int i=0;i<params.length();i++) {
               JSONObject child = params.getJSONObject(i);
               JSONObject par = new JSONObject();
               if (child.get("name")!=null && child.get("valueVoc")!=null)
                   par.put("name",child.get("name")).put("val",child.get("valueVoc"));
               array.put(par);
           }
           out.put("par",array);
           out.put("risk",getRisk(card.getTemplate(),card.getBallSum(),aContext.getEntityManager()));
           ru.ecom.diary.ejb.service.template.TemplateProtocolServiceBean bean = new ru.ecom.diary.ejb.service.template.TemplateProtocolServiceBean();
           String url=((IDisabilityService)EjbInjection.getInstance().getService("IDisabilityService","remote"))
                   .getSoftConfigValue("diabetesCardSrvr", null);
           if (url!=null) {
               String[] req = url.split("#");
               url=req[0];
               String aMethod = req[1];
               if (!url.equals("") && !aMethod.equals("")) {
                   bean.makePOSTRequest(out.toString(),url,aMethod,null);
               }
           }

       }
    }

    /**
     * Получить дату рождения пациента(из-за индексов в форме не объект, а long)
     * @param patientId Patient.id
     * @param entityManager EntityManager
     * @return String
     */
    private String getBirthday(Long patientId, EntityManager entityManager) {
        List<Object> birthday = entityManager.createNativeQuery("select birthday from patient where id=:patientId").setParameter("patientId",patientId).getResultList();
        return  birthday.isEmpty()? "" : birthday.get(0).toString();
    }

    /**
     * Получить риск по баллам
     * @param aCardTypeId Тип карты оценки
     * @param val Long Результат (баллы)
     * @return String
     */
    private String getRisk(Long aCardTypeId, Long val, EntityManager entityManager) {
        List<Object> risk = entityManager.createNativeQuery("select name from assessment" +
                " where assessmentcard=" + aCardTypeId+" and "+ val + " between minball and maxball").getResultList();
        return  risk.isEmpty()? "" : risk.get(0).toString();
    }
}