package ru.ecom.mis.ejb.domain.diet;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.diet.voc.VocMealTime;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * Меню блюд
 * @author azviagin
 *
 */

@Comment("Меню блюд")
@Entity
@Table(schema="SQLUser")
public abstract class MealMenu extends BaseEntity{
			
	/** Блюда */
	@Comment("Блюда")
	@OneToMany(mappedBy="menu", cascade=CascadeType.ALL)
	public List<DishMealMenu> getDishes() {return theDishes;}
	public void setDishes(List<DishMealMenu> aDishes) {theDishes = aDishes;}

	
	/** Дата начала действия */
	@Comment("Дата начала действия")
	public Date getDateFrom() {return theDateFrom;}
	public void setDateFrom(Date aDateFrom) {theDateFrom = aDateFrom;}
	
	/** Вид приема пищи */
	@Comment("Вид приема пищи")
	@OneToOne
	public VocMealTime getMealTime() {return theMealTime;}
	public void setMealTime(VocMealTime aMealTime) {theMealTime = aMealTime;}
	
	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@OneToOne
	public VocServiceStream getServiceStream() {return theServiceStream;}
	public void setServiceStream(VocServiceStream aServiceStream) {theServiceStream = aServiceStream;}

	
	/** ЛПУ */
	@Comment("ЛПУ")
	@OneToOne
	public MisLpu getLpu() {return theLpu;}
	public void setLpu(MisLpu aLpu) {theLpu = aLpu;	}

	
	/** Диета */
	@Comment("Диета")
	@ManyToOne
	public Diet getDiet() {return theDiet;}
	public void setDiet(Diet aDiet) {theDiet = aDiet;}

	//// Вычисляемые поля
	
	/** Название диеты */
	@Comment("Название диеты")
	@Transient
	public String getDietName() {
		return theDiet!=null?theDiet.getName():"";
	}
	
	/** Название потока обслуживания */
	@Comment("Название потока обслуживания")
	@Transient
	public String getServiceStreamName() {
		return theServiceStream!=null ? theServiceStream.getName() : "" ;
	}
	/** Название приема пищи */
	@Comment("Название приема пищи")
	@Transient
	public String getMealTimeName() {
		return theMealTime!=null? theMealTime.getName():"";
	}

		
	/** Список блюд */
	@Transient
	public String getListDishes() {
		StringBuilder ret = new StringBuilder() ;
		if (getDishes().size()>0) {
			ret.append("Блюда: ");
			for (DishMealMenu dish : getDishes() )  {
				ret.append(dish.getDishName()) ;
				ret.append("; ");
			}
			
		}
		String retst = ret.toString() ;
		if (retst.length()>6) {
			retst=retst.substring(0, retst.length()-2);
			retst = retst + ".";
		}
		
		return retst;
	}

	/** Блюда */
	private List<DishMealMenu> theDishes; 
	/** Дата начала действия */
	private Date theDateFrom;
	/** Вид приема пищи */
	private VocMealTime theMealTime;
	/** Поток обслуживания */
	private VocServiceStream theServiceStream;
	/** ЛПУ */
	private MisLpu theLpu;
	/** Диета */
	private Diet theDiet;
	

}
