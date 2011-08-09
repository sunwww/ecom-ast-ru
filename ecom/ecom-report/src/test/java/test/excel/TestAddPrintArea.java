package test.excel;

import ru.ecom.report.excel.ReportEngine;

/**
 * @author esinev
 * Date: 29.09.2006
 * Time: 12:08:49
 */
public class TestAddPrintArea {
    public static void main(String[] args) {

        ReportEngine e = new ReportEngine();
        System.out.println("ReportEngine.appendRowsToPrintArea() = " + ReportEngine.appendRowsToPrintArea("╦шёЄ1!$A$2:$I$37",123));
    }
}
