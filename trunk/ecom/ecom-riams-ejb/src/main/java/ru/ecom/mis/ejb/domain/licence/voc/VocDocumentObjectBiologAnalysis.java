package ru.ecom.mis.ejb.domain.licence.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Справочник целей микробиологических исследований")
@Entity
@Table(schema="SQLUser")
public class VocDocumentObjectBiologAnalysis extends VocBaseEntity{

}
