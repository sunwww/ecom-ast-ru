package ru.ecom.mis.ejb.domain.worker;

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
public class Worker extends BaseEntity {
	/** Персона */
	@Comment("Персона")
	@ManyToOne
	public Patient getPerson() {
		return thePerson;
	}

	public void setPerson(Patient aPerson) {
		thePerson = aPerson;
	}

	/** Персона */
	private Patient thePerson;
	
	/** Рабочие функции */
	@Comment("Рабочие функции")
	@OneToMany(mappedBy="worker", cascade=CascadeType.ALL)
	public List<PersonalWorkFunction> getWorkFunctions() {
		return theWorkFunctions;
	}

	public void setWorkFunctions(List<PersonalWorkFunction> aWorkFunctions) {
		theWorkFunctions = aWorkFunctions;
	}

	/** Рабочие функции */
	private List<PersonalWorkFunction> theWorkFunctions;
    
	
	/** ЛПУ */
    @Comment("ЛПУ")
	@ManyToOne
    public MisLpu getLpu() { return theLpu ; }
    public void setLpu(MisLpu aLpu) { theLpu = aLpu ; }
    /** ЛПУ */
    private MisLpu theLpu ;

    
    /** Информация о сотруднике */
    @Comment("Информация о сотруднике")
    @Transient
    public String getWorkerInfo() {
        StringBuilder sb = new StringBuilder();
        if(thePerson!=null) {
            add(sb, thePerson.getLastname(),"");
            add(sb, thePerson.getFirstname()," ");
            add(sb, thePerson.getMiddlename()," ");
            if(thePerson.getBirthday()!=null) {
                add(sb, DateFormat.formatToDate(thePerson.getBirthday())," ") ;
            }
        } else {
        	sb.append("ОШИБКА: Не выбрана персона.") ;
        }
        return sb.toString();
    }
    public void setWorkerInfo(String aPersonInfo) { }


    // IKO 2007-04-24 +++

    @Comment("ФИО и должность мед. специалиста")
    @Transient
    public String getDoctorInfo() {
        StringBuilder sb = new StringBuilder();
        if(thePerson!=null) {
            add(sb, thePerson.getLastname(),"");
            add(sb, thePerson.getFirstname()," ");
            add(sb, thePerson.getMiddlename()," ");
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

    public void setPost(String s) {}

    // IKO ====================

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
		return theSecUser;
	}
	public void setSecUser(SecUser aSecUser) {
		theSecUser = aSecUser;
	}
	/** Имя входа в систему */
	private SecUser theSecUser;
	






	/** Дата создания */
	@Comment("Дата создания")
	public Date getCreateDate() {return theCreateDate;}
	public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
	
	/** Дата редактирования */
	@Comment("Дата редактирования")
	public Date getEditDate() {return theEditDate;}
	public void setEditDate(Date aEditDate) {theEditDate = aEditDate;}
	
	/** Время создания */
	@Comment("Время создания")
	public Time getCreateTime() {return theCreateTime;}
	public void setCreateTime(Time aCreateTime) {theCreateTime = aCreateTime;}
	/** Время редактрования */
	@Comment("Время редактрования")
	public Time getEditTime() {return theEditTime;}
	public void setEditTime(Time aEditTime) {theEditTime = aEditTime;}
	/** Пользователь, который создал запись */
	@Comment("Пользователь, который создал запись")
	public String getCreateUsername() {return theCreateUsername;}
	public void setCreateUsername(String aCreateUsername) {theCreateUsername = aCreateUsername;}
	/** Пользователь, который последний редактировал запись */
	@Comment("Пользователь, который последний редактировал запись")
	public String getEditUsername() {return theEditUsername;}
	public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

	/** Пользователь, который последний редактировал запись */
	private String theEditUsername;
	/** Пользователь, который создал запись */
	private String theCreateUsername;
	/** Время редактрования */
	private Time theEditTime;
	/** Время создания */
	private Time theCreateTime;
	/** Дата редактирования */
	private Date theEditDate;
	/** Дата создания */
	private Date theCreateDate;





}
