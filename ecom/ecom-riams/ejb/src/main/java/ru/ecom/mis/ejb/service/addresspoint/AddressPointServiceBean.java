package ru.ecom.mis.ejb.service.addresspoint;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvBeanIntrospectionException;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import ru.ecom.address.ejb.domain.address.Address;
import ru.ecom.address.ejb.service.AddressPointCheck;
import ru.ecom.address.ejb.service.AddressPointCheckHelper;
import ru.ecom.address.ejb.service.IAddressService;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.ejb.services.util.QueryIteratorUtil;
import ru.ecom.ejb.util.injection.EjbEcomConfig;
import ru.ecom.ejb.xml.XmlUtil;
import ru.ecom.mis.ejb.domain.lpu.LpuArea;
import ru.ecom.mis.ejb.domain.lpu.LpuAreaAddressPoint;
import ru.ecom.mis.ejb.domain.lpu.LpuAreaAddressText;
import ru.ecom.mis.ejb.service.addresspoint.dto.PatientAttachmentDto;
import ru.ecom.mis.ejb.service.patient.QueryClauseBuilder;
import ru.ecom.report.util.XmlDocument;
import ru.nuzmsh.util.CollectionUtil;
import ru.nuzmsh.util.format.DateFormat;

import javax.annotation.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import java.io.*;
import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static ru.nuzmsh.util.EqualsUtil.isEquals;
import static ru.nuzmsh.util.EqualsUtil.isOneOf;
import static ru.nuzmsh.util.StringUtil.isNotEmpty;

/**
 * @author azviagin
 */
@Stateless
@Local(IAddressPointService.class)
@Remote(IAddressPointService.class)
public class AddressPointServiceBean implements IAddressPointService {

    private static final Logger LOG = Logger.getLogger(AddressPointServiceBean.class);
    private static final String PRIK = ".prik";
    private static final String ZIP = ".zip";
    private static final String XML = ".xml";
    private static final String CSV = ".csv";

    private final WebQueryResult res = new WebQueryResult();
    private final Collection<WebQueryResult> errList = new ArrayList<>();
    private final AddressPointCheckHelper pointCheckHelper = new AddressPointCheckHelper();
    private @EJB
    IAddressService addressService;
    private @PersistenceContext
    EntityManager entityManager;


