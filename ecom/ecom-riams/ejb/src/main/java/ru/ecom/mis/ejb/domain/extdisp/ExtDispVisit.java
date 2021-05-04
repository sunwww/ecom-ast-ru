package ru.ecom.mis.ejb.domain.extdisp;

import lombok.Getter;
import lombok.Setter;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

	/**
	 * Визит по дополнительной диспансеризации
	 */
	@Comment("Визит по дополнительной диспансеризации")
@Entity
	@Getter
	@Setter
public class ExtDispVisit extends ExtDispService {

	@OneToMany(mappedBy="visit", cascade=CascadeType.ALL)
	public List<ExtDispServiceIndication> getServiceIndications() {return serviceIndications;}
	/**
	 * Показания к услугам 2 этапа
	 */
	private List<ExtDispServiceIndication> serviceIndications;
	/**
	 * Подозрение на ранее перенесенное нарушение мозгового кровообращения (suspicion on earlier transferred disturbance of a cerebral circulation)
	 */
	private Boolean isEtdccSuspicion;
	/**
	 * Рекомендации
	 */
	private String recommendation;
}
