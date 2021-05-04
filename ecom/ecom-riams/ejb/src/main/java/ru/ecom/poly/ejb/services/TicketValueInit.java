package ru.ecom.poly.ejb.services;

import ru.ecom.mis.ejb.domain.patient.MedPolicy;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.poly.ejb.domain.Medcard;
import ru.ecom.poly.ejb.domain.Ticket;
import ru.ecom.report.replace.IValueGetter;
import ru.ecom.report.replace.SetValueException;

import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: morgun
 * Date: 08.05.2007
 * Time: 10:06:53
 * To change this template use File | Settings | File Templates.
 */
public class TicketValueInit implements IValueGetter  {
    private final Ticket ticket;
    private final HashMap<String, String> map;

    public TicketValueInit(Ticket aTicket){
        ticket = aTicket;
        map = new HashMap<String, String>();
        BuildMap();
    }

    private void BuildMap(){

        Medcard mc = ticket.getMedcard();
        Patient prs = mc.getPerson();
        MedPolicy plc = null;

        // номер мед. карты
        map.put("MedCardNum",mc.getNumber());
        // льгота
        // СНИЛС
        map.put("SNILS", prs.getSnils());
        // ФИО
        map.put("FIO", prs.getLastname() + " " + prs.getFirstname() + " " + prs.getMiddlename());
        // Пол
        if (prs.getSex() != null)
            map.put("Sex", prs.getSex().getName());
        // Дата рождения
        if (prs.getBirthday() != null)
            map.put("Birthday", prs.getBirthday().toString());
        // Удостоверяние личности
        //theMap.put("PassportInfo", prs.getPassportInfo());
        // Адрес
        if (prs.getAddress() != null)
            map.put("Address", prs.getAddress().getAddressInfo());
        // социальный статус
        if (prs.getSocialStatus() != null)
            map.put("SocialStatus", prs.getSocialStatus().getName());
        // Инвалидность
        // Специалист
        if (ticket.getWorkFunction() != null)
            map.put("Doctor", ticket.getWorkFunction().getWorkFunctionInfo());
        // Специалист
        // Вид оплаты
        if (ticket.getVocPaymentType() != null)
            map.put("PaymentType", ticket.getVocPaymentType().getName());
        // Место обслуживания
        if (ticket.getVocServicePlace() != null)
            map.put("ServicePlace", ticket.getVocServicePlace().getName());
        // Цель посещения
        if (ticket.getVocReason() != null)
            map.put("Reason", ticket.getVocReason().getName());
        // Результат обращения
        if (ticket.getVocVisitResult() != null)
            map.put("VisitResult", ticket.getVocVisitResult().getName());
        // Диагноз по МКБ
        if (ticket.getIdc10() != null)
            map.put("Idc10", ticket.getIdc10().getCode() + " " + ticket.getIdc10().getName());
        // Характер заболевания
        if (ticket.getVocIllnesType() != null)
            map.put("IllnesType", ticket.getVocIllnesType().getName());
        // Диспансерный учет
        if (ticket.getDispRegistration() != null)
            map.put("DispanseryRegistration", ticket.getDispRegistration().getName());
        // Травма
        if (ticket.getVocTrauma() != null)
            map.put("Trauma", ticket.getVocTrauma().getName());
    }

    public Object getValue(String aExpression) throws SetValueException {
        return map.get(aExpression);

    }

    public void set(String aKey, Object aValue) throws SetValueException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
