package ru.ecom.mis.ejb.service.sync.lpuattachment;

public interface ISyncAttachmentDefectService {
    /**
     * Импорт ответа с информацией о прикрепленном населении
     * @param fileContent Содержимое файла ответа
     * @return Журнал импорта ответа (html)
     */
    String importDefectFromXML(String fileContent);
    void cleanDefect(long attachmentId);
    String changeAttachmentArea (Long oldAreaId, Long newLpuId, Long newAreaId);
}
