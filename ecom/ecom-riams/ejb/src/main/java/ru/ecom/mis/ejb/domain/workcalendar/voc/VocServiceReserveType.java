package ru.ecom.mis.ejb.domain.workcalendar.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.VocBaseEntity;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Тип резерва обслуживания
 * @author azviagin
 *
 */
@Comment("Тип резерва обслуживания")
@Entity
@Table(schema="SQLUser")
@Getter
@Setter
public class VocServiceReserveType extends VocBaseEntity{
	/** Отображать удаленным пользователям */
	private Boolean isViewRemoteUser;
	/** Резерв для удаленных районов */
	private Boolean isRemoteRayon;
	/** Отображать только врачу */
	private Boolean isViewOnlyMineDoctor;
	/** Отображать только врачам */
	private Boolean isViewOnlyDoctor;
	/** Цвет в предварительной записи */
	private String background;
	/** Цвет текста */
	private String colorText;
	/** Потоки обслуживания */
	private String serviceStreams;
	/** Отделения */
	private String departments;
	/** Создавать направления без предварительной записи */
	private Boolean isNoPreRecord ;
	/** Сообщение при удаленной записи */
	private String recordComment ;



}
