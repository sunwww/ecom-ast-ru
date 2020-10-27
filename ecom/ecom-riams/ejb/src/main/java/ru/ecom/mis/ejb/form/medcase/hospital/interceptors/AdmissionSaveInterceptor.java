package ru.ecom.mis.ejb.form.medcase.hospital.interceptors;

import ru.ecom.ejb.services.entityform.IEntityForm;
import ru.ecom.ejb.services.entityform.interceptors.IFormInterceptor;
import ru.ecom.ejb.services.entityform.interceptors.InterceptorContext;
import ru.ecom.expomc.ejb.domain.med.VocIdc10;
import ru.ecom.mis.ejb.domain.medcase.Diagnosis;
import ru.ecom.mis.ejb.domain.medcase.HospitalMedCase;
import ru.ecom.mis.ejb.domain.medcase.voc.VocDiagnosisRegistrationType;
import ru.ecom.mis.ejb.form.medcase.hospital.AdmissionMedCaseForm;
import ru.nuzmsh.util.format.DateFormat;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdmissionSaveInterceptor implements IFormInterceptor {

    private static boolean isEmpty(Long aLong) {
        return (aLong == null) || (aLong == 0);
    }

    private static boolean isEmpty(String aString) {
        return (aString == null) || (aString.trim().equals(""));
    }

    public void intercept(IEntityForm aForm, Object aEntity, InterceptorContext aContext) {
        AdmissionMedCaseForm form = (AdmissionMedCaseForm) aForm;
        EntityManager manager = aContext.getEntityManager();
        HospitalMedCase medCase = (HospitalMedCase) aEntity;
        long id = medCase.getId();

        StatisticStubStac stub = new StatisticStubStac(medCase, aContext.getSessionContext(), manager);

        String statCardNumber = form.getStatCardNumber();
        String stubCode = medCase.getStatisticStub() != null ? medCase.getStatisticStub().getCode() : null;
        if (statCardNumber != null && !statCardNumber.equals("") && (id > 0L)
                && stubCode != null && !stubCode.equals(statCardNumber)
        ) {

            String year = form.getDateStart().substring(6);
            List<Object[]> list = manager
                    .createNativeQuery("select id from StatisticStub where medCase_id='" + id + "' and DTYPE='StatisticStubExist' and code=:number and year=:year ")
                    .setParameter("number", statCardNumber).setParameter("year", java.lang.Long.valueOf(year)).getResultList();

            if (list.isEmpty()) {
                boolean alwaysCreate = aContext.getSessionContext().isCallerInRole("/Policy/Mis/MedCase/Stac/Ssl/Admission/AlwaysCreateStatCardNumber");
                if (!alwaysCreate) {
                    if (form.getDeniedHospitalizating() != null && form.getDeniedHospitalizating() > 0L) {
                        //throw new IllegalArgumentException("Нельзя изменить номер стат.карты при отказе госпитализации");
                        StatisticStubStac.removeStatCardNumber(manager, aContext.getSessionContext(), medCase);
                    } else {
                        StatisticStubStac.createStacCardNumber(id, statCardNumber, manager, aContext.getSessionContext(), form);
                    }
                } else {
                    StatisticStubStac.changeStatCardNumber(id, statCardNumber, manager, aContext.getSessionContext());
                }

            } else {
                if (form.getDeniedHospitalizating().equals(0L)) {
                    StatisticStubStac.createStacCardNumber(id, statCardNumber, manager, aContext.getSessionContext(), form);
                } else {

                    StatisticStubStac.removeStatCardNumber(manager, aContext.getSessionContext(), medCase);
                    //throw "remove" ;
                }
            }

        } else {
            List<Object> list = manager.createNativeQuery("select id from StatisticStub where medCase_id='" + id + "' and DTYPE='StatisticStubExist'")
                    //.setParameter("number", aStatCardNumber).setParameter("year",java.lang.Long.valueOf(year))
                    .getResultList();
            if (list.isEmpty()) {
                //if (aForm.deniedHospitalizating==0) {
                StatisticStubStac.createStacCardNumber(id, statCardNumber, manager, aContext.getSessionContext(), form);
                //} else {

                //}
            }
        }

        stub.removeStatCardNumber();

        boolean adding1is = (!isEmpty(form.getOrderDiagnos()) || (!isEmpty(form.getOrderMkb())));
        boolean adding2is = (!isEmpty(form.getEntranceDiagnos()) || (!isEmpty(form.getEntranceMkb())));
        if (adding1is || adding2is) {
            boolean adding1 = false;
            if (!adding1is) adding1 = true;
            boolean adding2 = false;
            if (!adding2is) adding2 = true;
            VocDiagnosisRegistrationType vocTypeOrder = manager.find(VocDiagnosisRegistrationType.class, 1L);
            VocDiagnosisRegistrationType vocTypeEnter = manager.find(VocDiagnosisRegistrationType.class, 2L);
            List<Diagnosis> diagList = manager.createQuery("from Diagnosis where medCase=:med").setParameter("med", medCase).getResultList();
            if (diagList == null) diagList = new ArrayList<>();
            for (Diagnosis diag : diagList) {
                if (!adding1)
                    adding1 = setDiagnosisByType(false, diag, vocTypeEnter, form.getOrderDiagnos(), form.getOrderDate(), form.getOrderMkb(), medCase, manager);
                if (!adding2)
                    adding2 = setDiagnosisByType(false, diag, vocTypeOrder, form.getEntranceDiagnos(), form.getDateStart(), form.getEntranceMkb(), medCase, manager);
                if (adding1 && adding2) break;
            }

            if (!adding1 || !adding2) {
                if (!adding1) {
                    Diagnosis diag = new Diagnosis();
                    setDiagnosisByType(true, diag, vocTypeEnter, form.getOrderDiagnos(), form.getOrderDate(), form.getOrderMkb(), medCase, manager);
                    //diagList.add(diag);
                }
                if (!adding2) {
                    Diagnosis diag = new Diagnosis();
                    setDiagnosisByType(true, diag, vocTypeOrder, form.getEntranceDiagnos(), form.getDateStart(), form.getEntranceMkb(), medCase, manager);
                    //diagList.add(diag);
                }
                //medCase.setDiagnosis(diagList);
            }
        }

        if (medCase.getDeniedHospitalizating() == null && medCase.getTransferDate() == null) {
            //При отказе от госпитализации считаем что пациент выбыл из приемника (сразу направлен в отделение)
            long currentDate = new Date().getTime();
            medCase.setTransferDate(new java.sql.Date(currentDate));
            medCase.setTransferTime(new java.sql.Time(currentDate));
        }
    }

    private boolean setDiagnosisByType(boolean aNewIs, Diagnosis aDiag, VocDiagnosisRegistrationType aType, String aName, String aDate, Long aCode, HospitalMedCase aMedCase, EntityManager aManager) {
        boolean resault = false;
        if (!aNewIs) {
            aNewIs = aDiag.getRegistrationType() != null && aDiag.getRegistrationType().equals(aType);
        }
        if (aNewIs) {
            aDiag.setName(aName);
            if (aCode != null) {
                VocIdc10 mkb = aManager.find(VocIdc10.class, aCode);
                aDiag.setIdc10(mkb);
            }
            aDiag.setMedCase(aMedCase);
            try {
                aDiag.setEstablishDate(DateFormat.parseSqlDate(aDate));
            } catch (Exception e) {

            }
            if (aDiag.getRegistrationType() == null) aDiag.setRegistrationType(aType);
            resault = true;
        }
        aManager.persist(aDiag);
        return resault;

    }


}
