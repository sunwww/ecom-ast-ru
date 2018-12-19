package ru.ecom.expert2.service;

import java.sql.Date;

public interface IFinanceService {
    String fillAggregateTable(String aType, Date aStartDate, Date aFinishDate, String aServiceStream) ;
    String copyFinancePlanNextMonth (String aType, Date aMonthPlan, Date aStartMonth, Date aFinishMonth);
    void splitFinancePlan(String aType, String aYearPlan);
}
