package ru.ecom.mis.ejb.domain.worker;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.document.ejb.domain.DocumentFormJournal;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.jaas.ejb.domain.SecUser;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.format.DateFormat;


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
	
	/** Журналы учета бланков документов */
	@Comment("Журналы учета бланков документов")
	@OneToMany(mappedBy="responsibleWorker", cascade=CascadeType.ALL)
	public List<DocumentFormJournal> getDocumentFormJournals() {
		return theDocumentFormJournals;
	}

	public void setDocumentFormJournals(List<DocumentFormJournal> aDocumentFormJournals) {
		theDocumentFormJournals = aDocumentFormJournals;
	}

	/** Журналы учета бланков документов */
	private List<DocumentFormJournal> theDocumentFormJournals;
	
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
/*        List<WorkBook> workList = getWorkBook();
        if (workList == null || workList.size() == 0) {
            return "";
        }
        WorkBook workBook = workList.get(0);
        if (workBook == null) {
            return "";
        }*/
        return "// FIXME Worker.getPost()"; //FIXME 

 /*       VocPost post = workBook.getPost();
        if (post == null) return "";
        //return "["+post.getId()+"] "+post.getName();
        return post.getName();  //+" ("+post.getId()+")";
*/    }

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
	











}
