package ru.ecom.diary.ejb.domain.protocol.template;

import lombok.Getter;
import lombok.Setter;
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
@Setter
@Getter
public class TemplateProtocol extends BaseEntity {

	/** Создавать браслет по шаблону */
	private Boolean createBracelet ;
	
	/** Создавать дневник по умолчанию при приеме в лабораторию */
	private Boolean createDiaryByDefault;
	
	/** Запрет на ручное редактирование */
	private Boolean disableEdit;
	

    /** Текст */
    @Column(length = ColumnConstants.TEXT_MAXLENGHT)
    public String getText() { return text ; }

    /** Категории шаблона */
	@Comment("Категории шаблона")
	@ManyToMany
	public List<TemplateCategory> getCategories() {return categories;}


	/** Медицинская услуга */
	@Comment("Медицинская услуга")
	@ManyToOne
	public MedService getMedService() {return medService;}

	/** Информация по шаблону протокола */
	@Comment("Информация по шаблону протокола")
	@Transient
	public String getInformation() {return "";}

	
	@Comment("Информация по категориям")
	@Transient
	public String getCategoriesInfo() {
		StringBuilder ret = new StringBuilder() ;
		for (TemplateCategory categ : categories) {
			ret.append(", ").append(categ.getFullname()).append("<br>") ;
		}
		return ret.length()>2 ? ret.substring(2) : ret.toString() ;
	}
	
	/** Группы пользователей */
	@Comment("Группы пользователей")
	@ManyToMany
	public List<SecGroup> getSecGroups() {return secGroups;}

	/** Группы пользователей */
	private List<SecGroup> secGroups;

	
	/** Медицинская услуга */
	private MedService medService;
	/** Дата создания */
	private Date date;
	/** Пользователь */
	private String username;
	/** Категории шаблона */
	private List<TemplateCategory> categories;
    /** Ключевые слова */
    private String keyWord ;
    /** Текст */    
    private String text ;
    /** Заголовок */
    private String title ;

}
