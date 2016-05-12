package ru.ecom.mis.ejb.uc.privilege.domain;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.StringUtil;

/**
 * @author  azviagin
 */
@Entity
@Comment("Лекарственное средство")
@Table(schema="SQLUser")
@AIndexes(
		@AIndex(properties={"privilege"})
	)
public class DrugNeed extends BaseEntity {

	/** Льгота */
	@Comment("Льгота")
	@ManyToOne
	public Privilege getPrivilege() {
		return thePrivilege;
	}

	public void setPrivilege(Privilege aPrivilege) {
		thePrivilege = aPrivilege;
	}
	
	/** Среднемесячная доза в мг */
	@Comment("Среднемесячная доза в мг")
	public BigDecimal getMiddleMonthDoze() {
		return theMiddleMonthDoze;
	}

	public void setMiddleMonthDoze(BigDecimal aMiddleMonthDoze) {
		theMiddleMonthDoze = aMiddleMonthDoze;
	}

	/** Лекарственный препарат */
	@Comment("Лекарственный препарат")
	@OneToOne
	public VocDrugClassify getDrugClassify() {
		return theDrugClassify;
	}

	public void setDrugClassify(VocDrugClassify aDrugClassify) {
		theDrugClassify = aDrugClassify;
	}

	/** Торговое наименование лекарственного препарата */
	@Comment("Торговое наименование лекарственного препарата")
	public String getTradename() {
		return theTradename;
	}

	public void setTradename(String aTradename) {
		theTradename = aTradename;
	}
	
	/** Форма выпуска */
	@Comment("Форма выпуска")
	@OneToOne
	public VocDrugForm getDrugForm() {
		return theDrugForm;
	}

	public void setDrugForm(VocDrugForm aDrugForm) {
		theDrugForm = aDrugForm;
	}


	/** Код врача */
	@Comment("Код врача")
	@OneToOne
	public VocDloDoctor getDloDoctor() {
		return theDloDoctor;
	}

	public void setDloDoctor(VocDloDoctor aDloDoctor) {
		theDloDoctor = aDloDoctor;
	}
	
	/** Информация */
	@Comment("Информация")
	@Transient
	public String getInfo() {
		StringBuilder sb = new StringBuilder() ;
		if(theDrugClassify!=null) {
			sb.append(theDrugClassify.getName()) ;
		}
		if(!StringUtil.isNullOrEmpty(theTradename)) {
			sb.append(" ").append(theTradename) ;
		}
		if(theMiddleMonthDoze!=null) {
			sb.append(" ").append(theMiddleMonthDoze).append(" мг") ;
		}
		return sb.toString() ;
	}

	public void setInfo(String aInfo) {
	}
	
	/**
	 * Дата назначения
	 */
	public Date getIssuedDate() { return theIssuedDate ; }
	public void setIssuedDate(Date aDate) { theIssuedDate = aDate ; }
	private Date theIssuedDate ;

	/** Дата отмены */
	public Date getCancelDate() { return theCancelDate ; }
	public void setCancelDate(Date aDate) { theCancelDate = aDate ; }
	private Date theCancelDate ;
	
	

	/** Код врача */
	private VocDloDoctor theDloDoctor;
	/** Форма выпуска */
	private VocDrugForm theDrugForm;
	/** Торговое наименование лекарственного препарата */
	private String theTradename;
	/** Лекарственный препарат */
	private VocDrugClassify theDrugClassify;
	/** Среднемесячная доза в мг */
	private BigDecimal theMiddleMonthDoze;

	/** Льгота */
	private Privilege thePrivilege;
}
