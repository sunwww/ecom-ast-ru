package ru.ecom.mis.ejb.domain.licence;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.mis.ejb.domain.licence.voc.VocDocumentProfession;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Comment("Медицинское заключение")
@Getter
@Setter
public class MedicalConclusion extends InternalDocuments {
	/** Профессия */
	@Comment("Профессия")
	@OneToOne
	public VocDocumentProfession getProfession() {return profession;}

	/** Отделение */
	@Comment("Отделение")
	@OneToOne
	public MisLpu getDepartmentWork() {return departmentWork;}

	/** Отделение */
	private MisLpu departmentWork;
	/** Профессия */
	private VocDocumentProfession profession;
}
