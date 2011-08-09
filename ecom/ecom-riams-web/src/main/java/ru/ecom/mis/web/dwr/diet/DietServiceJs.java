package ru.ecom.mis.web.dwr.diet;

import java.sql.Date;
import java.text.ParseException;

import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;

import ru.ecom.mis.ejb.service.diet.IDietService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.format.DateFormat;

/**
 * Сервис для работы с диетой
 * @author STkacheva
 */
public class DietServiceJs {
	public String getDescriptionMenu(Long aIdTemplateMenu, HttpServletRequest aRequest) throws NamingException {
		IDietService service = Injection.find(aRequest).getService(IDietService.class) ;
		System.out.println("Получить описание шаблона: "+aIdTemplateMenu);
		return service.getDescriptionMenu(aIdTemplateMenu) ;
	}
	public String getDescriptionChildMenu(Long aIdTemplateMenu, HttpServletRequest aRequest) throws NamingException {
		IDietService service = Injection.find(aRequest).getService(IDietService.class) ;
		System.out.println("Получить описание шаблона: "+aIdTemplateMenu);
		return service.getDescriptionChildMenu(aIdTemplateMenu) ;
	}
	
	public String saveMenu(Long aIdParent,Long aIdTemplate, Long aFlag, HttpServletRequest aRequest) throws NamingException {
//		Long id = Long.valueOf(aIdParent);
//		Long flag = Long.valueOf(aFlag);
		System.out.println("Родитель: "+aIdParent);
		System.out.println("Шаблон: "+aIdTemplate);
		System.out.println("Флаг: "+aFlag);
		
		
		IDietService service = Injection.find(aRequest).getService(IDietService.class) ;
		if (aFlag==1) {
			return savePrescriptExists(aIdTemplate,aIdParent, service) ;
		}
		if (aFlag==2) {
			
			Date date;
			//try {
				//date = DateFormat.parseSqlDate(aDate);
				//return saveMenuNew(aIdTemplate,date,aOtd,aPortionAmount, service) ;
			//} catch (ParseException e) {
				//return "Ошибка при создании меню-заказов. Неправильная дата: "+aDate ;
				
			//}
		}
		if (aFlag==3) return saveMenuTempNew(aIdTemplate, service) ;
		return "" ;
		
	}
	
	public String saveMenuExist(Long aIdParent,Long aIdTemplate, HttpServletRequest aRequest) throws NamingException {
//		Long id = Long.valueOf(aIdParent);
//		Long flag = Long.valueOf(aFlag);
		System.out.println("Родитель: "+aIdParent);
		System.out.println("Шаблон: "+aIdTemplate);
		
		
		IDietService service = Injection.find(aRequest).getService(IDietService.class) ;
		return savePrescriptExists(aIdTemplate,aIdParent, service) ;
	}
	public String saveExistsTemplateMenu(Long aIdParent,Long aIdTemplate, HttpServletRequest aRequest) throws NamingException {
		System.out.println("Родитель: "+aIdParent);
		System.out.println("Шаблон: "+aIdTemplate);
		IDietService service = Injection.find(aRequest).getService(IDietService.class) ;
		return savePrescriptExists(aIdTemplate,aIdParent, service) ;
	}
	public String saveMenu(Long aIdParent,Long aIdTemplate, Long aFlag, String aDate,  Long aOtd, Integer aPortionAmount,HttpServletRequest aRequest) throws NamingException {
//		Long id = Long.valueOf(aIdParent);
//		Long flag = Long.valueOf(aFlag);
		System.out.println("Родитель: "+aIdParent);
		System.out.println("Шаблон: "+aIdTemplate);
		System.out.println("Флаг: "+aFlag);
		
		
		IDietService service = Injection.find(aRequest).getService(IDietService.class) ;
		if (aFlag==1) return savePrescriptExists(aIdTemplate,aIdParent, service) ;
		if (aFlag==2) {
			
			Date date;
			try {
				date = DateFormat.parseSqlDate(aDate);
				return saveMenuNew(aIdTemplate,date,aOtd,aPortionAmount, service) ;
			} catch (ParseException e) {
				return "Ошибка при создании меню-заказов. Неправильная дата: "+aDate ;
				
			}
		}
		if (aFlag==3) return saveMenuTempNew(aIdTemplate, service) ;
		return "" ;
		
	}
	public String saveMenuInNewTemplate(Long aIdTemplateMenu, Long aIdDiet, Long aIdServiceStream
			,Long aIdWeekDay, String aDateFrom, String aDateTo, HttpServletRequest aRequest) throws NamingException {
		IDietService service = Injection.find(aRequest).getService(IDietService.class) ;
		Date dateTo;
		System.out.println("Копирование шаблона...");
		try {
			dateTo = DateFormat.parseSqlDate(aDateTo);
			Date dateFrom = DateFormat.parseSqlDate(aDateFrom);
			service.saveInNewTemplateMenu(aIdTemplateMenu, aIdDiet, aIdServiceStream, aIdWeekDay, dateFrom, dateTo);
		} catch (ParseException e) {
			return "Ошибка при создании меню-заказов. Error: "+e.getMessage() ;
		}
		return "Создана копия шаблона";
		
	}
	private String savePrescriptExists(Long aIdTemplateMenu, Long aIdMenu, IDietService service) {
		String ret ="";
		try {
			if (service.saveInExistsMenu(aIdTemplateMenu, aIdMenu)) ret ="Сохранено в текущее меню" ;
			else ret = "Ошибка при сохранении  в текущее меню" ;

		} catch (Exception e) {
			ret = "Ошибка при сохранении  в текущий лист назначений"+e.getMessage() ;
		}
		System.out.println(ret) ;		
		return ret ;
	}
	
	private String saveMenuNew(Long aIdTemplateMenu,Date aDate, Long aLpu, Integer aPortionAmount, IDietService service) {
		String ret ="";
		try {
			if (service.saveInNewMenu(aIdTemplateMenu,aDate,aLpu,aPortionAmount)) ret ="Сохранено в новые меню-заказ" ;
			else ret = "Ошибка при сохранении  в новые меню-заказ" ;
		} catch (Exception e) {
			ret = "Ошибка при сохранении  в новые меню-заказ:"+e.getMessage() ;
		}
		return ret;	
	}
	private String saveMenuTempNew(Long aIdTemplateMenu, IDietService service) {
		String ret ="";
		try {
//			if (service.saveInNewTemplateMenu(aIdTemplateMenu)) ret ="Сохранено в новый шаблон меню-заказа" ;
//			else ret = "Ошибка при сохранении  в новый шаблон меню-заказа" ;
		} catch (Exception e) {
			ret = "Ошибка при сохранении  в новый шаблон меню-заказа:"+e.getMessage() ;
		}
		return ret;	
		}
}
