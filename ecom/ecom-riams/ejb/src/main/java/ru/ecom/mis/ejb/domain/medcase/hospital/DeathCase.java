package ru.ecom.mis.ejb.domain.medcase.hospital;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.address.ejb.domain.address.Address;
import ru.ecom.document.ejb.domain.certificate.DeathCertificate;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.medcase.kili.ProtocolKili;
import ru.ecom.mis.ejb.domain.medcase.voc.*;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
/**
 * Случай смерти
 * @author stkacheva
 *
 */
@Comment ("Случай смерти")
@Entity
@AIndexes({
	@AIndex(properties="medCase"),
	@AIndex(properties="patient")
    }) 
@Table(schema="SQLUser")
@Getter
@Setter
public class DeathCase extends BaseEntity {
	
	/** Протоколы КИЛИ */
	@Comment("Протоколы КИЛИ")
	@OneToMany(mappedBy = "deathCase", cascade = CascadeType.ALL)
	public List<ProtocolKili> getKiliProtocols() {return kiliProtocols;}
	/** Протоколы КИЛИ */
	private List<ProtocolKili> kiliProtocols;

	/** Мед. случай */
	@Comment("Мед. случай")
	@OneToOne
	public MedCase getMedCase() {return medCase;}

	/** Пациент */
	@Comment("Пациент")
	@OneToOne
	public Patient getPatient() {return patient;}

	/** Мать */
	@Comment("Мать")
	@OneToOne
	public Patient getMother() {return mother;}

	/** Доношенность */
	@Comment("Доношенность")
	@OneToOne
	public VocIsPrematurity getIsPrematurity() {return isPrematurity;}

	/** Место смерти */
	@Comment("Место смерти")
	@OneToOne
	public VocDeathPlace getDeathPlace() {return deathPlace;}

	/** Адрес места смерти */
	@Comment("Адрес места смерти")
	@OneToOne
	public Address getDeathPlaceAddress() {return deathPlaceAddress;}

	/** Причина смерти */
	@Comment("Причина смерти")
	@OneToOne
	public VocDeathReason getDeathReason() {return deathReason;	}

	/** Врач (фельдшер) */
	@Comment("Врач (фельдшер)")
	@OneToOne
	public WorkFunction getDeathWitness() {return deathWitness;}

	/** Причина смерти установлена */
	@Comment("Причина смерти установлена")
	@OneToOne
	public VocDeathWitnessFunction getDeathWitnessFunction() {return deathWitnessFunction;}

	/** На основании чего установлена смерть */
	@Comment("На основании чего установлена смерть")
	@ManyToMany
	public List<VocDeathEvidence> getDeathEvidence() {return deathEvidence;}

	/** Умерла после окончания родов */
	@Comment("Умерла после окончания родов")
	@OneToOne
	public VocAfterPregnance getAfterPregnance() {return afterPregnance;}

	/** Свидетельства о смерти */
	@Comment("Свидетельства о смерти")
	@OneToMany(mappedBy = "deathCase", cascade = CascadeType.ALL)
	public List<DeathCertificate> getDeathCertificate() {return deathCertificate;}

	/** Место рождения */
	private String birthPlace;
	
	/** Адрес места рождения */
	private String birthPlaceAdress;
	/** Свидетельства о смерти */
	private List<DeathCertificate> deathCertificate;
	/** Умерла после окончания родов */
	private VocAfterPregnance afterPregnance;
	/** На основании чего установлена смерть */
	private List<VocDeathEvidence> deathEvidence;
	/** Причина смерти установлена */
	private VocDeathWitnessFunction deathWitnessFunction;
	/** Врач (фельдшер) */
	private WorkFunction deathWitness;
	/** Обстоятельства, при которых произошла травма (отравление) */
	private String accidentCircumstance;
	/** Место, при которых произошла травма (отравление) */
	private String accidentPlace;
	/** Дата травмы (отравления) */
	private Date accidentDate;
	/** Причина смерти */
	private VocDeathReason deathReason;
	/** Адрес места смерти */
	private Address deathPlaceAddress;
	/** Место смерти */
	private VocDeathPlace deathPlace;
	/** Какой ребенок по счету у матери */
	private Integer babyNumber;
	/** Масса (вес) при рождении (грамм) */
	private Integer birthWeight;
	/** Доношенность */
	private VocIsPrematurity isPrematurity;
	/** Время смерти */
	private Time deathTime;
	/** Дата смерти */
	private Date deathDate;
	/** Мать */
	private Patient mother;
	/** Пациент */
	private Patient patient;
	/** Мед. случай */
	private MedCase medCase;
	
