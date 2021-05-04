package ru.ecom.ejb.services.live.domain;

import lombok.Getter;
import lombok.Setter;
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
@Getter
@Setter
public class LiveTransaction {
	 /** Идентификатор */
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    public long getId() { return id ; }

    /** Сущности */
	@Comment("Сущности")
	@OneToMany(cascade=CascadeType.ALL, mappedBy="transaction")
	public List<LiveEntity> getEntities() {
		return entities;
	}


	/** Сущности */
	private List<LiveEntity> entities;


	/** Имя пользователя */
	private String username;
	
	/** Время закрытия транзакции */
	private Timestamp endTime;
	/** Время начала транзакции */
	private Timestamp startTime;
    /** Идентификатор */
    private long id ;
    
	public void setStartYear(Integer aStartYear) {
		Calendar cal=Calendar.getInstance();
	    cal.setTime(startTime);
		startYear = cal.get(Calendar.YEAR);
	}

	/** Год начала */
	private Integer startYear;
	
	/** Месяц начала */
	@Comment("Месяц начала")
	public Integer getStartMonth() {
		return startMonth;
	}

	public void setStartMonth(Integer aStartMonth) {
		Calendar cal=Calendar.getInstance();
	    cal.setTime(startTime);
		startYear = cal.get(Calendar.MONTH);
	}

	/** Месяц начала */
	private Integer startMonth;
	
	/** День начала */
	@Comment("День начала")
	public Integer getStartDay() {
		return startDay;
	}

	public void setStartDay(Integer aStartDay) {
		Calendar cal=Calendar.getInstance();
	    cal.setTime(startTime);
		startYear = cal.get(Calendar.DAY_OF_MONTH);
	}

	/** День начала */
	private Integer startDay;
}
