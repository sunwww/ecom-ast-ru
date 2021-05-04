package ru.ecom.mis.ejb.domain.psychiatry;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.mis.ejb.domain.patient.voc.VocSocialStatus;
import ru.ecom.mis.ejb.domain.patient.voc.VocYesNo;
import ru.ecom.mis.ejb.domain.psychiatry.voc.VocPsychSuicideNature;
import ru.ecom.mis.ejb.domain.psychiatry.voc.VocSuicideMesPlace;
import ru.ecom.mis.ejb.domain.psychiatry.voc.VocSuicideMesType;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Извещение о суициде")
@Entity
@AIndexes({
	@AIndex(properties={"patient"})
})
@Table(schema="SQLUser")
@Getter
@Setter
public class SuicideMessage extends BaseEntity {
	/** Извещение заполнено в ЛПУ */
	@Comment("Извещение заполнено в ЛПУ")
	@OneToOne
	public MisLpu getRegOtherLpu() {return regOtherLpu;}

	/** Извещение заполнено в ЛПУ */
	private MisLpu regOtherLpu;
	/** Время регистрации */
	private Time regTime;
	/** Дата регистрации */
	private Date regDate;
	/** Время заполнения извещения */
	private Time regOtherLpuTime;
	/** Дата заполения извещения */
	private Date regOtherLpuDate;
	/** Дата суицида */
	private Date suicideDate;
	/** Время редактрования */
	private Time editTime;

	/** Время создания */
	private Time createTime;

	/** Пользователь, последний редактировавший запись */
	private String editUsername;

	/** Пользователь, создавший запись */
	private String createUsername;

	/** Дата редактирования */
	private Date editDate;

	/** Дата создания */
	private Date createDate;
	
	/** Представитель */
	private String kinsman;
	
	/** Телефон */
	private String phone;
	
	/** Пациент */
	@Comment("Пациент")
	@OneToOne
	public Patient getPatient() {
		return patient;
	}

	/** Пациент */
	private Patient patient;
	
	/** Вид суицида */
	@Comment("Вид суицида")
	@OneToOne
	public VocSuicideMesType getType() {
		return type;
	}

	/** Вид суицида */
	private VocSuicideMesType type;
	
	/** Другой вид суицида */
	private String otherType;
	
	/** Место */
	@Comment("Место")
	@OneToOne
	public VocSuicideMesPlace getPlace() {
		return place;
	}

	/** Другое место суицида */
	private String otherPlace;

	/** Место */
	private VocSuicideMesPlace place;
	
	/** Присутствовали др. люди при суициде */
	@Comment("Присутствовали др. люди при суициде")
	@OneToOne
	public VocYesNo getOtherPeople() {
		return otherPeople;
	}

	/** Присутствовали др. люди при суициде */
	private VocYesNo otherPeople;
	
	/** Опьянение */
	@Comment("Опьянение")
	@OneToOne
	public VocYesNo getIntoxication() {
		return intoxication;
	}

	/** Опьянение */
	private VocYesNo intoxication;
	
	/** Направлен */
	@Comment("Направлен")
	@OneToOne
	public MisLpu getOrderLpu() {
		return orderLpu;
	}

	/** Направлен */
	private MisLpu orderLpu;
	
	/** Доставлен */
	@Comment("Доставлен")
	@OneToOne
	public MisLpu getPostedLpu() {
		return postedLpu;
	}

	/** Доставлен */
	private MisLpu postedLpu;
	
	/** Завершен */
	private Boolean isFinished;
	
	/** Социальный статус */
	@Comment("Социальный статус")
	@OneToOne
	public VocSocialStatus getSocialStatus() {return socialStatus;}

	/** Социальный статус */
	private VocSocialStatus socialStatus;
	
	/** Мед.помощь оказано СМП */
	@Comment("Мед.помощь оказано СМП")
	@OneToOne
	public VocYesNo getHelpSMP() {return helpSMP;}

	/** Мед.помощь оказано СМП */
	private VocYesNo helpSMP;
 	/** Характер суицида */
	@Comment("Характер суицида")
	@OneToOne
	public VocPsychSuicideNature getNature() {
		return nature;
	}
	/** Характер суицида */
	private VocPsychSuicideNature nature;
	
	 /**
	  * Описание
	  */
	 private String notes;
}
