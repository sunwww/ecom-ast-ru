package ru.ecom.mis.ejb.domain.patient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.registry.RegInsuranceCompany;
import ru.ecom.mis.ejb.domain.lpu.LpuArea;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.patient.voc.VocAttachedType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

/**
 * @author  azviagin
 */
@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(properties={"patient"})
	,@AIndex(properties={"lpu"})
	,@AIndex(properties={"company"})
	,@AIndex(properties={"area"})
})
public class LpuAttachedByDepartment extends BaseEntity {

	/** Участок */
	@Comment("Участок")
	@OneToOne
	public LpuArea getArea() {return theArea;}
	public void setArea(LpuArea aArea) {theArea = aArea;}

	/** Пациент */
	@Comment("Пациент")
	@ManyToOne
	public Patient getPatient() {return thePatient;}
	public void setPatient(Patient aPatient) {thePatient = aPatient;}

	/** ЛПУ */
	@Comment("ЛПУ")
	@OneToOne
	public MisLpu getLpu() {return theLpu;}
	public void setLpu(MisLpu aLpu) {theLpu = aLpu;}

	@Transient
	public String getLpuFullname() {
		return "" ;
	}
	public void setLpuFullname(String aLpuFullname) {}
	/** ЛПУ */
	private MisLpu theLpu;
	/** Участок */
	private LpuArea theArea;
	/** Пациент */
	private Patient thePatient;
	/** Прикреплен с */
	@Comment("Прикреплен с")
	public Date getDateFrom() {return theDateFrom;}
	public void setDateFrom(Date aDateFrom) {theDateFrom = aDateFrom;}

	/** Прикреплен с */
	@Comment("Откреплен с")
	public Date getDateTo() {return theDateTo;}
	public void setDateTo(Date aDateTo) {theDateTo = aDateTo;}

	/** Прикреплен до */
	private Date theDateTo;
	/** Откреплен с */
	private Date theDateFrom;
	
	/** Тип прикрепления */
	@Comment("Тип прикрепления")
	@OneToOne
	public VocAttachedType getAttachedType() {return theAttachedType;}
	public void setAttachedType(VocAttachedType aAttachedType) {theAttachedType = aAttachedType;}

	/** Тип прикрепления */
	private VocAttachedType theAttachedType;

	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Дата редактирования */
	@Comment("Дата редактирования")
	public Date getEditDate() {return theEditDate;}
	public void setEditDate(Date aEditDate) {theEditDate = aEditDate;}
	
	/** Время создания */
	@Comment("Время создания")
	public Time getCreateTime() {return theCreateTime;}
	public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}
	/** Время редактрования */
	@Comment("Время редактрования")
	public Time getEditTime() {return theEditTime;}
	public void setEditTime(Time aEditTime) {theEditTime = aEditTime;}
	/** Пользователь, который создал запись */
	@Comment("Пользователь, который создал запись")
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
	/** Пользователь, который последний редактировал запись */
	@Comment("Пользователь, который последний редактировал запись")
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

	/** Дата редактирования услуги */
	@Comment("Дата редактирования услуги")
	public Date getEditDateRender() {return theEditDateRender;}
	public void setEditDateRender(Date aEditDateRender) {theEditDateRender = aEditDateRender;}

	/** Время редактирование услуги */
	@Comment("Время редактирование услуги")
	public Time getEditTimeRender() {return theEditTimeRender;}
	public void setEditTimeRender(Time aEditTimeRender) {theEditTimeRender = aEditTimeRender;}

	/** Пользователь редактировавший услуги */
	@Comment("Пользователь редактировавший услуги")
	public String getEditUsernameRender() {return theEditUsernameRender;}
	public void setEditUsernameRender(String aEditUsernameRender) {theEditUsernameRender = aEditUsernameRender;}

	/** Пользователь редактировавший услуги */
	private String theEditUsernameRender;
	/** Время редактирование услуги */
	private Time theEditTimeRender;
	/** Дата редактирования услуги */
	private Date theEditDateRender;
	/** Пользователь, который последний редактировал запись */
	private String theEditUsername;
	/** Пользователь, который создал запись */
	private String theCreateUsername;
	/** Время редактрования */
	private Time theEditTime;
	/** Время создания */
	private Time theCreateTime;
	/** Дата редактирования */
	private Date theEditDate;
	/** Дата создания */
	private Date theCreateDate;
	
	/** Дата импорта ( стар. Период дефекта ) */
	@Comment("Дата импорта")
	public String getDefectPeriod() {return theDefectPeriod;}
	public void setDefectPeriod(String aDefectPeriod) {theDefectPeriod = aDefectPeriod;}

	/** Текст дефекта */
	@Comment("Текст дефекта")
	public String getDefectText() {return theDefectText;}
	public void setDefectText(String aDefectText) {theDefectText = aDefectText;}

	/** Текст дефекта */
	private String theDefectText;
	/** Дата импорта ( стар. Период дефекта ) */
	private String theDefectPeriod;
	
	/** ЛПУ открепления */
	@Comment("ЛПУ открепления")
	public String getLpuTo() {
		return theLpuTo;
	}

	public void setLpuTo(String aLpuTo) {
		theLpuTo = aLpuTo;
	}

	/** ЛПУ открепления */
	private String theLpuTo;
	
	/** Страховая компания */
	@Comment("Страховая компания")
	@OneToOne
	public RegInsuranceCompany getCompany() {return theCompany;}
	public void setCompany(RegInsuranceCompany aCompany) {theCompany = aCompany;}

	/** Страховая компания */
	private RegInsuranceCompany theCompany;
	
	/** Подача производилась по неактуальному полису */
	@Comment("Подача производилась по неактуальному полису")
	public Boolean getNoActualPolicy() {return theNoActualPolicy;}
	public void setNoActualPolicy(Boolean aNoActualPolicy) {theNoActualPolicy = aNoActualPolicy;}

	/** Подача производилась по неактуальному полису */
	private Boolean theNoActualPolicy;

	/** Прикрепление не актуально */
	@Comment("Прикрепление не актуально")
	public Boolean getNoActuality() {return theNoActuality;}
	public void setNoActuality(Boolean aNoActuality) {theNoActuality = aNoActuality;}
	/** Прикрепление не актуально */
	private Boolean theNoActuality;
	
	/** Результаты последней проверки */
	@Comment("Результаты последней проверки")
	public String getCheckResult() {return theCheckResult;}
	public void setCheckResult(String aCheckResult) {theCheckResult = aCheckResult;}
	/** Результаты последней проверки */
	private String theCheckResult;
	
	/** Пациент сменил адрес */
	@Comment("Пациент сменил адрес")
	public Boolean getNewAddress() {return theNewAddress;}
	public void setNewAddress(Boolean aNewAddress) {theNewAddress = aNewAddress;}
	/** Пациент сменил адрес */
	private Boolean theNewAddress;

	/** Дата последнего экспорта в ФОМС */
	@Comment("Дата последнего экспорта в ФОМС")
	public Date getExportDate() {return theExportDate;}
	public void setExportDate(Date aExportDate) {theExportDate = aExportDate;}
	/** Дата последнего экспорта в ФОМС */
	private Date theExportDate;
}
