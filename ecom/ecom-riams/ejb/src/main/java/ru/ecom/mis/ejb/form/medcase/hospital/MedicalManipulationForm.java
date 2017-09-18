package ru.ecom.mis.ejb.form.medcase.hospital;
import ru.ecom.ejb.form.simple.IdEntityForm;
import ru.ecom.ejb.services.entityform.WebTrail;
import ru.ecom.ejb.services.entityform.annotation.PersistManyToManyOneProperty;
import ru.ecom.ejb.services.entityform.interceptors.AParentEntityFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.AParentPrepareCreateInterceptors;
import ru.ecom.ejb.services.live.DeleteListener;
import ru.ecom.mis.ejb.domain.medcase.MedicalManipulation;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;
import ru.ecom.mis.ejb.form.medcase.MedCaseForm;
import ru.ecom.mis.ejb.form.medcase.hospital.interceptors.MedicalManipulationCreateInterceptor;
import ru.nuzmsh.commons.formpersistence.annotation.*;
import ru.nuzmsh.ejb.formpersistence.annotation.EntityFormPersistance;
import ru.nuzmsh.forms.validator.transforms.DoDateString;
import ru.nuzmsh.forms.validator.transforms.DoIntegerString;
import ru.nuzmsh.forms.validator.transforms.DoTimeString;
import ru.nuzmsh.forms.validator.validators.*;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Milamesher on 31.08.2017.
 * Медицинская манипуляция
 */
@Comment("Манипуляция")
@EntityForm
@EntityFormPersistance(clazz= MedicalManipulation.class)
@WebTrail(comment = "Манипуляция", nameProperties= "id", view="entityParentView-stac_bandage.do")
@Parent(property="medCase", parentForm= MedCaseForm.class)
@EntityFormSecurityPrefix("/Policy/Mis/MedCase/Stac/Ssl/Bandage")
@AParentPrepareCreateInterceptors(
        @AParentEntityFormInterceptor(MedicalManipulationCreateInterceptor.class)
)
@EntityListeners(DeleteListener.class)
public class MedicalManipulationForm extends IdEntityForm {
    /** Дата начала */
    @Comment("Дата начала")
    @Persist
    @Required
    @MaxDateCurrent
    @DateString
    @DoDateString
    public String getTheStartDate() {return theStartDate;}
    public void setTheStartDate(String aStartDate) {theStartDate = aStartDate;	}

    /** Время начала */
    @Comment("Время начала")
    @Persist @Required
    @TimeString
    @DoTimeString
    public String getTheStartTime() {return theStartTime;}
    public void setTheStartTime(String aStartTime) {theStartTime = aStartTime;	}

    /** Отделение */
    @Comment("Отделение")
    @Persist @Required
    public Long getTheDepartment() {return theDepartment;}
    public void setTheDepartment(Long aDepartment) {theDepartment = aDepartment;}

    /** Анестизия */
    @Comment("Анестезия")
    public Long getAnesthesia() {return theAnesthesia;}
    public void setAnesthesia(Long aAnesthesia) {theAnesthesia = aAnesthesia;	}

    /** Случай медицинского обслуживания */
    @Comment("Случай медицинского обслуживания")
    @Persist
    public Long getMedCase() {return theMedCase;}
    public void setMedCase(Long aMedCase) {theMedCase = aMedCase;}

    /** Пациент */
    @Comment("Пациент")
    @Persist
    public Long getPatient() {return thePatient;}
    public void setPatient(Long aPatient) {thePatient = aPatient;}

    /** Лечебное учреждение */
    @Comment("Лечебное учреждение")
    @Persist
    public Long getLpu() {return theLpu;}
    public void setLpu(Long aLpu) {theLpu = aLpu;}

    /** Кол-во  анастезии */
    @Comment("Кол-во  анастезии")
    @IntegerString
    @DoIntegerString
    public String getAnesthesiaAmount() {return theAnesthesiaAmount;}
    public void setAnesthesiaAmount(String aAnesthesiaAmount) {theAnesthesiaAmount = aAnesthesiaAmount;}

    ///** Рабочая функция врача, проводившего операцию */
    //@Comment("Рабочая функция врача, проводившего операцию")
    //@Persist @Required
    //public Long getSurgeonFunction() {return theSurgeonFunction;}
    //public void setSurgeonFunction(Long aSurgeonFunction) {theSurgeonFunction = aSurgeonFunction;}