    @Override
    public WebQueryResult exportExtDispPlanAll(String ageString, String filenamePrefix
            , String addSqlString, Long lpuId, Long lpuAreaId
            , String dateFrom, String dateTo, String regPeriod
            , String regNumber, String packageNumber, Long insCompanyId, String dispPlanType) throws ParserConfigurationException, TransformerException {
        StringBuilder addSql = new StringBuilder().append(addSqlString);
        StringBuilder filenames = new StringBuilder();
        errList.clear();
        if (ageString != null) {
            addSql.append("and cast(to_char(to_date('").append(dateTo).append("','dd.mm.yyyy'),'yyyy') as int) -cast(to_char(p.birthday,'yyyy') as int) +(case when (cast(to_char(to_date('")
                    .append(dateTo).append("','dd.mm.yyyy'), 'mm') as int) -cast(to_char(p.birthday, 'mm') as int) +(case when (cast(to_char(to_date('")
                    .append(dateTo).append("','dd.mm.yyyy'),'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end) <0) then -1 else 0 end) ").append(ageString);
        }
        EjbEcomConfig config = EjbEcomConfig.getInstance();
        String workDir = config.get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
        String filename;
        StringBuilder sql = new StringBuilder();
        List<Object[]> listPat;
        String[][] props = new String[][]{
                {"ltrim(to_char(p.birthday,'MM'),'0')", "PERIOD", "p.birthday", "1", "Месяц планируемого проведения ДД"}
                , {" cast(to_char(to_date('" + dateTo + "','dd.MM.yyyy'),'yyyy') as int)-cast(to_char(p.birthday,'yyyy') as int) +(case when (cast(to_char(to_date('" + dateTo + "','dd.MM.yyyy'), 'mm') as int)-cast(to_char(p.birthday, 'mm') as int) +(case when (cast(to_char(to_date('" + dateTo + "','dd.MM.yyyy'),'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end)<0) then -1 else 0 end)", "TIP_DATA", "", "1", "Возраст (полных лет)"}
                , {"p.lastname", "FAM", "p.lastname", "1", "Фамилия"}, {"p.firstname", "IM", "p.firstname", "1", "Имя"}
                , {"case when p.middlename='' or p.middlename='Х' or p.middlename is null then '' else p.middlename end", "OT", "p.middlename", null, "Отчество"}
                , {"vs.omccode", "W", "vs.omccode", "Пол"}
                , {"to_char(p.birthday,'yyyy-mm-dd')", "DR", "p.birthday", "1", "Дата рождение"}, {"p.snils", "SNILS", "p.snils", null, "СНИЛС"}
                , {"vic.omcCode", "DOCTYPE", "vic.omcCode", null, "Тип документа"}, {"p.passportSeries", "DOCSER", "p.passportSeries", null, "Серия документа"}
                , {"p.passportNumber", "DOCNUM", "p.passportNumber", null, "Номер паспорта"}, {"to_char(p.passportdateissued,'yyyy-mm-dd')", "DOCDATE", "p.passportdateissued", "1", "Дата выдачи документа"}
                , {"p.commonNumber", "ENP", "p.commonNumber", null, "ЕПН"}

                , {"vmpo.code", "VPOLIS", "vmpo.code", "1", "Вид полиса"}
                , {"mp.series", "SPOLIS", "mp.series", null, "Серия полиса"}
                , {"mp.polnumber", "NPOLIS", "mp.polnumber", "1", "Номер полиса"}
        };

        StringBuilder fld = new StringBuilder();
        StringBuilder fldGroup = new StringBuilder();
        for (int ind = 0; ind < props.length; ind++) {
            String[] p = props[ind];
            if (ind != 0 && !p[0].equals("")) {
                fld.append(",");
                if (!p[2].equals("")) {
                    fldGroup.append(",");
                }
            }
            if (!p[0].equals("")) {
                fld.append(" ").append(p[0]).append(" as ").append(" fld").append(ind).append("_");
                fldGroup.append(" ").append(p[2]);
            }

        }
        //MO212345620200617.csv
        filename = "ND" + filenamePrefix + regNumber + "T30_" + regPeriod.substring(2, 4) + regPeriod.substring(5, 7) + XmlUtil.namePackage(packageNumber);
        filenames.append("#").append(filename).append(".xml");
        sql.setLength(0);
        sql.append("select ").append(fld);
        sql.append(" ,p.id as pid, lp.id as lpid");
        if ("DISPPLAN".equals(dispPlanType)) {
            sql.append(" from ExtDispPlanPopulation plan ")
                    .append(" left join ExtDispPlanPopulationRecord rec on rec.plan_id=plan.id")
                    .append(" left join patient p on p.id=rec.patient_id")
                    .append(" left join lpuattachedbydepartment lp on lp.patient_id=p.id ");
        } else {
            sql.append(" from LpuAttachedByDepartment lp");
            sql.append(" left join patient p on lp.patient_id=p.id");
        }

        sql.append(" left join VocIdentityCard vic on vic.id=p.passportType_id");
        sql.append(" left join VocSex vs on vs.id=p.sex_id");
        sql.append(" left join medpolicy mp on mp.patient_id=p.id and mp.dtype='MedPolicyOmc'");
        sql.append(" left join vocmedpolicyomc vmpo on vmpo.id=mp.type_id");

        sql.append(" where ");
        if ("DISPPLAN".equals(dispPlanType)) {
            sql.append(" plan.year='").append(dateTo.substring(6)).append("' and ");
        }
        sql.append(" cast(to_char(p.birthday,'MM') as int) between cast(to_char(to_date('").append(dateFrom).append("','dd.MM.yyyy'),'MM') as int) and cast(to_char(to_date('")
                .append(dateTo).append("','dd.MM.yyyy'),'MM') as int) and ");
        sql.append(" 0 = (select count(edc.id) from extdispcard edc where edc.patient_id=p.id and cast(to_char(edc.finishdate,'yyyy') as int) = cast(to_char(to_date('")
                .append(dateTo).append("','dd.MM.yyyy'),'yyyy') as int)) and");
        if (lpuId != null && lpuId > 0) {
            sql.append(" (p.lpu_id='").append(lpuId).append("' or lp.lpu_id='").append(lpuId).append("' ) and ");
        }
        if (lpuAreaId != null && lpuAreaId.intValue() > 0) {
            sql.append(" (p.lpuArea_id='").append(lpuAreaId).append("' or lp.area_id='").append(lpuAreaId).append("') and ");
        }
        sql.append(" p.noActuality is not true and p.deathDate is null ");
        sql.append(" and mp.id is not null and (mp.actualdateto is null or mp.actualdateto >current_date)");
        sql.append(" ").append(addSql).append(" and lp.dateto is null");
        sql.append(" group by p.id, lp.id, ").append(fldGroup);
        sql.append(" order by p.lastname,p.firstname,p.middlename,p.birthday");
        listPat = entityManager.createNativeQuery(sql.toString())
                .setMaxResults(90000).getResultList();
        for (int i = 0; i < listPat.size(); i++) {
            Object[] o = listPat.get(i);
            int age = Double.valueOf(o[1].toString()).intValue();

            /**
             *  Если старше 18 лет, и возраст делится на 3 без остатка, до ДД (DP), иначе - профосмотр (DO >=18), (DF <18).
             *
             */
            String dispType;
            if (age > 17) {
                dispType = age % 3 == 0 && age > 18 ? "DP" : "DO";
            } else {
                dispType = "DF";
            }
            o[1] = dispType;
            listPat.set(i, o);
        }
        createXml(workDir, filename, regPeriod, regNumber, listPat, props, "NPR");
        res.set1(filenames.substring(1));
        return res;
    }

