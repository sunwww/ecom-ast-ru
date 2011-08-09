/**
 * @author ikouzmin 02.03.2007 10:54:50
 */

package ru.ecom.expomc.ejb.services.exportformat;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.transform.Result;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;

import ru.ecom.ejb.services.file.IJbossGetFileLocalService;
import ru.ecom.ejb.services.monitor.ILocalMonitorService;
import ru.ecom.ejb.services.monitor.IMonitor;
import ru.ecom.expomc.ejb.domain.format.ExportFormat;
import ru.ecom.expomc.ejb.services.exportformat.driver.DriverManager;
import ru.ecom.expomc.ejb.services.exportformat.util.Xslt;


@Stateless
@Remote(IExportFormatService.class)
public class ExportFormatServiceBean implements IExportFormatService {
    private final static Logger LOG = Logger.getLogger(ExportFormatServiceBean.class);
    private final static boolean CAN_DEBUG = LOG.isDebugEnabled();

    private ExportFormat getExportFormat(Long anId) {
        return theManager.find(ExportFormat.class, anId);
    }

    /** Максимальное кол-во записей 0 - б.ограничений */
    public int getMaxRecords() { return theMaxRecords ; }
    public void setMaxRecords(int aMaxRecords) { theMaxRecords = aMaxRecords ; }


    public String getResultSet(Long anId) {
        ExportFormat exportFormat = getExportFormat(anId);
        String sql = exportFormat.getQuery();
        LOG.info("Query =[" + sql);
        StringBuffer s = new StringBuffer();
        String [] sqls = sql.split(";;");
        try {
            s.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            SimpleDateFormat FORMAT_ODBC = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat FORMAT_ODBCTIME = new SimpleDateFormat("HH:mm:ss.SSS");
            Date date  = new Date();
            s.append("<results exportdate='" + FORMAT_ODBC.format(date)+
                    "' exporttime='" + FORMAT_ODBCTIME.format(date)+"' exportdatetime='" +
                    FORMAT_ODBC.format(date)+" "+FORMAT_ODBCTIME.format(date)+"'>");

            boolean activeSql = true;
            for (int nQuery = 0; nQuery < sqls.length; nQuery++) {
                String sq = sqls[nQuery].trim();
                if (!activeSql && sq.endsWith("-->")) {
                    activeSql = true;
                    continue;
                }
                if (!activeSql) continue;
                if (sq.startsWith("<!--")) {
                    activeSql = false;
                    continue;
                }
                if (sq.startsWith("---")) break;
                if (sq.startsWith("--")) continue;


                Matcher matcher = Pattern.compile("^#([^#]*)#(.*)$").matcher(sq);
                String driverName, driverQuery;
                if (matcher.matches()) {
                    driverName = matcher.group(1);
                    driverQuery = matcher.group(2);
                    LOG.info("QUERY driver:`"+driverName+"' qry:`"+driverQuery+"'");
                } else {
                    driverName = (exportFormat.isNative())?"sql":"";
                    driverQuery = sq;
                    LOG.info("QUERY driver: `default' qry:`"+driverQuery+"'");
                }

                IExportFomatDriver driver = DriverManager.getDriver(driverName, theManager, exportFormat.isNative(), driverQuery);
                driver.execute(getMaxRecords());
                driver.saveXml(s);
            }

            s.append("</results>");
            return s.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "<results>" +
                    "<error><!CDATA[" + e.getMessage() +
                    "]]</error></results>";

        }
    }

