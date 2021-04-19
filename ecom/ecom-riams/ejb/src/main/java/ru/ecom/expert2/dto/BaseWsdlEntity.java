package ru.ecom.expert2.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.ecom.expert2.config.LocalDateAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Non", propOrder = {
        "externalId",
        "directNumber",
        "directDate",
        "serviceKind",
        "directLpu",
        "directLpuDepartment",
        "lpuCode",
        "lpuDepartment",
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
        "cHospitalBranch",
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
public class BaseWsdlEntity {

    /**
     * Идентификатор факта госпитализации в ИС МО
     */
    @XmlElement(name = "ID", required = true)
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
    @XmlSchemaType(name = "date")
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate directDate;
    /**
     * Тип медицинской помощи: 1	Планово, 2	Экстренно, 3 Неотложная помощь
     */
    @XmlElement(name = "F_ServiceKind")
    private int serviceKind;
    //    private VocServiceKind serviceKind;
    /**
     * Реестровый номер МО, направившей на госпитализацию
     */
    @XmlElement(name = "F_MO_Source", required = true)
    private String directLpu;
    /**
     * Код подразделения, направившего на госпитализацию (справочник подразделений в АИС)
     */
    @XmlElement(name = "C_DepartmentSource")
    private String directLpuDepartment;
    /**
     * Реестровый номер МО госпитализации (справочник F003)
     */
    @XmlElement(name = "F_MO_Dest", required = true)
    private String lpuCode;
    /**
     * Код подразделения госпитализации (справочник подразделений в АИС)
     */
    @XmlElement(name = "C_DepartmentDest")
    private String lpuDepartment;

    /**
     * Тип полиса ОМС (1,2,3)
     */
    @XmlElement(name = "F_TypeOMS")
    private int medPolicyType;

    /**
     * Серия полиса ОМС
     */
    @XmlElement(name = "C_InsuranceSeries")
    private String medPolicySeries;

    /**
     * Номер полиса ОМС
     */
    @XmlElement(name = "C_InsurancePolicy", required = true)
    private String medPolicyNumber;

    /**
     * Реестровый номер СМО (справочник F002 ФФОМС)
     */
    @XmlElement(name = "F_SMO", required = true)
    private String insuranceCompanyCode;

    /**
     * ОКАТО Региона, субъекта РФ, в котором застрахован гражданин
     */
    @XmlElement(name = "F_Regions", required = true)
    private String regionOkato;

    /**
     * Фамилия пациента
     */
    @XmlElement(name = "C_Surname", required = true)
    private String patientLastname;
    /**
     * Имя застрахованного лица
     */
    @XmlElement(name = "C_Name", required = true)
    private String patientFirstname;
    /**
     * Отчество застрахованного лица
     */
    @XmlElement(name = "C_Patronymic")
    private String patientMiddlename;
    /**
     * Пол пациента (0-Ж, 1-М)
     */
    @XmlElement(name = "B_Gender")
    private int sex;

    @XmlElement(name = "D_BirthDate", required = true)
    @XmlSchemaType(name = "date")
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate birthDate;

    /**
     * Код профиля медицинской помощи (справочник V002 ФФОМС)
     */
    @XmlElement(name = "F_Profile", required = true)
    private String medHelpProfile;

    /**
     * Наименование отделения (профиль медицинской помощи)
     */
    @XmlElement(name = "C_HospitalBranch")
    private String cHospitalBranch;

    /**
     * № карты стац.больного
     */
    @XmlElement(name = "C_CardNumber")
    private String statisticStub;

    /**
     * Диагноз приемного отделения
     */
    @XmlElement(name = "C_MKB_PO", required = true)
    private String diagnosis;

    /**
     * Условия оказания МП:
     * КР Стационар круглосуточный
     * ДС Дневное пребывание (в стационаре)
     * ДП Дневной стационар (в поликлинике)
     * ДД Стационар на дому
     */
    @XmlElement(name = "USLMP", required = true)
    @XmlSchemaType(name = "string")
    private String medTerms;
    //    private VocMedTerms medTerms;

}
