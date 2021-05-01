package ru.ecom.mis.ejb.domain.report.voc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.patient.voc.VocSex;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Тип параметра по типу отчетного показателя
 * @author azviagin
 *
 */

@Comment("Тип параметра по типу отчетного показателя")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = { @AIndex(properties = { "codeFrom","codeTo" })
	,@AIndex(properties = { "parameterType" })
	,@AIndex(properties = { "sex" })
})
@Getter
@Setter
public class ReportSetTypeParameterType extends VocBaseEntity{
	
	/** Тип параметра */
	@Comment("Тип параметра")
	@OneToOne
	public VocReportSetParameterType getParameterType() {
		return parameterType;
	}

	/** Пол */
	@Comment("Пол")
	@OneToOne
	public VocSex getSex() {
		return sex;
	}

	/** Пол */
	private VocSex sex;
	/** Пол код */
	private String sexCode;
	/** Последний параметр */
	private String codeTo;
	/** Начальный параметр */
	private String codeFrom;

	/** Тип параметра */
	private VocReportSetParameterType parameterType;
}
