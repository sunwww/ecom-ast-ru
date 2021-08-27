package ru.ecom.mis.ejb.form.medcase.poly;

import lombok.Setter;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.mis.ejb.domain.medcase.PolyclinicMedCase;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.ecom.mis.ejb.form.patient.PatientForm;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.validators.DateString;
/**
 * Форма случая поликлинического обслуживания
 * @author STkacheva
 *
 */
@Comment("Случай поликлинического обслуживания")
@EntityForm
@EntityFormPersistance(clazz= PolyclinicMedCase.class)
@WebTrail(comment = "Случай поликлинического обслуживания", nameProperties= "id"
, view="entityParentView-smo_spo.do",shortList="entityParentList-smo_spo.do?short=Short",list="entityParentList-smo_spo.do")
@Parent(property="patient", parentForm= PatientForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Spo")
@Setter
public class PolyclinicMedCaseForm extends MedCaseForm {

	/** Признак консультативно-диагностического обращения */
	@Comment("Признак консультативно-диагностического обращения")
	@Persist @Deprecated
	public Boolean getIsDiagnosticSpo() {return isDiagnosticSpo;}
	/** Признак консультативно-диагностического обращения */
	private Boolean isDiagnosticSpo ;

	/** Дата окончания */
	@Comment("Дата окончания")
	@Persist	@DateString @DoDateString
	public String getDateFinish() {return dateFinish;}

	/** Продолжительность */
	@Comment("Продолжительность")
	@Persist
	public String getDuration() {return duration;}

	/** Код МКБ-10 */
	@Comment("Код МКБ-10")
	@Persist
	public Long getIdc10() {return idc10;}

	/** Кто завершил */
	@Comment("Кто завершил")
	@Persist
	public Long getFinishFunction() {return finishFunction;}

	/** Владелец */
	@Comment("Владелец")
	@Persist
	public Long getOwnerFunction() {return ownerFunction;}

	/** Кол-во визитов */
	@Comment("Кол-во визитов")
	@Persist
	public Integer getVisitsCount() {return visitsCount;}

	/** Кол-во дней */
	@Comment("Кол-во дней")
	@Persist
	public String getDaysCount() {return daysCount;}

	/** Кол-во дней */
	private String daysCount;
	/** Кол-во визитов */
	private Integer visitsCount;
	/** Владелец */
	private Long ownerFunction;
	/** Кто завершил */
	private Long finishFunction;
	/** Код МКБ-10 */
	private Long idc10;
	/** Продолжительность */
	private String duration;
	/** Дата окончания */
	private String dateFinish;

	private String promedCode;

	@Comment("Код в промеде")
	@Persist
	public String getPromedCode() {
		return promedCode;
	}
}

