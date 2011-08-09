package ru.ecom.mis.ejb.domain.birth.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Справочник общих состояний для осмотра родильницы")
@Entity
@Table(schema="SQLUser")
public class VocInspectionCondition extends VocBaseEntity {

}