    @Override
    public WebQueryResult exportAll(String filenamePrefix
            , String aAddSql, Long lpuId, Long areaId
            , String dateFrom, String dateTo, String periodByReestr
            , String reestrNum, String packageNum, Long companyId, boolean needDivide, String xmlFormat, String fileType, String returnType) throws ParserConfigurationException, TransformerException {
        StringBuilder addSql = new StringBuilder().append(aAddSql);
        StringBuilder filenames = new StringBuilder();
        errList.clear();
        boolean lpuCheck = lpuId != null && lpuId > 0;
        String workDir = EjbEcomConfig.getInstance().get("tomcat.data.dir", "/opt/tomcat/webapps/rtf");
        String filename;
        StringBuilder sql = new StringBuilder();
        List<Object[]> listPat;
        String[][] props;
        if ("1".equals(xmlFormat)) { //NEW FORMAT (01.07.2015)
            props = new String[][]{
                    {"p.lastname", "FAM", "p.lastname", "1", "Фамилия"}
                    , {"p.firstname", "IM", "p.firstname", "1", "Имя"}
                    , {"case when p.middlename='' or p.middlename='Х' or p.middlename is null then '' else p.middlename end", "OT", "p.middlename", null, "Отчество"}
                    , {"to_char(p.birthday,'yyyy-mm-dd')", "DR", "p.birthday", "1", "Дата рождение"}
                    , {"p.snils", "SNILS", "p.snils", null, "СНИЛС"}
                    , {"vic.omcCode", "DOCTYPE", "vic.omcCode", null, "Тип документа"}
                    , {"p.passportSeries", "DOCSER", "p.passportSeries", null, "Серия документа"}
                    , {"p.passportNumber", "DOCNUM", "p.passportNumber", null, "Номер паспорта"}
                    , {"to_char(p.passportdateissued,'yyyy-mm-dd')", "DOCDT", "p.passportdateissued", null, "Дата выдачи документа"}
                    , {"cast('' as varchar(1))", "TEL", "p.phone", null, "Телефон"}
                    , {"p.commonNumber", "RZ", "p.commonNumber", null, "ЕПН"}
                    , {" case when lp.id is null then '1' else coalesce(vat.code,'2') end", "SP_PRIK", "lp.id,vat.code", "1", "Тип прикрепления"}
                    , {"case when lp.dateTo is null then '1' else '2' end", "T_PRIK", "lp.dateTo", "1", "прикрепление/открепление"}
                    , {"to_char(lp.dateFrom,'yyyy-mm-dd')", "DATE_1", "lp.dateFrom", "1", "Дата прикрепления"}
                    , {"case when lp.newAddress='1' then '1' else '0' end", "N_ADR", "lp.newAddress", null, ""}
                    , {"case when la.codeDepartment!='' then la.codeDepartment else ml3.codeDepartment end", "KODPODR", "la.codeDepartment,ml3.codeDepartment", "1", "Код подразделения"}
                    , {"la.number", "LPUUCH", "la.number", null}
                    , {"wp.snils", "SSD", "wp.snils", "1", "СНИЛС врача"}
                    , {"cast('1' as varchar(1))", "MEDRAB", "", null, "Врач/медработник"}
            };
        } else {
            throw new IllegalArgumentException("Версия xml документа не допускается: " + xmlFormat);
        }

        StringBuilder fld = new StringBuilder();
        StringBuilder fldGroup = new StringBuilder();
        for (int ind = 0; ind < props.length; ind++) {
            String[] p = props[ind];
            if (ind != 0 && !p[0].equals("")) {
                fld.append(",");
                if (!p[2].equals("")) {
                    fldGroup.append(",");
                }
            }
            if (!p[0].equals("")) {
                fld.append(" ").append(p[0]).append(" as ").append(" fld").append(ind).append("_");
                fldGroup.append(" ").append(p[2]);
            }

        }
        if (needDivide) {
            StringBuilder sqlGroup = new StringBuilder()
                    .append("select lp.company_id,vri.smocode")
                    .append(" from Patient p")
                    .append(" left join MisLpu ml1 on ml1.id=p.lpu_id")
                    .append(" left join LpuAttachedByDepartment lp on lp.patient_id=p.id")
                    .append(" left join MisLpu ml2 on ml2.id=lp.lpu_id")
                    .append(" left join VocAttachedType vat on lp.attachedType_id=vat.id")
                    .append(" left join VocIdentityCard vic on vic.id=p.passportType_id")
                    .append(" left join REG_IC vri on vri.id=lp.company_id")
                    .append(" where ");
            if (companyId != null && companyId != 0)
                sqlGroup.append("lp.company_id='").append(companyId).append("' and ");
            if (lpuCheck)
                sqlGroup.append(" (p.lpu_id='").append(lpuId).append("' or lp.lpu_id='").append(lpuId).append("' or ml1.parent_id='").append(lpuId).append("' or ml2.parent_id='").append(lpuId).append("') and ");
            if (lpuCheck && areaId != null && areaId > 0L) {
                sqlGroup.append(" (p.lpuArea_id='").append(areaId).append("' or lp.area_id='").append(areaId).append("') and ");
            }
            sqlGroup.append(" p.noActuality is not true and p.deathDate is null ")
                    .append(" ").append(addSql)
                    .append(" group by lp.company_id,vri.smocode")
                    .append(" order by lp.company_id,vri.smocode");
            List<Object[]> listComp = entityManager.createNativeQuery(sqlGroup.toString())
                    .setMaxResults(90000).getResultList();

            for (Object[] company : listComp) {
                filename = getFilenameForAttachment(filenamePrefix, periodByReestr, reestrNum, packageNum, fileType, company[1]);

                filenames.append("#").append(filename).append(".").append(fileType);

                sql.setLength(0);
                sql.append("select ").append(fld)
                        .append(",case when vmpt.code='1' then mp.series ||' № '||mp.polnumber when vmpt.code='2' then mp.series ||mp.polnumber end as mpNumber")
                        .append(", case when vmpt.code ='1' then 'С' when vmpt.code ='2' then 'В' else 'П' end as medpolicytype")
                        .append(" ,to_char(lp.dateTo,'yyyy-mm-dd') as f21_detachDate")
                        .append(" ,p.id as pid, lp.id as lpid")
                        .append(" from Patient p")
                        .append(" left join MisLpu ml1 on ml1.id=p.lpu_id")
                        .append(" left join LpuAttachedByDepartment lp on lp.patient_id=p.id")
                        .append(" left join MisLpu ml2 on ml2.id=lp.lpu_id")
                        .append(" left join VocAttachedType vat on lp.attachedType_id=vat.id")
                        .append(" left join VocIdentityCard vic on vic.id=p.passportType_id")
                        .append(" left join lpuArea la on la.id=lp.area_id")
                        .append(" left join mislpu ml3 on ml3.id= la.lpu_id")
                        .append(" left join workfunction wf on wf.id=la.workfunction_id")
                        .append(" left join worker w on w.id=wf.worker_id")
                        .append(" left join patient wp on wp.id=w.person_id")
                        .append(" left join medpolicy mp on mp.patient_id=p.id and coalesce(mp.actualdateto, current_date)>=current_date and mp.dtype='MedPolicyOmc'")
                        .append(" left join vocmedpolicyomc vmpt on vmpt.id=mp.type_id")
                        .append(" where ")
                        .append(" lp.company_id");
                if (company[0] != null) {
                    sql.append("=").append(company[0]);
                } else {
                    sql.append(" is null ");
                }
                sql.append(" and ");
                if (lpuCheck) {
                    sql.append(" (p.lpu_id='").append(lpuId).append("' or lp.lpu_id='").append(lpuId).append("' or ml1.parent_id='").append(lpuId).append("' or ml2.parent_id='").append(lpuId).append("') and ");
                }
                if (lpuCheck && areaId != null && areaId.intValue() > 0) {
                    sql.append(" (p.lpuArea_id='").append(areaId).append("' or lp.area_id='").append(areaId).append("') and ");
                }
                sql.append(" p.noActuality is not true and p.deathDate is null ")
                        .append(" ").append(addSql)
                        .append(" group by p.id, lp.id, mp.series ,mp.polnumber ,vmpt.code, ").append(fldGroup)
                        .append(" order by p.lastname,p.firstname,p.middlename,p.birthday");
                listPat = entityManager.createNativeQuery(sql.toString())
                        .setMaxResults(90000).getResultList();
                createFile(workDir, filename, periodByReestr, reestrNum, listPat, props, fileType);

            }
        } else {
            filename = getFilenameForAttachment(filenamePrefix, periodByReestr, reestrNum, packageNum, fileType, null);

            filenames.append("#").append(filename).append(".").append(fileType);
            sql.setLength(0);
            sql.append("select ").append(fld)
                    .append(",case when vmpt.code='1' then mp.series ||' № '||mp.polnumber when vmpt.code='2' then mp.series ||mp.polnumber end as f19_mpNumber")
                    .append(", case when vmpt.code ='1' then 'С' when vmpt.code ='2' then 'В' else 'П' end as f20_medpolicytype")
                    .append(" ,to_char(lp.dateTo,'yyyy-mm-dd') as f21_detachDate")
                    .append(" ,p.id as pid, lp.id as lpid")
                    .append(" from Patient p")
                    .append(" left join MisLpu ml1 on ml1.id=p.lpu_id")
                    .append(" left join LpuAttachedByDepartment lp on lp.patient_id=p.id")
                    .append(" left join MisLpu ml2 on ml2.id=lp.lpu_id")
                    .append(" left join VocAttachedType vat on lp.attachedType_id=vat.id")
                    .append(" left join VocIdentityCard vic on vic.id=p.passportType_id")
                    .append(" left join lpuArea la on la.id=lp.area_id")
                    .append(" left join mislpu ml3 on ml3.id=la.lpu_id")
                    .append(" left join workfunction wf on wf.id=la.workfunction_id")
                    .append(" left join worker w on w.id=wf.worker_id")
                    .append(" left join patient wp on wp.id=w.person_id")
                    .append(" left join medpolicy mp on mp.patient_id=p.id and coalesce(mp.actualdateto, current_date)>=current_date and mp.dtype='MedPolicyOmc'")
                    .append(" left join vocmedpolicyomc vmpt on vmpt.id=mp.type_id")
                    .append(" where ");
            if (companyId != null && companyId != 0) {
                sql.append("lp.company_id='").append(companyId).append("' and ");
            }
            if (lpuCheck) {
                sql.append(" (p.lpu_id='").append(lpuId).append("' or lp.lpu_id='").append(lpuId).append("' or ml1.parent_id='").append(lpuId).append("' or ml2.parent_id='").append(lpuId).append("') and ");
            }
            if (lpuCheck && areaId != null && areaId.intValue() > 0) {
                sql.append(" (p.lpuArea_id='").append(areaId).append("' or lp.area_id='").append(areaId).append("') and ");
            }
            sql.append(" p.noActuality is not true and p.deathDate is null ")
                    .append(addSql)
                    .append(" group by p.id, lp.id, mp.series ,mp.polnumber ,vmpt.code, ").append(fldGroup)
                    .append(" order by p.lastname,p.firstname,p.middlename,p.birthday");
            listPat = entityManager.createNativeQuery(sql.toString())
                    .setMaxResults(90000).getResultList();
            createFile(workDir, filename, periodByReestr, reestrNum, listPat, props, fileType);
        }
        if (filenames.length() > 0 && isOneOf(returnType, "prik", "zip")) { //Если надо сделать архивы
            String[] files = filenames.substring(1).split("#");
            String[] zipFiles = filenames.substring(1).replace(XML, PRIK).split("#");
            for (String s : files) {
                String[] ss = {s};
                createArchive(workDir, s.replace(XML, PRIK).replace(CSV, PRIK), ss);
            }
            res.set1(filenames.substring(1).replace(XML, PRIK).replace(CSV, PRIK));
            if (returnType.equals("zip")) {
                createArchive(workDir, periodByReestr + reestrNum + ZIP, zipFiles);
                res.set1(periodByReestr + reestrNum + ZIP);
                return res;
            }
            return res;
        }

        res.set1(filenames.length() > 0 ? filenames.substring(1) : "");
        return res;
    }

