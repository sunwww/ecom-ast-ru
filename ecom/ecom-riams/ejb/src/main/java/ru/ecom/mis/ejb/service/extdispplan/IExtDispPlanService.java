package ru.ecom.mis.ejb.service.extdispplan;

public interface IExtDispPlanService {
    Boolean addPersonToPlan(Long aPlanId, Long aPersonId);
    int fillExtDispPlanByPersons(String aPersonJson, Long aPlanId);
}
