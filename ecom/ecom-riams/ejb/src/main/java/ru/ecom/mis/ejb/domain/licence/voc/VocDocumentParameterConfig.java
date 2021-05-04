package ru.ecom.mis.ejb.domain.licence.voc;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@AIndexes(value = { @AIndex(properties = { "documentParameter" })
,@AIndex(properties={"department"}) }
)
@Getter
@Setter
public class VocDocumentParameterConfig extends BaseEntity {
	/** Отделение */
	@Comment("Отделение")
	@OneToOne
	public MisLpu getDepartment() {return department;}

	/** Параметр */
	@Comment("Параметр")
	@OneToOne
	public VocDocumentParameter getDocumentParameter() {return documentParameter;}

	/** Преобразовывать в нижний регистр */
	private Boolean isLowerCase;
	/** Параметр */
	private VocDocumentParameter documentParameter;
	/** Отделение */
	private MisLpu department;
}
