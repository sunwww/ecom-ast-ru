package ru.ecom.diary.ejb.domain.protocol.template;

import ru.ecom.diary.ejb.domain.category.TemplateCategory;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.ejb.services.util.ColumnConstants;
import ru.ecom.jaas.ejb.domain.SecGroup;
import ru.ecom.mis.ejb.domain.medcase.MedService;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: STkacheva
 * Date: 21.12.2006
 * Time: 10:55:27
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(properties="username")
    ,@AIndex(properties="medService")
    }) 
@EntityListeners(DeleteListener.class)
public class TemplateProtocol extends BaseEntity {

	/** Создавать браслет по шаблону */
	@Comment("Создавать браслет по шаблону")
	public Boolean getCreateBracelet() {return theCreateBracelet;}
	public void setCreateBracelet(Boolean aCreateBracelet) {theCreateBracelet = aCreateBracelet;}
	private Boolean theCreateBracelet ;
	
	/** Создавать дневник по умолчанию при приеме в лабораторию */
	@Comment("Создавать дневник по умолчанию при приеме в лабораторию")
	public Boolean getCreateDiaryByDefault() {return theCreateDiaryByDefault;}
	public void setCreateDiaryByDefault(Boolean aCreateDiaryByDefault) {theCreateDiaryByDefault = aCreateDiaryByDefault;}
	/** Создавать дневник по умолчанию при приеме в лабораторию */
	private Boolean theCreateDiaryByDefault;
	
	/** Запрет на ручное редактирование */
	@Comment("Запрет на ручное редактирование")
	public Boolean getDisableEdit() {return theDisableEdit;}
	public void setDisableEdit(Boolean aDisableEdit) {theDisableEdit = aDisableEdit;}
	/** Запрет на ручное редактирование */
	private Boolean theDisableEdit;
	
	/** Заголовок */
    public String getTitle() { return theTitle ; }
    public void setTitle(String aTitle) { theTitle = aTitle ; }

    /** Текст */
    @Column(length = ColumnConstants.TEXT_MAXLENGHT)
    public String getText() { return theText ; }
    public void setText(String aText) { theText = aText ; }

    /** Ключевые слова */
    public String getKeyWord() { return theKeyWord ; }
    public void setKeyWord(String aKeyWord) { theKeyWord = aKeyWord ; }

    /** Категории шаблона */
	@Comment("Категории шаблона")
	@ManyToMany
	public List<TemplateCategory> getCategories() {return theCategories;}
	public void setCategories(List<TemplateCategory> aCategories) {theCategories = aCategories;}

	@Comment("Пользователь")
	/** Пользователь */
	public String getUsername() {return theUsername;}
	public void setUsername(String aUsername) {theUsername = aUsername;}

	/** Дата создания */
	@Comment("Дата создания")
	public Date getDate() {return theDate;}
	public void setDate(Date aDate) {theDate = aDate;}

	/** Медицинская услуга */
	@Comment("Медицинская услуга")
	@ManyToOne
	public MedService getMedService() {return theMedService;}
	public void setMedService(MedService aMedService) {theMedService = aMedService;}

	/** Информация по шаблону протокола */
	@Comment("Информация по шаблону протокола")
	@Transient
	public String getInformation() {return "";}

	
	@Comment("Информация по категориям")
	@Transient
	public String getCategoriesInfo() {
		StringBuilder ret = new StringBuilder() ;
		for (TemplateCategory categ : theCategories) {
			ret.append(", ").append(categ.getFullname()).append("<br>") ;
		}
		return ret.length()>2 ? ret.substring(2) : ret.toString() ;
	}
	
	/** Группы пользователей */
	@Comment("Группы пользователей")
	@ManyToMany
	public List<SecGroup> getSecGroups() {return theSecGroups;}
	public void setSecGroups(List<SecGroup> aSecGroups) {theSecGroups = aSecGroups;}

	/** Группы пользователей */
	private List<SecGroup> theSecGroups;

	
	/** Медицинская услуга */
	private MedService theMedService;
	/** Дата создания */
	private Date theDate;
	/** Пользователь */
	private String theUsername;
	/** Категории шаблона */
	private List<TemplateCategory> theCategories;
    /** Ключевые слова */
    private String theKeyWord ;
    /** Текст */    
    private String theText ;
    /** Заголовок */
    private String theTitle ;

}
