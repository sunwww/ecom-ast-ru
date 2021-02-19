package ru.ecom.mis.web.dwr.dispensary;

import org.json.JSONObject;
import ru.ecom.mis.ejb.service.patient.IPatientService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.format.DateFormat;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;

public class DispensaryServiceJs {
    public String exportDispendaryCards(String aJson, HttpServletRequest aRequst) throws NamingException, ParseException {
        Date dateFrom, dateTo, dateChangedFrom;
        JSONObject object = new JSONObject(aJson);

        dateFrom = DateFormat.parseDate(object.getString("startDate"));
        dateTo = DateFormat.parseDate(object.getString("finishDate"));
        dateChangedFrom = DateFormat.parseDate(object.getString("changedDateFrom"));
        String packetNumber = object.getString("packetNumber");

        return Injection.find(aRequst).getService(IPatientService.class).exportDispensaryCard(dateFrom, dateTo, dateChangedFrom, packetNumber);
    }
}
