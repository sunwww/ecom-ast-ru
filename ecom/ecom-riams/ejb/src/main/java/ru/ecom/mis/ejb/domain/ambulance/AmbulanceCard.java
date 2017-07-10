package ru.ecom.mis.ejb.domain.ambulance;

import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
@AIndexes(value = { @AIndex(properties = { "medCase" }) })
public class AmbulanceCard extends BaseEntity {
	/** Номер карты скорой помощи */
	@Comment("Номер карты скорой помощи")
	public String getNumberCard() {return theNumberCard;}
	public void setNumberCard(String aNumberCard) {theNumberCard = aNumberCard;}

	/** Номер карты скорой помощи */
	private String theNumberCard;
	/** СМО */
	@Comment("СМО")
	@OneToOne
	public MedCase getMedCase() {return theMedCase;}
	public void setMedCase(MedCase aMedCase) {theMedCase = aMedCase;}

	/** СМО */
	private MedCase theMedCase;
	
	/** Время получения вызова СМП */
	@Comment("Время получения вызова СМП")
	public Time getCallReceiveTime() {return theCallReceiveTime;}
	public void setCallReceiveTime(Time aCallReceiveTime) {theCallReceiveTime = aCallReceiveTime;}
	/** Время получения вызова СМП */
	private Time theCallReceiveTime;
	
	/** Время прибытия бригады до места назначения */
	@Comment("Время прибытия бригады до места назначения")
	public Time getArrivalTime() {return theArrivalTime;}
	public void setArrivalTime(Time aArrivalTime) {theArrivalTime = aArrivalTime;}
	/** Время прибытия бригады до места назначения */
	private Time theArrivalTime;
}
