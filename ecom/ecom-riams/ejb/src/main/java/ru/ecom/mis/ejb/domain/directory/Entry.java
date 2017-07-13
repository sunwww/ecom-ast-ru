package ru.ecom.mis.ejb.domain.directory;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;


/** Номер телефона */
@Comment("Номер телефона")
@Entity
@Table(schema="SQLUser")
@EntityListeners(DeleteListener.class)
public class Entry extends BaseEntity{
            
           /** Персона */
        @Comment("Персона")
        @OneToOne
        public WorkFunction getPerson() {return thePerson;}
        public void setPerson(WorkFunction aPerson) {thePerson = aPerson;}
        private WorkFunction thePerson;
        
        /** Отделение */
        @Comment("Отделение")
        @OneToOne
        public Department getDepartment() {return theDepartment;}
        public void setDepartment(Department aDepartment) {theDepartment = aDepartment;}
        private Department theDepartment;
        
        /** Внутренний номер */
	@Comment("Внутренний номер")
	public String getInsideNumber() {return theInsideNumber;}
	public void setInsideNumber(String aInsideNumber) {theInsideNumber = aInsideNumber;}
	private String theInsideNumber;
	
        /** Комментарий */
        @Comment("Комментарий")
        public String getComment() {return theComment;}
        public void setComment(String aComment) {theComment = aComment;}
        private String theComment;
        
       
        /** номера */
	@Comment("Номера")
	@OneToMany(mappedBy="entry", cascade=CascadeType.ALL)
	public List<TelephoneNumber> getTelephoneNumbers() {return theTelephoneNumbers;}
	public void setTelephoneNumbers(List<TelephoneNumber> aTelephoneNumbers) {theTelephoneNumbers = aTelephoneNumbers;}
	private List<TelephoneNumber> theTelephoneNumbers;
	
	

}