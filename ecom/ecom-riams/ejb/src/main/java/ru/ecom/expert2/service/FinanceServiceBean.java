package ru.ecom.expert2.service;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.expert2.domain.financeplan.*;
import ru.ecom.expert2.domain.voc.VocE2BaseTariff;
import ru.ecom.expomc.ejb.domain.med.VocKsg;
import ru.ecom.mis.ejb.domain.medcase.voc.VocKindHighCare;
import ru.nuzmsh.util.PropertyUtil;
import ru.nuzmsh.util.format.DateFormat;

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
    private static final Logger LOG = Logger.getLogger(FinanceServiceBean.class);

    /**Разбиваем годовой финансовый план помесячно*/
    public void splitFinancePlan(String aType, String aYearPlan) {
       try {
            String sql = "select id from FinancePlan fp where dtype='" + aType + "' and to_char(fp.startDate,'MM.yyyy')='01." + aYearPlan + "' and to_char(fp.finishDate,'MM.yyyy')='12." + aYearPlan + "'";
            LOG.info("sql0=" + sql);
            List<Long> plans = theManager.createQuery(sql).getResultList();
            if (plans == null || plans.isEmpty()) {
                LOG.error("Не найдено планов на год");
                return;
            }
            sql = "delete from FinancePlan where  dtype='" + aType + "' and to_char(startDate,'MM.yyyy')= to_char(finishDate,'MM.yyyy') and to_char(startDate,'yyyy')='" + aYearPlan + "'";
            LOG.info("sql1=" + sql);
            theManager.createNativeQuery(sql).executeUpdate();
            FinancePlan yearPlan;
            Calendar startFromCalendar = Calendar.getInstance();
            HashMap<Long, String> littleAmountMonth = new HashMap<>();
           HashMap<String, BigDecimal> caseCost= new HashMap<>();
            List<MonthLittleAmountTable> monthLittleAmountTables = theManager.createQuery("from MonthLittleAmountTable").getResultList();
            for (MonthLittleAmountTable mLAT : monthLittleAmountTables) {
                littleAmountMonth.put(mLAT.getAmount(), mLAT.getMonths());
            }
            SimpleDateFormat month = new SimpleDateFormat("MM");
            int planCnt=0;
            boolean hospPlan = aType.equals("HospitalFinancePlan");
            boolean polPlan = aType.equals("PolyclinicFinancePlan");
            boolean vmpPlan = aType.equals("VmpFinancePlan");
            for (Long planId : plans) {
                planCnt++;
                //   boolean first = true;
                yearPlan = theManager.find(FinancePlan.class, planId);
                String priceKey;
                BigDecimal cost;
                startFromCalendar.setTimeInMillis(yearPlan.getStartDate().getTime());

                Date currentMonth;
                Long yearCount = yearPlan.getCount();
                Long count = yearPlan.getCount();
                Long cnt12 = count%12;

                for (int i = 0; i < 12; i++) {
                    currentMonth = new java.sql.Date(startFromCalendar.getTimeInMillis());

                    count=yearCount>11? yearCount/12 : 0;

                    if (yearCount<12 || cnt12>0){
                        //Включаем режим поиска подходит ли месяц +
                        try {
                            count += littleAmountMonth.get(cnt12).contains(month.format(currentMonth)) ? 1L : 0L;
                        } catch (Exception e) {
                           LOG.error(e.getMessage(),e);
                        }
                    }

                    //Находим цену
                    if (hospPlan) {
                        HospitalFinancePlan monthPlan = (HospitalFinancePlan) cloneEntity(yearPlan, false);
                        monthPlan.setStartDate(currentMonth);
                        monthPlan.setFinishDate(currentMonth);
                        monthPlan.setCount(count);


                        sql="stacType_id=" +monthPlan.getBedSubType().getId();
                        VocKsg ksg=monthPlan.getKsg();
                       if (yearPlan.getCost()!=null) {
                           cost=yearPlan.getCost().divide(BigDecimal.valueOf(12.0),2, RoundingMode.UP);
                       } else if (ksg!=null) {
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
                        } else { //Нет КСГ, цена = 0 рублей
                            cost = BigDecimal.valueOf(0.0);
                        }

                        monthPlan.setCost(cost);
                        theManager.persist(monthPlan);
                    } else if (polPlan) { //Считаем цену для плана по поликлиники
                        PolyclinicFinancePlan monthPlan = (PolyclinicFinancePlan) cloneEntity(yearPlan, false);
                        monthPlan.setStartDate(currentMonth);
                        monthPlan.setFinishDate(currentMonth);
                        monthPlan.setCount(count);
                        priceKey="POL#"+monthPlan.getVidSluch().getId()+"#"+currentMonth.getTime();

                        if (!caseCost.containsKey(priceKey)){
                        cost = expert2ServiceBean.calculatePolyclinicEntryPrice(monthPlan.getVidSluch(), monthPlan.getFinishDate(),monthPlan.getProfile());
                        caseCost.put(priceKey,cost);
                        } else {
                            cost=caseCost.get(priceKey);
                        }

                        cost=cost.multiply(new BigDecimal(count)).setScale(2,RoundingMode.HALF_UP);
                        monthPlan.setCost(cost);
                        theManager.persist(monthPlan);
                    } else if (vmpPlan) {
                        VmpFinancePlan monthPlan = (VmpFinancePlan) cloneEntity(yearPlan, false);
                        monthPlan.setStartDate(currentMonth);
                        monthPlan.setFinishDate(currentMonth);
                        monthPlan.setCount(count);
                        priceKey="VMP#"+monthPlan.getMethod().getCode();
                        if (!caseCost.containsKey(priceKey)){
                            VocKindHighCare kind =  expert2ServiceBean.getActualVocBySqlString(VocKindHighCare.class,"select id from VocKindHighCare where code='"+monthPlan.getMethod().getKindHighCare()+"' " +
                                            "and to_date('" +DateFormat.formatToDate(monthPlan.getFinishDate()) +"','dd.MM.yyyy') between datefrom and coalesce(dateTo,current_date) and serviceStreamCode='OBLIGATORYINSURANCE'");
                            if (kind==null) {
                                LOG.error("Невозможно найти вид ВМП "+monthPlan.getMethod().getKindHighCare());
                                return;
                            }
                            cost=kind.getCost();
                            cost=cost.setScale(2,RoundingMode.HALF_UP);
                            caseCost.put(priceKey,cost);
                        } else {
                            cost=caseCost.get(priceKey);
                        }
                        cost=cost.multiply(new BigDecimal(count)).setScale(2,RoundingMode.HALF_UP);
                        monthPlan.setCost(cost);
                        theManager.persist(monthPlan);
                    } else {
                        LOG.warn("Неизвестный тип финансового плана, доделать: "+yearPlan.getId());
                        //sql="1==2";
                    }

                    startFromCalendar.add(Calendar.MONTH, 1);
                }
            }
            LOG.info("Finished!");
        } catch (Exception e) {
       LOG.error(e.getMessage(),e);
       }

    }

    /** Копируем финансовый план с одного месяца на несколько */
    public String copyFinancePlanNextMonth (String aType, Date aMonthPlan, Date aStartMonth, Date aFinishMonth) {
        /* Находим все планы по всем отделениям на выбранный месяц и копируем их на следующие месяцы. Проще простого) */
        LOG.info("monthPlan = "+aMonthPlan+", startFrom "+aStartMonth+", finish = "+aFinishMonth);
        List<HospitalFinancePlan> plans = theManager.createQuery("from HospitalFinancePlan where :date between startDate and finishDate").setParameter("date",aMonthPlan).getResultList();
        LOG.info("plans for date = "+aMonthPlan+" found = "+plans.size());
        if (plans.isEmpty()) {return "null, no data";}
        Calendar startFromCalendar = Calendar.getInstance();
        Calendar lastCalendar = Calendar.getInstance();
        startFromCalendar.setTime(aStartMonth);
        lastCalendar.setTime(aFinishMonth);
        LOG.info("1 startMonth = "+startFromCalendar.getTime());
        LOG.info("1 finishMonth = "+lastCalendar.getTime());

        HospitalFinancePlan clonedPlan;
        while (!startFromCalendar.after (lastCalendar)) {
            LOG.info("while last="+lastCalendar.getTime()+", start="+startFromCalendar.getTime());
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
    public String fillAggregateTable(String aType, Date aStartDate, Date aFinishDate, String aServiceStream) {
        if (aStartDate.getTime()>aFinishDate.getTime()) {return new JSONObject().put("status","error").put("error_code","Дата окончания больше даты начала").toString();}
        StringBuilder sql ;

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(aStartDate.getTime());
        SimpleDateFormat yyyyMM = new SimpleDateFormat("yyyy-MM");
        int ret=0;
        String entryType;
        StringBuilder sqlAdd = new StringBuilder();
        boolean isHosp, isVmp, isPol;
        switch (aType) {
            case "HospitalFinancePlan":
                isHosp = true;
                isPol=isVmp=false;
                entryType="='HOSPITAL'";
                sqlAdd.append( " and plan.ksgGroup_id=(select group_id from vocksg where id=e.ksg_id)" );
                break;
            case "PolyclinicFinancePlan":
                entryType=" in ('POLYCLINIC','POLYCLINICKDO')";
                isPol = true;
                isHosp=isVmp=false;
                break;
            case "VmpFinancePlan":
                isVmp = true;
                isPol=isHosp=false;
                entryType="='VMP'";
                sqlAdd.append( " and e.vmpMethod=vmp.code " );
                break;
            default:
                isVmp=isPol=isHosp=false;
                    entryType=" is null";
        }
        while (calendar.getTimeInMillis()<=aFinishDate.getTime()) {
            sql = new StringBuilder();
            String finishDate =yyyyMM.format(calendar.getTime());
            LOG.info("Ищем данные за месяц "+finishDate);
            if (aServiceStream==null) {aServiceStream="OBLIGATORYINSURANCE";}
            sql.append("insert into aggregatevolumesfinanceplan (type, vidSluch_id, month, year,medhelpprofile, department, bedsubtype, plancount, plancost, factcount, factcost, vmp, vmpName, ksgGroup)( ");
            sql.append("select '").append(aType).append("',plan.vidSluch_id, cast(date_part('month',plan.startdate) as int) as d1, cast (date_part('year',plan.startdate) as int) as d2, plan.profile_id, plan.department_id")
                    .append(", plan.bedsubtype_id")
                    .append(", plan.count as planCount, plan.cost as planCost ")
                    .append(" ,count(case when bill.status_id=3 then e.id else null end)  as factCount")
                    .append(" ,sum(case when bill.status_id=3 then e.cost else null end) as f9_factCost")
                    .append(" ,vmp.code as f10_vmpCode ").append(" ,vmp.name as f11_vmpName , plan.ksgGroup_id as f12_ksgGroupId")
                    .append(" from financeplan plan")
                    .append(" left join vocmethodhighcare vmp on vmp.id=plan.method_id")
                    .append(" left join e2entry e on e.medhelpprofile_id=plan.profile_id")
                        .append(" and e.vidSluch_id = plan.vidSluch_id and (plan.department_id is null or plan.department_id=e.departmentid) ")
                        .append(" and to_char(e.finishdate,'yyyy-MM') ='").append(finishDate).append("'")
                        .append(sqlAdd).append(" and (e.isDeleted is null or e.isDeleted='0')")
                        .append(" and (e.doNotSend is null or e.doNotSend='0')").append(" and (e.isDefect is null or e.isDefect='0')").append(" and (e.isForeign is null or e.isForeign='0')")
                        .append(" and e.servicestream='").append(aServiceStream).append("'")
                    .append(" left join e2bill bill on bill.id=e.bill_id")
                    .append(" left join vocksg ksg on ksg.id=e.ksg_id")
                    .append(" where to_char(plan.startdate,'mm.yyyy') = to_char(plan.finishdate,'mm.yyyy')")
                    .append(" and plan.dtype='").append(aType).append("'")
                    .append(" and to_char(plan.startdate,'yyyy-MM')='").append(finishDate).append("' ")
                    .append(" group by plan.vidSluch_id, cast(date_part('month',plan.startdate) as int),cast (date_part('year',plan.startdate) as int), plan.profile_id, plan.department_id, plan.bedsubtype_id, plan.ksgGroup_id ,plan.count, plan.cost, vmp.code, vmp.name")

                    .append(" union select '") //Находим фактические случаи без плана
                    .append(aType).append("', e.vidSluch_id, cast(date_part('month',e.finishDate)as int) , cast(date_part('year',e.finishDate)as int), e.medhelpprofile_id, cast('0'||e.departmentid as int), cast('0'||e.bedsubtype as int)")
                    .append(", 0,0, count(e.id), sum(e.cost)")
                    .append(" ,vmp.code as f10_vmpCode ").append(" ,vmp.name as f11_vmpName ,ksg.group_id as f12_ksgGroupId")
                    .append(" from e2entry e")
                    .append(" left join vocksg ksg on ksg.id=e.ksg_id")
                    .append(" left join e2bill bill on bill.id=e.bill_id")
                    .append(" left join vocmethodhighcare vmp on e.vmpMethod=vmp.code and vmp.dateto is null")
                    .append(" left join financeplan plan on e.medhelpprofile_id=plan.profile_id and plan.vidsluch_id=e.vidsluch_id and to_char(plan.startdate,'mm.yyyy') = to_char(plan.finishdate,'mm.yyyy') " +
                            " and (plan.department_id is null or plan.department_id=e.departmentid) and to_char(plan.startdate,'yyyy-MM')='").append(finishDate).append("'");
                       if (isHosp) sql.append(" and plan.ksgGroup_id=ksg.group_id ");
                       else if (isVmp) sql.append(" and plan.method_id=vmp.id");

                    sql.append(" where to_char(e.finishdate,'yyyy-MM') ='").append(finishDate).append("'")
                    .append(" and e.entrytype ").append(entryType)
                    .append(" and (e.isDeleted is null or e.isDeleted='0')").append(" and (e.doNotSend is null or e.doNotSend='0')").append(" and (e.isDefect is null or e.isDefect='0')").append(" and (e.isForeign is null or e.isForeign='0')")
                    .append(" and e.servicestream='").append(aServiceStream).append("'").append(" and bill.status_id=3 and plan.id is null")
                    .append(" group by ksg.group_id, e.vidSluch_id, cast(date_part('month',e.finishDate)as int) , cast(date_part('year',e.finishDate)as int), e.medhelpprofile_id, cast('0'||e.departmentid as int), cast('0'||e.bedsubtype as int), vmp.code, vmp.name");
            sql.append(")");
            LOG.info("sql="+sql.toString());
            cleanAggregateTable(aType, calendar.getTime());
             ret += theManager.createNativeQuery(sql.toString()).executeUpdate();
             calendar.add(Calendar.MONTH,1);
        }
        LOG.info("Закончили формировать факты/планы");
        return new JSONObject().put("status","ok").put("count",ret).toString();

    }
    private void cleanAggregateTable(String aType, java.util.Date aMonthDate) {
        SimpleDateFormat mm = new SimpleDateFormat("MM");
        SimpleDateFormat yyyy= new SimpleDateFormat("yyyy");
        LOG.info("Очищаем сведения о факте-плане за "+aMonthDate+" месяц");
        theManager.createNativeQuery("delete from aggregatevolumesfinanceplan where type='"+aType+"' and  month="+mm.format(aMonthDate)+" and year="+yyyy.format(aMonthDate)).executeUpdate();
    }

    /** Клонируем запись*/
    private Object cloneEntity(Object aSourceObject, boolean needPersist) {
        try {
            Class aClass = aSourceObject.getClass();
            Method[] methodList = aClass.getMethods();
            Object newEntity = aClass.newInstance();
            for (Method setterMethod: methodList) {
                if (setterMethod.getName().startsWith("set")) {
                    if (setterMethod.getName().equals("setId") || setterMethod.isAnnotationPresent(OneToMany.class)) {
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
           LOG.error(e.getMessage(),e);
            return null;
        }

    }

    private @PersistenceContext
    EntityManager theManager;
    private @EJB
    ILocalMonitorService theMonitorService;

    private @EJB IExpert2Service expert2ServiceBean;
}
