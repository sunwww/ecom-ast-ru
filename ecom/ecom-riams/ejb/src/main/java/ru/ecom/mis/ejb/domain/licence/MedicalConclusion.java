package ru.ecom.mis.ejb.domain.licence;

import ru.ecom.mis.ejb.domain.licence.voc.VocDocumentProfession;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Comment("Медицинское заключение")
@Table(schema="SQLUser")
public class MedicalConclusion extends InternalDocuments {
	/** Профессия */
	@Comment("Профессия")
	@OneToOne
	public VocDocumentProfession getProfession() {return theProfession;}
	public void setProfession(VocDocumentProfession aProfession) {theProfession = aProfession;}

	/** Отделение */
	@Comment("Отделение")
	@OneToOne
	public MisLpu getDepartmentWork() {return theDepartmentWork;}
	public void setDepartmentWork(MisLpu aDepartmentWork) {theDepartmentWork = aDepartmentWork;}

	/** Отделение */
	private MisLpu theDepartmentWork;
	/** Профессия */
	private VocDocumentProfession theProfession;
}
