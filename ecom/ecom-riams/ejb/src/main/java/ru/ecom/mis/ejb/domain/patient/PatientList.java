package ru.ecom.mis.ejb.domain.patient;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

@Entity
@Getter
@Setter
public class PatientList extends BaseEntity{

	/** Тип списка */
	private Long type;
	
	/** Название списка */
	private String name;
	
	/** Цвет сообщения */
	private String colorName;
	
	/** Отображать цвет в WebTrail */
	private Boolean isViewInWebTrail;
	/** Отображать цвет при поиске */
	private Boolean isViewWhenSeaching;
	/** Цвет текста */
	private String colorText;
	/** Сообщение */
	private String message;
}
