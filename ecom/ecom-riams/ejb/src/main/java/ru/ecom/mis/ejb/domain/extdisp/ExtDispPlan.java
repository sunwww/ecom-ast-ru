package ru.ecom.mis.ejb.domain.extdisp;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.extdisp.voc.VocExtDisp;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.util.List;

	/**
	 * План дополнительной диспансеризации
	 */
	@Comment("План дополнительной диспансеризации")
@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(properties="dispType")
    })
	@Getter
	@Setter
public class ExtDispPlan extends BaseEntity{
	/**
	 * Тип дополнительной диспансеризации
	 */
	@Comment("Тип дополнительной диспансеризации")
	@OneToOne
	public VocExtDisp getDispType() {
		return dispType;
	}
	/**
	 * Тип дополнительной диспансеризации
	 */
	private VocExtDisp dispType;
	@OneToMany(mappedBy="plan", cascade=CascadeType.ALL)
	public List<ExtDispPlanService> getServices() {
		return services;
	}
	/**
	 * Услуги
	 */
	private List<ExtDispPlanService> services;
}
