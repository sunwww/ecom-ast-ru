package test.excel;

import ru.ecom.report.excel.ReportEngine;
import ru.ecom.report.excel.ReportEngineMakeException;
import ru.ecom.report.replace.BshValueGetter;
import ru.ecom.report.replace.SetValueException;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

import test.excel.example.RegistryPrintHolder;
import test.excel.example.RegistryPrintRenderHolder;
import test.excel.example.RegistryPrintGroupHolder;
import bsh.EvalError;

/**
 * @author esinev
 * Date: 28.09.2006
 * Time: 15:47:01
 */
public class TestReportEngine {

    public static RegistryPrintGroupHolder createGroup() {
        RegistryPrintGroupHolder h = new RegistryPrintGroupHolder();
        h.setName("Ленинский");
        h.addCount(12);
        h.addSumm(new BigDecimal(123));
        h.addSumm(new BigDecimal(11));
        return h ;
    }
    public static RegistryPrintRenderHolder createRender() {
        RegistryPrintRenderHolder h = new RegistryPrintRenderHolder();
        h.setRenderCode("ddd");
        h.setRenderName("adfasdfasdf");
        h.setLevel("123");
        h.setSerialNumber(1);
        return h ;
    }

    public static RegistryPrintHolder generateHolder() {
        RegistryPrintHolder h = new RegistryPrintHolder();
        h.getRenders().add(createRender()) ;
        h.getRenders().add(createRender()) ;
        h.getRenders().add(createRender()) ;
        h.getRenders().add(createRender()) ;
        h.getRenders().add(createRender()) ;

        h.getRegionGroups().add(createGroup()) ;
        h.getRegionGroups().add(createGroup()) ;
        h.getRegionGroups().add(createGroup()) ;
        h.getRegionGroups().add(createGroup()) ;
        h.getRegionGroups().add(createGroup()) ;
        h.getRegionGroups().add(createGroup()) ;
        return h ;
    }
    public static void main(String[] args) throws ReportEngineMakeException, EvalError, SetValueException, IOException {
        ReportEngine e = new ReportEngine();
        BshValueGetter getter = new BshValueGetter();
        getter.set("h", generateHolder());
        getter.set("table", new RegistryPrintRenderHolder()) ;
        e.make(new File("src/test/resources/registry.xls"), new File("test.xls"),getter, 0);
        getter.clear();
        Runtime.getRuntime().exec("oocalc test.xls") ;

    }
}
