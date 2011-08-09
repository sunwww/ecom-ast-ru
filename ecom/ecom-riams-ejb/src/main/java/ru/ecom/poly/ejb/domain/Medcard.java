package ru.ecom.poly.ejb.domain;

import static javax.persistence.CascadeType.ALL;

import java.sql.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.ecom.poly.ejb.domain.voc.VocCardIndex;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

// Медицинская карта

@Entity
@AIndexes({
	@AIndex(properties= ("number"))
    ,    @AIndex(properties= ("person"))
})
@EntityListeners(DeleteListener.class)
@Table(schema="SQLUser")
public class Medcard extends BaseEntity {
    /**
     * Номер карты *
     */
    public String getNumber() {return theNumber;}
    public void setNumber(String aNumber) {theNumber = aNumber;}


    /**
     * Дата заведения карты *
     */
    public Date getDateRegistration() {return theDateRegistration;}
    public void setDateRegistration(Date aDateRegistration) {theDateRegistration = aDateRegistration;    }

    /**
     * Регистратор *
     */
    public String getRegistrator() {return theRegistrator;}
    public void setRegistrator(String aRegistrator) {theRegistrator = aRegistrator;}

    /**
     * Регистратор *
     */
    private String theRegistrator;


    /**
     * @return Фамилия *
     */
    @Transient
    public String getLastname() {
        if(getPerson()!=null)
            return getPerson().getLastname();
        return "";
    }

    /**
     * @return Имя *
     */
    @Transient
    public String getFirstname() {
        if(getPerson()!=null)
            return getPerson().getFirstname();
        return "";
    }

    /**
     * @return Отчество *
     */
    @Transient
    public String getMiddlename() {
        if(getPerson()!=null)
            return getPerson().getMiddlename();
        return "";
    }

    /**
     * @return Дата рождения *
     */
    @Transient
    public Date getBirthday() {
    	return getPerson()!=null ? getPerson().getBirthday() : null ;
    }
    @Transient
    public String getCardIndexInfo() {
    	return theCardIndex!=null? theCardIndex.getName():"" ;
    }

    /** Талоны */
    @OneToMany(mappedBy = "medcard", cascade = ALL)
    @OrderBy("date")
    public List<Ticket> getTickets() {return theTickets;}
    public void setTickets(List<Ticket> aTickets) {theTickets = aTickets;}

    /** return Пациент */
    @OneToOne
    public Patient getPerson() {return thePerson;}
    public void setPerson(Patient aPerson) {thePerson = aPerson;}

    /** ЛПУ */
	@Comment("ЛПУ")
	@OneToOne
	public MisLpu getLpu() {return theLpu;}
	public void setLpu(MisLpu aLpu) {theLpu = aLpu;}
	
	/** Катротека */
	@Comment("Катротека")
	@OneToOne
	public VocCardIndex getCardIndex() {return theCardIndex;}
	public void setCardIndex(VocCardIndex aCardIndex) {theCardIndex = aCardIndex;}

	/** Катротека */
	private VocCardIndex theCardIndex;

    private List<Ticket> theTickets;
	/** ЛПУ */
	private MisLpu theLpu;
    /** Пациент */
    private Patient thePerson;
    /** Номер карты */
    private String theNumber;
    /** Дата заведения карты */
    private Date theDateRegistration;

}
