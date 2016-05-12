package ru.ecom.mis.ejb.domain.medcase;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Новый стат.номер по году")
@Table(schema="SQLUser")
public class StatisticStubNew extends StatisticStub {
	/**Информация (текст)*/
	@Transient
	@Comment("Информация")
	public String getInfo() {
		return new StringBuilder().append("Следующий новый номер стат.карты ").append(getCode()).append(" за год ").append(getYear()).toString() ;
	}
}
