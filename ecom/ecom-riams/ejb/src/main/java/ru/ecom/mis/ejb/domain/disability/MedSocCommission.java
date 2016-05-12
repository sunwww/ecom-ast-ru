package ru.ecom.mis.ejb.domain.disability;

import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

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
	@AIndex(unique = false, properties= {"disabilityDocument"})
})
@Table(schema="SQLUser")
public class MedSocCommission extends BaseEntity{
	
	/** Документ нетрудоспособности */
	@Comment("Документ нетрудоспособности")
	@OneToOne
	public DisabilityDocument getDisabilityDocument() {return theDisabilityDocument;}
	public void setDisabilityDocument(DisabilityDocument aDisabilityDocument) {theDisabilityDocument = aDisabilityDocument;}

	/** Дата направления */
	@Comment("Дата направления")
	public Date getAssignmentDate() {return theAssignmentDate;}
	public void setAssignmentDate(Date aAssignmentDate) {theAssignmentDate = aAssignmentDate;}

	/** Дата регистрации документов */
	@Comment("Дата регистрации документов")
	public Date getRegistrationDate() {return theRegistrationDate;}
	public void setRegistrationDate(Date aRegistrationDate) {theRegistrationDate = aRegistrationDate;}
	
	/** Дата освидетельствования */
	@Comment("Дата освидетельствования")
	public Date getExaminationDate() {return theExaminationDate;}
	public void setExaminationDate(Date aExaminationDate) {theExaminationDate = aExaminationDate;}

	/** Комментарии */
	@Comment("Комментарии")
	public String getComments() {return theComments;}
	public void setComments(String aComments) {theComments = aComments;}

	/** Степень ограничения трудоспособности */
	@Comment("Степень ограничения трудоспособности")
	@OneToOne
	public VocDisabilityDegree getDisabilityDergee() {return theDisabilityDergee;}
	public void setDisabilityDergee(VocDisabilityDegree aDisabilityDergee) {theDisabilityDergee = aDisabilityDergee;}

	/** Инвалидность */
	@Comment("Инвалидность")
	@OneToOne
	public VocInvalidity getInvalidity() {return theInvalidity;}
	public void setInvalidity(VocInvalidity aInvalidity) {theInvalidity = aInvalidity;}
	
	@Comment("Инвалидность инфо")
	@Transient
	public String getInvalidityInfo() {
		return theInvalidity!=null? theInvalidity.getName() :"" ;
	}
	
	@Comment("Степень ограничения трудоспособности инфо")
	@Transient	
	public String getDisabilityDergeeInfo() {
		return theDisabilityDergee!=null? theDisabilityDergee.getName():"" ;
	}
	
	@Comment("Информация о МСЭК")
	@Transient
	public String getInfo() {
		final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy") ;
		StringBuilder ret = new StringBuilder() ;
		if (theExaminationDate!=null) ret.append(format.format(theExaminationDate)).append(" ") ;
		ret.append(getDisabilityDergeeInfo()) ;
		return ret.toString() ;
	}
	
	/** Изменена/установлена инвалидность */
	@Comment("Изменена/установлена инвалидность")
	public Boolean getInvalidityRegistration() {return theInvalidityRegistration;}
	public void setInvalidityRegistration(Boolean aInvalidityRegistration) {theInvalidityRegistration = aInvalidityRegistration;}

	/** Изменена/установлена инвалидность */
	private Boolean theInvalidityRegistration;
	/** Документ нетрудоспособности */
	private DisabilityDocument theDisabilityDocument;
	/** Дата направления */
	private Date theAssignmentDate;
	/** Дата регистрации документов */
	private Date theRegistrationDate;
	/** Дата освидетельствования */
	private Date theExaminationDate;
	/** Комментарии */
	private String theComments;
	/** Степень ограничения трудоспособности */
	private VocDisabilityDegree theDisabilityDergee;
	/** Инвалидность */
	private VocInvalidity theInvalidity;
}
