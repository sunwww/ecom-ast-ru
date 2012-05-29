package ru.ecom.poly.ejb.services;

import java.util.HashMap;

import ru.ecom.mis.ejb.domain.patient.MedPolicy;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.poly.ejb.domain.Medcard;
import ru.ecom.poly.ejb.domain.Ticket;
import ru.ecom.report.replace.IValueGetter;
import ru.ecom.report.replace.SetValueException;

/**
 * Created by IntelliJ IDEA.
 * User: morgun
 * Date: 08.05.2007
 * Time: 10:06:53
 * To change this template use File | Settings | File Templates.
 */
public class TicketValueInit implements IValueGetter  {
    private Ticket theTicket;
    private HashMap<String, String> theMap;

    public TicketValueInit(Ticket aTicket){
        theTicket = aTicket;
        theMap = new HashMap<String, String>();
        BuildMap();
    }

    private void BuildMap(){

        Medcard mc = theTicket.getMedcard();
        Patient prs = mc.getPerson();
        MedPolicy plc = null;
        if (prs.getMedPolicies().size() > 0)
            plc = prs.getMedPolicies().get(0);

        // номер мед. карты
        theMap.put("MedCardNum",mc.getNumber());
        // льгота
        // Номер полиса ОМС
        if (plc != null)
            theMap.put("OMCCode", plc.getSeries() + plc.getPolNumber());
        // СНИЛС
        theMap.put("SNILS", prs.getSnils());
        // ФИО
        theMap.put("FIO", prs.getLastname() + " " + prs.getFirstname() + " " + prs.getMiddlename());
        // Пол
        if (prs.getSex() != null)
            theMap.put("Sex", prs.getSex().getName());
        // Дата рождения
        if (prs.getBirthday() != null)
            theMap.put("Birthday", prs.getBirthday().toString());
        // Удостоверяние личности
        //theMap.put("PassportInfo", prs.getPassportInfo());
        // Адрес
        if (prs.getAddress() != null)
            theMap.put("Address", prs.getAddress().getAddressInfo());
        // социальный статус
        if (prs.getSocialStatus() != null)
            theMap.put("SocialStatus", prs.getSocialStatus().getName());
        // Инвалидность
        // Специалист
        if (theTicket.getWorkFunction() != null)
            theMap.put("Doctor", theTicket.getWorkFunction().getWorkFunctionInfo());
        // Специалист
        // Вид оплаты
        if (theTicket.getVocPaymentType() != null)
            theMap.put("PaymentType", theTicket.getVocPaymentType().getName());
        // Место обслуживания
        if (theTicket.getVocServicePlace() != null)
            theMap.put("ServicePlace", theTicket.getVocServicePlace().getName());
        // Цель посещения
        if (theTicket.getVocReason() != null)
            theMap.put("Reason", theTicket.getVocReason().getName());
        // Результат обращения
        if (theTicket.getVocVisitResult() != null)
            theMap.put("VisitResult", theTicket.getVocVisitResult().getName());
        // Диагноз по МКБ
        if (theTicket.getIdc10() != null)
            theMap.put("Idc10", theTicket.getIdc10().getCode() + " " + theTicket.getIdc10().getName());
        // Характер заболевания
        if (theTicket.getVocIllnesType() != null)
            theMap.put("IllnesType", theTicket.getVocIllnesType().getName());
        // Диспансерный учет
        if (theTicket.getDispRegistration() != null)
            theMap.put("DispanseryRegistration", theTicket.getDispRegistration().getName());
        // Травма
        if (theTicket.getVocTrauma() != null)
            theMap.put("Trauma", theTicket.getVocTrauma().getName());
    }

    public Object getValue(String aExpression) throws SetValueException {
        return theMap.get(aExpression);

    }

    public void set(String aKey, Object aValue) throws SetValueException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
