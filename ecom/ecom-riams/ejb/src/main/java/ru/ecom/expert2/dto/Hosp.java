package ru.ecom.expert2.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.ecom.expert2.config.LocalDateAdapter;
import ru.ecom.expert2.config.LocalTimeAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalTime;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Hosp", propOrder = {
        "externalId",
        "isEmergency",
        "directNumber",
        "directDate",
        "serviceKind",
        "directLpu",
        "directLpuDepartment",
        "lpuCode",
        "departmentName",
        "hospStartDate",
        "hospStartTime",
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
        "medHelpProfile",
        "hospitalBranch",
        "statisticStub",
        "diagnosis",
        "medTerms"
})
@Data
@Getter
@Setter
/**
 * Случай госпитализации
 */
public class Hosp {

    /**
     * Идентификатор факта госпитализации в ИС МО
     */
    private String externalId;
    /**
     * Экстренная госпитализация 0/1
     */
    private int isEmergency;
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
    //    private VocServiceKind serviceKind;
    /**
     * Реестровый номер МО, направившей на госпитализацию
     */
    private String directLpu;
    /**
     * Код подразделения, направившего на госпитализацию (справочник подразделений в АИС)
     */
    private String directLpuDepartment;
    /**
     * Реестровый номер МО госпитализации (справочник F003)
     */
    private String lpuCode;
    /**
     * Код подразделения госпитализации (справочник подразделений в АИС)
     */
    private String departmentName;
    /**
     * Дата фактической госпитализации
     */
    private String hospStartDate;

    /**
     * Время фактической госпитализации
     */
    private String hospStartTime;

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
     * Профиль койки (справочник V020 ФФОМС)
     */
    private String medHelpProfile;

    /**
     * Наименование отделения (профиль медицинской помощи)
     */
    private String hospitalBranch;

    /**
     * № карты стац.больного
     */
    private String statisticStub;

    /**
     * Диагноз приемного отделения
     */
    private String diagnosis;

    /**
     * Условия оказания МП:
     * КР Стационар круглосуточный
     * ДС Дневное пребывание (в стационаре)
     * ДП Дневной стационар (в поликлинике)
     * ДД Стационар на дому
     */
    private String medTerms;
    //    private VocMedTerms medTerms;

}
