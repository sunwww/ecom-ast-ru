package ru.ecom.mis.ejb.domain.medcase.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Связь профиля операции с типом аборта")
@Entity
@Table(schema="SQLUser")
public class SurgicalProfileAbortation extends BaseEntity{
	/** Тип профиля */
	@Comment("Тип профиля")
	public Long getSurgicalProfile() {return theSurgicalProfile;}
	public void setSurgicalProfile(Long aSurgicalProfile) {theSurgicalProfile = aSurgicalProfile;}
	/** Тип профиля */
	private Long theSurgicalProfile;
	
	/** Тип аборта */
	@Comment("Тип аборта")
	public Long getAbortation() {return theAbortation;	}
	public void setAbortation(Long aAbortation) {theAbortation = aAbortation;	}
	/** Тип аборта */
	private Long theAbortation;

}
