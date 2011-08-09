package ru.ecom.expomc.ejb.domain.registry;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Table(schema="SQLUser")
public class Review extends BaseEntity{
	/** Хозяин экспертизы */
	@Comment("Хозяин экспертизы")
	@Column(nullable=false)
	public String getHolder() {return theHolder;}
	public void setHolder(String aNAME) {theHolder = aNAME;}

	/** Время начала экспертизы */
	@Comment("Время начала экспертизы")
	public String getTimeStart() {return theTimeStart;}
	public void setTimeStart(String aTimeStart) {theTimeStart = aTimeStart;}

	/** Врем окончания эксперизы */
	@Comment("Врем окончания эксперизы")
	public String getTimeStop() {return theTimeStop;}
	public void setTimeStop(String aTimeStop) {theTimeStop = aTimeStop;}

	/** Состояние */
	@Comment("Состояние")
	public Integer getState() {return theState;}
	public void setState(Integer aState) {theState = aState;	}

	/** Временной документ, по которому проводится экспертизы */
	@Comment("Временной документ, по которому проводится экспертизы")
	@Column(nullable=false)
	public String getImportTime() {return theImportTime;}
	public void setImportTime(String aImportTime) {theImportTime = aImportTime;	}

	/** Номер счета */
	@Comment("Номер счета")
	public String getAccountExt() {return theAccountExt;}
	public void setAccountExt(String aAccountExt) {theAccountExt = aAccountExt;}

	/** Дата счета */
	@Comment("Дата счета")
	public Date getDateAccountExt() {return theDateAccountExt;}
	public void setDateAccountExt(Date aDateAccountExt) {theDateAccountExt = aDateAccountExt;}

	/** Код проверяемого ЛПУ */
	@Comment("Код проверяемого ЛПУ")
	@Column(nullable=false)
	public String getKodLpu() {return theKodLpu;}
	public void setKodLpu(String aKodLpu) {theKodLpu = aKodLpu;}

	/** Признак разрешенности исправлений */
	@Comment("Признак разрешенности исправлений")
	@Column(nullable=false)
	public Integer getUpdateEnable() {return theUpdateEnable;}
	public void setUpdateEnable(Integer aUpdateEnable) {theUpdateEnable = aUpdateEnable;}

	/** Дата начала проверяемого периода */
	@Comment("Дата начала проверяемого периода")
	@Column(nullable=false)
	public Date getDateStart() {return theDateStart;}
	public void setDateStart(Date aDateStart) {theDateStart = aDateStart;}

	/** Дата окончаемого проверяемого периода */
	@Comment("Дата окончаемого проверяемого периода")
	@Column(nullable=false)
	public Date getDateStop() {return theDateStop;}
	public void setDateStop(Date aDateStop) {theDateStop = aDateStop;}

	/** Documents */
	@Comment("Documents")
	@Column(nullable=false, length=1000)
	public String getDocuments() {return theDocuments;}
	public void setDocuments(String aDocuments) {theDocuments = aDocuments;}

	/** Documents */
	private String theDocuments;
	/** Дата окончаемого проверяемого периода */
	private Date theDateStop;
	/** Дата начала проверяемого периода */
	private Date theDateStart;
	/** Признак разрешенности исправлений */
	private Integer theUpdateEnable;
	/** Код проверяемого ЛПУ */
	private String theKodLpu;
	/** Дата счета */
	private Date theDateAccountExt;
	/** Номер счета */
	private String theAccountExt;
	/** Временной документ, по которому проводится экспертизы */
	private String theImportTime;
	/** Состояние */
	private Integer theState;
	/** Врем окончания эксперизы */
	private String theTimeStop;
	/** Время начала экспертизы */
	private String theTimeStart;
	/** Хозяин экспертизы */
	private String theHolder;

}
