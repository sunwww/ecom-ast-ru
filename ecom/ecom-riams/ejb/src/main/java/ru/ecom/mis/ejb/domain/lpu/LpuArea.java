package ru.ecom.mis.ejb.domain.lpu;

import lombok.Getter;
import lombok.Setter;
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
		@AIndex(properties = {"lpu"})
	}
)
@Getter
@Setter
public class LpuArea extends BaseEntity {

	/**
	 * Педиатрический?
	 * @return
	 */
	@Transient
	public boolean isPediatric() {
		return type!=null && type.isPediatric();
	}
    /** Тип участка */
    @OneToOne
    public VocAreaType getType() { return type ; }

    /** Тип участка */
    private VocAreaType type ;

    /** Название учaстка */
    @Transient
    public String getName() {
        return number + " " + getTypeName();
    }
    public void setName(String aName) { }

    /** ЛПУ */
    @ManyToOne
    public MisLpu getLpu() { return lpu; }

    /** Адреса */
    @OneToMany(mappedBy = "area", cascade= CascadeType.ALL)
    public List<LpuAreaAddressText> getAddresses() { return addresses ; }

    /** Название типа участка */
    @Transient
    public String getTypeName() {
        return type!=null ? type.getName() : "" ;
    }
    
    public void setTypeName(String aTypeName) { }

    /** Адреса */
    private List<LpuAreaAddressText> addresses ;
    /** ЛПУ */
    private MisLpu lpu;
    /** Комментарий */
    private String comment ;
    /** Номер участка */
    private String number ;
    
    /** Участковый */
	@Comment("Участковый")
	@OneToOne
	public WorkFunction getWorkFunction() {return workFunction;}

	/** Участковый */
	private WorkFunction workFunction;
	
	/** Код подразделения */
	private String codeDepartment;
	
	/** Отображать в пациенте */
	private Boolean isViewInfoPatient;
}
