package ru.ecom.mis.ejb.domain.medcase;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.mis.ejb.domain.medcase.voc.VocPreparatorBlood;
import ru.ecom.mis.ejb.domain.medcase.voc.VocTransfusionDefinitionRhesus;
import ru.ecom.mis.ejb.domain.medcase.voc.VocTransfusionMethod;
import ru.ecom.mis.ejb.domain.medcase.voc.VocTransfusionPregnancyHang;
import ru.ecom.mis.ejb.domain.medcase.voc.VocTransfusionReaction;
import ru.ecom.mis.ejb.domain.medcase.voc.VocTransfusionReason;
import ru.ecom.mis.ejb.domain.medcase.voc.VocTransfusionResearchAntibodies;
import ru.ecom.mis.ejb.domain.patient.voc.VocYesNo;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Переливание трансфузионных сред
 * @author azviagin
 *
 */

@Comment("Переливание трансфузионных сред")
@Entity
@AIndexes({
	@AIndex(properties="medCase")
    })
@Table(schema="SQLUser")
@EntityListeners(DeleteListener.class)
@Getter
@Setter
public abstract class Transfusion extends BaseEntity{

	/** СМО */
	@Comment("СМО")
	@OneToOne
	public MedCase getMedCase() {return medCase;}

	/** Показания к применению */
	@Comment("Показания к применению")
	@OneToOne
	public VocTransfusionReason getReason() {return reason;}

	/** Изготовитель */
	@Comment("Изготовитель")
	@OneToOne
	public VocPreparatorBlood getPreparator() {return preparator;}

	/** Способ переливания */
	@Comment("Способ переливания")
	@OneToOne
	public VocTransfusionMethod getTransfusionMethod() {return transfusionMethod;}

	/** Исполнитель */
	@Comment("Исполнитель")
	@OneToOne
	public WorkFunction getExecutor() {return executor;}

	/** Трансфузионная реакция */
	@Comment("Трансфузионная реакция")
	@OneToOne
	public VocTransfusionReaction getTransfusionReaction() {return transfusionReaction;}

	/** Дата начала */
	private Date startDate;
	/** Первичное */
	private Boolean primaryCase;
	/** СМО */
	private MedCase medCase;
	/** Показания к применению */
	private VocTransfusionReason reason;
	/** Доза (мл) */
	private Integer doze;
	/** Серия */
	private String series;
	/** Дата приготовления */
	private Date preparationDate;
	/** Изготовитель */
	private VocPreparatorBlood preparator;
	/** Способ переливания */
	private VocTransfusionMethod transfusionMethod;
	/** Исполнитель */
	private WorkFunction executor;
	/** Трансфузионная реакция */
	private VocTransfusionReaction transfusionReaction;
	/** Осложнения после переливания */
	private String complications;
	/** Номер в журнале */
	private Integer journalNumber;
	
	/** Время переливания по */
	private Time timeTo;
	/** Время переливание с */
	private Time timeFrom;
	/** Фенотип E */
	private Boolean phenotypee1;	
	/** Фенотип e */
	private Boolean phenotypeE;
	/** Фенотип Е */
	private Boolean phenotypeD;
	/** Фенотип с */
	private Boolean phenotypec1;
	/** Фенотип C */
	private Boolean phenotypeC;
	/** Фенотип не определялся*/
	private Boolean phenotypeNone;
	/** Фенотип */
	private String phenotype;
	

	/** Исследования антител */
	@Comment("Исследования антител")
	@OneToOne
	public VocTransfusionResearchAntibodies getResearchAntibodies() {return researchAntibodies;}

	/** Трансфузионный анамнез */
	@Comment("Трансфузионный анамнез")
	@OneToOne
	public VocYesNo getTransfusionHistory() {return transfusionHistory;}

	
	/** Трансфузионный по индив. подбору в прошлом */
	@Comment("Трансфузионный по индив. подбору в прошлом")
	@OneToOne
	public VocYesNo getPersonalTransfusionHistory() {return personalTransfusionHistory;}

	/** Особенности беременности */
	@Comment("Особенности беременности")
	@OneToOne
	public VocTransfusionPregnancyHang getPregnancyHang() {return pregnancyHang;}

	/** Особенности беременности */
	private VocTransfusionPregnancyHang pregnancyHang;
	/** Кол-во беременностей */
	private String countPregnancy;
	/** Трансфузионный по индив. подбору в прошлом */
	private VocYesNo personalTransfusionHistory;
	/** Трансфузионный анамнез */
	private VocYesNo transfusionHistory;
	/** Выявленные антитела */
	private String antibodiesList;
	/** Исследования антител */
	private VocTransfusionResearchAntibodies researchAntibodies;
	/** Номер контейнера */
	private String containerNumber;
	/** Срок годности */
	private Date expirationDate;

	/** Реакции на переливания в прошлом */
	@Comment("Реакции на переливания в прошлом")
	@OneToOne
	public VocYesNo getTransfusionReactionLast() {return transfusionReactionLast;}

	/** Реакции на переливания в прошлом */
	private VocYesNo transfusionReactionLast;
	
	/** Определение резус-принадлежности рециента производилось */
	@Comment("Определение резус-принадлежности рециента производилось")
	@OneToOne
	public VocTransfusionDefinitionRhesus getDefinitionRhesus() {return definitionRhesus;}

	/** Определение резус-принадлежности рециента производилось */
	private VocTransfusionDefinitionRhesus definitionRhesus;
	/** Время редакции */
	private Time editTime;
	/** Дата редакции */
	private Date editDate;
	/** Пользователь, которые последним редактировал запись */
	private String editUsername;
	/** Время создания */
	private Time createTime;
	/** Дата создания */
	private Date createDate;
	/** Пользователь, создавший запись */
	private String createUsername;
}