package ru.ecom.expomc.ejb.services.registry;

import java.sql.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ru.ecom.ejb.services.util.QueryResultUtil;
import ru.ecom.expomc.ejb.domain.impdoc.ImportDocument;
import ru.ecom.expomc.ejb.domain.registry.RegistryEntry;
import ru.ecom.expomc.ejb.services.importservice.IImportService;
import ru.ecom.expomc.ejb.services.importservice.ImportException;
import ru.ecom.expomc.ejb.services.importservice.ImportFileForm;
import ru.ecom.expomc.ejb.services.importservice.ImportFileResult;

/**
 * Импорт реестра
 */
@Stateless
@Remote(IImportRegistryService.class)
public class ImportRegistryServiceBean implements IImportRegistryService {

    public long getRegistryDocumentId() {
        final String keyName = "LPU_FOND" ;
        ImportDocument doc  = QueryResultUtil.getSingleResult(ImportDocument.class, theManager.createQuery("from ImportDocument where keyName = :keyName")
                .setParameter("keyName",keyName)) ;
        if(doc==null) throw new IllegalStateException("Нет документа с ключом "+keyName) ;
        return doc.getId() ;
    }

    public long getRegistryForeignDocumentId() {
        try {
            String keyName = "FOREIGN" ;
            ImportDocument doc = (ImportDocument) theManager.createQuery("from ImportDocument where keyName = :keyName")
                    .setParameter("keyName",keyName).getSingleResult() ;
            return doc.getId() ;
        } catch (Exception e) {
            throw new IllegalStateException("Нет документа к ключом FOREIGN",e) ;
        }
    }


    public PreImportResult preImportRegistry(
             long aFormatId
            , String aRegistryFilename
    ) throws ImportRegistryException {

        ImportFileForm form = new ImportFileForm();
        form.setImportFormat(aFormatId);
        try {
            ImportFileResult result = theImportService.importFile(aRegistryFilename,1, aRegistryFilename, form) ;
            List<RegistryEntry> entries = theManager.createQuery("from RegistryEntry where time=:time").setParameter("time",result.getTimeId()).getResultList(); ;
            PreImportResult ret = new PreImportResult();
            TreeSet<String> companies = new TreeSet<String>();
            TreeSet<String> registries = new TreeSet<String>();
            TreeSet<String> bills = new TreeSet<String>();
            Date minDate = null ;
            Date maxDate = null ;

            for (RegistryEntry entry : entries) {
                add(companies, entry.getInsuranceCompany()) ;
                add(bills, entry.getBillNumber()) ;
                add(registries, entry.getRegistryNumber()) ;
                if(minDate==null) { minDate = entry.getDischargeDate() ; }
                if(minDate!=null && entry.getDischargeDate()!=null && minDate.getTime()>entry.getDischargeDate().getTime()) {
                    minDate = entry.getDischargeDate() ;
                }

                if(maxDate==null) { maxDate = entry.getDischargeDate() ; }
                if(maxDate!=null && entry.getDischargeDate()!=null && maxDate.getTime()<entry.getDischargeDate().getTime()) {
                    maxDate = entry.getDischargeDate() ;
                }
            }

            ret.setAllInOneCompany(companies.size()==1);
            ret.setCompanyName(toString(companies));

            ret.setAllInOneBill(bills.size()==1);
            ret.setBillNumber(toString(bills));

            ret.setAllInOneRegistry(registries.size()==1);
            ret.setRegistryNumber(toString(registries));

            ret.setCount(entries.size());

            return  ret;
        } catch (ImportException e) {
            throw new ImportRegistryException("Ошибка импорта реестра",e);
        }

    }

    private void add(Set<String> aSet, String aKey) {
        if(aKey==null) {
            aKey = "Нет ЗНАЧЕНИЯ!" ;
        }
        aSet.add(aKey) ;
    }

    private String toString(Set<String> aSet) {
        StringBuilder sb = new StringBuilder();
        boolean firstPassed = false ;
        for (String s : aSet) {
            if(firstPassed) {
                sb.append(", ") ;
            } else {
                firstPassed = true ;
            }
            sb.append(s) ;
        }
        return sb.toString();
    }

    public void importRegistry(String aOmcInsuranceCompanyCode, String aBillNumber, String aReestrNumber, Date aBillDate, boolean aForeign, String aRegistryFilename) throws ImportRegistryException {

    }

    @EJB IImportService theImportService ;
    @PersistenceContext public EntityManager theManager;
}
