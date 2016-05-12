package ru.ecom.mis.ejb.domain.licence;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Comment("Направление на микробиологическое исследование")
@Table(schema="SQLUser")
public class DirectionToMicrobiologAnalysis extends InternalDocuments {

}
