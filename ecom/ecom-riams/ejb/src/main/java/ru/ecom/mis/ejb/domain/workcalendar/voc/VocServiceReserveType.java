package ru.ecom.mis.ejb.domain.workcalendar.voc;

import javax.persistence.Entity;
import javax.persistence.Table;

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
public class VocServiceReserveType extends VocBaseEntity{
	/** Отображать только врачам */
	@Comment("Отображать только врачам")
	public Boolean getIsViewOnlyDoctor() {return theIsViewOnlyDoctor;}
	public void setIsViewOnlyDoctor(Boolean aIsViewOnlyDoctor) {theIsViewOnlyDoctor = aIsViewOnlyDoctor;}

	/** Отображать только врачу */
	@Comment("Отображать только врачу")
	public Boolean getIsViewOnlyMineDoctor() {return theIsViewOnlyMineDoctor;}
	public void setIsViewOnlyMineDoctor(Boolean aIsViewOnlyMineDoctor) {theIsViewOnlyMineDoctor = aIsViewOnlyMineDoctor;}

	/** Резерв для удаленных районов */
	@Comment("Резерв для удаленных районов")
	public Boolean getIsRemoteRayon() {return theIsRemoteRayon;}
	public void setIsRemoteRayon(Boolean aIsRemoteRayon) {theIsRemoteRayon = aIsRemoteRayon;}
	
	/** Отображать удаленным пользователям */
	@Comment("Отображать удаленным пользователям")
	public Boolean getIsViewRemoteUser() {return theIsViewRemoteUser;}
	public void setIsViewRemoteUser(Boolean aIsViewRemoteUser) {theIsViewRemoteUser = aIsViewRemoteUser;}

	/** Отображать удаленным пользователям */
	private Boolean theIsViewRemoteUser;
	/** Резерв для удаленных районов */
	private Boolean theIsRemoteRayon;
	/** Отображать только врачу */
	private Boolean theIsViewOnlyMineDoctor;
	/** Отображать только врачам */
	private Boolean theIsViewOnlyDoctor;
	
	/** Цвет в предварительной записи */
	@Comment("Цвет в предварительной записи")
	public String getBackground() {
		return theBackground;
	}

	public void setBackground(String aBackground) {
		theBackground = aBackground;
	}

	/** Цвет в предварительной записи */
	private String theBackground;
	
	/** Цвет текста */
	@Comment("Цвет текста")
	public String getColorText() {return theColorText;}
	public void setColorText(String aColorText) {theColorText = aColorText;}

	/** Цвет текста */
	private String theColorText;
	
	/** Потоки обслуживания */
	@Comment("Потоки обслуживания")
	public String getServiceStreams() {return theServiceStreams;}
	public void setServiceStreams(String aServiceStreams) {theServiceStreams = aServiceStreams;}

	/** Потоки обслуживания */
	private String theServiceStreams;
	
	/** Отделения */
	@Comment("Отделения")
	public String getDepartments() {return theDepartments;}
	public void setDepartments(String aDepartments) {theDepartments = aDepartments;}

	/** Отделения */
	private String theDepartments;

	/** Создавать направления без предварительной записи */
	@Comment("Создавать направления без предварительной записи")
	public Boolean getIsNoPreRecord() {return theIsNoPreRecord;}
	public void setIsNoPreRecord(Boolean aIsNoPreRecord) {theIsNoPreRecord = aIsNoPreRecord;}
	/** Создавать направления без предварительной записи */
	private Boolean theIsNoPreRecord ;

	/** Сообщение при удаленной записи */
	@Comment("Сообщение при удаленной записи")
	public String getRecordComment() {return theRecordComment;}
	public void setRecordComment(String aRecordComment) {theRecordComment = aRecordComment;}
	/** Сообщение при удаленной записи */
	private String theRecordComment ;



}
