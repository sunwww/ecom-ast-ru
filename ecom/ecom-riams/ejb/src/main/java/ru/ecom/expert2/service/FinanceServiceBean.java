package ru.ecom.expert2.service;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.expert2.domain.financeplan.FinancePlan;
import ru.ecom.expert2.domain.financeplan.HospitalFinancePlan;
import ru.ecom.expert2.domain.financeplan.MonthLittleAmountTable;
import ru.ecom.expert2.domain.voc.VocE2BaseTariff;
import ru.ecom.expomc.ejb.domain.med.VocKsg;
import ru.nuzmsh.util.PropertyUtil;

import javax.annotation.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceContext;
import java.lang.reflect.Method;
import java.math.BigDecimal;
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
            String sql = "select id from FinancePlan fp where dtype='" + aType + "' and to_char(fp.startDate,'MM.yyyy')='01" + aYearPlan + "' and to_char(fp.finishDate,'MM.yyyy')='12." + aYearPlan + "'";
            log.info("sql0=" + sql);
            List<Long> plans = theManager.createQuery(sql).getResultList();
            if (plans == null || plans.isEmpty()) {
                log.error("Не найдено планов на год");
                return;
            }
            sql = "delete from FinancePlan where  dtype='" + aType + "' and to_char(startDate,'MM.yyyy')= to_char(finishDate,'MM.yyyy') and to_char(startDate,'yyyy')='" + aYearPlan + "'";
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
                log.info(planCnt+"<<>>"+planId);
                HospitalFinancePlan monthPlan;
                Date currentMonth;
                Long yearCount = yearPlan.getCount();
                Long count = yearPlan.getCount();
                Long cnt12 = count%12;
                for (int i = 0; i < 12; i++) {
                    currentMonth = new java.sql.Date(startFromCalendar.getTimeInMillis());
                    monthPlan = (HospitalFinancePlan) cloneEntity(yearPlan, false);
                    monthPlan.setStartDate(currentMonth);
                    monthPlan.setFinishDate(currentMonth);

                    count=yearCount>11? yearCount/12 : 0;

                    if (yearCount<12 || cnt12>0){
                        //Включаем режим поиска подходит ли месяц +
                        try {
                            count += littleAmountMonth.get(cnt12).contains(month.format(currentMonth)) ? 1L : 0L;
                        } catch (Exception e) {
                            e.printStackTrace();
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

    /**Заполняем агг. таблицу с планом и фактом */
    public String fillAggregateTable(String aType, Date aStartDate, Date aFinishDate, String aServiceStream) throws JSONException {
        if (aStartDate.getTime()>aFinishDate.getTime()) {return new JSONObject().put("status","error").put("error_code","Дата окончания больше даты начала").toString();}
        StringBuilder sql ;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(aStartDate.getTime());
        SimpleDateFormat yyyyMM = new SimpleDateFormat("yyyy-MM");
        int ret=0;
        while (calendar.getTimeInMillis()<=aFinishDate.getTime()) {
            sql = new StringBuilder();
            String finishDate =yyyyMM.format(calendar.getTime());
            log.info("Ищем данные за месяц"+finishDate+" > "+calendar.getTimeInMillis());
            if (aServiceStream==null) {aServiceStream="OBLIGATORYINSURANCE";}
            sql.append("insert into aggregatevolumesfinanceplan  (month, year,medhelpprofile, department, bedsubtype, ksg, plancount, plancost, factcount, factcost)( ");
            sql.append("select cast(date_part('month',plan.startdate) as int) as d1, cast (date_part('year',plan.startdate) as int) as d2, plan.profile_id, plan.department_id, plan.bedsubtype_id, plan.ksg_id as f5_ksg" +
                    ", plan.count as planCount, plan.cost as planCost " +
                    " ,count(case when bill.status_id=3 then e.id else null end)  as factCount" +
                    " ,sum(case when bill.status_id=3 then e.cost else null end) as f9_factCost" +
                    " from financeplan plan" +
                    " left join e2entry e on e.medhelpprofile_id=plan.profile_id" +
                    " and e.departmentid=plan.department_id" +
                    " and e.bedsubtype=''||plan.bedsubtype_id" +
                    " and e.ksg_id=plan.ksg_id" +
                    " and (e.isDeleted is null or e.isDeleted='0')" +
                    " and (e.doNotSend is null or e.doNotSend='0')" +
                    " and e.servicestream='"+aServiceStream+"'" +
                    " and to_char(e.finishdate,'yyyy-MM') ='" + finishDate+"'" +
                    " left join e2bill bill on bill.id=e.bill_id" +
                    " where to_char(plan.startdate,'mm.yyyy') = to_char(plan.finishdate,'mm.yyyy')" +
                    " and plan.dtype='HospitalFinancePlan'" +
                    " and to_char(plan.startdate,'yyyy-MM')='"+finishDate+"' " +
                    " group by cast(date_part('month',plan.startdate) as int),cast (date_part('year',plan.startdate) as int), plan.profile_id, plan.department_id, plan.bedsubtype_id, plan.ksg_id, plan.count, plan.cost" +
                    " union select cast(date_part('month',ee.finishDate)as int) , cast(date_part('year',ee.finishDate)as int), ee.medhelpprofile_id, cast(ee.departmentid as int), cast(ee.bedsubtype as int), ee.ksg_id,0,0, count(ee.id), sum(ee.cost)" +
                    " from e2entry ee" +
                    " left join e2bill bill on bill.id=ee.bill_id" +
                    " left join financeplan fp on ee.medhelpprofile_id=fp.profile_id" +
                    " and ee.departmentid=fp.department_id" +
                    " and ee.bedsubtype=''||fp.bedsubtype_id" +
                    " and ee.ksg_id=fp.ksg_id" +
                    " where to_char(ee.finishdate,'yyyy-MM') ='" + finishDate+"'" +
                    " and ee.entrytype='HOSPITAL' and (ee.isDeleted is null or ee.isDeleted='0')" +
                    " and (ee.doNotSend is null or ee.doNotSend='0')" +
                    " and ee.servicestream='"+aServiceStream+"'" +
                    " and bill.status_id=3 and fp.id is null" +
                    " group by cast(date_part('month',ee.finishDate)as int) , cast(date_part('year',ee.finishDate)as int), ee.medhelpprofile_id, cast(ee.departmentid as int), cast(ee.bedsubtype as int), ee.ksg_id");
            sql.append(")");
            log.info("sql="+sql.toString());
            cleanAggregateTable(calendar.getTime());
             ret += theManager.createNativeQuery(sql.toString()).executeUpdate();
             calendar.add(Calendar.MONTH,1);
        }


        return new JSONObject().put("status","ok").put("count",ret).toString();

    }
    public void cleanAggregateTable(java.util.Date aMonthDate) {
        SimpleDateFormat mm = new SimpleDateFormat("MM");
        SimpleDateFormat yyyy= new SimpleDateFormat("yyyy");
        log.info("Очищаем сведения о факте-плане за "+aMonthDate+" месяц");
        String sql = "delete from aggregatevolumesfinanceplan where month="+mm.format(aMonthDate)+" and year="+yyyy.format(aMonthDate);
        log.info("sqql="+sql);
        theManager.createNativeQuery(sql).executeUpdate();

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
