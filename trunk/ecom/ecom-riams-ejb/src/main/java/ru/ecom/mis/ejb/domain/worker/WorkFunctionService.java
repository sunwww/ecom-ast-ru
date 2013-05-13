package ru.ecom.mis.ejb.domain.worker;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.lpu.voc.VocBedSubType;
import ru.ecom.mis.ejb.domain.medcase.MedService;
import ru.ecom.mis.ejb.domain.medcase.voc.VocBedType;
import ru.ecom.mis.ejb.domain.medcase.voc.VocRoomType;
import ru.ecom.mis.ejb.domain.worker.voc.VocWorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Услуги по рабочим функциям
 * @author azviagin
 *
 */

@Comment("Услуги по рабочим функциям")
@Entity
@AIndexes({
	@AIndex(properties={"vocWorkFunction"})
	,@AIndex(properties={"medService"})
	,@AIndex(properties={"vocWorkFunction","medService"})
	,@AIndex(properties={"vocWorkFunction","lpu"})
	,@AIndex(properties={"lpu","bedType"})
})
@Table(schema="SQLUser")
public class WorkFunctionService extends BaseEntity{
	
	/** Рабочая функция */
	@Comment("Рабочая функция")
	@ManyToOne
	public VocWorkFunction getVocWorkFunction() {
		return theVocWorkFunction;
	}

	public void setVocWorkFunction(VocWorkFunction aVocWorkFunction) {
		theVocWorkFunction = aVocWorkFunction;
	}

	/** Рабочая функция */
	private VocWorkFunction theVocWorkFunction;
	
	/** Мед. услуга */
	@Comment("Мед. услуга")
	@ManyToOne
	public MedService getMedService() {
		return theMedService;
	}

	public void setMedService(MedService aMedService) {
		theMedService = aMedService;
	}

	/** Мед. услуга */
	private MedService theMedService;
	
	/** ЛПУ */
	@Comment("ЛПУ")
	@OneToOne
	public MisLpu getLpu() {return theLpu;}
	public void setLpu(MisLpu aLpu) {theLpu = aLpu;}

	/** ЛПУ */
	private MisLpu theLpu;
	
	/** Профиль коек */
	@Comment("Профиль коек")
	@OneToOne
	public VocBedType getBedType() {return theBedType;}
	public void setBedType(VocBedType aBedType) {theBedType = aBedType;}

	/** Профиль коек */
	private VocBedType theBedType;
	
	/** Тип коек */
	@Comment("Тип коек")
	@OneToOne
	public VocBedSubType getBedSubType() {return theBedSubType;}
	public void setBedSubType(VocBedSubType aBedSubType) {theBedSubType = aBedSubType;}

	/** Тип коек */
	private VocBedSubType theBedSubType;
	
	/** Уровень палат */
	@Comment("Уровень палат")
	@OneToOne
	public VocRoomType getRoomType() {return theRoomType;}
	public void setRoomType(VocRoomType aRoomType) {theRoomType = aRoomType;}

	/** Уровень палат */
	private VocRoomType theRoomType;
}
