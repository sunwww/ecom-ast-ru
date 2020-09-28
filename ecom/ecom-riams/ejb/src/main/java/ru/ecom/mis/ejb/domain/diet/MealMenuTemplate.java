package ru.ecom.mis.ejb.domain.diet;


import ru.ecom.mis.ejb.domain.workcalendar.voc.VocWeekDay;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * Шаблон меню приема пищи
 * @author azviagin
 *
 */

@Comment("Шаблон меню приема пищи")
@Entity
public class MealMenuTemplate extends MealMenu {

	/** Дата окончания действия */
	@Comment("Дата окончания действия")
	public Date getDateTo() {return theDateTo;}
	public void setDateTo(Date aDateTo) {theDateTo = aDateTo;}
	
	/** День недели */
	@Comment("День недели")
	@OneToOne
	public VocWeekDay getWeekDay() {return theWeekDay;}
	public void setWeekDay(VocWeekDay aWeekDay) {theWeekDay = aWeekDay;}
	
	
	/////// Вычисляемые поля
	
	
	/** Название дня недели */
	@Comment("Название дня недели")
	@Transient
	public String getWeekDayName() {
		return theWeekDay!=null?theWeekDay.getName():"";
	}
	
	/** Описание */
	@Comment("Описание")
	@Transient
	public String getDescription() {
		StringBuilder ret = new StringBuilder() ;
		if (getParentMenu()==null)  {
		if (getWeekDay()!=null) ret.append(getWeekDay().getName()) ;
			ret.append(" ");
			ret.append(getDateFrom()) ;
			ret.append("-") ;
			ret.append(getDateTo()) ;
		} else {
			if (getMealTime()!=null) ret.append(getMealTime().getName());
		}
		return ret.toString() ;
	}

	
	/** Шаблон списка меню на день */
	@Comment("Шаблон списка меню на день")
	@OneToMany(mappedBy="parentMenu", cascade=CascadeType.ALL)
	public List<MealMenuTemplate> getChildMenus() {
		return theChildMenus;
	}

	public void setChildMenus(List<MealMenuTemplate> aChildMenu) {
		theChildMenus = aChildMenu;
	}

	/** Шаблон основного меню */
	@Comment("Шаблон основного меню")
	@ManyToOne
	public MealMenuTemplate getParentMenu() {
		return theParentMenu;
	}

	public void setParentMenu(MealMenuTemplate aParentMenu) {
		theParentMenu = aParentMenu;
	}

	/** Шаблон основного меню */
	private MealMenuTemplate theParentMenu;
	/** Шаблон списка меню на день */
	private List<MealMenuTemplate> theChildMenus;
	
	/** День недели */
	private VocWeekDay theWeekDay;
	/** Дата окончания действия */
	private Date theDateTo;
	
}	
