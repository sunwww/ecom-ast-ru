package ru.ecom.mis.ejb.domain.patient;

import ru.ecom.ejb.util.injection.EjbInjection;
import ru.ecom.mis.ejb.service.patient.IPatientService;

import javax.naming.NamingException;
import javax.persistence.PostPersist;
import javax.persistence.PreUpdate;

/**
 * Обновление пациента
 */
public class PatientListener {



    @PreUpdate @PostPersist
    public void onUpdate(Patient aPatient) throws NamingException {
        findService().updatePatientLpuByAddress(aPatient);
    }

    private IPatientService findService() throws NamingException {
    	return EjbInjection.getInstance().getLocalService(IPatientService.class) ;
    }

}
