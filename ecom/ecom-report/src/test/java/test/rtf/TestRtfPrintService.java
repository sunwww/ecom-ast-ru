package test.rtf;

import ru.ecom.report.rtf.RtfPrintServiceHelper;
import ru.ecom.report.rtf.RtfPrintException;

import java.io.File;

/**
 * @author esinev
 * Date: 10.10.2006
 * Time: 16:43:09
 */
public class TestRtfPrintService {
    public static void main(String[] args) throws RtfPrintException {

        RtfPrintServiceHelper service = new RtfPrintServiceHelper();
        System.out.println(new File("src/test/resources/066.rtf").getAbsolutePath());
        service.setTemplateDir("src/test/resources");
        service.setWorkDir("src/test/resources");
        service.print("066", "test.rtf.HelloValueInit", null) ;
        System.out.println("DONE.");
    }
}
