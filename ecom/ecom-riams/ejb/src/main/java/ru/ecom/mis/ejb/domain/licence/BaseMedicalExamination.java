package ru.ecom.mis.ejb.domain.licence;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.mis.ejb.domain.licence.voc.VocDocumentProfession;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Comment("Профосмотр")
@Getter
@Setter
public class BaseMedicalExamination extends InternalDocuments {
	/** Профессия */
	@Comment("Профессия")
	@OneToOne
	public VocDocumentProfession getProfession() {return profession;}
	private VocDocumentProfession profession;

	/** Отделение */
	@Comment("Отделение")
	@OneToOne
	public MisLpu getDepartmentWork() {return departmentWork;}
	private MisLpu departmentWork;



}