    public void getResultSet(Long anId, Writer writer) throws SaveXmlException {
        ExportFormat exportFormat = getExportFormat(anId);
        String sql = exportFormat.getQuery();
        LOG.info("Query (TOFILE) =[" + sql);
        //StringBuffer s = new StringBuffer();
        String [] sqls = sql.split(";;");
        try {
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
            SimpleDateFormat FORMAT_ODBC = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat FORMAT_ODBCTIME = new SimpleDateFormat("HH:mm:ss.SSS");
            Date date  = new Date();
            writer.write("<results format='" +exportFormat.getComment()+
                    "' exportdate='" + FORMAT_ODBC.format(date)+
                    "' exporttime='" + FORMAT_ODBCTIME.format(date)+"' exportdatetime='" +
                    FORMAT_ODBC.format(date)+" "+FORMAT_ODBCTIME.format(date)+"'>");

            for (int nQuery = 0; nQuery < sqls.length; nQuery++) {
                String sq = sqls[nQuery].trim();
                if (sq.startsWith("--")) continue;
                Matcher matcher = Pattern.compile("^#([^#]*)#(.*)$").matcher(sq);
                String driverName, params;
                if (matcher.matches()) {
                    driverName = matcher.group(1);
                    params = matcher.group(2);
                    LOG.info("QUERY driver:`"+driverName+"' qry:`"+params+"'");
                } else {
                    driverName = (exportFormat.isNative())?"sql":"";
                    params = sq;
                    LOG.info("QUERY driver: `default' qry:`"+params+"'");
                }

                IExportFomatDriver driver = DriverManager.getDriver(driverName, theManager, exportFormat.isNative(), params);
                driver.execute(getMaxRecords());
                driver.saveXml(writer);
            }

            writer.write("</results>\n");

        } catch (Exception e) {
            e.printStackTrace();
            throw new SaveXmlException(e.getMessage(),e);
        }
    }

    private static String exportEntity(Object s) {
        if (s == null) return "";
        return s.toString().replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
    }


    public void transform(Long anId, Result outStream) throws TransformerException {
        ExportFormat entity = getExportFormat(anId);
        String xsltString = entity.getXslt();

        if (xsltString.trim().length()==0) {
            xsltString =
                    "<xsl:output method = \"xml\" indent = \"yes\" />\n" +
                    "<xsl:template match=\"/results\">\n" +
                    "<exportdoc exportdate=\"{@exportdatetime}\">\n" +
                    "\t<xsl:apply-templates/>\n" +
                    "</exportdoc>\n" +
                    "</xsl:template>\n" +
                    "\n" +
                    "<xsl:template match=\"/results/result[@tag]\">\n" +
                    "\t<xsl:element name=\"{concat(@tag,'s')}\">\n" +
                    "\t<xsl:attribute name=\"entityClass\"><xsl:value-of select=\"@entityClass\"/></xsl:attribute>\n" +
                    "\t<xsl:attribute name=\"count\"><xsl:value-of select=\"@rows\"/></xsl:attribute>\n" +
                    "\t<xsl:for-each select=\"./row\">\n" +
                    "\t\t<xsl:element name=\"{../@tag}\">\n" +
                    "\t\t<xsl:attribute name=\"id\"><xsl:value-of select=\"id\"/></xsl:attribute>\n" +
                    "\t\t<xsl:copy-of select=\"./*[name()!='id']\"/>\n" +
                    "\t\t</xsl:element>\n" +
                    "\t</xsl:for-each>\n" +
                    "\t</xsl:element>\n" +
                    "</xsl:template>";
        } else if (xsltString.trim().equalsIgnoreCase("text")) {
            xsltString =
                    "<xsl:output method=\"text\"/>\n" +
                    "<xsl:template match=\"/results\">\n" +
                    "#exporttime:<xsl:value-of select=\"@exportdatetime\"/>\n" +
                    "\t<xsl:apply-templates/>\n" +
                    "</xsl:template>\n" +
                    "\n" +
                    "<xsl:template match=\"/results/result[@tag]\">\n" +
                    "#name:<xsl:value-of select=\"@tag\"/>\n" +
                    "#entityClass:<xsl:value-of select=\"@entityClass\"/>\n" +
                    "#count:<xsl:value-of select=\"@rows\"/>\n" +
                    "#cols:<xsl:for-each select=\"./row[1]/*\">\n" +
                    "<xsl:value-of select=\"name()\"/><xsl:if test=\"position()!=last()\">,</xsl:if></xsl:for-each>\n" +
                    "\t<xsl:for-each select=\"./row\">\n" +
                    "<xsl:text>\n" +
                    "</xsl:text>\n" +
                    "<xsl:for-each select=\"./*\"><xsl:value-of select=\".\"/><xsl:if test=\"position()!=last()\"><xsl:text>\t</xsl:text></xsl:if></xsl:for-each>\n" +
                    "</xsl:for-each>\n" +
                    "</xsl:template>";
        }

        xsltString = Xslt.parseXsl(xsltString);

        StreamSource xmlSource;
        StreamSource xsltSource = new StreamSource(new StringReader(xsltString));

//        if (getMaxRecords()==0) {
//            try {
//                File xsqlFile = File.createTempFile("qry",".xml");
//                LOG.info("Use temp file for XSLT transform: "+xsqlFile.getAbsolutePath());
//
//
//            } catch (IOException e) {
//                LOG.error("transform XML: "+e.getMessage());
//                return;
//            }
//
//            xmlSource = null;
//        } else {
            String sourceXmlString = getResultSet(anId);
            LOG.info("Source xml loaded");
            xmlSource = new StreamSource(new StringReader(sourceXmlString));
//        }

        LOG.info("Transform started");
        Xslt.transform(xmlSource, xsltSource, outStream);
        LOG.info("Transform completed");

        return;
    }

