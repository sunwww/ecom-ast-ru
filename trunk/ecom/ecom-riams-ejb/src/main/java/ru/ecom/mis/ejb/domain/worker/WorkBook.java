package ru.ecom.mis.ejb.domain.worker;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Трудовая книжка
 */
@Entity
@Comment("Трудовая книжка")
@Table(schema="SQLUser")
public class WorkBook extends BaseEntity {
	
	/** Записи трудовой книжки */
	@Comment("Записи трудовой книжки")
	@OneToMany(mappedBy="workBook", cascade=CascadeType.ALL)
	public List<WorkBookRecord> getWorkBookRecords() {
		return theWorkBookRecords;
	}

	public void setWorkBookRecords(List<WorkBookRecord> aWorkBookRecords) {
		theWorkBookRecords = aWorkBookRecords;
	}

	/** Записи трудовой книжки */
	private List<WorkBookRecord> theWorkBookRecords;
 
    /** Номер трудовой книжки */
    @Comment("Номер трудовой книжки")
	public Integer getNumberBook() { return theNumberBook ; }
    public void setNumberBook(Integer aNumberBook) { theNumberBook = aNumberBook ; }
    /** Номер трудовой книжки */
    private Integer theNumberBook ;
    
    /** Дата заведения */
    @Comment("Дата заведения")
    public Date getDateStart() { return theDateStart ; }
    public void setDateStart(Date aDateStart) { theDateStart = aDateStart ; }
    /** Дата заведения */
    private Date theDateStart ;
    
    /** Персона */
    @Comment("Персона")
    @ManyToOne
    public Patient getPerson() { return thePerson ; }
    public void setPerson(Patient aPerson) { thePerson = aPerson ; }
    /** Персона*/
    private Patient thePerson ;
    
    /** Серия трудовой книжки */
	@Comment("Серия трудовой книжки")
	public String getSeriesBook() {
		return theSeriesBook;
	}

	public void setSeriesBook(String aSeriesBook) {
		theSeriesBook = aSeriesBook;
	}

	/** Серия трудовой книжки */
	private String theSeriesBook;
   
}
