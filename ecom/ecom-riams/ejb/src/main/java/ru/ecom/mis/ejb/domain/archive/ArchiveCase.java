package ru.ecom.mis.ejb.domain.archive;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Getter
@Setter
public class ArchiveCase extends BaseEntity {

	/** Номер стат. карты */
	private Long statCard;
	
	/** Пациент */
	private Long patient;
	
	/** Дата создания */
	private Date createDate;
	
	/** Время создания */
	private Time createTime;
	
	/** Пользователь, создавший запись */
	private String createUsername;
	
	/** Рабочая функция создателя */
	private Long workFunction;
	
}
