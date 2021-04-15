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
@Data
public class HospLeave {

    /**
     * Идентификатор карты выбывшего из стационара в МИС
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

    private String serviceKind;
//    private VocServiceKind serviceKind;

    /**
     * Код медицинской оргинизации
     */
    private String lpuCode;

    /**
     * Дата начала госпитализации
     */
    private String hospStartDate;

    /**
     * Дата окончания госпитализации
     */
    private String hospFinishDate;

    /**
     * ПОхоже, мед профиль V002
     */
    private String medHelpProfile;

    /**
     * Наименование отделения
     */
    private String departmentName;

    /**
     * № карты стац.больного
     */
    private String statisticStub;

    /**
     * Фамилия застрахованного лица
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
     * Условия оказания МП:
     * КР Стационар круглосуточный
     * ДС Дневное пребывание (в стационаре)
     * ДП Дневной стационар (в поликлинике)
     * ДД Стационар на дому
     */
    private String medTerms;
//    private VocMedTerms medTerms;


}
