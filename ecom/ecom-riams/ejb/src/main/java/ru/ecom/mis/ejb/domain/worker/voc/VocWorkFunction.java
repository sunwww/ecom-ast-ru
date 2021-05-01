package ru.ecom.mis.ejb.domain.worker.voc;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.expert2.domain.voc.VocE2MedHelpProfile;
import ru.ecom.expert2.domain.voc.federal.VocE2FondV021;
import ru.ecom.mis.ejb.domain.worker.WorkFunctionService;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.util.List;

/**
 * Рабочая функция
 * @author azviagin
 *
 */
@Comment("Рабочая функция")
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class VocWorkFunction extends VocBaseEntity{

	/** Специальность по справочнику V021 */
	@Comment("Специальность по справочнику V021")
	@OneToOne
	public VocE2FondV021 getFondSpeciality() {return fondSpeciality;}
	/** Специальность по справочнику V021 */
	private VocE2FondV021 fondSpeciality ;
	
	/** Должности */
	@Comment("Должности")
	@ManyToOne
	public VocPost getVocPost() {
		return vocPost;
	}

	/** Должности */
	private VocPost vocPost;

	/** Мед. услуги */
	@Comment("Мед. услуги")
	@OneToMany(mappedBy="vocWorkFunction", cascade=CascadeType.ALL)
	public List<WorkFunctionService> getWorkFunctionServices() {
		return workFunctionServices;
	}

	/** Мед. услуги */
	private List<WorkFunctionService> workFunctionServices;

	/** Короткое название */
	private String shortName;
	
	/** Не заполняется диагноз */
	private Boolean isNoDiagnosis;
	
	/** Создавать заголовок в дневнике */
	@Comment("Создавать заголовок в дневнике")
	@Column(nullable=false, columnDefinition="boolean default false")
	public Boolean getIsDiaryTitle() {return isDiaryTitle;}
	private Boolean isDiaryTitle ;

	/** Лучевая диагностика */
	private Boolean isRadiationDiagnosis;
	/** Лаборатория */
	private Boolean isLab;
	/** Функциональная диагностика */
	private Boolean isFuncDiag;
	
	/** Не включать в 039 форму */
	private Boolean isNo039;

	/** Короткое название ФСС*/
	private String fSSShortName;

	/** Код ФСС */
	private String fSSCode;

	private String promedCode_polic;
	private String promedCode_stac;


	/** Не подавать по ОМС */
	private Boolean isNoOmc=false ;

	/** Профиль медицинской помощи */
	@Comment("Профиль медицинской помощи")
	@OneToOne
	public VocE2MedHelpProfile getMedHelpProfile() {return medHelpProfile;}
	/** Профиль медицинской помощи */
	private VocE2MedHelpProfile medHelpProfile ;

	/** Можно назначать в инфекционном?? */
	private Boolean isSuitForCovid;
}
