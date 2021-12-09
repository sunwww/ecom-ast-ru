package ru.ecom.api.promed;

import ru.ecom.api.entity.export.ExportType;
import ru.ecom.api.form.PromedPolyclinicTapForm;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Milamesher on 06.02.2019.
 */
public interface IApiPolyclinicService {
    /**
     * Получить список закрытых за определенную дату поликлинических СМО в формате для загрузки их в РИАМС ПРомед
     * @param dateTo Дата закрытия СПО
     * @param sstream Поток обслуживания
     * @param wfId ИД рабочей функции врача, закрывшего СМО
     * @param limitNum Лимит СМО
     * @param isUpload были ли случаи выгружены ранее //TODO ненужно?
     * @return Список закрытых СМО
     */
    List<PromedPolyclinicTapForm> getPolyclinicCase(Date dateTo, String sstream, Long wfId, Integer limitNum, Boolean isUpload);

    String setEvnTap(Long medcaseId, Long tapId);

    /**
     * Запись о выгрузке СМО в промед (для массовой выгрузки, где инициатор - промедатор
     * @param medcaseId
     * @param packetGuid
     * @param exportType
     */
    void createPacketLog(Long medcaseId, UUID packetGuid, ExportType exportType);

    String getWfInfo(Long workfunctionId);

    String setWfInfo(Long workfunctionId, Long promedcodeLpuSection, Long promedcodeWorkstaff);
}