    private String getFilenameForAttachment(String filenamePrefix, String periodByReestr, String reestrNum, String packageNum, String fileType, Object smoCode) {
        String filename;
        if ("xml".equals(fileType)) {
            filename = "P" + filenamePrefix + reestrNum + "S" + (smoCode == null ? "-" : smoCode)
                    + "_" + periodByReestr + XmlUtil.namePackage(packageNum);
        } else { //csv
            String currentDate;
            try {
                currentDate = DateFormat.formatToLogic(new Date(System.currentTimeMillis()));

            } catch (ParseException e) {
                currentDate = "";
            }
            filename = "MO2" + reestrNum + currentDate;
        }
        return filename;
    }

    private void createArchive(String aWorkDir, String archiveName, String[] aFileNames) {
        StringBuilder sb = new StringBuilder();
        sb.append("zip -r -j -9 ").append(aWorkDir).append("/").append(archiveName).append(" ");
        for (String filename : aFileNames) {
            sb.append(aWorkDir).append("/").append(filename).append(" ");
        }
        try {
            LOG.info("START EXECUTING = " + sb);
            try {//Удаляем архив перед созданием;
                Runtime.getRuntime().exec("zip -d " + aWorkDir + "/" + archiveName + " *");
            } catch (Exception ignored) {
            }//Не удалось очистить архив, т.к. его нету. Ничего страшного)
            Runtime.getRuntime().exec(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setExportDate(Long attachmentId) {
        entityManager.createNativeQuery("update lpuattachedbydepartment set exportDate=current_date , defectperiod='' where id=" + attachmentId).executeUpdate();
    }

    private void createFile(String workDir, String filename, String aPeriodByReestr, String regNumber
            , List<Object[]> listPat, String[][] aProps, String fileType) throws ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException {
        if (isEquals(fileType, "xml")) {
            createXml(workDir, filename, aPeriodByReestr, regNumber, listPat, aProps, "ZAP");
        } else if (isEquals(fileType, "csv")) {
            createCsv(workDir, filename, listPat, regNumber);
        }
    }

    /**
     * Формирование файла с информацией о прикрепленном населении в формате csv
     *
     * @param workDir
     * @param filename
     * @param patientList
     * @param regNumber   Код ЛПУ
     */
    private void createCsv(String workDir, String filename, List<Object[]> patientList, String regNumber) {
        File outFile = new File(workDir + "/" + filename + CSV);
        try {
            PrintWriter writer = new PrintWriter(outFile, "windows-1251");
            StatefulBeanToCsv<PatientAttachmentDto> beanToCsv = new StatefulBeanToCsvBuilder(writer).withQuotechar(CSVWriter.DEFAULT_ESCAPE_CHARACTER)
                    .withSeparator(';').build();
            for (Object[] pat : patientList) {
                beanToCsv.write(createPatient(pat, regNumber));
            }
            writer.close();

        } catch (FileNotFoundException | CsvRequiredFieldEmptyException | CsvDataTypeMismatchException
                | UnsupportedEncodingException | CsvBeanIntrospectionException e) {
            e.printStackTrace();
        }
    }


   /* private List<Object[]> makeTest() {
        List<Object[]> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Object[] o = new Object[18];
            for (int j = 0; j < 18; j++) {
                o[j] = "ИВАНОВ_" + i + "_" + j;
            }
            list.add(o);
        }
        return list;
    }*/

    private PatientAttachmentDto createPatient(Object[] patObject, String regNumber) {
        boolean isDettached = isEquals("2", toString(patObject[12]));
        String policyType = toString(patObject[20]);
        PatientAttachmentDto pat = new PatientAttachmentDto();
        pat.setMedPolicyType(policyType);
        pat.setLpuCode(toString(regNumber));
        pat.setLastname(toString(patObject[0]));
        pat.setFirstname(toString(patObject[1]));
        pat.setMiddlename(toString(patObject[2]));
        pat.setBirthDate(toDate(patObject[3]));
        pat.setSnils(toString(patObject[4]));
        pat.setPassportType(toString(patObject[5]));
        pat.setPassportNumber(toString(patObject[6]) + " " + toString(patObject[7]));
        pat.setPassportDate(toDate(patObject[8]));
        pat.setCommonNumber(toString(patObject[10]));
        pat.setAttachedMethod(toString(patObject[11]));
        pat.setAttachedType(toString(patObject[12]));
        pat.setAttachedDate(toDate(patObject[13]));
        if (isDettached) {
            pat.setDettachedDate(toDate(patObject[21]));
        }
        pat.setDepartmentCode(toString(patObject[15]));
        pat.setAreaNumber(toString(patObject[16]));
        pat.setDoctorSnils(toString(patObject[17]));
        if (!isEquals("П", policyType)) {
            pat.setMedPolicyNumber(toString(patObject[19]));
        }
        return pat;
    }

    //toString
    private String toString(Object o) {
        return o == null ? null : o.toString();
    }

    //toDate
    private Date toDate(Object o) {
        try {
            return o == null ? null : DateFormat.parseSqlDate(o.toString(), "yyyy-MM-dd");
        } catch (ParseException e) {
            return null;
        }
    }

    private void createXml(String workDir, String filename, String regPeriod, String regNumber
            , List<Object[]> listPat, String[][] props, String zapName
    ) throws ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException {
        XmlDocument xmlDoc = new XmlDocument();

        Element root = xmlDoc.newElement(xmlDoc.getDocument(), "ZL_LIST", null);
        File outFile = new File(workDir + "/" + filename + XML);
        Element title = xmlDoc.newElement(root, "ZGLV", null);

        if ("NPR".equals(zapName)) { //Для планирования ДД
            xmlDoc.newElement(title, "VERSION", "1.0");
            xmlDoc.newElement(title, "DATA", regPeriod);
        } else {
            xmlDoc.newElement(title, "PERIOD", regPeriod.substring(2, 4));
            xmlDoc.newElement(title, "N_REESTR", regNumber);
        }

        xmlDoc.newElement(title, "FILENAME", filename);
        int i = 0;
        for (Object[] pat : listPat) {
            boolean isError = false;
            for (int j = 0; j < props.length; j++) {
                String[] prop = props[j];
                if (!checkIsRequiedValueIsNotEmpty(pat[j], prop[3])) {
                    WebQueryResult ress = new WebQueryResult();
                    ress.set1(pat[pat.length - 2]);
                    ress.set2(pat[pat.length - 1]);

                    if ("NPR".equals(zapName)) {
                        ress.set3("Пациент - " + pat[2] + " " + pat[3] + " " + pat[4] + " г.р." + pat[6] + ". Неверно заполнено поле \"" + prop[4] + "\" - " + pat[j]);
                    } else {
                        ress.set3("Пациент - " + pat[0] + " " + pat[1] + " " + pat[2] + ". Неверно заполнено поле \"" + prop[4] + "\" - " + pat[j]);
                    }
                    errList.add(ress);
                    isError = true;
                }
            }
            if (!isError) {
                Element zap = xmlDoc.newElement(root, zapName, null);
                xmlDoc.newElement(zap, "IDCASE", XmlUtil.getStringValue(++i));
                for (int ind = 0; ind < props.length; ind++) {
                    String[] prop = props[ind];
                    xmlDoc.newElement(zap, prop[1], XmlUtil.getStringValue(pat[ind]));
                }
                if (!"NPR".equals(zapName)) {
                    xmlDoc.newElement(zap, "REFREASON", "");
                    setExportDate(Long.parseLong(pat[pat.length - 1].toString()));
                }
            }
        }

        XmlUtil.saveXmlDocument(xmlDoc, outFile);
        res.set2(errList);
    }

    private boolean checkIsRequiedValueIsNotEmpty(Object aValue, String isRequid) {
        if ("1".equals(isRequid)) {
            return aValue != null && !aValue.toString().equals("");
        }
        return true;
    }

    public void onPersist(LpuAreaAddressText aLpuAreaAddressText) {
        Address address = aLpuAreaAddressText.getAddress();
        EntityManager manager = entityManager;
        // весь поселок
        if (address.getDomen() == 5) {
            Query query = manager.createQuery("from Address where parent = :parent").setParameter("parent", address);
            Iterator<Address> iterator = QueryIteratorUtil.iterate(Address.class, query);
            // перебираем все улицы и прикрепляем
            while (iterator.hasNext()) {
                Address child = iterator.next();
                LpuAreaAddressPoint point = new LpuAreaAddressPoint();
                point.setLpuAreaAddressText(aLpuAreaAddressText);
                point.setAddress(child);
                manager.persist(point);
                manager.flush();
                manager.clear();
            }
        } else {
            List<AddressPointCheck> checks = pointCheckHelper.parsePoints(aLpuAreaAddressText.getAddressString());
            // нет домов, прикрепляем по всех улице
            if (checks.isEmpty()) {
                LpuAreaAddressPoint point = new LpuAreaAddressPoint();
                point.setLpuAreaAddressText(aLpuAreaAddressText);
                point.setAddress(address);
                manager.persist(point);
            } else {
                // по домам , корпусам и квартирам
                for (AddressPointCheck check : checks) {
                    LpuAreaAddressPoint point = new LpuAreaAddressPoint();
                    point.setLpuAreaAddressText(aLpuAreaAddressText);
                    point.setAddress(address);
                    point.setHouseNumber(check.getNumber());
                    point.setHouseBuilding(check.getBuilding());
                    point.setFlat(check.getFlat());
                    manager.persist(point);
                }
            }
        }
        manager.flush();
        manager.clear();
    }

    @SuppressWarnings("unchecked")
    public void checkExists(long aLpuAreaId, Long aLpuAddressTextId, long aAddress, String aNumber, String aBuilding, String aFlat) {
        QueryClauseBuilder builder = new QueryClauseBuilder();
        builder.addIsNull("houseNumber", aNumber);
        builder.addIsNull("houseBuilding", aBuilding);
        builder.addIsNull("flat", aFlat);
        builder.addNot("lpuAreaAddressText_id", aLpuAddressTextId);
        builder.add("address_AddressId", aAddress);
        EntityManager manager = entityManager;
        Query query = builder.build(manager, "from LpuAreaAddressPoint where", "");
        List<LpuAreaAddressPoint> list = query.getResultList();
        if (CollectionUtil.isNotEmpty(list)) {
            long areaTypeId = manager.find(LpuArea.class, aLpuAreaId).getType().getId();
            for (LpuAreaAddressPoint point : list) {
                if (point.getLpuAreaAddressText().getArea().getType().getId() == areaTypeId) {
                    String addressText = addressService.getAddressString(aAddress, aNumber, aBuilding, aFlat, null);
                    throw new IllegalStateException("Адрес " + addressText
                            + " уже существует у участка " + point.getLpuAreaAddressText().getArea().getName()
                            + " в ЛПУ " + point.getLpuAreaAddressText().getArea().getLpu().getName()
                            + " по адресу " + point.getLpuAreaAddressText().getAddressString());
                }
            }
        }

        // дом корпус кв
        if (isNotEmpty(aNumber) && isNotEmpty(aBuilding) && isNotEmpty(aFlat)) {
            checkExists(aLpuAreaId, aLpuAddressTextId, aAddress, aNumber, aBuilding, null);
            // дом кв
        } else if (isNotEmpty(aNumber) && isNotEmpty(aFlat)) {
            checkExists(aLpuAreaId, aLpuAddressTextId, aAddress, aNumber, null, null);
            // дом корпус
        } else if (isNotEmpty(aNumber) && isNotEmpty(aBuilding)) {
            checkExists(aLpuAreaId, aLpuAddressTextId, aAddress, aNumber, null, null);
            // дом
        } else if (isNotEmpty(aNumber)) {
            checkExists(aLpuAreaId, aLpuAddressTextId, aAddress, null, null, null);
        }
    }

    public void onUpdate(LpuAreaAddressText aLpuAreaAddressText) {
        onPersist(aLpuAreaAddressText);
    }

    public void refresh() {
        entityManager.createQuery("delete from LpuAreaAddressPoint").executeUpdate();
        Iterator<LpuAreaAddressText> texts = QueryIteratorUtil.iterate(LpuAreaAddressText.class, entityManager.createQuery("from LpuAreaAddressText"));
        while (texts.hasNext()) {
            LpuAreaAddressText text = texts.next();
            onUpdate(text);
        }
    }
}
