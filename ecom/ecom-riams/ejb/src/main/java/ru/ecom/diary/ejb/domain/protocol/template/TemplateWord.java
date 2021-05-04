package ru.ecom.diary.ejb.domain.protocol.template;

import java.sql.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.jaas.ejb.domain.SecGroup;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Created by IntelliJ IDEA.
 * User: STkacheva
 * Date: 21.12.2006
 * Time: 10:55:14
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(schema="SQLUser")
@Setter
@Getter
public class TemplateWord extends BaseEntity {

    /** Сокращение */
    @Column(unique = true)
    public String getReduction() { return reduction ; }


    /** Расшифровка */
    private String decryption ;
    /** Сокращение */
    private String reduction ;
    

	/** Группы пользователей */
	@Comment("Группы пользователей")
	@ManyToMany
	public List<SecGroup> getSecGroups() {return secGroups;}

	/** Группы пользователей */
	private List<SecGroup> secGroups;
	/** Дата редактирования */
	private Date editDate;
	/** Дата создания */
	private Date createDate;
	/** Пользователь */
	private String createUsername;
	
	/** Пользователь, отредактировающий запись */
	private String editUsername;
}
