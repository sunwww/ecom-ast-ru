package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity 
@Table(schema="SQLUser")
@Comment("Справочник использования высоких медицинских технологий: 1-да 2-нет")
public class VocOperationTechnology extends VocBaseEntity{

}
