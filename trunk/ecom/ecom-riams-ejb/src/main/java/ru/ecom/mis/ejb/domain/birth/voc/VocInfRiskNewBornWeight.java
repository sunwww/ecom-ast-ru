package ru.ecom.mis.ejb.domain.birth.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Справочник оценки  массы тела ребенка при оценке риска бактериальной инфекции
 * @author oegorova
 *
 */

@Comment("Справочник оценки  массы тела ребенка при оценке риска бактериальной инфекции")
@Entity
@Table(schema="SQLUser")
public class VocInfRiskNewBornWeight  extends VocBall {

}
