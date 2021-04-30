package ru.ecom.mis.ejb.domain.patient;


import lombok.Getter;
import lombok.Setter;
import ru.ecom.ejb.services.index.annotation.AIndex;
import ru.ecom.ejb.services.index.annotation.AIndexes;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcKodTer;
import ru.ecom.expomc.ejb.domain.omcvoc.OmcSprSmo;
import ru.ecom.mis.ejb.domain.patient.voc.VocMedPolicyOmc;
import ru.nuzmsh.commons.formpersistence.annotation.Comment;
import ru.nuzmsh.util.format.DateFormat;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

/**
 * Полис ОМС иногороднего
 *
 * @author oegorova
 */
@Entity
@AIndexes(value = {@AIndex(properties = {"insuranceCompanyCode"}, table = "MedPolicy")})
@Getter
@Setter
public class MedPolicyOmcForeign extends MedPolicy {

    /**
     * Область Страховщика
     */
    @Comment("Область Страховщика")
    @OneToOne
    public OmcKodTer getInsuranceCompanyRegion() {
        return insuranceCompanyRegion;
    }

    /**
     * СМО, выдавшей паспорт
     */
    @Comment("СМО, выдавшей паспорт")
    @OneToOne
    public OmcSprSmo getInsuranceCompanyCode() {
        return insuranceCompanyCode;
    }

    /**
     * СМО, выдавшей паспорт
     */
    private OmcSprSmo insuranceCompanyCode;

    @Transient
    public String getText() {
        StringBuilder sb = new StringBuilder();
        sb.append("Полис ОМС иногороднего: ");
        sb.append(getPolNumber());
        if (getActualDateFrom() != null) {
            sb.append(", действителен с ");
            sb.append(DateFormat.formatToDate(getActualDateFrom()));
            sb.append(" по ");
            if (getActualDateTo() != null) {
                sb.append(DateFormat.formatToDate(getActualDateTo()));
            } else {
                sb.append("нет даты окончания");
            }
        }
        sb.append("Страховщика:");
        if (insuranceCompanyRegion != null && !insuranceCompanyRegion.getName().equals("")) {
            sb.append(" область: ");
            sb.append(insuranceCompanyRegion.getName());
        }
        if (insuranceCompanyCity != null && !insuranceCompanyCity.equals("")) {
            sb.append(" город: ");
            sb.append(insuranceCompanyCity);
        }
        if (insuranceCompanyName != null && !insuranceCompanyName.equals("")) {
            sb.append(" наименование: ");
            sb.append(insuranceCompanyName);
        }
        return sb.toString();
    }

    /**
     * Тип полиса
     */
    @Comment("Тип полиса")
    @OneToOne
    public VocMedPolicyOmc getType() {
        return type;
    }

    /**
     * Тип полиса
     */
    private VocMedPolicyOmc type;
    /**
     * Название Страховщика
     */
    private String insuranceCompanyName;
    /**
     * Область Страховщика
     */
    private OmcKodTer insuranceCompanyRegion;
    /**
     * Город Страховщика
     */
    private String insuranceCompanyCity;


    /**
     * ОГРН страховщика
     */
    private String insuranceCompanyOgrn;

    @Transient
    public String getOgrn() {
        if (insuranceCompanyCode != null) return insuranceCompanyCode.getCode();
        return getCompany() == null ? "" : getCompany().getOgrn();
    }

}
