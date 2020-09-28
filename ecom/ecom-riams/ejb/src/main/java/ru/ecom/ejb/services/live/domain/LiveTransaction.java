package ru.ecom.ejb.services.live.domain;

import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

@Entity
@AIndexes({
	@AIndex(name="stardate", properties = {"startYear", "startMonth", "startDay"})
	,@AIndex( properties = {"username"})
})
@Table(schema="SQLUser")
public class LiveTransaction {
	 /** Идентификатор */
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() { return theId ; }
    public void setId(long aId) { theId = aId ; }
	
    /** Сущности */
	@Comment("Сущности")
	@OneToMany(cascade=CascadeType.ALL, mappedBy="transaction")
	public List<LiveEntity> getEntities() {
		return theEntities;
	}

	public void setEntities(List<LiveEntity> aEntities) {
		theEntities = aEntities;
	}

	/** Сущности */
	private List<LiveEntity> theEntities;
    /** Время начала транзакции */
	@Comment("Время начала транзакции")
	public Timestamp getStartTime() {
		return theStartTime;
	}

	public void setStartTime(Timestamp aStartTime) {
		theStartTime = aStartTime;
	}

	/** Время закрытия транзакции */
	@Comment("Время закрытия транзакции")
	public Timestamp getEndTime() {
		return theEndTime;
	}

	public void setEndTime(Timestamp aEndTime) {
		theEndTime = aEndTime;
	}

	/** Имя пользователя */
	@Comment("Имя пользователя")
	public String getUsername() {
		return theUsername;
	}

	public void setUsername(String aUsername) {
		theUsername = aUsername;
	}

	/** Имя пользователя */
	private String theUsername;
	
	/** Время закрытия транзакции */
	private Timestamp theEndTime;
	/** Время начала транзакции */
	private Timestamp theStartTime;
    /** Идентификатор */
    private long theId ;
    
    /** Год начала */
	@Comment("Год начала")
	public Integer getStartYear() {
		return theStartYear;
	}

	public void setStartYear(Integer aStartYear) {
		Calendar cal=Calendar.getInstance();
	    cal.setTime(theStartTime);
		theStartYear = cal.get(Calendar.YEAR);
	}

	/** Год начала */
	private Integer theStartYear;
	
	/** Месяц начала */
	@Comment("Месяц начала")
	public Integer getStartMonth() {
		return theStartMonth;
	}

	public void setStartMonth(Integer aStartMonth) {
		Calendar cal=Calendar.getInstance();
	    cal.setTime(theStartTime);
		theStartYear = cal.get(Calendar.MONTH);
	}

	/** Месяц начала */
	private Integer theStartMonth;
	
	/** День начала */
	@Comment("День начала")
	public Integer getStartDay() {
		return theStartDay;
	}

	public void setStartDay(Integer aStartDay) {
		Calendar cal=Calendar.getInstance();
	    cal.setTime(theStartTime);
		theStartYear = cal.get(Calendar.DAY_OF_MONTH);
	}

	/** День начала */
	private Integer theStartDay;
}
