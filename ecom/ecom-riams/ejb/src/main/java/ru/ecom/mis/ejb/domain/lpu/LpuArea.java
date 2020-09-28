package ru.ecom.mis.ejb.domain.lpu;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.lpu.voc.VocAreaType;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.util.List;

/**
 * Участок
 */
@Entity
@Table(schema="SQLUser")
@AIndexes(
	{
		@AIndex(unique= false, properties = {"lpu"})
	}
)
public class LpuArea extends BaseEntity {

	/**
	 * Педиатрический?
	 * @return
	 */
	@Transient
	public boolean isPediatric() {
		return theType!=null && theType.isPediatric();
	}
    /** Тип участка */
    @OneToOne
    public VocAreaType getType() { return theType ; }
    public void setType(VocAreaType aType) { theType = aType ; }

    /** Тип участка */
    private VocAreaType theType ;
    /** Номер участка */
    public String getNumber() { return theNumber ; }
    public void setNumber(String aNumber) { theNumber = aNumber ; }


    /** Название учaстка */
    @Transient
    public String getName() {
        return theNumber + " " + getTypeName();
    }
    public void setName(String aName) { }


    public String getComment() { return theComment ; }
    public void setComment(String aComment) { theComment = aComment ; }

    /** ЛПУ */
    @ManyToOne
    public MisLpu getLpu() { return theMisLpu ; }
    public void setLpu(MisLpu aMisLpu) { theMisLpu = aMisLpu ; }

    /** Адреса */
    @OneToMany(mappedBy = "area", cascade= CascadeType.ALL)
    public List<LpuAreaAddressText> getAddresses() { return theAddresses ; }
    public void setAddresses(List<LpuAreaAddressText> aAddresses) { theAddresses = aAddresses ; }

    /** Название типа участка */
    @Transient
    public String getTypeName() {
        return theType!=null ? theType.getName() : "" ;
    }
    
    public void setTypeName(String aTypeName) { }

    /** Адреса */
    private List<LpuAreaAddressText> theAddresses ;
    /** ЛПУ */
    private MisLpu theMisLpu ;
    /** Комментарий */
    private String theComment ;
    /** Номер участка */
    private String theNumber ;
    
    /** Участковый */
	@Comment("Участковый")
	@OneToOne
	public WorkFunction getWorkFunction() {return theWorkFunction;}
	public void setWorkFunction(WorkFunction aWorkFunction) {theWorkFunction = aWorkFunction;}

	/** Участковый */
	private WorkFunction theWorkFunction;
	
	/** Код подразделения */
	@Comment("Код подразделения")
	public String getCodeDepartment() {return theCodeDepartment;}
	public void setCodeDepartment(String aCodeDepartment) {theCodeDepartment = aCodeDepartment;}

	/** Код подразделения */
	private String theCodeDepartment;
	
	/** Отображать в пациенте */
	@Comment("Отображать в пациенте")
	public Boolean getIsViewInfoPatient() {return theIsViewInfoPatient;}
	public void setIsViewInfoPatient(Boolean aIsViewInfoPatient) {theIsViewInfoPatient = aIsViewInfoPatient;}

	/** Отображать в пациенте */
	private Boolean theIsViewInfoPatient;
}
