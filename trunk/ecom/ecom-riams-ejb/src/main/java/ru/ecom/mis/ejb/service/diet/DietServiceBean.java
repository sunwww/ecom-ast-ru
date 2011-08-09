package ru.ecom.mis.ejb.service.diet;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.EJB;
import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ru.ecom.ejb.services.entityform.ILocalEntityFormService;
//import ru.ecom.mis.ejb.domain.diet.ChildMealMenuTemplate;
import ru.ecom.mis.ejb.domain.diet.Diet;
import ru.ecom.mis.ejb.domain.diet.DishMealMenu;
import ru.ecom.mis.ejb.domain.diet.DishMealMenuOrder;
import ru.ecom.mis.ejb.domain.diet.DishMealMenuTemplate;
import ru.ecom.mis.ejb.domain.diet.MealMenu;
import ru.ecom.mis.ejb.domain.diet.MealMenuOrder;
import ru.ecom.mis.ejb.domain.diet.MealMenuTemplate;
import ru.ecom.mis.ejb.domain.diet.voc.VocMealTime;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocWeekDay;
import ru.nuzmsh.util.format.DateFormat;

/**
 * Сервис для работы с диетой
 * @author STkacheva
 *
 */
@Stateless
@Remote(IDietService.class)
public class DietServiceBean implements IDietService {

	public String getDescriptionMenu(Long aIdTemplateMenu) {
		MealMenuTemplate temp = theManager.find(MealMenuTemplate.class, aIdTemplateMenu) ;
		StringBuffer desc = new StringBuffer() ;
		desc.append("Описание меню-раскладки №") ;
		desc.append(aIdTemplateMenu) ;
		desc.append('\n');
		desc.append("Дата действия: с ");
		desc.append(getDateFormat(temp.getDateFrom())) ;
		desc.append(" по ");
		desc.append(getDateFormat(temp.getDateTo())) ;
		desc.append('\n');
		desc.append("Диета: ") ;
		desc.append(temp.getDietName()) ;
		desc.append('\n');
		desc.append("День недели: ") ;
		desc.append(temp.getWeekDayName()) ;
		desc.append('\n');
		desc.append('\n');
		desc.append("Поток обслуживания: ") ;
		desc.append(temp.getServiceStreamName()) ;
		desc.append('\n');
		
		for (MealMenuTemplate child:temp.getChildMenus()) {
			desc.append("---------------------------------");
			desc.append("Время приема пищи: ") ;
			if(child.getMealTime()!=null) desc.append(child.getMealTime().getName()) ;
			desc.append('\n');
			desc.append("-------Блюда--------");
			desc.append('\n');
			desc.append(getDescriptDishMenu(child)) ;
		}
		return desc.toString() ;
	}
	
	public String getDescriptionChildMenu(Long aIdTemplateMenu) {
		MealMenuTemplate temp = theManager.find(MealMenuTemplate.class, aIdTemplateMenu) ;
		MealMenuTemplate parent = temp.getParentMenu() ;
		StringBuffer desc = new StringBuffer() ;
		desc.append("Описание меню №") ;
		desc.append(aIdTemplateMenu) ;
		desc.append('\n');
		desc.append("Диета: ") ;
		desc.append(temp.getDietName()) ;
		desc.append('\n');
		desc.append("День недели: ") ;
		desc.append(temp.getWeekDayName()) ;
		desc.append('\n');
		if (parent!=null) {
			desc.append("Дата действия: с ");
			desc.append(getDateFormat(parent.getDateFrom())) ;
			desc.append(" по ");
			desc.append(getDateFormat(parent.getDateTo())) ;
			desc.append('\n');
			desc.append("Поток обслуживания: ") ;
			desc.append(parent.getServiceStreamName()) ;
			desc.append('\n');		
		}
		desc.append('\n');
		desc.append('\n');
		desc.append("-------Блюда--------");
		desc.append('\n');
		desc.append(getDescriptDishMenu(temp)) ;
		return desc.toString() ;
	}

	public boolean saveInExistsMenu(Long aIdTemplateMenu, Long aIdParent) {
		System.out.println("tempMenu="+aIdTemplateMenu) ;
		System.out.println("curMenu="+aIdParent) ;
		if (aIdTemplateMenu.equals(aIdParent)) throw new IllegalArgumentException("Невозможно добавить блюда. Шаблон и текущее меню должны быть разными!!!");
		MealMenuTemplate temp = theManager.find(MealMenuTemplate.class, aIdTemplateMenu) ;
		MealMenu menu = theManager.find(MealMenu.class, aIdParent) ;
		if (menu instanceof MealMenuTemplate) {
			System.out.print("Copy to template") ;
			for (MealMenuTemplate childMenu: temp.getChildMenus()) {
				MealMenuTemplate newtemp = new MealMenuTemplate() ;
				newtemp.setMealTime(childMenu.getMealTime());
				newtemp.setParentMenu((MealMenuTemplate)menu);
				theManager.persist(newtemp);
				addDishInMenu(childMenu, newtemp);
			}
		}
		if (menu instanceof MealMenu) {
			addDishInMenu(temp, menu) ;
		}
		return true;
	}

