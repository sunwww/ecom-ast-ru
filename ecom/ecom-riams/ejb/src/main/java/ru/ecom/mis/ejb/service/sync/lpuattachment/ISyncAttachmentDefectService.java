package ru.ecom.mis.ejb.service.sync.lpuattachment;

public interface ISyncAttachmentDefectService {
    void sync(long aMonitorId, long aTimeId) ;
    String importDefectFromXML(String aFileName);
    String cleanDefect(long aAttachmentId);
    String changeAttachmentArea (Long aOldAreaId, Long aNewLpuId, Long aNewAreaId);
}
