package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
/**
 * Справочник времени заболевания (получения травмы) до госпитализации:
 * 	в первые 6 часов, в теч. 1-24 часов, позднее 24 часов
 * */
@Entity
@Comment("Справочник времени заболевания (получения травмы) до госпитализации")
@Table(schema="SQLUser")
public class VocPreAdmissionTime extends VocBaseEntity{

}
