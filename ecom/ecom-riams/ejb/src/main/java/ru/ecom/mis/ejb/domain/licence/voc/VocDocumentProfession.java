package ru.ecom.mis.ejb.domain.licence.voc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.Table;

@Comment("Справочник профессий и производственных факторов")
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class VocDocumentProfession extends VocBaseEntity {
	/** Производственный фактор */
	private String factorOfProduction;
}