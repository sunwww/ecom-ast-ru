package ru.ecom.mis.ejb.domain.worker;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.worker.listener.StateListListener;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

/**
 * Штатное расписание
 */        
@Entity
@EntityListeners(StateListListener.class)
@Comment("Штатное расписание")
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(properties={"lpu"})
})
public class Staff extends BaseEntity {
	
	/** Штатные единицы */
	@Comment("Штатные единицы")
	@OneToMany(mappedBy="staff", cascade=CascadeType.ALL)
	public List<StaffUnit> getStaffUnits() {
		return theStaffUnits;
	}

	public void setStaffUnits(List<StaffUnit> aStaffUnits) {
		theStaffUnits = aStaffUnits;
	}

	/** Штатные единицы */
	private List<StaffUnit> theStaffUnits;
 
    /** Ставок всего */
    @Comment("Ставок всего")
    @Transient
    public BigDecimal getFullRate() {
    	return add(getBudjetRate(), getOffBudjetRate());
    }
    
    /** Ставок бюджетных */
    @Comment("Ставок бюджетных")
    @Transient
    public BigDecimal getBudjetRate() {
    	BigDecimal ret = new BigDecimal(0) ;
    	for(StaffUnit u : getStaffUnits()) {
    		if(u.getBudjet()!=null && u.getBudjet()) {
    			ret = add(ret, u.getTotalAmount()) ;
    		}
    	}
    	return ret ;
    }
    
	private static BigDecimal add(BigDecimal aParent, BigDecimal aBd) {
		return aBd!=null ? aParent.add(aBd) : aParent ;
	}

	private static BigDecimal minus(BigDecimal aParent, BigDecimal aBd) {
		if(aBd!=null) {
			aBd = aBd.multiply(new BigDecimal(-1));
			return aParent.add(aBd);
		} else {
			return aParent ;
		}
	}
	
    /** Ставок внебюджетных */
    @Comment("Ставок внебюджетных")
    @Transient
    public BigDecimal getOffBudjetRate() {
    	BigDecimal ret = new BigDecimal(0) ;
    	for(StaffUnit u : getStaffUnits()) {
    		if(u.getBudjet()==null || !u.getBudjet()) {
    			ret = add(ret, u.getTotalAmount()) ;
    		}
    	}
    	return ret ;
    }
    
    /** Ставок свободных всего*/
    @Comment("Ставок свободных всего")
    @Transient
    public BigDecimal getFreeFullRate() {
    	return add(getFreeBudjetRate(), getOffBudjetRate());
    }
    
    /** Ставок свободных бюджетных */
    @Comment("Ставок свободных бюджетных")
    @Transient
    public BigDecimal getFreeBudjetRate() {
    	BigDecimal ret = getBudjetRate() ;
    	if(ret==null)  {
    		return new BigDecimal(-1);
    	}
    	for(StaffUnit u : getStaffUnits()) {
    		if(u.getBudjet()!=null && u.getBudjet()) {
    			ret = minus(ret, u.getBusyAmount());
    		}
    	}
    	return ret;
    }
    
    /** Ставок свободных внебюджетных */
    @Comment("Ставок свободных внебюджетных")
    @Transient
    public BigDecimal getFreeOffBudjetRate() { 
    	BigDecimal ret = getBudjetRate() ;
    	if(ret==null)  {
    		return new BigDecimal(-1);
    	}
    	for(StaffUnit u : getStaffUnits()) {
    		if(u.getBudjet()==null || !u.getBudjet()) {
    			ret = minus(ret, u.getBusyAmount());
    		}
    	}
    	return ret;
    }
    
    /** ЛПУ */
    @Comment("ЛПУ")
    @ManyToOne
    public MisLpu getLpu() { return theLpu ; }
    public void setLpu(MisLpu aLpu) { theLpu = aLpu ; }

    /** ЛПУ */
    private MisLpu theLpu ;








}
