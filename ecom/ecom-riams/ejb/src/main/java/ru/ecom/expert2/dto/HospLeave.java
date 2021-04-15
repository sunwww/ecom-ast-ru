package ru.ecom.expert2.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.ecom.expert2.config.LocalDateAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Модель для отправки выписки в ФОМС в качестве содержимого тэга xml
 */
@Getter
@Setter
@Data
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "HospLeave")
@XmlType(name = "", propOrder = {
        "directNumber",
        "directDate",
        "serviceKind",
        "lpuCode",
        "hospStartDate",
        "hospFinishDate",
        "medHelpProfile",
        "departmentName",
        "statisticStub",
        "patientLastname",
        "patientFirstname",
        "patientMiddlename",
        "medTerms"
})
public class HospLeave {

    /**
     * Идентификатор карты выбывшего из стационара в МИС
     */
    @XmlTransient
    private String externalId;
    /**
     * Номер направления
     */
    @XmlElement(name = "C_Number")
    private String directNumber;
    /**
     * Дата направления
     */
    @XmlElement(name = "D_Referral")
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private String directDate;
    /**
     * Тип медицинской помощи: 1	Планово, 2	Экстренно, 3 Неотложная помощь
     */

    @XmlElement(name = "F_ServiceKind")
    private String serviceKind;
//    private VocServiceKind serviceKind;

    /**
     * Код медицинской оргинизации
     */
    @XmlElement(name = "F_MO")
    private String lpuCode;

    /**
     * Дата начала госпитализации
     */
    @XmlElement(name = "D_DateHosp")
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private String hospStartDate;

    /**
     * Дата окончания госпитализации
     */
    @XmlElement(name = "D_DateLeave")
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private String hospFinishDate;

    /**
     * ПОхоже, мед профиль V002
     */
    @XmlElement(name = "F_Profile")
    private String medHelpProfile;

    /**
     * Наименование отделения
     */
    @XmlElement(name = "C_HospitalBranch")
    private String departmentName;

    /**
     * № карты стац.больного
     */
    @XmlElement(name = "C_CardNumber")
    private String statisticStub;

    /**
     * Фамилия застрахованного лица
     */
    @XmlElement(name = "C_Surname")
    private String patientLastname;
    /**
     * Имя застрахованного лица
     */
    @XmlElement(name = "C_Name")
    private String patientFirstname;
    /**
     * Отчество застрахованного лица
     */
    @XmlElement(name = "C_Patronymic")
    private String patientMiddlename;
    /**
     * Условия оказания МП:
     * КР Стационар круглосуточный
     * ДС Дневное пребывание (в стационаре)
     * ДП Дневной стационар (в поликлинике)
     * ДД Стационар на дому
     */
    @XmlElement(name = "USLMP")
    private String medTerms;
//    private VocMedTerms medTerms;


}
