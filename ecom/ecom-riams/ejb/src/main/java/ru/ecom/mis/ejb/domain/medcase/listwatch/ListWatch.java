package ru.ecom.mis.ejb.domain.medcase.listwatch;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class ListWatch extends BaseEntity{
	
	/**Дата наблюдения */
	private Date dateWatch;
}