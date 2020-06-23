package ru.ecom.mis.ejb.domain.medcase;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@Comment("Новый стат.номер по году")
public class StatisticStubNew extends StatisticStub {
	/**Информация (текст)*/
	@Transient
	@Comment("Информация")
	public String getInfo() {
		return "Следующий новый номер стат.карты " + getCode() + " за год " + getYear();
	}
}
