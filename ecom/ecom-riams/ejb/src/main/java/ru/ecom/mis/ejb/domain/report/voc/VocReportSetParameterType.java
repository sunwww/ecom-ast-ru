package ru.ecom.mis.ejb.domain.report.voc;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.patient.voc.VocSex;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Тип параметра отчетного показателя
 * @author azviagin
 *
 */

@Comment("Тип параметра отчетного показателя")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = { @AIndex(properties = { "className","strCode" })
})
@Getter
@Setter
public class VocReportSetParameterType extends VocBaseEntity{
	
	/** Пол */
	@Comment("Пол")
	@OneToOne
	public VocSex getSex() {
		return sex;
	}

	/** Пол код */
	private String sexCode;

	/** Строка */
	private String strCode;
	/** Пол */
	private VocSex sex;

	/** Имя класса данных */
	private String className;

}
