package ru.ecom.mis.ejb.domain.diet;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public abstract class MealMenu extends BaseEntity{
			
	/** Блюда */
	@Comment("Блюда")
	@OneToMany(mappedBy="menu", cascade=CascadeType.ALL)
	public List<DishMealMenu> getDishes() {return dishes;}

	/** Вид приема пищи */
	@Comment("Вид приема пищи")
	@OneToOne
	public VocMealTime getMealTime() {return mealTime;}

	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@OneToOne
	public VocServiceStream getServiceStream() {return serviceStream;}

	
	/** ЛПУ */
	@Comment("ЛПУ")
	@OneToOne
	public MisLpu getLpu() {return lpu;}

	
	/** Диета */
	@Comment("Диета")
	@ManyToOne
	public Diet getDiet() {return diet;}

	/** Название диеты */
	@Comment("Название диеты")
	@Transient
	public String getDietName() {
		return diet!=null?diet.getName():"";
	}
	
	/** Название потока обслуживания */
	@Comment("Название потока обслуживания")
	@Transient
	public String getServiceStreamName() {
		return serviceStream!=null ? serviceStream.getName() : "" ;
	}
	/** Название приема пищи */
	@Comment("Название приема пищи")
	@Transient
	public String getMealTimeName() {
		return mealTime!=null? mealTime.getName():"";
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
	private List<DishMealMenu> dishes; 
	/** Дата начала действия */
	private Date dateFrom;
	/** Вид приема пищи */
	private VocMealTime mealTime;
	/** Поток обслуживания */
	private VocServiceStream serviceStream;
	/** ЛПУ */
	private MisLpu lpu;
	/** Диета */
	private Diet diet;
	

}
