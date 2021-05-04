package ru.ecom.mis.ejb.domain.birth;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class Pregnancy extends BaseEntity{
	
	/** Пациент */
	@Comment("Пациент")
	@OneToOne
	public Patient getPatient() {return patient;}

	/** Обменная карта беременной */
	@Comment("Обменная карта беременной")
	@OneToOne
	public PregnanExchangeCard getPregnanExchangeCard() {return pregnanExchangeCard;}

	/** Обменная карта родильницы */
	@Comment("Обменная карта родильницы")
	@OneToOne
	public ConfinedExchangeCard getConfinedExchangeCard() {return confinedExchangeCard;}

	@Transient @Comment("Общая информация о беременности")
	public String getInformation() {
		StringBuilder ret = new StringBuilder() ;
		ret.append("Беременность: ").append(orderNumber) ;
		ret.append(" роды: ").append(childbirthAmount) ;
		if (pregnanExchangeCard!=null) ret.append(" обменная карта есть") ;
		return ret.toString() ;
	}


	/** Сертификаты */
	@Comment("Сертификаты")
	@OneToMany(mappedBy="pregnancy",cascade=CascadeType.ALL)
	public List<Certificate> getCertificates() {return certificates;}

	@Transient
	public ConfinementCertificate getConfinementCertificate() {
		for (Certificate cert:certificates) {
			if (cert instanceof ConfinementCertificate) return (ConfinementCertificate)cert ;
		}
		return null ;
	}
	@Transient
	public String getIsExchangeCard() {return pregnanExchangeCard!=null? "ЕСТЬ": "НЕТ" ;}
	
	/** Сертификаты */
	private List<Certificate> certificates;
	/** Количество родов */
	private Integer childbirthAmount;
	/** Какая по счету беременность */
	private Integer orderNumber;
	/** Заведена пользователем */
	private String username;
	/** Дата создания */
	private Date dateCreate;
	/** Обменная карта родильницы */
	private ConfinedExchangeCard confinedExchangeCard;
	/** Обменная карта беременной */
	private PregnanExchangeCard pregnanExchangeCard;
	/** Пациент */
	private Patient patient;
}
