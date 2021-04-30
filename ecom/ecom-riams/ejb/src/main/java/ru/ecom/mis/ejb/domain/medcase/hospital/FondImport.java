package ru.ecom.mis.ejb.domain.medcase.hospital;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Comment("Госпитализации данные фонда таблица импорта")
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class FondImport extends BaseEntity{
	/** Дата импорта */
	private Date importDate;
	/** Таблица */
	private String importTable;
	/** Пользователь */
	private String username;
	/** Время импорта */
	private Time importTime;
	
	/** Имя файла */
	private String filename;
	
	/** Кол-во импортируемых данных */
	private Long cntImport;
	
	/** Кол-во дефектных данных */
	private Long cntDefect;
	
	/** Номер импорта */
	private String importNumber;
}