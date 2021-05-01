package ru.ecom.mis.ejb.domain.medcase;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.ecom.mis.ejb.domain.medcase.voc.VocAnesthesia;
import ru.ecom.mis.ejb.domain.medcase.voc.VocAnesthesiaMethod;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

/**
 * Анестезия
 * @author azviagin
 *
 */

@Comment("Анестезия")
@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(properties="surgicalOperation"),
	@AIndex(properties="manipulation")
    })
@Getter
@Setter
public class Anesthesia extends BaseEntity{

	/** Метод */
	@Comment("Метод")
	@OneToOne
	public VocAnesthesiaMethod getMethod() {return method;}
	private VocAnesthesiaMethod method;
	
	/** Вид анестезии */
	@Comment("Вид анестезии")
	@OneToOne
	public VocAnesthesia getType() {return type;}
	private VocAnesthesia type;

	/** Длительность в минутах */
	private Integer duration;
	
	/** Описание */
	@Comment("Описание")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getDescription() {return description;}
	private String description;
	
	/** Хирургическая операция */
	@Comment("Хирургическая операция")
	@ManyToOne
	public SurgicalOperation getSurgicalOperation() {return surgicalOperation;}
	private SurgicalOperation surgicalOperation;

	/* Манипуляция*/
	@Comment("Манипуляция")
	@ManyToOne
	public MedicalManipulation getManipulation() {return manipulation;}
	private MedicalManipulation manipulation;

	/** Анестезиолог */
	@Comment("Анестезист")
	@OneToOne
	public WorkFunction getAnesthesist() {return anesthesist;}
	private WorkFunction anesthesist;
	
	/** Дата создания */
	private Date createDate;

	/** Пользователь создавший запись */
	private String createUsername;

	@Transient
	public String getMethodInfo() {return method!=null?method.getName():"" ;}
	@Transient
	public String getAnesthesistInfo() {return anesthesist != null ? anesthesist.getWorkerInfo():"" ;}
	
	/** Мед.услуга */
	@Comment("Мед.услуга")
	@OneToOne
	public MedService getMedService() {return medService;}
	private MedService medService;
	
	/** Время создания */
	private Time createTime;
}