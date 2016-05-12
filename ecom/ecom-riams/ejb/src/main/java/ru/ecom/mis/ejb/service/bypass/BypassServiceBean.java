package ru.ecom.mis.ejb.service.bypass;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;

import javax.annotation.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import jxl.Cell;
import jxl.Workbook;
import jxl.format.CellFormat;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.apache.log4j.Logger;

import ru.ecom.ejb.services.file.IJbossGetFileLocalService;
import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.ejb.services.util.JBossConfigUtil;
import ru.ecom.ejb.services.util.QueryIteratorUtil;
import ru.ecom.mis.ejb.domain.patient.Patient;
import ru.nuzmsh.util.PropertyUtil;

/**
 * Печать обходных листов
 */
@Stateless
@Remote(IBypassService.class)
public class BypassServiceBean implements IBypassService {

    private final static Logger LOG = Logger.getLogger(BypassServiceBean.class) ;
//    private final static boolean CAN_DEBUG = LOG.isDebugEnabled() ;
    
    //[start] asdfasdf
    public void printByAreaAddress(long aMonitorId, long aLpuAreaAddressTextId, long aFileId) {
        printByClause(aMonitorId, aFileId, "lpuAreaAddressText_id="+aLpuAreaAddressTextId);
    }
    //[end]

    public void printByArea(long aMonitorId, long aLpuAreaId, long aFileId) {
        printByClause(aMonitorId, aFileId, "lpuArea_id="+aLpuAreaId);
    }

    private void printByClause(long aMonitorId, long aFileId, String aClause) {

//        LpuArea area = theManager.find(LpuArea.class, aLpuAreaId) ;
        IMonitor monitor = theMonitorService.acceptMonitor(aMonitorId, "Подготовка к экспорту обходного листка");

//        Query query = theManager.createQuery("from Patient where lpuArea = :lpuArea")
//                .setParameter("lpuArea", area) ;

        long count = (Long)theManager.createQuery("select count(*) from Patient where "+aClause).getSingleResult() ;

        Query query = theManager.createQuery("from Patient where "+aClause) ;
        Iterator<Patient> iterator = QueryIteratorUtil.iterate(Patient.class, query) ;

//        HSSFWorkbook wb = new HSSFWorkbook();
//
//        HSSFSheet sheet = wb.createSheet();

        File file = theJbossGetFileLocalService.createFile(aFileId, "reg.xls");

        try {
            monitor = theMonitorService.startMonitor(aMonitorId, "Экспорт обходного листка", count);
            Workbook template = Workbook.getWorkbook(JBossConfigUtil.getDataFile("reg.xls")) ;
            WritableWorkbook workbook = Workbook.createWorkbook(file, template);
            WritableSheet sheet = workbook.getSheet(0) ;
            writeToSheet(monitor, sheet, iterator, createResponse()) ;
            workbook.write();
            workbook.close() ;
            monitor.finish(aMonitorId+"");
        } catch(IllegalMonitorStateException e) {
            throw e ;
        } catch (Exception e) {
            monitor.error("Ошибка экспорта",e);
            throw new IllegalArgumentException(e) ;
        }

    }

    private void writeToSheet(IMonitor aMonitor, WritableSheet aSheet, Iterator<Patient> aPatients, QueryResponse aResponse) throws WriteException {

        QueryResponse r = createResponse() ;
        int column = 1 ;
//        for (QueryResponseProperty property : r.getProperties()) {
//            Label label = new Label(column++, 1, property.getTitle());
//            aSheet.addCell(label);
//        }

        int rowNumber = 1 ;
        Cell cell = aSheet.getCell(1,2) ;
        CellFormat format = cell.getCellFormat() ;
        while (aPatients.hasNext()) {
            if(aMonitor.isCancelled()) throw new IllegalMonitorStateException("Прервано пользователем") ;
            rowNumber++ ;
            if(rowNumber%100==0) aMonitor.advice(100);
            Patient patient = aPatients.next();

            column = 1 ;
            for (QueryResponseProperty property : r.getProperties()) {
                String value ;
                try {
                    value = getValue(patient, property.getProperty()) ;
                } catch (Exception e) {
                    value = e.getMessage() ;
                    LOG.error("Ошибка "+patient,e);
                }

                Label label = new Label(column++, rowNumber, value) ;
                label.setCellFormat(format);
                aSheet.addCell(label);
            }
            rowNumber++ ;

            aMonitor.setText(patient.getLastname());
        }
    }

    private String getValue(Object aObject, String aProperty) {
        try {
            Object obj = PropertyUtil.getPropertyValue(aObject, aProperty) ;
            return obj!=null ? obj.toString() : "" ;
        } catch (Exception e) {
            throw new IllegalArgumentException(e) ;
        }
    }

    private QueryResponse createResponse() {
        QueryResponse r = new QueryResponse();
        LinkedList<QueryResponseProperty> props = new LinkedList<QueryResponseProperty>();
        props.add(new QueryResponseProperty("lastname","Фамилия")) ;
        props.add(new QueryResponseProperty("firstname", "Имя")) ;
        props.add(new QueryResponseProperty("middlename","Отчество")) ;
        props.add(new QueryResponseProperty("sexName","Пол")) ;
        props.add(new QueryResponseProperty("birthday","Дата рождения")) ;
        props.add(new QueryResponseProperty("passportInfo","Удостоверение личности")) ;
        props.add(new QueryResponseProperty("snils","СНИЛС")) ;
        props.add(new QueryResponseProperty("addressInfo","Адрес")) ;

        r.setProperties(props);
        return r ;
    }

    private @PersistenceContext EntityManager theManager;
    private @EJB IJbossGetFileLocalService theJbossGetFileLocalService ;
    private @EJB ILocalMonitorService theMonitorService;


}
