package ru.ecom.mis.ejb.service.directory;

/**
 * Created by rkurbanov on 21.08.2017.
 */
public interface IDirectoryService {
    Long setDepartment(Long buildingId,Long buildingLevelId,Long misLpuId);
    Long setEntry2(String comment,String insideNumber, Long departmentId,Long personId);
    Long setEntry1(String comment,String insideNumber, Long departmentId);
    void setTelephoneNumber(String telNumber, Long entryId, Long typenumber);
}
