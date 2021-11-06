package ru.ecom.expert2.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Направление на госпитализацию в Алькону
 */
@Getter
@Setter
@Data
@ToString
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Referrals")
@XmlType(name = "", propOrder = {
        "externalId",
        "directNumber",
        "directDate",
        "serviceKind",
        "directLpu",
        "lpuCode",
        "medPolicyType",
        "medPolicySeries",
        "medPolicyNumber",
        "insuranceCompanyCode",
        "regionOkato",
        "patientLastname",
        "patientFirstname",
        "patientMiddlename",
        "sex",
        "birthDate",
        "diagnosis",
        "medHelpProfile",
        "hospitalBranch",
        "doctorSnils",
        "doctorInfo",
        "planHospDate",
        "medTerms"
})
public class Refferal {

    /**
     * Идентификатор факта госпитализации в ИС МО
     */
    private String externalId;
    /**
     * Номер направления
     */
    private String directNumber;
    /**
     * Дата направления
     */
    private String directDate;
    /**
     * Тип медицинской помощи: 1	Планово, 2	Экстренно, 3 Неотложная помощь
     */
    private int serviceKind;
    /**
     * Реестровый номер МО, направившей на госпитализацию
     */
    private String directLpu;
    /**
     * Реестровый номер МО госпитализации (справочник F003)
     */
    private String lpuCode;

    /**
     * СНИЛС врача, направившего на госпитализацию
     */
    private String doctorSnils;

    /**
     * ФИО врача, направившего на госпитализацию
     */
    private String doctorInfo;
    /**
     * Плановая дата госпитализации
     */
    private String planHospDate;

    /**
     * Тип полиса ОМС (1,2,3)
     */
    private int medPolicyType;

    /**
     * Серия полиса ОМС
     */
    private String medPolicySeries;

    /**
     * Номер полиса ОМС
     */
    private String medPolicyNumber;

    /**
     * Реестровый номер СМО (справочник F002 ФФОМС)
     */
    private String insuranceCompanyCode;

    /**
     * ОКАТО Региона, субъекта РФ, в котором застрахован гражданин
     */
    private String regionOkato;

    /**
     * Фамилия пациента
     */
    private String patientLastname;
    /**
     * Имя застрахованного лица
     */
    private String patientFirstname;
    /**
     * Отчество застрахованного лица
     */
    private String patientMiddlename;
    /**
     * Пол пациента (0-Ж, 1-М)
     */
    private int sex;

    private String birthDate;

    /**
     * Диагноз какой-то
     */
    private String diagnosis;

    /**
     * Профиль койки (справочник V020 ФФОМС)
     */
    private String medHelpProfile;

    /**
     * Наименование отделения (профиль медицинской помощи)
     */
    private String hospitalBranch;

    /**
     * Условия оказания МП:
     * КР Стационар круглосуточный
     * ДС Дневное пребывание (в стационаре)
     * ДП Дневной стационар (в поликлинике)
     * ДД Стационар на дому
     */
    private String medTerms;

}
