package ru.ecom.mis.ejb.domain.disability;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Медико-социальная экспертная комиссия")
@Entity
@AIndexes({
	@AIndex(properties= {"lineR"})
	,@AIndex(properties= {"caseR"})
})
@Table(schema="SQLUser")
@Getter
@Setter
public class DisabilityReport extends BaseEntity {
	/** Случай нетрудоспособности */
	private Long caseR;
	/** Строка */
	private Long lineR;
	/** Дата окончания */
	private Date finishDate;
}
