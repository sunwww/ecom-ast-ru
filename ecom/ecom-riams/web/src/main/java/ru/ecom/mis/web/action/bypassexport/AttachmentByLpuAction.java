package ru.ecom.mis.web.action.bypassexport;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.ejb.services.query.WebQueryResult;
import ru.ecom.mis.ejb.service.addresspoint.IAddressPointService;
import ru.ecom.web.util.ActionUtil;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.util.StringUtil;
import ru.nuzmsh.util.format.DateFormat;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import static ru.nuzmsh.util.EqualsUtil.isOneOf;

public class AttachmentByLpuAction extends BaseAction {
    public ActionForward myExecute(ActionMapping mapping, ActionForm actionForm, HttpServletRequest request, HttpServletResponse response) throws Exception {
        AttachmentByLpuForm form = (AttachmentByLpuForm) actionForm;

        if (form != null) {
            ActionErrors errors = form.validate(mapping, request);

            if (errors.isEmpty()) {
                IAddressPointService service = Injection.find(request).getService(IAddressPointService.class);
                String typeRead = ActionUtil.updateParameter("PatientAttachment", "typeRead", "1", request);
                String typeView = ActionUtil.updateParameter("PatientAttachment", "typeView", "1", request);
                String typeAge = ActionUtil.updateParameter("PatientAttachment", "typeAge", "3", request);
                String typeAttachment = ActionUtil.updateParameter("PatientAttachment", "typeAttachment", "3", request);
                String typeDefect = ActionUtil.updateParameter("PatientAttachment", "typeDefect", "3", request);
                String typeChange = ActionUtil.updateParameter("PatientAttachment", "typeChange", "1", request);
                String typeCompany = ActionUtil.updateParameter("PatientAttachment", "typeCompany", "3", request);
                String typeDivide = ActionUtil.updateParameter("PatientAttachment", "typeDivide", "1", request);
                String typeAreaCheck = ActionUtil.updateParameter("PatientAttachment", "typeAreaCheck", "3", request);
                String returnType = ActionUtil.updateParameter("PatientAttachment", "typeResult", "file", request); //тип архива (file, prik, zip)
                String fileType = ActionUtil.updateParameter("PatientAttachment", "fileType", "csv", request); //тип файла (xml, csv)
                String typeDispPlan = ActionUtil.updateParameter("PatientAttachment", "typeDispPlan", "ATTACHMENT", request); //По какому алгоритму формировать выгрузку (план либо прикрепления)
                Date cur = DateFormat.parseDate(form.getPeriod());
                Calendar cal = Calendar.getInstance();
                Calendar calTo = Calendar.getInstance();
                cal.setTime(cur);
                Date curTo = DateFormat.parseDate(form.getPeriodTo());
                calTo.setTime(curTo);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMM");
                SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat format2 = new SimpleDateFormat("dd.MM.yyyy");
                String prefix = "";
                StringBuilder sqlAdd = new StringBuilder();
                if (isOneOf(typeAge, "1", "2")) {
                    sqlAdd.append(" and cast(to_char(to_date('").append(form.getPeriodTo())
                            .append("','dd.mm.yyyy'),'yyyy') as int) -cast(to_char(p.birthday,'yyyy') as int) +(case when (cast(to_char(to_date('")
                            .append(form.getPeriodTo()).append("','dd.mm.yyyy'), 'mm') as int) -cast(to_char(p.birthday, 'mm') as int) +(case when (cast(to_char(to_date('")
                            .append(form.getPeriodTo()).append("','dd.mm.yyyy'),'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end) <0) then -1 else 0 end) ")
                            .append(typeAge.equals("1") ? "<=18" : ">=18");
                }
                if ("2".equals(typeView)) {
                    prefix = "_no_addresss";
                    sqlAdd.append(" and p.address_addressid is null ");
                }
                if ("1".equals(typeAttachment)) {
                    sqlAdd.append(" and (vat.code='1' or (vat.id is null and lp.id is null)) ");
                } else if ("2".equals(typeAttachment)) {
                    sqlAdd.append(" and vat.code='2'");
                }
                if (typeRead == null || !typeRead.equals("3")) {
                    if ("3".equals(typeChange)) {
                        sqlAdd.append(" and (lp.dateFrom between to_date('")
                                .append(form.getPeriod()).append("','dd.mm.yyyy') and to_date('").append(form.getPeriodTo()).append("','dd.mm.yyyy') or lp.dateTo between to_date('")
                                .append(form.getPeriod()).append("','dd.mm.yyyy') and to_date('").append(form.getPeriodTo()).append("','dd.mm.yyyy'))");
                    } else if ("2".equals(typeChange)) {
                        sqlAdd.append(" and (lp.dateFrom<=to_date('").append(form.getPeriodTo()).append("','dd.mm.yyyy')" +
                                " or (lp.dateTo is not null and lp.dateTo<=to_date('").append(form.getPeriodTo()).append("','dd.mm.yyyy')))");
                    }
                }
                if (StringUtil.isNotEmpty(form.getChangedDateFrom())) {
                    sqlAdd.append(" and (coalesce(lp.editDate,lp.createDate) >= to_date('").append(form.getChangedDateFrom()).append("','dd.mm.yyyy')  )");

                }
                if ("1".equals(typeAreaCheck)) {
                    sqlAdd.append(" and lp.area_id is not null ");
                } else if ("2".equals(typeAreaCheck)) {
                    sqlAdd.append(" and lp.area_id is null ");
                }
                if ("1".equals(typeDefect)) {
                    sqlAdd.append(" and lp.defectText!='' and lp.defectText is not null");
                } else if ("2".equals(typeDefect)) {
                    sqlAdd.append(" and (lp.defectText='' or lp.defectText is null)");
                }
                if (form.getCompany() != null && form.getCompany() != 0) {
                    sqlAdd.append(" and lp.company_id='").append(form.getCompany()).append("' ");
                }
                if ("1".equals(typeCompany)) {
                    sqlAdd.append(" and lp.company_id is not null ");
                } else if ("2".equals(typeCompany)) {
                    sqlAdd.append(" and lp.company_id is null ");
                }

                if (isOneOf(typeRead, "1", "3")) {
                    WebQueryResult fs;
                    boolean needDivide = !"2".equals(typeDivide);
                    if (typeRead.equals("1")) { //форируем файл по прик населению
                        fs = service.exportAll(prefix, sqlAdd.toString()
                                , form.getLpu(), form.getArea(), format2.format(cal.getTime()), format2.format(calTo.getTime()), format1.format(calTo.getTime()), form.getNumberReestr()
                                , form.getNumberPackage(), form.getCompany(), needDivide, "1",fileType, returnType);
                    } else { //план ДД
                        fs = service.exportExtDispPlanAll(null, prefix, sqlAdd.toString()
                                , form.getLpu(), form.getArea(), format2.format(cal.getTime()), format2.format(calTo.getTime()), format3.format(calTo.getTime()), form.getNumberReestr()
                                , form.getNumberPackage(), form.getCompany(), typeDispPlan);
                    }
                    if (fs != null) {
                        Collection<WebQueryResult> def = (Collection<WebQueryResult>) fs.get2();
                        String[] files = fs.get1().toString().split("#");
                        StringBuilder sb = new StringBuilder();
                        for (String file : files) {
                            sb.append("<a href='../rtf/").append(file).append("'>").append(file).append("</a> ");
                        }
                        form.setFilename(sb.toString());
                        if (def != null && !def.isEmpty()) {
                            request.setAttribute("defectWQR", def);
                        }
                    } else {
                        form.setFilename("---");
                    }
                } else {
                    if (form.getLpu() != null && form.getLpu() > 0) {
                        sqlAdd.append(" and lp.lpu_id = ").append(form.getLpu());
                        if (form.getArea() != null && form.getArea() > 0) {
                            sqlAdd.append(" and lp.area_id=").append(form.getArea());
                        }
                    }
                    request.setAttribute("sqlAdd", sqlAdd.toString());
                }
            }
        }
        return mapping.findForward(SUCCESS);
    }
}