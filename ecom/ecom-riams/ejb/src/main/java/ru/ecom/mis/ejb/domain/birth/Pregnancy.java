package ru.ecom.mis.ejb.domain.birth;

import ru.ecom.document.ejb.domain.certificate.Certificate;
import ru.ecom.document.ejb.domain.certificate.ConfinementCertificate;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * Беременность
 * @author azviagin
 *
 */
@Comment("Беременность")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = { 
		@AIndex(properties = { "patient" }) 
	}
)
public class Pregnancy extends BaseEntity{
	
	/** Пациент */
	@Comment("Пациент")
	@OneToOne
	public Patient getPatient() {return thePatient;}
	public void setPatient(Patient aPatient) {thePatient = aPatient;}

	
	/** Обменная карта беременной */
	@Comment("Обменная карта беременной")
	@OneToOne
	public PregnanExchangeCard getPregnanExchangeCard() {return thePregnanExchangeCard;}
	public void setPregnanExchangeCard(PregnanExchangeCard aPregnanExchangeCard) {thePregnanExchangeCard = aPregnanExchangeCard;}

	
	/** Обменная карта родильницы */
	@Comment("Обменная карта родильницы")
	@OneToOne
	public ConfinedExchangeCard getConfinedExchangeCard() {return theConfinedExchangeCard;}
	public void setConfinedExchangeCard(ConfinedExchangeCard aConfinedExchangeCard) {theConfinedExchangeCard = aConfinedExchangeCard;}
	
	@Transient @Comment("Общая информация о беременности")
	public String getInformation() {
		StringBuilder ret = new StringBuilder() ;
		ret.append("Беременность: ").append(theOrderNumber) ;
		ret.append(" роды: ").append(theChildbirthAmount) ;
		//if (thePatient!=null) ret.append(" ").append(thePatient.getLastname()).append(" ")
		//	.append(thePatient.getFirstname()).append(" ").append(thePatient.getMiddlename()) ;
		if (thePregnanExchangeCard!=null) ret.append(" обменная карта есть") ;
		return ret.toString() ;
	}

	/** Дата создания */
	@Comment("Дата создания")
	public Date getDateCreate() {return theDateCreate;}
	public void setDateCreate(Date aDateCreate) {theDateCreate = aDateCreate;}

	/** Заведена пользователем */
	@Comment("Заведена пользователем")
	public String getUsername() {return theUsername;	}
	public void setUsername(String aUsername) {theUsername = aUsername;}

	/** Какая по счету беременность */
	@Comment("Какая по счету беременность")
	public Integer getOrderNumber() {return theOrderNumber;}
	public void setOrderNumber(Integer aNAME) {theOrderNumber = aNAME;	}
	
	@Comment("Количество родов")
	public Integer getChildbirthAmount() {return theChildbirthAmount;}
	public void setChildbirthAmount(Integer aChildbirthAmount) {theChildbirthAmount = aChildbirthAmount;}

	/** Сертификаты */
	@Comment("Сертификаты")
	@OneToMany(mappedBy="pregnancy",cascade=CascadeType.ALL)
	public List<Certificate> getCertificates() {return theCertificates;}
	public void setCertificates(List<Certificate> aCertificate) {theCertificates = aCertificate;}

	@Transient
	public ConfinementCertificate getConfinementCertificate() {
		for (Certificate cert:theCertificates) {
			if (cert instanceof ConfinementCertificate) return (ConfinementCertificate)cert ;
		}
		return null ;
	}
	@Transient
	public String getIsExchangeCard() {return thePregnanExchangeCard!=null? "ЕСТЬ": "НЕТ" ;}
	
	/** Сертификаты */
	private List<Certificate> theCertificates;
	/** Количество родов */
	private Integer theChildbirthAmount;
	/** Какая по счету беременность */
	private Integer theOrderNumber;
	/** Заведена пользователем */
	private String theUsername;
	/** Дата создания */
	private Date theDateCreate;
	/** Обменная карта родильницы */
	private ConfinedExchangeCard theConfinedExchangeCard;
	/** Обменная карта беременной */
	private PregnanExchangeCard thePregnanExchangeCard;
	/** Пациент */
	private Patient thePatient;
}
