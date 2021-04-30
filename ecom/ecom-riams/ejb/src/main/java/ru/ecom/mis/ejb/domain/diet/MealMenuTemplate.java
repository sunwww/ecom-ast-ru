package ru.ecom.mis.ejb.domain.diet;


import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class MealMenuTemplate extends MealMenu {

	/** День недели */
	@Comment("День недели")
	@OneToOne
	public VocWeekDay getWeekDay() {return weekDay;}

	/** Название дня недели */
	@Comment("Название дня недели")
	@Transient
	public String getWeekDayName() {
		return weekDay!=null?weekDay.getName():"";
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
		return childMenus;
	}

	/** Шаблон основного меню */
	@Comment("Шаблон основного меню")
	@ManyToOne
	public MealMenuTemplate getParentMenu() {
		return parentMenu;
	}

	/** Шаблон основного меню */
	private MealMenuTemplate parentMenu;
	/** Шаблон списка меню на день */
	private List<MealMenuTemplate> childMenus;
	
	/** День недели */
	private VocWeekDay weekDay;
	/** Дата окончания действия */
	private Date dateTo;
	
}	
