package ru.ecom.mis.ejb.domain.directory;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;


/** Номер телефона */
@Comment("Номер телефона")
@Entity
@Table(schema="SQLUser")
@EntityListeners(DeleteListener.class)
@Getter
@Setter
public class Entry extends BaseEntity{
            
           /** Персона */
        @Comment("Персона")
        @OneToOne
        public WorkFunction getPerson() {return person;}
        private WorkFunction person;
        
        /** Отделение */
        @Comment("Отделение")
        @OneToOne
        public Department getDepartment() {return department;}
        private Department department;
        
        /** Внутренний номер */
	private String insideNumber;
	
        /** Комментарий */
        private String comment;
        
       
        /** номера */
	@Comment("Номера")
	@OneToMany(mappedBy="entry", cascade=CascadeType.ALL)
	public List<TelephoneNumber> getTelephoneNumbers() {return telephoneNumbers;}
	private List<TelephoneNumber> telephoneNumbers;
	
	

}