	public boolean saveInNewMenu(Long aIdTemplateMenu
			, Date aDateFrom, Long aLpu
			, Integer aPortionAmount) {
		MealMenuTemplate temp = theManager.find(MealMenuTemplate.class, aIdTemplateMenu) ;
		MisLpu lpu = theManager.find(MisLpu.class, aLpu) ;
		//Date date = getDateFormat(aDateFrom);
		if (aDateFrom!=null) {
			//Diet diet = theManager.find(Diet.class, aIdParent) ;
			if (temp!=null && temp.getChildMenus()!=null && temp.getChildMenus().size()>0) {
				for (MealMenuTemplate childMenu:temp.getChildMenus()) {
					MealMenuOrder order = new MealMenuOrder() ;
					order.setDiet(temp.getDiet()) ;
					order.setServiceStream(temp.getServiceStream()) ;
					order.setMealTime(childMenu.getMealTime()) ;
					order.setPortionAmount(aPortionAmount) ;
					order.setLpu(lpu);
					order.setDateFrom(aDateFrom);
					theManager.persist(order) ;
					addDishInMenu(childMenu, order) ;
				}
			}
			return true ;
		} else {
			return false ;
		}
		
	} 

	public boolean saveInNewTemplateMenu(Long aIdTemplateMenu
			,Long aIdDiet, Long aIdServiceStream
			,Long aIdWeekDay, Date aDateFrom, Date aDateTo) {
		System.out.println("копирование данных шаблона");
		//MealMenuTemplate temp = theManager.find(MealMenuTemplate.class, aIdTemplateMenu) ;
		Diet diet = theManager.find(Diet.class, aIdDiet);
		VocWeekDay weekDay = theManager.find(VocWeekDay.class, aIdWeekDay);
		VocServiceStream serviceStream = theManager.find(VocServiceStream.class, aIdServiceStream);
		MealMenuTemplate newtemp = new MealMenuTemplate();
		newtemp.setDiet(diet);
		newtemp.setWeekDay(weekDay);
		newtemp.setDateFrom(aDateFrom);
		newtemp.setDateTo(aDateTo);
		newtemp.setServiceStream(serviceStream);
		theManager.persist(newtemp);
		return saveInExistsMenu(aIdTemplateMenu, newtemp.getId()); 
	}
	
	/**
	 * Добавать блюда из меню шаблона aMenuTemplate в меню aMenu
	 * @param aMenuTemplate - меню шаблон
	 * @param aMenu - меню, в которое необходимо произвести копирование
	 */
	private void addDishInMenu(MealMenuTemplate aMenuTemplate, MealMenu aMenu) {
		if (aMenuTemplate!=null &&aMenu!=null) {
			List<DishMealMenu> list = aMenu.getDishes() ;
			if (list==null) list = new ArrayList<DishMealMenu>() ;
			for (DishMealMenu dishMenu:aMenuTemplate.getDishes()) {
				System.out.println(new StringBuffer().append("Блюдо меню:").append(dishMenu.getId()).toString());
//				DishMealMenu newDishMenu = newDishMenuOnTemplate(dishMenu, aMenu);
				DishMealMenu newDishMenu = new DishMealMenu() ;
				newDishMenu.setDish(dishMenu.getDish()) ;
				newDishMenu.setMenu(aMenu) ;
				list.add(newDishMenu) ;
				theManager.flush() ;
			}
			aMenu.setDishes(list) ;
			theManager.persist(aMenu) ;
		}
	}
	private DishMealMenu newDishMenuOnTemplate(DishMealMenu aOldDishMenu, MealMenu aMenu) {
		DishMealMenu newDishMenu = null ;
		if (aMenu instanceof MealMenuTemplate) {
			newDishMenu = new DishMealMenuTemplate() ;
		} 
		if (aMenu instanceof MealMenuOrder){
			newDishMenu = new DishMealMenuOrder() ;
		}
		if (newDishMenu!=null) {
			newDishMenu.setDish(aOldDishMenu.getDish()) ; 
			newDishMenu.setMenu(aMenu) ;
		}
		return newDishMenu ;
	}
	
	private String getDateFormat(Date aDate) {
		String ret ="___";
		if (aDate!=null) {
			ret = DateFormat.formatToDate(aDate) ;
		}
		return ret ;
	}
	
	private Date getDateFormat(String aDate) {
		Date date = null ;
		if (aDate!=null && !aDate.equals("")) {
			try {
				date = DateFormat.parseSqlDate(aDate);
			} catch (ParseException e) {
				date = null ;
			}
		}
		return date ;
	}
	
	/**
	 * Получить описание блюд шаблона меню
	 * @param aTemp - Шаблон меню
	 * @return описание
	 */
	private String getDescriptDishMenu(MealMenuTemplate aTemp) {
		StringBuffer ret = new StringBuffer();
		if (aTemp.getDishes()!=null) {
			int i = 0 ;
			for(DishMealMenu dish:aTemp.getDishes()) {
				ret.append("    ") ;
				ret.append(++i) ;
				ret.append(dish.getDishName()) ;
				ret.append(";") ;
				ret.append('\n') ;
			}
		}
		return ret.toString() ;
	}
	
	@EJB ILocalEntityFormService 
	theEntityFormService ;
    @PersistenceContext
    EntityManager theManager ;
    @Resource
	SessionContext theContext ;
}
