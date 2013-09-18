package ru.ecom.mis.ejb.domain.extdisp;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.extdisp.ExtDispPlanService;
import ru.ecom.mis.ejb.domain.extdisp.voc.VocExtDisp;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

	/**
	 * План дополнительной диспансеризации
	 */
	@Comment("План дополнительной диспансеризации")
@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(properties="dispType")
    }) 
public class ExtDispPlan extends BaseEntity{
	/**
	 * Тип дополнительной диспансеризации
	 */
	@Comment("Тип дополнительной диспансеризации")
	@OneToOne
	public VocExtDisp getDispType() {
		return theDispType;
	}
	public void setDispType(VocExtDisp aDispType) {
		theDispType = aDispType;
	}
	/**
	 * Тип дополнительной диспансеризации
	 */
	private VocExtDisp theDispType;
	@OneToMany(mappedBy="plan", cascade=CascadeType.ALL)
	public List<ExtDispPlanService> getServices() {
		return theServices;
	}
	public void setServices(List<ExtDispPlanService> aServices) {
		theServices = aServices;
	}
	/**
	 * Услуги
	 */
	private List<ExtDispPlanService> theServices;
}
