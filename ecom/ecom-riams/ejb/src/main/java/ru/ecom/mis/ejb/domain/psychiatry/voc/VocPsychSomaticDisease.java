package ru.ecom.mis.ejb.domain.psychiatry.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Справочник соматических заболеваний для блока суицид")
@Entity
@Table(schema="SQLUser")
public class VocPsychSomaticDisease extends VocBaseEntity {

}
