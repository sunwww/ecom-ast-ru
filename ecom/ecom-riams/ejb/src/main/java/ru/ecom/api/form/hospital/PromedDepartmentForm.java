package ru.ecom.api.form.hospital;

import lombok.Data;
import ru.ecom.api.form.PromedDiagnosis;
import ru.ecom.api.form.PromedDoctor;
import ru.ecom.api.form.PromedEntityForm;

import java.util.List;

@Data
public class PromedDepartmentForm extends PromedEntityForm {
    private String entranceDate; //дата время поступления в отделения
    private String transferDate; //дата и время перевода из отделения (в другое отделение)
    private String dischargeDate; //дата и время выписки из отделения
    private Long departmentPromedId; //ИД отделения в промеде
    private PromedDoctor doctor; //Лечащий врач
    private PromedDiagnosis mainDiagnosis;//Основной (клинический) диагноз
    private List<PromedDiagnosis> diagnoses; //сопутствующие, осложнения, фоновые,..
    private String bedTypeId; //1 - круглосуточный, 2 - дневной

}
