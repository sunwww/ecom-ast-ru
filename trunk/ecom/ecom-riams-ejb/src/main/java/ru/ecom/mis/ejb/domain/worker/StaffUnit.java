package ru.ecom.mis.ejb.domain.worker;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.worker.voc.VocPost;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.StringUtil;

/**
 * Штатная единица
 * @author azviagin
 *
 */
@Comment("Штатная единица")
@Entity
@Table(schema="SQLUser")
@AIndexes({
	@AIndex(properties={"staff"})
})
public class StaffUnit extends BaseEntity{
	
	/** Штатное расписание */
	@Comment("Штатное расписание")
	@ManyToOne
	public Staff getStaff() {
		return theStaff;
	}

	public void setStaff(Staff aStaff) {
		theStaff = aStaff;
	}

	/** Штатное расписание */
	private Staff theStaff;
	
	/** Записи трудовых книжек */
	@Comment("Записи трудовых книжек")
	@OneToMany(mappedBy="staffUnit")
	public List<WorkBookRecord> getWorkBookRecords() {
		return theWorkBookRecords;
	}

	public void setWorkBookRecords(List<WorkBookRecord> aWorkBookRecords) {
		theWorkBookRecords = aWorkBookRecords;
	}

	/** Записи трудовых книжек */
	private List<WorkBookRecord> theWorkBookRecords;
	
	/** Должность */
	@Comment("Должность")
	@OneToOne
	public VocPost getPost() {
		return thePost;
	}

	public void setPost(VocPost aPost) {
		thePost = aPost;
	}

	/** Должность */
	private VocPost thePost;
	
	/** Бюджетная ставка */
	@Comment("Бюджетная ставка")
	public Boolean getBudjet() {
		return theBudjet;
	}

	public void setBudjet(Boolean aBudjet) {
		theBudjet = aBudjet;
	}

	/** Бюджетная ставка */
	private Boolean theBudjet;
	
	/** Общее количество */
	@Comment("Общее количество")
	public BigDecimal getTotalAmount() {
		return theTotalAmount;
	}

	public void setTotalAmount(BigDecimal aTotalAmount) {
		theTotalAmount = aTotalAmount;
	}

	/** Общее количество */
	private BigDecimal theTotalAmount;
	
	/** Занято */
	@Comment("Занято")
	@Transient
	public BigDecimal getBusyAmount() {
		BigDecimal ret = new BigDecimal(0) ;
		for(WorkBookRecord r : getWorkBookRecords()) {
			ret = add(ret, r.getStaffUnitAmount());
		}
		return ret;
	}

	private BigDecimal add(BigDecimal aParent, BigDecimal aBd) {
		return aBd!=null ? aParent.add(aBd) : aParent ;
	}
	

	/** Информация о единице */
	@Comment("Информация о единице")
	@Transient
	public String getInfo() {
		StringBuilder sb = new StringBuilder() ;
		if(getStaff()!=null && getStaff().getLpu()!=null && !StringUtil.isNullOrEmpty(getStaff().getLpu().getName())) {
			sb.append("ЛПУ: "+getStaff().getLpu().getName()) ;
		}
		if(getPost()!=null && !StringUtil.isNullOrEmpty(getPost().getName())) {
			sb.append(", должность: "+getPost().getName()) ;
		}
		return sb.toString();
	}
}
