package ru.ecom.mis.ejb.domain.medcase.voc;

import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.ecom.mis.ejb.domain.patient.voc.VocSex;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.util.Date;

/**
 * Справочник медицинских услуг
 * @author azviagin,stkacheva,vtsybulin
 *
 */
@Comment("Справочник медицинских услуг")
@Entity
@Table(schema="SQLUser")
@NamedQueries({
		@NamedQuery( name="VocMedService.vocMedServiceByCode"
				, query="from VocMedService where code=:code and finishDate is null")
})
public class VocMedService extends VocBaseEntity{
	/** Полное название */
	@Comment("Полное название")
	public String getLongName() {return theLongName;}
	public void setLongName(String aLongName) {theLongName = aLongName;}

	/** Комментарий */
	@Comment("Комментарий")
	public String getComment() {return theComment;}
	public void setComment(String aComment) {theComment = aComment;}

	/** Тип услуги */
	@Comment("Тип услуги")
	public String getServiceType() {return theServiceType;}
	public void setServiceType(String aServiceType) {theServiceType = aServiceType;}

	/** Входит в омс */
	@Comment("Входит в омс")
	public Boolean getIsOmc() {return theIsOmc;}
	public void setIsOmc(Boolean aIsOmc) {theIsOmc = aIsOmc;}

	/** Дата начала действия */
	@Comment("Дата начала действия")
	public Date getStartDate() {return theStartDate;}
	public void setStartDate(Date aStartDate) {theStartDate = aStartDate;}


	/** Дата окончания действия */
	@Comment("Дата окончания действия")
	public Date getFinishDate() {return theFinishDate;}
	public void setFinishDate(Date aFinishDate) {theFinishDate = aFinishDate;}

	/** Запрещена для пола */
	@Comment("Запрещена для пола")
	@OneToOne
	public VocSex getNotForSex() {return theNotForSex;}
	public void setNotForSex(VocSex aNotForSex) {theNotForSex = aNotForSex;}
	/** Запрещена для пола */
	private VocSex theNotForSex ;


	/** Входит в омс */
	private Boolean theIsOmc;
	/** Тип услуги */
	private String theServiceType;
	/** Комментарий */
	private String theComment;
	/** Полное название */
	private String theLongName;
	/** Дата начала действия */
	private Date theStartDate ;
	/** Дата окончания действия */
	private Date theFinishDate ;
}
