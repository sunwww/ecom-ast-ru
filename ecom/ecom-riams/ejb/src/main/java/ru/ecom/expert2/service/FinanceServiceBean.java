package ru.ecom.expert2.service;

import org.apache.log4j.Logger;
import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.expert2.domain.financeplan.FinancePlan;
import ru.ecom.expert2.domain.financeplan.HospitalFinancePlan;
import ru.nuzmsh.util.PropertyUtil;

import javax.annotation.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.lang.reflect.Method;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

@Stateless
@Local(IFinanceService.class)
@Remote(IFinanceService.class)
public class FinanceServiceBean implements IFinanceService {
    private final Logger log = Logger.getLogger(FinanceServiceBean.class);
    /** Копируем финансовый план с одного месяца на несколько */
    public String copyFinancePlanNextMonth (String aType, Date aMonthPlan, Date aStartMonth, Date aFinishMonth) {
        /* Находим все планы по всем отделениям на выбранный месяц и копируем их на следующие месяцы. Проще простого) */
        System.out.println("monthPlan = "+aMonthPlan+", startFrom "+aStartMonth+", finish = "+aFinishMonth);
        List<HospitalFinancePlan> plans = theManager.createQuery("from HospitalFinancePlan where :date between startDate and finishDate").setParameter("date",aMonthPlan).getResultList();
        log.info("plans for date = "+aMonthPlan+" found = "+plans.size());
        if (plans.isEmpty()) {return "null, no data";}
        Calendar startFromCalendar = Calendar.getInstance();
        Calendar lastCalendar = Calendar.getInstance();
        startFromCalendar.setTime(aStartMonth);
        lastCalendar.setTime(aFinishMonth);
        log.info("1 startMonth = "+startFromCalendar.getTime());
        log.info("1 finishMonth = "+lastCalendar.getTime());

        HospitalFinancePlan clonedPlan;
        while (!startFromCalendar.after (lastCalendar)) {
            log.info("while last="+lastCalendar.getTime()+", start="+startFromCalendar.getTime());
            Date currentMonth = new java.sql.Date(startFromCalendar.getTimeInMillis());
            for (HospitalFinancePlan plan:plans) {
                clonedPlan = (HospitalFinancePlan) cloneEntity(plan,false);
                clonedPlan.setStartDate(currentMonth);
                clonedPlan.setFinishDate(currentMonth);
                theManager.persist(clonedPlan);
            }
            startFromCalendar.add(Calendar.MONTH,1);
        }

return "good";
    }

    /** Клонируем запись*/
    private Object cloneEntity(Object aSourceObject, boolean needPersist) {
        try {
            Class aClass = aSourceObject.getClass();
            Method[] methodList = aClass.getMethods();
            Object newEntity = aClass.newInstance();
            for (Method setterMethod: methodList) {
                if (setterMethod.getName().startsWith("set")) {
                    if (setterMethod.getName().equals("setId")) {
                        continue;
                    } else if (setterMethod.isAnnotationPresent(OneToMany.class)) {
                        continue;
                    } else {
                        String propertyName = PropertyUtil.getPropertyName(setterMethod);
                        try {
                            Object val = PropertyUtil.getPropertyValue(aSourceObject,propertyName);
                            PropertyUtil.setPropertyValue(newEntity,propertyName,val);
                        } catch (Exception e) {}
                    }

                }
            }
            if (needPersist) {theManager.persist(newEntity);}
            return newEntity;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private @PersistenceContext
    EntityManager theManager;
    private @EJB
    ILocalMonitorService theMonitorService;
}