    /** Дата окончания */
    @Comment("Дата окончания")
    @DateString
    @DoDateString
    @Persist  @MaxDateCurrent
    public String getTheEndDate() {return theEndDate;}
    public void setTheEndDate(String aEndDate) {theEndDate = aEndDate;}


    /** Анестезиолог */
    @Comment("Анестезиолог")
    public Long getAnaesthetist() {return theAnaesthetist;}
    public void setAnaesthetist(Long aAnaesthetist) {theAnaesthetist = aAnaesthetist;}

    /** Показания */
    @Comment("Показания ")
    @Persist
    public Long getAspect() {return theAspect;}
    public void setAspect(Long aAspect) {theAspect = aAspect;}

    /** Медсестра */
    @Comment("Медсестра")
    @Persist
    public Long getTheNurse() {return theNurse;}
    public void setTheNurse(Long aNurse) {theNurse = aNurse;}

    /** Хирург */
    @Comment("Хирург")
    @Persist
    public Long getTheSurgeon() {return theSurgeon;}
    public void setTheSurgeon(Long aSurgeon) {theSurgeon = aSurgeon;}

    /** Эпикриз */
    @Comment("Эпикриз")
    @Persist
    public String getTheEpicrisis() {return theEpicrisis;}
    public void setTheEpicrisis(String aEpicrisis) {theEpicrisis = aEpicrisis;}


    /** Описание */
    @Comment("Описание")
    @Persist
    public String getTheText() {return theText;}
    public void setTheText(String aText) {theText = aText;}

    /** Время окончания */
    @Comment("Время окончания")
    @Persist
    @TimeString
    @DoTimeString
    public String getTheEndTime() {return theEndTime;}
    public void setTheEndTime(String aEndTime) {theEndTime = aEndTime;}

    /** Хирурги */
    @Comment("Хирурги")
    @Persist @PersistManyToManyOneProperty(collectionGenericType=WorkFunction.class)
    public String getSurgeonFunctions() {return theSurgeonFunctions;}
    public void setSurgeonFunctions(String aSurgeonFunctions) {theSurgeonFunctions = aSurgeonFunctions;}

    /** Хирурги */
    private String theSurgeonFunctions;

    /** Информация */
    @Comment("Информация")
    @Persist
    public String getInformation() {return theInformation;}
    public void setInformation(String aInformation) {theInformation = aInformation;}

    /** Период */
    @Comment("Период")
    @Persist
    public String getPeriod() {return thePeriod;}
    public void setPeriod(String aPeriod) {thePeriod = aPeriod;}

    /** Информация о пациенте */
    @Comment("Информация о пациенте")
    @Persist
    public String getPatientInfo() {return thePatientInfo;}
    public void setPatientInfo(String aPatientInfo) {thePatientInfo = aPatientInfo;}

    /** Номер в журнале */
    @Comment("Номер в журнале")
    @Persist
    public String getNumberInJournal() {return theNumberInJournal;}
    public void setNumberInJournal(String aNumberInJournal) {theNumberInJournal = aNumberInJournal;}

    /** Информация о хир.операции */
    private String theInformation;
    /** Малая */
    private Boolean theMinor;

    /** Хирург */
    private Long theSurgeon;

    /** Номер в журнале */
    private String theNumberInJournal;

    /** Анестезии */
    private List<Long> theAnesthesies;
    /** Информация о пациенте */
    private String thePatientInfo;
    /** Период */
    private String thePeriod;

    /** Кол-во  анастезии */
    private String theAnesthesiaAmount;
    /** Анестезиолог */
    private Long theAnaesthetist;
    /** Дата манипуляции по */
    private String theEndDate;
    /** Эпикриз */
    private String theEpicrisis;
    /** Описание */
    private String theText;
    /** Медсестра */
    private Long theNurse;
    /** Время окончания */
    private String theEndTime;

    /** Лечебное учреждение */
    private Long theLpu;
    /** Пациент */
    private Long thePatient;
    /** Случай медицинского обслуживания */
    private Long theMedCase;
    /** Анестезия */
    private Long theAnesthesia;
    /** Отделение */
    private Long theDepartment;
    /** Время манипуляции */
    private String theStartTime;
    /** Дата манипуляции */
    private String theStartDate;

