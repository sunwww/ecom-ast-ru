package ru.ecom.poly.ejb.domain.protocol;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.diary.ejb.domain.Diary;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.mis.ejb.domain.medcase.MedCase;
import ru.ecom.mis.ejb.domain.medcase.ServiceMedCase;
import ru.ecom.mis.ejb.domain.patient.ObservationSheet;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.poly.ejb.domain.voc.VocProtocolMode;
import ru.ecom.poly.ejb.domain.voc.VocTypeProtocol;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.format.DateFormat;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.sql.Date;
import java.sql.Time;

/**
 * Created by IntelliJ IDEA.
 * User: STkacheva
 * Date: 07.02.2007
 * Time: 15:42:52
 * To change this template use File | Settings | File Templates.
 */
@Entity
@AIndexes({
	@AIndex(properties="medCase",table="Diary"),
	@AIndex(properties={"medCase","printDate"},table="Diary"),
	@AIndex(properties="dateRegistration",table="Diary"),
	@AIndex(properties="obsSheet",table="Diary")
    })
@Getter
@Setter
public class Protocol extends Diary {

	/** Медицинская услуга */
	@Comment("Медицинская услуга")
	@OneToOne
	public ServiceMedCase getServiceMedCase() {return serviceMedCase;}
	private ServiceMedCase serviceMedCase;
	
	/** Шаблон, на основе которого создано заключение */
	private Long templateProtocol;
	
	/** Запрет на ручное редактирование */
	private Boolean disableEdit;


    /** Визит */
	@Comment("Визит")
	@OneToOne
	public MedCase getMedCase() {return medCase;}

	/** Специалист */
	@Comment("Специалист")
	@OneToOne
	public WorkFunction getSpecialist() {return specialist;}

	/** Тип протокола */
	@Comment("Тип протокола")
	@OneToOne
	public VocTypeProtocol getType() {return type;}

	/** Общая информация о протоколе */
	@Transient
    public String getInfo() {
        StringBuilder info = new StringBuilder() ;
        if (type!=null) {
        	info.append(type.getName()) ;
        }
        if (specialist!=null) {
            info.append(" Врач: ").append(specialist.getName()).append(". ") ;
        }
        return info.append(" Дата: ").append(DateFormat.formatToDate(dateRegistration)).toString() ;
    }

	@Transient
	public String getSpecialistInfo() {
		return specialist!=null ?specialist.getWorkFunctionInfo() :"" ;
	}
	
	@Transient
	public String getTypeInfo() {
		return type!=null?type.getName():"" ;
	}
	

	/** Время печати */
	private Time printTime;
	/** Дата печати */
	private Date printDate;
	/** Время регистрации*/
	private Time timeRegistration;
	/** Для выписки */
	private Boolean isDischarge;
	/** Тип протокола */
	private VocTypeProtocol type;
	/** Пользователь */
	private String username;
	/** Специалист */
	private WorkFunction specialist;
	/** Визит */
	private MedCase medCase;
    /** Дата регистрации талона */
    private Date dateRegistration ;
    
	/** Информация для журнала */
	private String journalText;
	
	/** Режим */
	@Comment("Режим")
	@OneToOne
	public VocProtocolMode getMode() {return mode;}

	/** Режим */
	private VocProtocolMode mode;

	/** Лист наблюдения (для ЕДКЦ) */
	@Comment("Лист наблюдения (для ЕДКЦ)")
	@ManyToOne
	public ObservationSheet getObsSheet() {return obsSheet;}

	/** Лист наблюдения (для ЕДКЦ) */
	private ObservationSheet obsSheet;

	/** Заголовок дневника */
	private String title ="";

	/** Подвал */
	private String bottom ;
}
