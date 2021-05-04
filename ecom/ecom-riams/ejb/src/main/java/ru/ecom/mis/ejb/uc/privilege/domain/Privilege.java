package ru.ecom.mis.ejb.uc.privilege.domain;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.licence.Document;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.sql.Date;

/**
 * Льгота
 */
@Entity
@Comment("Льгота")
@Table(schema="SQLUser")
@AIndexes(
		@AIndex(properties={"person"})
)
@Getter
@Setter
public class Privilege extends BaseEntity {
	private Patient person;
	private VocPrivilegeCategory category;
	private String serialDoc;
	private String numberDoc;
	private Boolean isDelete;
	private Date endDate;
	private Date beginDate;
	private VocPrivilegeCode privilegeCode;
	private Document document;
	private String takeover;

	@OneToOne
	public VocPrivilegeCode getPrivilegeCode() {
		return privilegeCode;
	}
	@OneToOne
	public Document getDocument() {
		return document;
	}

    @Comment("Категория льготников")
	@OneToOne
	public VocPrivilegeCategory getCategory() {return category;}

	@Comment("Персона")
	@OneToOne
	public Patient getPerson() {return person;}
}
