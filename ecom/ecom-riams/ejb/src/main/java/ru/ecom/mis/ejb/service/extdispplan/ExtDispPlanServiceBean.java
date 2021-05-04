package ru.ecom.mis.ejb.service.extdispplan;


import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.ecom.mis.ejb.domain.extdispplan.ExtDispPlanPopulation;
import ru.ecom.mis.ejb.domain.extdispplan.ExtDispPlanPopulationRecord;
import ru.ecom.mis.ejb.domain.patient.Patient;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Local(IExtDispPlanService.class)
@Remote(IExtDispPlanService.class)
public class ExtDispPlanServiceBean implements IExtDispPlanService {

    public Boolean addPersonToPlan(Long aPlanId, Long aPersonId) {return addPersonToPlan(manager.find(ExtDispPlanPopulation.class,aPlanId),aPersonId);}
    public Boolean addPersonToPlan(ExtDispPlanPopulation aPlan, Long aPersonId) {
        //Проверка на наличия пациента в плане
        if (!manager.createNativeQuery("select id from  extdispplanpopulationrecord where plan_id=:plan and patient_id=:pat")
                .setParameter("plan",aPlan.getId()).setParameter("pat",aPersonId).getResultList().isEmpty()) {
            log.warn("Пациент с ИД "+aPersonId+" уже находится в плане ИД "+aPlan.getId());
            return false;
        }
        ExtDispPlanPopulationRecord rec = new ExtDispPlanPopulationRecord();
        rec.setPatient(manager.find(Patient.class,aPersonId));
        rec.setPlan(aPlan);
        manager.persist(rec);
        return true;
    }

    /** добавляем в план на список людей */
    public int fillExtDispPlanByPersons(String aPersonJson, Long aPlanId)  {
        int cnt=0;
        try {
            ExtDispPlanPopulation plan = manager.find(ExtDispPlanPopulation.class,aPlanId);
            JSONArray patients = new JSONArray(aPersonJson); //id, *name
            for (int i=0;i<patients.length();i++) {
                JSONObject pat = patients.getJSONObject(i);
               if (addPersonToPlan(plan,pat.getLong("id"))) {cnt++;}
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return cnt;
    }

    private @PersistenceContext
    EntityManager manager;
    private static final Logger log = Logger.getLogger(ExtDispPlanServiceBean.class);
}
