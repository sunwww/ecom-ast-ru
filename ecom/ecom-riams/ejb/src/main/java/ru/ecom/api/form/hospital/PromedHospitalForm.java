package ru.ecom.api.form.hospital;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import ru.ecom.api.form.PromedDiagnosis;
import ru.ecom.api.form.PromedEntityForm;
import ru.ecom.api.form.PromedPatientForm;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Случай госпитализации
 */
@Data
@Builder
@ToString
public class PromedHospitalForm extends PromedEntityForm {
    private UUID guid;
    private PromedPatientForm patient;
    private List<PromedDepartmentForm> departmentCases; //СЛО, отсортированы по порядку положения в них пациента
    private PromedDiagnosis directDiagnosis;
    private String statStubNumber; //номер стат карты+
    private Boolean isFinished; //EvnPL_IsFinish
    private Boolean isEmergency;
    private Boolean isManualSend;
    private String entranceDate; //Дата и время поступления в приемное отделения (начало госпитализации)
    private String priemDischargeDate;//Дата и время выбытия из приемного отделения
    private String directNumber;
    private Date directDate;
    private String directLpuCode;//Код направившей ЛПУ (при план госпитализации)
    private Integer preAdmissionHour; //Кол-во часов с начала заболевания (маппим на стороне медоса)
    private Boolean isUnLawTrauma; //противоправная травма

    private Long departmentPromedId; //ИД отделения, куда направили пациента с приемного отделения
    private Long departmentProfileId; //ИД отделения, куда направили пациента с приемного отделения
    private Boolean isAmbulanceTreatment; //направлен на амбулаторное лечение (после выписки)
    private String transferLpuCode;
    private String dischargeOutcomeCode; //Исход, VocHospitalizationOutcome
    private String dischargeResultCode; //Результат, VocHospitalizationResult
    private String dischargeReasonCode; //Причина выписки, VocReasonDischarge
    private String bedTypeId; //1 - круглосуточный, 2 - дневной. считаем что нельзя перевести пациента с дневного в круглосуточный стационар в пределах СЛС

}
