package ru.ecom.mis.ejb.domain.prescription;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import java.sql.Date;
import java.sql.Time;

/**
 * Журнал аннулирования результатов лабораторных исследований
 * @author user
 *
 */
@Entity
@Getter
@Setter
public class AdminChangeJournal extends BaseEntity {

	public AdminChangeJournal() {}
	public AdminChangeJournal(String cType) {
		setCType(cType);
	}
	@PrePersist
	void onPrePersist() {
		long currentTime = System.currentTimeMillis();
		createDate=new java.sql.Date(currentTime);
		createTime=new java.sql.Time(currentTime);
	}
	
	/** Назначение, результаты которого аннулированы  */
	private Long prescription;
	
	/** Визит по назначению */
	private Long medCase;
	
	/** Дата создания (аннулирования) */
	private Date createDate;
	
	/** Время создания (аннулирования) */
	private Time createTime;

	/** Пользователь, который создал запись  (аннулировал результаты) */
	private String createUsername;

	/** Причина аннулирования */
	private String annulReason;
	
	/** Рабочая функция лица, аннулировавшего анализ */
	private Long annulWorkFunction;
	
	/** Рабочая функция лица, создавшего назначение */
	private Long prescriptWorkFunction;
	
	/** Текст аннулированого результат */
	@Comment("Текст аннулированого результат")
	@Column(length=ColumnConstants.TEXT_MAXLENGHT)
	public String getAnnulRecord() {return annulRecord;}
	private String annulRecord;
	
	/** Тип */
	private String cType;
	
	/** Пациент */
	private Long patient;

	/** Дата выписки (которая была удалена) */
	private Date dateFinishDisch;

	/** Время выписки (которое было удалено) */
	private Time timeDisch;
}
