package ru.ecom.mis.ejb.domain.medcase.listwatch;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.sql.Date;

/**
 * Лист наблюдений
 */

@Comment("Лист наблюдений")
@Entity 
@Table(schema="SQLUser")
public class ListWatch extends BaseEntity{
	
	/**Дата наблюдения */
	@Comment("Дата наблюдения")
	public Date getDateWatch() {return theDateWatch;}
	public void setDateWatch(Date aDateWatch) {theDateWatch = aDateWatch;}
	
	/**Дата наблюдения */
	private Date theDateWatch;
}