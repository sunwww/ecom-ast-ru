package ru.ecom.mis.ejb.domain.licence.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Справочник микробиологических исследований")
@Entity
@Table(schema="SQLUser")
public class VocDocumentBiologAnalysis extends VocBaseEntity{

}
