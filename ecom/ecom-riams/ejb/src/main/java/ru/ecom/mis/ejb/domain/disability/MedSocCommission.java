package ru.ecom.mis.ejb.domain.disability;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.disability.voc.VocDisabilityDegree;
import ru.ecom.mis.ejb.domain.disability.voc.VocInvalidity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/***
 * Медико-социальная экспертная комиссия
 * @author azviagin,stkacheva
 *
 */
@Comment("Медико-социальная экспертная комиссия")
@Entity
@AIndexes({
	@AIndex(properties= {"disabilityDocument"})
})
@Table(schema="SQLUser")
@Getter
@Setter
public class MedSocCommission extends BaseEntity{
	
	/** Документ нетрудоспособности */
	@Comment("Документ нетрудоспособности")
	@OneToOne
	public DisabilityDocument getDisabilityDocument() {return disabilityDocument;}


	/** Степень ограничения трудоспособности */
	@Comment("Степень ограничения трудоспособности")
	@OneToOne
	public VocDisabilityDegree getDisabilityDergee() {return disabilityDergee;}

	/** Инвалидность */
	@Comment("Инвалидность")
	@OneToOne
	public VocInvalidity getInvalidity() {return invalidity;}

	@Comment("Инвалидность инфо")
	@Transient
	public String getInvalidityInfo() {
		return invalidity!=null? invalidity.getName() :"" ;
	}
	
	@Comment("Степень ограничения трудоспособности инфо")
	@Transient	
	public String getDisabilityDergeeInfo() {
		return disabilityDergee!=null? disabilityDergee.getName():"" ;
	}
	
	@Comment("Информация о МСЭК")
	@Transient
	public String getInfo() {
		final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy") ;
		StringBuilder ret = new StringBuilder() ;
		if (examinationDate!=null) ret.append(format.format(examinationDate)).append(" ") ;
		ret.append(getDisabilityDergeeInfo()) ;
		return ret.toString() ;
	}
	
	/** Изменена/установлена инвалидность */
	private Boolean invalidityRegistration;
	/** Документ нетрудоспособности */
	private DisabilityDocument disabilityDocument;
	/** Дата направления */
	private Date assignmentDate;
	/** Дата регистрации документов */
	private Date registrationDate;
	/** Дата освидетельствования */
	private Date examinationDate;
	/** Комментарии */
	private String comments;
	/** Степень ограничения трудоспособности */
	private VocDisabilityDegree disabilityDergee;
	/** Инвалидность */
	private VocInvalidity invalidity;
}
