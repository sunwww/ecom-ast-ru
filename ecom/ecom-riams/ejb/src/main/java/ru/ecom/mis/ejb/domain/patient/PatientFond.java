package ru.ecom.mis.ejb.domain.patient;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Пациент ФОМС
 */
@Comment("Пациент ФОМС")
@Entity
@Table(schema="SQLUser")
@AIndexes(value = {
		@AIndex(properties = { "lastname","firstname","middlename","birthday" }),
		@AIndex(properties = { "commonNumber" })
})
@Getter
@Setter
public class PatientFond extends BaseEntity{
	public static final String STATUS_CHECK_TYPE_AUTOMATIC="A" ;
	public static final String STATUS_CHECK_TYPE_PACKAGE="P" ;
	public static final String STATUS_CHECK_TYPE_MANUAL="M" ;

	private String lastname;
	private String firstname;
	private String middlename;
	private Date birthday;
	private String snils;
	private Date deathDate;

	private String street;
	private String department;
	private String doctorSnils;
	private String okato;
	private Boolean isDifference;
	private String documentType;
	private String documentSeries;
	private String documentNumber;
	private String companyCode;
	private String policySeries;
	private String policyNumber;
	private String commonNumber;
	private String kladr;
	private String house;
	private String houseBuilding;
	private String flat;
	private Date checkDate;
	private String checkType;
	private Date policyDateFrom;
	private Date policyDateTo;
	private String companyOgrn;
	private String companyOkato;
	private String checker;
	private String compabyCodeF;
	private String lpuAttached;
	private String attachedType;
	private Date attachedDate;
	private String documentDateIssued;
	private String documentWhomIssued;

	private Long patient;
	private Boolean isAttachmentUpdate;
	private Boolean isPolicyUpdate;
	private Boolean isDocumentUpdate;
	private Boolean isPatientUpdate;
	private PatientFondCheckData checkTime;
	private Timestamp dateTimeCreate;

	public PatientFond() {
		this.dateTimeCreate = new Timestamp(System.currentTimeMillis());
		this.checkDate=new java.sql.Date(System.currentTimeMillis());
	}

	@Comment("Данные проверки")
	@ManyToOne
	public PatientFondCheckData getCheckTime() {return checkTime;}
}
