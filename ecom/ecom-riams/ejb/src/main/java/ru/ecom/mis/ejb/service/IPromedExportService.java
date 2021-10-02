package ru.ecom.mis.ejb.service;

import ru.ecom.mis.ejb.domain.medcase.PolyclinicMedCase;

public interface IPromedExportService {

    void exportPolyclinic(PolyclinicMedCase medCase);

    void exportPolyclinicById(Long medCaseId);
}
