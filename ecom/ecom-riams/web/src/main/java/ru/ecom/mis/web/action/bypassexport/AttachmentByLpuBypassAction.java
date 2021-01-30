package ru.ecom.mis.web.action.bypassexport;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.web.util.ActionUtil;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AttachmentByLpuBypassAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
        AttachmentByLpuBypassForm form = (AttachmentByLpuBypassForm) aForm;

        if (form != null) {
            String dateFrom = form.getPeriod();
            String dateTo = form.getPeriodTo();
            if (dateTo == null || dateTo.equals("")) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                dateTo = sdf.format(new Date());
            }

            String typeView = ActionUtil.updateParameter("PatientAttachment", "typeView", "1", aRequest);
            String typeAge = ActionUtil.updateParameter("PatientAttachment", "typeAge", "3", aRequest);
            String typeAttachment = ActionUtil.updateParameter("PatientAttachment", "typeAttachment", "3", aRequest);
            String typeDefect = ActionUtil.updateParameter("PatientAttachment", "typeDefect", "3", aRequest);
            String typeCompany = ActionUtil.updateParameter("PatientAttachment", "typeCompany", "3", aRequest);
            String typeAreaCheck = ActionUtil.updateParameter("PatientAttachment", "typeAreaCheck", "3", aRequest);
            String typeSex = ActionUtil.updateParameter("PatientAttachment", "typeSex", "3", aRequest);
            String age = null;
            StringBuilder sqlAdd = new StringBuilder();
            if (dateFrom != null && !dateFrom.equals("")) {
                if (!dateTo.equals("")) {
                    sqlAdd.append(" and lp.datefrom between to_date('").append(dateFrom).append("','dd.MM.yyyy') and to_date('").append(dateTo).append("','dd.MM.yyyy')");
                } else {
                    sqlAdd.append(" and lp.datefrom >= to_date('").append(dateFrom).append("','dd.MM.yyyy')");
                }
            }
            if (typeAge != null) {
                if (typeAge.equals("1")) {
                    age = "<18";
                } else if (typeAge.equals("2")) {
                    age = ">=18";
                }
                if (typeAge.equals("1") || typeAge.equals("2")) {
                    sqlAdd.append(" and cast(to_char(to_date('").append(dateTo).append("','dd.mm.yyyy'),'yyyy') as int) -cast(to_char(p.birthday,'yyyy') as int) +(case when (cast(to_char(to_date('").append(dateTo).append("','dd.mm.yyyy'), 'mm') as int) -cast(to_char(p.birthday, 'mm') as int) +(case when (cast(to_char(to_date('").append(dateTo).append("','dd.mm.yyyy'),'dd') as int) - cast(to_char(p.birthday,'dd') as int)<0) then -1 else 0 end) <0) then -1 else 0 end) ").append(age);
                }
            }
            if ("1".equals(typeSex) || "2".equals(typeSex)) {
                sqlAdd.append(" and vs.omccode='").append(typeSex).append("'");

            }
            if ("2".equals(typeView)) {
                sqlAdd.append(" and p.address_addressid is null ");
            }
            if ("1".equals(typeAttachment)) {
                sqlAdd.append(" and (vat.code='1') ");
            } else if ("2".equals(typeAttachment)) {
                sqlAdd.append(" and vat.code='2'");
            } else if ("4".equals(typeAttachment)) {
                sqlAdd.append(" and lp.attachedType_id is null");
            }
            if ("1".equals(typeAreaCheck)) {
                sqlAdd.append(" and lp.area_id is not null ");
            } else if ("2".equals(typeAreaCheck)) {
                sqlAdd.append(" and (lp.area_id is null or lp.lpu_id is null)");
            }
            if ("1".equals(typeDefect)) {
                sqlAdd.append(" and lp.defectText!='' and lp.defectText is not null");
            } else if ("2".equals(typeDefect)) {
                sqlAdd.append(" and (lp.defectText='' or lp.defectText is null)");
            }
            if (form.getCompany() != null && form.getCompany() != 0) {
                sqlAdd.append(" and lp.company_id='").append(form.getCompany()).append("' ");
            }
            if (typeCompany != null && typeCompany.equals("1")) {
                sqlAdd.append(" and lp.company_id is not null ");
            } else if (typeCompany != null && typeCompany.equals("2")) {
                sqlAdd.append(" and lp.company_id is null ");
            }
            if (form.getLpu() != null && form.getLpu() > 0) {
                sqlAdd.append(" and lp.lpu_id = ").append(form.getLpu());
            }
            if (form.getArea() != null && form.getArea() > 0) {
                sqlAdd.append(" and lp.area_id=").append(form.getArea());
            }
            aRequest.setAttribute("sqlAdd", sqlAdd.toString());

        } else {
            throw new Exception("HELLLO");
        }
        return aMapping.findForward(SUCCESS);
    }
}