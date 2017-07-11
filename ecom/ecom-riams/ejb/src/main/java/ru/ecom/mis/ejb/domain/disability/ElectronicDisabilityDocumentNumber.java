package ru.ecom.mis.ejb.domain.disability;

import org.jboss.remoting.samples.chat.exceptions.DatabaseException;
import ru.ecom.ejb.domain.simple.BaseEntity;
import ru.ecom.mis.ejb.domain.disability.voc.VocDisabilityDocumentExportStatus;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.sql.Date;
import java.sql.Time;

/**
 * Created by vtsybulin on 30.05.2017.
 */
@Comment("Сведения о номере больничного листа")
@Entity
public class ElectronicDisabilityDocumentNumber extends BaseEntity {

    /** Номер электронного больничного листа */
    @Comment("Номер электронного больничного листа")
    public String getNumber() {return theNumber;}
    public void setNumber(String aNumber) {theNumber = aNumber;}
    /** Номер электронного больничного листа */
    private String theNumber ;

    /** Документ нетрудоспособности */
    @Comment("Документ нетрудоспособности")
    @OneToOne
    public DisabilityDocument getDisabilityDocument() {return theDisabilityDocument;}
    public void setDisabilityDocument(DisabilityDocument aDisabilityDocument) {theDisabilityDocument = aDisabilityDocument;}
    /** Документ нетрудоспособности */
    private DisabilityDocument theDisabilityDocument ;

    /** Пользователь, занявший номер ЭЛН */
    @Comment("Пользователь, занявший номер ЭЛН")
    public String getUsername() {return theUsername;}
    public void setUsername(String aUsername) {theUsername = aUsername;}
    /** Пользователь, занявший номер ЭЛН */
    private String theUsername ;

    /** Статус экспорта */
    @Comment("Статус экспорта")
    @ManyToOne
    public VocDisabilityDocumentExportStatus getStatus() {return theStatus;}
    public void setStatus(VocDisabilityDocumentExportStatus aStatus) {theStatus = aStatus;}
    /** Статус экспорта */
    private VocDisabilityDocumentExportStatus theStatus ;

    /** Дата последнего экспорта */
    @Comment("Дата последнего экспорта")
    public Date getExportDate() {return theExportDate;}
    public void setExportDate(Date aExportDate) {theExportDate = aExportDate;}
    /** Дата последнего экспорта */
    private Date theExportDate ;


    /** Дата резерва */
    @Comment("Дата резерва")
    public Date getReserveDate() {return theReserveDate;}
    public void setReserveDate(Date aReserveDate) {theReserveDate = aReserveDate;}
    /** Дата резерва */
    private Date theReserveDate ;

    /** Время резерва */
    @Comment("Время резерва")
    public Time getReserveTime() {return theReserveTime;}
    public void setReserveTime(Time aReserveTime) {theReserveTime = aReserveTime;}
    /** Время резерва */
    private Time theReserveTime ;

    /** Дата получения номера от ФСС */
    @Comment("Дата получения номера от ФСС")
    public Date getCreateDate() {return theCreateDate;}
    public void setCreateDate(Date aCreateDate) {theCreateDate = aCreateDate;}
    /** Дата получения номера от ФСС */
    private Date theCreateDate ;

}