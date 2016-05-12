package ru.ecom.mis.ejb.domain.licence.voc;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@AIndexes(value = { @AIndex(properties = { "documentParameter" })
,@AIndex(properties={"department"}) }
)
public class VocDocumentParameterConfig extends BaseEntity {
	/** Отделение */
	@Comment("Отделение")
	@OneToOne
	public MisLpu getDepartment() {return theDepartment;}
	public void setDepartment(MisLpu aDepartment) {theDepartment = aDepartment;}

	/** Параметр */
	@Comment("Параметр")
	@OneToOne
	public VocDocumentParameter getDocumentParameter() {return theDocumentParameter;}
	public void setDocumentParameter(VocDocumentParameter aDocumentParameter) {theDocumentParameter = aDocumentParameter;}

	/** Преобразовывать в нижний регистр */
	@Comment("Преобразовывать в нижний регистр")
	public Boolean getIsLowerCase() {return theIsLowerCase;}
	public void setIsLowerCase(Boolean aIsLowerCase) {theIsLowerCase = aIsLowerCase;}

	/** Преобразовывать в нижний регистр */
	private Boolean theIsLowerCase;
	/** Параметр */
	private VocDocumentParameter theDocumentParameter;
	/** Отделение */
	private MisLpu theDepartment;
}
