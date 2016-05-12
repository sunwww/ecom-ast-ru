package ru.ecom.mis.ejb.domain.birth.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник родовой деятельности (схватки, поясничные боли, отсутствует)
 * @author oegorova
 *
 */
@Comment ("Справочник родовой деятельности")
@Entity
@Table(schema="SQLUser")
public class VocPregnancyActivity extends VocBaseEntity{

}
