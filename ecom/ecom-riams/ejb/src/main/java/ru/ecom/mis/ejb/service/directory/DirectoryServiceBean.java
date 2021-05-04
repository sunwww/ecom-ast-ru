package ru.ecom.mis.ejb.service.directory;

import ru.ecom.ejb.services.entityform.ILocalEntityFormService;
import ru.ecom.mis.ejb.domain.directory.Department;
import ru.ecom.mis.ejb.domain.directory.Entry;
import ru.ecom.mis.ejb.domain.directory.TelephoneNumber;
import ru.ecom.mis.ejb.domain.directory.voc.VocBuilding;
import ru.ecom.mis.ejb.domain.directory.voc.VocBuildingLevel;
import ru.ecom.mis.ejb.domain.directory.voc.VocTypeNumber;
import ru.ecom.mis.ejb.domain.lpu.MisLpu;
import ru.ecom.mis.ejb.domain.worker.WorkFunction;

import javax.annotation.EJB;
import javax.annotation.Resource;
import javax.ejb.Remote;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by rkurbanov on 21.08.2017.
 */

@Stateless
@Remote(IDirectoryService.class)
public class DirectoryServiceBean implements IDirectoryService{

    public void setWhat(){

    }
    public Long setDepartment(Long buildingId,Long buildingLevelId,Long misLpuId){
        Department department = new Department();
        department.setBuilding(manager.find(VocBuilding.class,buildingId));
        department.setBuildingLevel(manager.find(VocBuildingLevel.class,buildingLevelId));
        department.setDepartment(manager.find(MisLpu.class,misLpuId));
        manager.persist(department);
        return department.getId();
    }

    public Long setEntry2(String comment,String insideNumber, Long departmentId,Long personId){
        Entry entry = new Entry();
        entry.setComment(comment);
        entry.setInsideNumber(insideNumber);
        entry.setDepartment(manager.find(Department.class,departmentId));
        entry.setPerson(manager.find(WorkFunction.class,personId));
        manager.persist(entry);
        return entry.getId();
    }

    public Long setEntry1(String comment,String insideNumber, Long departmentId){
        Entry entry = new Entry();
        entry.setComment(comment);
        entry.setInsideNumber(insideNumber);
        entry.setDepartment(manager.find(Department.class,departmentId));
        manager.persist(entry);
        return entry.getId();
    }

    public void setTelephoneNumber(String telNumber, Long entryId, Long typenumber){
        TelephoneNumber telephoneNumber = new TelephoneNumber();
        telephoneNumber.setTelNumber(telNumber);
        telephoneNumber.setEntry(manager.find(Entry.class,entryId));
        telephoneNumber.setTypeNumber(manager.find(VocTypeNumber.class,typenumber));
        manager.persist(telephoneNumber);
    }

    @EJB
    ILocalEntityFormService
            entityFormService;
    @PersistenceContext
    EntityManager manager;
    @Resource
    SessionContext context;
}
