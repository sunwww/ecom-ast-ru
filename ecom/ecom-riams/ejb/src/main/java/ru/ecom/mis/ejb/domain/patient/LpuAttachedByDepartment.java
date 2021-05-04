package ru.ecom.mis.ejb.domain.patient;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class LpuAttachedByDepartment extends BaseEntity {

	/** Участок */
	@Comment("Участок")
	@OneToOne
	public LpuArea getArea() {return area;}

	/** Пациент */
	@Comment("Пациент")
	@ManyToOne
	public Patient getPatient() {return patient;}

	/** ЛПУ */
	@Comment("ЛПУ")
	@OneToOne
	public MisLpu getLpu() {return lpu;}

	@Transient
	public String getLpuFullname() {
		return "" ;
	}
	public void setLpuFullname(String aLpuFullname) {}
	/** ЛПУ */
	private MisLpu lpu;
	/** Участок */
	private LpuArea area;
	/** Пациент */
	private Patient patient;
	/** Прикреплен до */
	private Date dateTo;
	/** Откреплен с */
	private Date dateFrom;
	
	/** Тип прикрепления */
	@Comment("Тип прикрепления")
	@OneToOne
	public VocAttachedType getAttachedType() {return attachedType;}

	/** Тип прикрепления */
	private VocAttachedType attachedType;
	/** Пользователь редактировавший услуги */
	private String editUsernameRender;
	/** Время редактирование услуги */
	private Time editTimeRender;
	/** Дата редактирования услуги */
	private Date editDateRender;
	/** Пользователь, который последний редактировал запись */
	private String editUsername;
	/** Пользователь, который создал запись */
	private String createUsername;
	/** Время редактрования */
	private Time editTime;
	/** Время создания */
	private Time createTime;
	/** Дата редактирования */
	private Date editDate;
	/** Дата создания */
	private Date createDate;
	/** Текст дефекта */
	private String defectText;
	/** Дата импорта ( стар. Период дефекта ) */
	private String defectPeriod;
	/** ЛПУ открепления */
	private String lpuTo;
	
	/** Страховая компания */
	@Comment("Страховая компания")
	@OneToOne
	public RegInsuranceCompany getCompany() {return company;}

	/** Страховая компания */
	private RegInsuranceCompany company;

	/** Подача производилась по неактуальному полису */
	private Boolean noActualPolicy;

	/** Прикрепление не актуально */
	private Boolean noActuality;
	
	/** Результаты последней проверки */
	private String checkResult;
	
	/** Пациент сменил адрес */
	private Boolean newAddress;

	/** Дата последнего экспорта в ФОМС */
	private Date exportDate;
}