	/** Ятрогения */
	@Comment("Ятрогения")
	@OneToOne
	public VocDeathCategory getLatrogeny() {return latrogeny;}

	/** Категория расхождения между диагнозами после патан. */
	@Comment("Категория расхождения между диагнозами после патан.")
	@OneToOne
	public VocDeathCategory getCategoryDifference() {return categoryDifference;}
	/** Копрус дома, где умер человек */
	private String deathPlaceHouseBuilding;
	/** Дом, где умер человекhPlaceHouseNumber */
	private String deathPlaceHouseNumber;
	/** Квартира, где умер человек */
	private String deathPlaceFlatNumber;
	/** Дата ПАБ */
	private Date postmortemBureauDt;
	/** Номер в патологоанатомическом бюро */
	private String postmortemBureauNumber;
	/** Комментарий к категории смерти */
	private String commentCategory;
	/** Описание причины смерти */
	private String commentReason;
	/** Категория расхождения между диагнозами после патан. */
	private VocDeathCategory categoryDifference;
	/** Ятрогения */
	private VocDeathCategory latrogeny;
	/** Пользователь, который последний редактировал запись */
	private String editUsername;
	/** Пользователь, который создал запись */
	private String createUsername;
	/** Время редактрования */
	private Time editTime;
	/** Время создания */
	private Time createTime;
	/** Дата редактирования */
	private Date editDate;
	/** Дата создания */
	private Date createDate;
	
	/** Тип диагноза категории расхождения */
	@Comment("Тип диагноза категории расхождения")
	@OneToOne
	public VocPriorityDiagnosis getDiagnosisDifference() {return diagnosisDifference;}

	/** Тип диагноза категории расхождения */
	private VocPriorityDiagnosis diagnosisDifference;
	/** Произведено вскрытие */
	private Boolean isAutopsy;
	/** Дата СМЭ */
	private Date dateForensic;
	
	/** Код мкб */
	@Comment("Код мкб")
	@OneToOne
	public VocIdc10 getReasonMainMkb() {return reasonMainMkb;}

	/** Код мкб */
	private VocIdc10 reasonMainMkb;
	
	/** Код мкб осложнения */
	@Comment("Код мкб осложнения")
	@OneToOne
	public VocIdc10 getReasonComplicationMkb() {return reasonComplicationMkb;}

	/** Код мкб осложнения */
	private VocIdc10 reasonComplicationMkb;
	
	/** Код мкб сопутсвующий */
	@Comment("Код мкб сопутсвующий")
	@OneToOne
	public VocIdc10 getReasonConcomitantMkb() {return reasonConcomitantMkb;}
	/** Код мкб сопутсвующий */
	private VocIdc10 reasonConcomitantMkb;
	/** Фоновое заболевание */
	private String backgroundDisease;
	/** Сочетанное заболевание */
	private String polypathia;
	/** Конкурирующее заболевание */
	private String competingDisease;
	/** Сопутствующий диагноз текст */
	private String reasonConcomitantText;	
	/** Текст мкб осложнения */
	private String reasonComplicationText;
	/** Присутствие врача на вскрытие */
	private Boolean isPresenceDoctorAutopsy;
	/** Мертворождение */
	private Boolean isNeonatologic;
	/** История развития новорождённого - только для акушерских случаев */
	private String newBornHistory;
}