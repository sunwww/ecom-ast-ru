package ru.ecom.expert2.service;

import java.sql.Date;

public interface IFinanceService {
    String copyFinancePlanNextMonth (String aType, Date aMonthPlan, Date aStartMonth, Date aFinishMonth);
}
