package ru.ecom.mis.ejb.domain.prescription;

import lombok.Getter;
import lombok.Setter;
import ru.ecom.mis.ejb.domain.medcase.MedService;
import ru.ecom.mis.ejb.domain.medcase.voc.VocAnesthesia;
import ru.ecom.mis.ejb.domain.medcase.voc.VocBloodGroup;
import ru.ecom.mis.ejb.domain.medcase.voc.VocRhesusFactor;
import ru.ecom.mis.ejb.domain.workcalendar.voc.VocServiceStream;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

/**
 * Назначение на услугу
 *
 * @author azviagin
 */

@Comment("Назначение на услугу")
@Entity
@Getter
@Setter
public class ServicePrescription extends Prescription {

    /**
     * Вид наркоза
     */
    @Comment("Вид наркоза")
    @OneToOne
    public VocAnesthesia getAnesthesiaType() {
        return anesthesiaType;
    }
    private VocAnesthesia anesthesiaType;

    /**
     * Поток обслуживания
     */
    @Comment("Поток обслуживания")
    @Transient
    public VocServiceStream getServiceStream() {
        return getPrescriptionList().getServiceStream();
    }

    /**
     * Номер штрих-кода
     */
    private String barcodeNumber;

    /**
     * Медицинская услуга
     */
    @Comment("Медицинская услуга")
    @OneToOne
    public MedService getMedService() {
        return medService;
    }

    /**
     * Медицинская услуга
     */
    private MedService medService;

    /**
     * Описание назначения
     */
    @Comment("Описание назначения")
    @Transient
    public String getDescriptionInfo() {
        StringBuilder sb = new StringBuilder();
        if (getMedService() != null) {
            sb.append(" ");
            sb.append(getMedService().getName());
        }
        return sb.toString();
    }

    /**
     * Группа крови пациента
     */
    @Comment("Группа крови пациента")
    @OneToOne
    public VocBloodGroup getBloodGroup() {
        return bloodGroup;
    }
    private VocBloodGroup bloodGroup;

    /**
     * Резус-фактор пациента
     */
    @Comment("Резус-фактор пациента")
    @OneToOne
    public VocRhesusFactor getRhesusFactor() {
        return rhesusFactor;
    }
    private VocRhesusFactor rhesusFactor;

}
