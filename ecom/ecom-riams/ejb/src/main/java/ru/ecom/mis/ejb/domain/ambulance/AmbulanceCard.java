package ru.ecom.mis.ejb.domain.ambulance;

import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
@AIndexes(value = { @AIndex(properties = { "medCase" }) })
@Getter
@Setter
public class AmbulanceCard extends BaseEntity {
	/** Номер карты скорой помощи */
	private String numberCard;
	/** СМО */
	@Comment("СМО")
	@OneToOne
	public MedCase getMedCase() {return medCase;}

	/** СМО */
	private MedCase medCase;
	
	/** Время получения вызова СМП */
	private Time callReceiveTime;
	
	/** Время прибытия бригады до места назначения */
	private Time arrivalTime;
}
