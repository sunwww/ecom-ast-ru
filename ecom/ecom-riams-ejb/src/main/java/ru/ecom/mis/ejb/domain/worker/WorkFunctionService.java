package ru.ecom.mis.ejb.domain.worker;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.medcase.MedService;
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

}
