package ru.ecom.mis.ejb.domain.worker;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.jaas.ejb.domain.SecUser;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.format.DateFormat;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.List;


/**
 * Сотрудник ЛПУ
 */
@Entity
@Comment("Сотрудник ЛПУ")
@AIndexes({
	@AIndex(properties={"person"}),
	@AIndex(properties={"lpu"})
})
@Table(schema="SQLUser")
@Getter
@Setter
public class Worker extends BaseEntity {
	/** Персона */
	@Comment("Персона")
	@ManyToOne
	public Patient getPerson() {
		return person;
	}
	/** Персона */
	private Patient person;
	
	/** Рабочие функции */
	@Comment("Рабочие функции")
	@OneToMany(mappedBy="worker", cascade=CascadeType.ALL)
	public List<PersonalWorkFunction> getWorkFunctions() {
		return workFunctions;
	}
	/** Рабочие функции */
	private List<PersonalWorkFunction> workFunctions;
    
	
	/** ЛПУ */
    @Comment("ЛПУ")
	@ManyToOne
    public MisLpu getLpu() { return lpu ; }
    /** ЛПУ */
    private MisLpu lpu ;

    
    /** Информация о сотруднике */
    @Comment("Информация о сотруднике")
    @Transient
    public String getWorkerInfo() {
        StringBuilder sb = new StringBuilder();
        if(person!=null) {
            add(sb, person.getLastname(),"");
            add(sb, person.getFirstname()," ");
            add(sb, person.getMiddlename()," ");
            if(person.getBirthday()!=null) {
                add(sb, DateFormat.formatToDate(person.getBirthday())," ") ;
            }
        } else {
        	sb.append("ОШИБКА: Не выбрана персона.") ;
        }
        return sb.toString();
    }
    public void setWorkerInfo(String aPersonInfo) { }


    @Comment("ФИО и должность мед. специалиста")
    @Transient
    public String getDoctorInfo() {
        StringBuilder sb = new StringBuilder();
        if(person!=null) {
            add(sb, person.getLastname(),"");
            add(sb, person.getFirstname()," ");
            add(sb, person.getMiddlename()," ");
            //add(sb, getPost()," - ");
        } else {
        	sb.append("ОШИБКА: Не выбрана персона.") ;
        }
        
        return sb.toString();
    }
    
    public void setDoctorInfo(String s) {}

    @Comment("Должность")
    @Transient
    public String getPost() {
        return "// FIXME Worker.getPost()"; //FIXME
    }

    private static void add(StringBuilder aSb, String aStr, String aPre) {
        if(!StringUtil.isNullOrEmpty(aStr)) {
            if(aSb.length()!=0) aSb.append(aPre) ;
            aSb.append(aStr) ;
        }
    }
    /** Имя входа в систему */
	@Comment("Имя входа в систему")
	@OneToOne
	@Deprecated
	public SecUser getSecUser() {
		return secUser;
	}
	/** Имя входа в систему */
	private SecUser secUser;
	
	/** Пользователь, который последний редактировал запись */
	private String editUsername;
	/** Пользователь, который создал запись */
	private String createUsername;
	/** Время редактрования */
	private Time editTime;
	/** Время создания */
	private Time createTime;
	/** Дата редактирования */
	private Date editDate;
	/** Дата создания */
	private Date createDate;
}
