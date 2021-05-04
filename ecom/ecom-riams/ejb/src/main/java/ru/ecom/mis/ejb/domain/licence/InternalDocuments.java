package ru.ecom.mis.ejb.domain.licence;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.licence.voc.VocDocumentBiologAnalysis;
import ru.ecom.mis.ejb.domain.licence.voc.VocDocumentMaterialBiologAnalysis;
import ru.ecom.mis.ejb.domain.licence.voc.VocDocumentObjectBiologAnalysis;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.lpu.voc.VocBedSubType;
import ru.ecom.mis.ejb.domain.medcase.voc.VocBedType;
import ru.ecom.mis.ejb.domain.medcase.voc.VocHealthGroup;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.ecom.poly.ejb.domain.Ticket;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.sql.Date;

@Entity
@Comment("Внутренние документы")
@AIndexes(value = {
		@AIndex(properties={"medCase"},table="Document"),
		@AIndex(properties={"ticket"},table="Document")
})
@Getter
@Setter
public class InternalDocuments extends Document {
	/** Обоснование */
	@Comment("Обоснование")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getHistory() {return history;}
	private String history;

	/** Рекомендации */
	@Comment("Рекомендации")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getRecommendations() {return recommendations;}
	private String recommendations;

	/** Куда направлен */
	@Comment("Куда направлен")
	@OneToOne
	public MisLpu getSentToLpu() {return sentToLpu;}
	private MisLpu sentToLpu;

	/** Диагноз */
	@Comment("Диагноз")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getDiagnosis() {return diagnosis;}
	private String diagnosis;


	/** Код диагноза */
	@Comment("Код диагноза")
	@OneToOne
	public VocIdc10 getIdc10() {return idc10;}
	private VocIdc10 idc10;

	/** Телефон */
	private String phonePatient;

	/** Поток обслуживания */
	@Comment("Поток обслуживания")
	@OneToOne
	public VocServiceStream getServiceStream() {return serviceStream;}
	private VocServiceStream serviceStream;

	/** Талон */
	@Comment("Талон")
	@OneToOne @Deprecated
	public Ticket getTicket() {return ticket;}
	private Ticket ticket;

	/** Цель биологического исследования */
	@Comment("Цель биологического исследования")
	@OneToOne
	public VocDocumentObjectBiologAnalysis getObjectBiologAnalysis() {
		return objectBiologAnalysis;
	}
	private VocDocumentObjectBiologAnalysis objectBiologAnalysis;

	/** Исследование */
	@Comment("Исследование")
	@OneToOne
	public VocDocumentBiologAnalysis getBiologAnalysis() {
		return biologAnalysis;
	}
	private VocDocumentBiologAnalysis biologAnalysis;

	/** Материал для микробилогического исследования */
	@Comment("Материал для микробилогического исследования")
	@OneToOne
	public VocDocumentMaterialBiologAnalysis getMaterialBiologAnalysis() {
		return materialBiologAnalysis;
	}
	private VocDocumentMaterialBiologAnalysis materialBiologAnalysis;

	/** Услуги */
	private String servicies;

	/** Отделение */
	@Comment("Отделение")
	@OneToOne
	public MisLpu getDepartment() {return department;}
	private MisLpu department;

	/** Профиль коек */
	@Comment("Профиль коек")
	@OneToOne
	public VocBedType getBedType() {return bedType;}
	private VocBedType bedType;

	/** Планируемая дата с */
	private Date planDateFrom;

	/** Планируемая дата по */
	private Date planDateTo;

	/** Планируется операция? */
	private Boolean isPlanOperation;
	
	/** Тип коек */
	@Comment("Тип коек")
	@OneToOne
	public VocBedSubType getBedSubType() {return bedSubType;}
	private VocBedSubType bedSubType;

	/** Группа здоровья */
	@Comment("Группа здоровья")
	@OneToOne
	public VocHealthGroup getHealthGroup() {return healthGroup;}
	private VocHealthGroup healthGroup ;

}
