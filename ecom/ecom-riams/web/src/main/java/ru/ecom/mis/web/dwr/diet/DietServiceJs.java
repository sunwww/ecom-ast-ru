package ru.ecom.mis.web.dwr.diet;

import ru.ecom.mis.ejb.service.diet.IDietService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.format.DateFormat;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.text.ParseException;

/**
 * Сервис для работы с диетой
 *
 * @author STkacheva
 */
public class DietServiceJs {
    public String getDescriptionMenu(Long aIdTemplateMenu, HttpServletRequest aRequest) throws NamingException {
        return Injection.find(aRequest).getService(IDietService.class).getDescriptionMenu(aIdTemplateMenu);
    }

    public String getDescriptionChildMenu(Long aIdTemplateMenu, HttpServletRequest aRequest) throws NamingException {
        return Injection.find(aRequest).getService(IDietService.class).getDescriptionChildMenu(aIdTemplateMenu);
    }

    public String saveMenu(Long aIdParent, Long aIdTemplate, Long aFlag, HttpServletRequest aRequest) throws NamingException {
        IDietService service = Injection.find(aRequest).getService(IDietService.class);
        if (aFlag == 1) {
            return savePrescriptExists(aIdTemplate, aIdParent, service);
        }
        return "";
    }

    public String saveMenuExist(Long aIdParent, Long aIdTemplate, HttpServletRequest aRequest) throws NamingException {
        IDietService service = Injection.find(aRequest).getService(IDietService.class);
        return savePrescriptExists(aIdTemplate, aIdParent, service);
    }

    public String saveExistsTemplateMenu(Long aIdParent, Long aIdTemplate, HttpServletRequest aRequest) throws NamingException {
        IDietService service = Injection.find(aRequest).getService(IDietService.class);
        return savePrescriptExists(aIdTemplate, aIdParent, service);
    }

    public String saveMenu(Long aIdParent, Long aIdTemplate, Long aFlag, String aDate, Long aOtd, Integer aPortionAmount, HttpServletRequest aRequest) throws NamingException {


        IDietService service = Injection.find(aRequest).getService(IDietService.class);
        if (aFlag == 1) return savePrescriptExists(aIdTemplate, aIdParent, service);
        if (aFlag == 2) {
            try {
				Date date = DateFormat.parseSqlDate(aDate);
                return saveMenuNew(aIdTemplate, date, aOtd, aPortionAmount, service);
            } catch (ParseException e) {
                return "Ошибка при создании меню-заказов. Неправильная дата: " + aDate;
            }
        }
        return "";

    }

    public String saveMenuInNewTemplate(Long aIdTemplateMenu, Long aIdDiet, Long aIdServiceStream
            , Long aIdWeekDay, String aDateFrom, String aDateTo, HttpServletRequest aRequest) throws NamingException {
        IDietService service = Injection.find(aRequest).getService(IDietService.class);
        try {
			Date dateTo = DateFormat.parseSqlDate(aDateTo);
            Date dateFrom = DateFormat.parseSqlDate(aDateFrom);
            service.saveInNewTemplateMenu(aIdTemplateMenu, aIdDiet, aIdServiceStream, aIdWeekDay, dateFrom, dateTo);
        } catch (ParseException e) {
            return "Ошибка при создании меню-заказов. Error: " + e.getMessage();
        }
        return "Создана копия шаблона";
    }

    private String savePrescriptExists(Long aIdTemplateMenu, Long aIdMenu, IDietService service) {
        String ret;
        try {
            if (service.saveInExistsMenu(aIdTemplateMenu, aIdMenu)) ret = "Сохранено в текущее меню";
            else ret = "Ошибка при сохранении  в текущее меню";

        } catch (Exception e) {
            ret = "Ошибка при сохранении  в текущий лист назначений" + e.getMessage();
        }
        return ret;
    }

    private String saveMenuNew(Long aIdTemplateMenu, Date aDate, Long aLpu, Integer aPortionAmount, IDietService service) {
        String ret;
        try {
            if (service.saveInNewMenu(aIdTemplateMenu, aDate, aLpu, aPortionAmount))
                ret = "Сохранено в новые меню-заказ";
            else ret = "Ошибка при сохранении  в новые меню-заказ";
        } catch (Exception e) {
            ret = "Ошибка при сохранении  в новые меню-заказ:" + e.getMessage();
        }
        return ret;
    }
}