    /** Дата создания */
    @Comment("Дата создания")
    @Persist @DoDateString @DateString
    public String getCreateDate() {return theCreateDate;}
    public void setCreateDate(String aCreateDate) {theCreateDate = aCreateDate;}

    /** Пользователь, создавший запись */
    @Comment("Пользователь, создавший запись")
    @Persist
    public String getCreateUsername() {return theCreateUsername;}
    public void setCreateUsername(String aUsername) {theCreateUsername = aUsername;}

    /** Дата редактирования */
    @Comment("Дата редактирования")
    @Persist @DoDateString @DateString
    public String getEditDate() {return theEditDate;}
    public void setEditDate(String aEditDate) {theEditDate = aEditDate;}

    /** Пользователь, последний изменявший запись */
    @Comment("Пользователь, последний изменявший запись")
    @Persist
    public String getEditUsername() {return theEditUsername;}
    public void setEditUsername(String aEditUsername) {theEditUsername = aEditUsername;}

    /** Поток обслуживания */
    @Comment("Поток обслуживания")
    @Persist @Required
    public Long getServiceStream() {return theServiceStream;}
    public void setServiceStream(Long aServiceStream) {theServiceStream = aServiceStream;}

    /** Поток обслуживания */
    private Long theServiceStream;

    /** Пользователь, последний изменявший запись */
    private String theEditUsername;
    /** Дата редактирования */
    private String theEditDate;
    /** Пользователь, создавший запись */
    private String theCreateUsername;
    /** Дата создания */
    private String theCreateDate;

    /** Мед. услуга */
    @Comment("Мед. услуга")
    @Persist @Required
    public Long getMedService() {return theMedService;}
    public void setMedService(Long aMedService) {theMedService = aMedService;}


    /** Мед. услуга */
    private Long theMedService;
    /** Дата печати */
    @Comment("Дата печати")
    @Persist @DateString @DoDateString
    public String getPrintDate() {return thePrintDate;}
    public void setPrintDate(String aPrintDate) {thePrintDate = aPrintDate;}

    /** Дата печати */
    private String thePrintDate;

    /** Время печати */
    @Comment("Время печати")
    @Persist @TimeString @DoTimeString
    public String getPrintTime() {return thePrintTime;}
    public void setPrintTime(String aPrintTime) {thePrintTime = aPrintTime;}

    /** Время печати */
    private String thePrintTime;

    /** Пользователь, посл. распечат. документ */
    @Comment("Пользователь, посл. распечат. документ")
    @Persist
    public String getPrintUsername() {return thePrintUsername;}
    public void setPrintUsername(String aPrintUsername) {thePrintUsername = aPrintUsername;}

    /** Пользователь, посл. распечат. документ */
    private String thePrintUsername;

    /** Проводилась анестезия */
    @Comment("Проводилась анестезия")
    public Long getIsAnesthesia() {return theIsAnesthesia;}
    public void setIsAnesthesia(Long aIsAnesthesia) {theIsAnesthesia = aIsAnesthesia;}

    /** Проводилась анестезия */
    private Long theIsAnesthesia;

    /** Анестезия вида */
    @Comment("Анестезия вида")
    public Long getAnesthesiaType() {return theAnesthesiaType;}
    public void setAnesthesiaType(Long aAnesthesiaType) {theAnesthesiaType = aAnesthesiaType;}

    /** Анестезия вида */
    private Long theAnesthesiaType;

    /** Анестезия услуга */
    @Comment("Анестезия услуга")
    public Long getAnesthesiaService() {
        return theAnesthesiaService;
    }

    public void setAnesthesiaService(Long aAnesthesiaService) {
        theAnesthesiaService = aAnesthesiaService;
    }

    /** Анестезия услуга */
    private Long theAnesthesiaService;

    /** Длительность */
    @Comment("Длительность")
    public Integer getAnesthesiaDuration() {
        return theAnesthesiaDuration;
    }

    public void setAnesthesiaDuration(Integer aAnesthesiaDuration) {
        theAnesthesiaDuration = aAnesthesiaDuration;
    }

    /** Длительность */
    private Integer theAnesthesiaDuration;
    /** Показания */
    private Long theAspect;
}