    public String getTransformedXml(Long anId) throws TransformerException {

        // Вывод данных в строку
        StringWriter stringWriter = new StringWriter();

        transform(anId, new StreamResult(stringWriter));

        String ret = stringWriter.toString();
        //LOG.info("XML-OUT = [" + ret + "]");
        return ret;
        //return "ERR";
    }

    public void exportAsXml(Long anId, long aFileId) {
        try {
            File file = theJbossGetFileLocalService.createFile(aFileId, "exportdata.xml");
            transform(anId, new StreamResult(file));

        } catch (IllegalMonitorStateException e) {
            throw e;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }


    public void exportAsXml(Long anId, long aFileId, long aMonitorId) {
        IMonitor monitor = null;

        try {
            monitor = theMonitorService.startMonitor(aMonitorId, "Экспорт данных", 100);
            File xmlFile = File.createTempFile("export",".xml");
            LOG.info("Create output:'" + xmlFile.getAbsolutePath() + "'");
            monitor.advice(25);
            transform(anId, new StreamResult(xmlFile));

            File zipFile = theJbossGetFileLocalService.createFile(aFileId, "exportdata.xml.zip");
            LOG.info("Archiving started to "+zipFile.getAbsolutePath());
            sun.tools.jar.Main m = new sun.tools.jar.Main(System.out, System.err, "jar");
//            LOG.info("jar "+"cfM "+zipFile.getAbsolutePath()+ " -C "+xmlFile.getParentFile().getCanonicalPath()+" "+ xmlFile.getName());
            
            String[] cmd = {"cfM",zipFile.getAbsolutePath(),"-C",xmlFile.getParentFile().getCanonicalPath(), xmlFile.getName()} ;
            if(!m.run(cmd)) throw new SaveXmlException("Ошибка создания архива "+zipFile.getAbsoluteFile());
            LOG.info("Archiving completed");

            monitor.finish(aFileId + "");

        } catch (IllegalMonitorStateException e) {
            throw e;
        } catch (Exception e) {
            monitor.error("Ошибка экспорта", e);
            throw new IllegalArgumentException(e);
        }
    }

    public void createStandardXsl(Long anId) {
        LOG.info("createStandardXsl" + anId.toString());
//        ExportFormat entity = getExportFormat(anId);
//        String sourceXmlString = getResultSet(anId);
//        SAXBuilder saxBuilder = new SAXBuilder();
//        Document xdoc = saxBuilder.build(new StringReader());
    }

    /** Максимальное кол-во записей 0 - б.ограничений */
    private int theMaxRecords ;


    @PersistenceContext
    private EntityManager theManager;

    private
    @EJB
    IJbossGetFileLocalService theJbossGetFileLocalService;
    private
    @EJB
    ILocalMonitorService theMonitorService;


}


class BeanPropertyUtil {
    static List<Method> getBeanPropertyGetMethods(Class aClass) {
        List<Method> methodList = new ArrayList<Method>();
        Method[] methods = aClass.getMethods();
        for (Method method : methods) {
            String methodName = method.getName();

            if (method.getParameterTypes().length > 0) continue;
            if (methodName.equals("getClass")) continue;

            if (methodName.startsWith("get") ||
                    methodName.startsWith("is")) {

                methodList.add(method);
            }
        }
        return methodList;
    }

    static String getBeanPropertyByGetMethod(String aMethodName) {
        String fieldName = aMethodName.replaceFirst("^get", "").replaceFirst("^is", "");
        fieldName = fieldName.substring(0, 1).toLowerCase() +
                fieldName.substring(1);
        return fieldName;

    }



}