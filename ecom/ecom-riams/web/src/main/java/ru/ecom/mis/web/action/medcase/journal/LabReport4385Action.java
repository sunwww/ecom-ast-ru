package ru.ecom.mis.web.action.medcase.journal;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.mis.web.action.medcase.PrescriptionForm;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class LabReport4385Action extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm, HttpServletRequest aRequest, HttpServletResponse aResponse) throws Exception {
    	PrescriptionForm form = (PrescriptionForm) aForm;
        form.validate(aMapping, aRequest) ;
        String dateFrom  = form.getBeginDate();
        if (dateFrom!=null&&!dateFrom.equals("")){
        	String dateTo = form.getEndDate();
        	if (dateTo ==null||dateTo.equals("")) {dateTo = dateFrom;}
        	
	        IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class) ;
	        StringBuilder sql = new StringBuilder();
	    	StringBuilder sqlAdd = new StringBuilder();
	    	String leftJoinSql = "";
	    	StringBuilder leftJoinAdd = new StringBuilder();
	        String department = ""+form.getDepartment();
	        String materialType = form.getPrescriptType()>0L?""+form.getPrescriptType():"";
	        String diagnosis = (form.getDiagnosis()!=null&&form.getDiagnosis()>0L)?""+form.getDiagnosis():"" ;
	    	if (department!=null&&!department.equals("")&&!department.equals("0")) {
	    		sqlAdd.append(" and coalesce(p.department_id, w.lpu_id)="+department);
	    	}
	    	if (materialType!=null&&!materialType.equals("")&&!materialType.equals("0")) {
	    		//sqlAdd.append(" and fip.valuevoc_id="+materialType);
	    	}
	    	if (diagnosis!=null&&!diagnosis.equals("")&&!diagnosis.equals("0")) {
	    		leftJoinSql=" left join diagnosis diag on diag.medcase_id=mc.id" ;
	    	}
	    	StringBuilder table = new StringBuilder();
	    	sqlAdd.append(" and p.planstartdate between to_date('"+dateFrom+"','dd.MM.yyyy') and to_date ('"+dateTo+"','dd.MM.yyyy')");
    		List<Object[]> titleList = service.executeNativeSqlGetObj("select count (distinct p.id) as cntPrescriptions, count(case when mc.dtype='DepartmentMedCase' then mcP.id else mc.id end) as cntMedCase" +
    			" from  prescription p" +
    			" left join workfunction wf on wf.id=p.prescriptspecial_id" +
    			" left join worker w on w.id=wf.worker_id" +
    			" left join medcase mc on mc.id=p.medcase_id" +
    			" left join medcase mcP on mcP.id=mc.parent_id" +
	    		" left join diary d on d.medcase_id=p.medcase_id" +
	    		" left join forminputprotocol fip on fip.docprotocol_id=d.id" +
		    	" left join parameter par on par.id=fip.parameter_id" +
	    		leftJoinSql+
		    	" where p.medservice_id=4385 and par.valuedomain_id=79" +sqlAdd.toString());
    	if (titleList.size()>0){
    		table.append("<table>");
    		table.append("<tr><td>Период:</td><td> "+dateFrom+" - "+dateTo+"</td></tr>");
    		table.append("<tr><td>Отделение:</td><td>"+aRequest.getParameter("departmentName")+"</td><td>Всего больных</td><td>"+titleList.get(0)[1]+"</td></tr>");
    		table.append("<tr><td>Количество анализов:</td><td>"+titleList.get(0)[0]+"</td></tr>");
    		table.append("<tr><td>Диагноз:</td><td>"+aRequest.getParameter("diagnosisName")+"</td><td>Биологический материал:</td><td>"+aRequest.getParameter("prescriptTypeName")+"</td></tr>");
    		
    		table.append("</table>");
    	}
    	    
		    List<Object[]> antiBioList = service.executeNativeSqlGetObj("select uv.id, uv.name, count(p.id) " +
		    	" from  prescription p" +
		    	" left join workfunction wf on wf.id=p.prescriptspecial_id" +
    			" left join worker w on w.id=wf.worker_id" +
	    		" left join diary d on d.medcase_id=p.medcase_id" +
	    		" left join forminputprotocol fip on fip.docprotocol_id=d.id" +
		    	" left join parameter par on par.id=fip.parameter_id" +
		    	" left join uservalue uv on uv.id=fip.valuevoc_id" +
		    	" where p.medservice_id=4385 and par.valuedomain_id=79 and uv.id is not null " +sqlAdd.toString() +
		    	" group by uv.id, uv.name order by uv.name");
		    		
		    List<Object[]> RISList = service.executeNativeSqlGetObj("select id, name from uservalue where domain_id= '60' ");
		    StringBuilder RISTD = new StringBuilder();
		    if (antiBioList.size()>0) {
		    	
		    	StringBuilder selectAdd = new StringBuilder();
		    	
		    	StringBuilder counts = new StringBuilder();
		    	int k=3;
		    	table.append("<table border='1' id='report4385ResultTable' class='tabview sel tableArrow'><tr><td rowspan='3'>Название препарата</td>");
		    	boolean isFirst = true;
		    	for (Object[] o: antiBioList) { //Формируем строку для основного запроса
		    		table.append("<td colspan='3'>"+o[1]+"</td>");
		    		counts.append("<td colspan='3' align='center'><b>"+o[2]+"</b></td>");		    	
		    		for (Object[] oo: RISList) {
		    			if (isFirst) {
		    				RISTD.append("<td>"+oo[1].toString().substring(0,1)+"</td>");
		    			}
		    			selectAdd.append(", count(case when (select fip1.valuevoc_id from forminputprotocol fip1 left join parameter par1 on par1.id=fip1.parameter_id where fip1.docprotocol_id=d.id and par1.valuedomain_id=79)='"+o[0]+"' and fip.valuevoc_id='"+oo[0]+"' then p.id else null end) as cnt_"+k);
		    			//tdNames.append(" <msh:tableColumn columnName='"+o[1]+" "+(oo[1].toString().substring(0, 1))+"' property='"+k+"'/>");
		    			k++;
		    		}
		    		isFirst = false;
		    	}
		    	table.append("</tr>");
		    	table.append("<tr>");
		    	table.append(counts);
		    	table.append("</tr><tr>");
		    	//Формируем шапку для таблицы
		    	for (int i=0;i<antiBioList.size();i++) {
		    		table.append(RISTD);
		    	}
		    	table.append("</tr>");
		    	//А вот и пришло время основного запроса
		    	sql.append("select  par.shortname as f2").append(selectAdd).append(" from prescription p"+
		    			" left join workfunction wf on wf.id=p.prescriptspecial_id" +
		    			" left join worker w on w.id=wf.worker_id" +
		    			" left join diary d on d.medcase_id=p.medcase_id"+
		    			" left join forminputprotocol fip on fip.docprotocol_id=d.id"+
		    			" left join parameter par on par.id = fip.parameter_id ")
		    		.append(leftJoinAdd)
		    		.append(" where p.medservice_id=4385 and par.valuedomain_id=60 ").append(sqlAdd)
		    		.append("group by par.id, par.shortname order by par.shortname");
		    	List<Object[]> resultList = service.executeNativeSqlGetObj(sql.toString());
		    	if (resultList.size()>0){
		    		for (Object[] res: resultList) {
		    			table.append("<tr>");
		    			for (Object td: res) {
		    				
		    				table.append("<td>").append(""+td).append("</td>");
		    			}
		    			table.append("</tr>");
		    		}
		    
		    		table.append("</tr></table>");
		    		//table.append("<tr>");
		    aRequest.setAttribute("tableList", table.toString());	
		    } else {
		    	aRequest.setAttribute("tableList", "Данных не найдено");	
		    }
		    }
		 }
		    
        
        return aMapping.findForward("success");
    }
}
