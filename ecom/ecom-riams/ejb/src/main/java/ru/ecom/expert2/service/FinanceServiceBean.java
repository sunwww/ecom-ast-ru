package ru.ecom.expert2.service;

import org.apache.log4j.Logger;
import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.expert2.domain.financeplan.FinancePlan;
import ru.ecom.expert2.domain.financeplan.HospitalFinancePlan;
import ru.ecom.expert2.domain.financeplan.MonthLittleAmountTable;
import ru.ecom.expert2.domain.voc.VocE2BaseTariff;
import ru.ecom.expomc.ejb.domain.med.VocKsg;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.format.DateFormat;

import javax.annotation.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

@Stateless
@Local(IFinanceService.class)
@Remote(IFinanceService.class)
public class FinanceServiceBean implements IFinanceService {
    private final Logger log = Logger.getLogger(FinanceServiceBean.class);

    /**Разбиваем годовой финансовый план помесячно*/
    public void splitFinancePlan(String aType, String aYearPlan) {
        //try {
            //TODO учитывать тип плана (стационар, поликлиника, ВМП
            String sql = "select id from FinancePlan fp where dtype='" + aType + "' and to_char(fp.startDate,'MM.yyyy')='01." + aYearPlan + "' and to_char(fp.finishDate,'MM.yyyy')='12." + aYearPlan + "'";
            log.info("sql0=" + sql);
            List<Long> plans = theManager.createQuery(sql).getResultList();
            if (plans == null || plans.isEmpty()) {
                log.error("Не найдено планов на год");
                return;
            }
            sql = "delete from FinancePlan where  dtype='" + aType + "' and to_char(startDate,'MM')= to_char(finishDate,'MM') and to_char(startDate,'yyyy')='" + aYearPlan + "'";
            log.info("sql1=" + sql);
            theManager.createNativeQuery(sql).executeUpdate();
            FinancePlan yearPlan;
            Calendar startFromCalendar = Calendar.getInstance();
            //   Calendar lastCalendar = Calendar.getInstance();
            HashMap<Long, String> littleAmountMonth = new HashMap<Long, String>();
           HashMap<String, BigDecimal> caseCost= new HashMap<String, BigDecimal>();
            List<MonthLittleAmountTable> monthLittleAmountTables = theManager.createQuery("from MonthLittleAmountTable").getResultList();
            for (MonthLittleAmountTable mLAT : monthLittleAmountTables) {
                littleAmountMonth.put(mLAT.getAmount(), mLAT.getMonths());
            }
            SimpleDateFormat month = new SimpleDateFormat("MM");
            int planCnt=0;
            log.info("planCnt="+plans.size());
            for (Long planId : plans) {
                planCnt++;
                //   boolean first = true;
                yearPlan = theManager.find(FinancePlan.class, planId);
                String priceKey;
                BigDecimal cost;
                startFromCalendar.setTimeInMillis(yearPlan.getStartDate().getTime());
                log.info(planCnt+"<<>>"+planId+", startFromTime = "+startFromCalendar.getTime());
                HospitalFinancePlan monthPlan;
                Date currentMonth;
                for (int i = 0; i < 12; i++) {
                    currentMonth = new java.sql.Date(startFromCalendar.getTimeInMillis());
                    monthPlan = (HospitalFinancePlan) cloneEntity(yearPlan, false);
                    monthPlan.setStartDate(currentMonth);
                    monthPlan.setFinishDate(currentMonth);

                    Long count = yearPlan.getCount();
                    if (count > 11) {
                        count = count / 12;
                    } else {
                        //Включаем режим поиска подходит ли месяц
                        try {
                            count = littleAmountMonth.get(count).indexOf(month.format(currentMonth)) > -1 ? 1L : 0L;
                        } catch (Exception e) {
                            count = 0L;
                        }
                    }
                    monthPlan.setCount(count);
                    //Находим цену
                    if (yearPlan instanceof HospitalFinancePlan) {
                        sql="stacType_id=" +monthPlan.getBedSubType().getId();
                        VocKsg ksg=monthPlan.getKsg();
                        priceKey=ksg.getId()+"#"+sql+"#"+currentMonth.getTime();

                        if (!caseCost.containsKey(priceKey)){
                            VocE2BaseTariff tariff = expert2ServiceBean.getActualVocByClassName(VocE2BaseTariff.class, currentMonth, sql);
                            BigDecimal baseTariff = tariff.getValue();
                            cost=baseTariff
                                    .multiply(BigDecimal.valueOf(ksg.getKZ())
                                            .multiply(expert2ServiceBean.getActualKsgUprCoefficient(ksg,currentMonth))
                                            .multiply(expert2ServiceBean.calculateCusmo(monthPlan.getBedSubType().getCode(),monthPlan.getDepartment().getId(),monthPlan.getProfile().getId(),currentMonth)));
                            cost=cost.setScale(2,RoundingMode.HALF_UP);
                            caseCost.put(priceKey,cost);
                        } else {
                            cost=caseCost.get(priceKey);
                        }

                        cost=cost.multiply(new BigDecimal(count)).setScale(2,RoundingMode.HALF_UP);
                        log.info("total cost = "+cost);
                        monthPlan.setCost(cost);
                        theManager.persist(monthPlan);
                    } else {
                        log.warn("Неизвестный тип финансового плана, доделать: "+yearPlan.getId());
                        //sql="1==2";
                    }

                    startFromCalendar.add(Calendar.MONTH, 1);
                }
            }
            log.info("Finished!");
      //  } catch (Exception e) {e.printStackTrace();}

    }

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

    private @EJB IExpert2Service expert2ServiceBean;
}
