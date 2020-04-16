package ru.ecom.mis.web.action.diet;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import ru.ecom.ejb.services.query.IWebQueryService;
import ru.ecom.web.util.Injection;
import ru.nuzmsh.web.struts.BaseAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DietJournalAction extends BaseAction {
    public ActionForward myExecute(ActionMapping aMapping, ActionForm aForm,
	    HttpServletRequest aRequest, HttpServletResponse aResponse)
	    throws Exception {
	DietJournalForm form = (DietJournalForm) aForm;
	form.validate(aMapping, aRequest);
	String dateFrom = form.getBeginDate();
		String typeReestr = aRequest.getParameter("typeReestr");
		if ("1".equals(typeReestr) && dateFrom != null && !dateFrom.equals("")) {
			Long diet = form.getDiet();
			IWebQueryService service = Injection.find(aRequest).getService(IWebQueryService.class);
			StringBuilder sql = new StringBuilder();
			StringBuilder sqlAdd = new StringBuilder();
			StringBuilder leftJoinAdd = new StringBuilder();
			Long department = form.getDepartment();

			String typeView = aRequest.getParameter("typeView");
			if ("2".equals(typeView)) {
				sqlAdd.append(" and p.planStartDate = to_date('").append(dateFrom).append("','dd.MM.yyyy') and p.planStartTime>=cast ('12:00:00' as time) ");
			}
			if (department!=null && department>0L) {
				sqlAdd.append(" and slo.department_id=").append(department);
			}
			if (diet != null && diet > 0L) {
				sqlAdd.append(" and p.diet_id=").append(diet);
			}

			StringBuilder table = new StringBuilder();

			String tmpSql = "select d.id, d.name, count(d.id) as cntByDiet"
				+ " from prescription p "
				+ " left join diet d on d.id=p.diet_id"
				+ " left join prescriptionList pl on pl.id=p.prescriptionList_id"
				+ " left join medcase slo  on slo.id=pl.medcase_id"
				+ " where to_date('"+ dateFrom+ "','dd.MM.yyyy') between p.planStartDate and coalesce(p.planEndDate, current_date) "
				+ "and (p.planEndTime is null or p.planEndTime>=current_time) and p.dtype='DietPrescription' and p.diet_id is not null"
				+ sqlAdd + " group by d.id, d.name order by d.name";
			List<Object[]> dietList = service.executeNativeSqlGetObj(tmpSql);

			if (!dietList.isEmpty()) {

			StringBuilder selectAdd = new StringBuilder();
			StringBuilder tableBottom = new StringBuilder();

			int k = 3;
			table.append("<table class='tabview cell' border='1'><tr><th>ИД</th><th>Отделение/Название диеты</th>");
			tableBottom.append("<tr><td colspan='2'><b>ВСЕГО:</b></td>");
			int sum = 0;
			StringBuilder sb = new StringBuilder();
			for (Object[] o : dietList) {
				if (sb.length() > 0) {
					sb.append("#");
				}
				sb.append("dietId=").append(o[0]);
				table.append("<th colspan='1'>").append(o[1]).append("</th>");
				tableBottom.append("<td>").append(o[2]).append("</td>");
				// Формируем строку для основного запроса
				selectAdd.append(", count(case when p.diet_id='").append(o[0]).append("' then p.id else null end) as cnt_").append(k);
				k++;
				sum += Integer.parseInt("" + o[2]);
			}
			String[] dietsLink = sb.toString().split("#");
			table.append("<th colspan='1'>ВСЕГО:</th>");
			tableBottom.append("<td><b>").append(sum).append("</b></td>");
			tableBottom.append("</tr>");
			table.append("</tr>");
			// А вот и пришло время основного запроса
			sql.append("select  dep.id as f1, dep.name as f2")
					.append(selectAdd)
					.append(", count(p.id) as cntAllByDepartment")
					.append(" from prescription p")
					.append(" left join prescriptionList pl on pl.id=p.prescriptionList_id")
					.append(" left join medcase slo  on slo.id=pl.medcase_id")
					.append(" left join mislpu dep on dep.id=slo.department_id ")
					.append(leftJoinAdd).append(" where p.dtype='DietPrescription' and to_date('").append(dateFrom).append("','dd.MM.yyyy')")
				.append(" between p.planStartDate and coalesce(p.planEndDate, current_date) and (p.planEndTime is null or p.planEndTime>=current_time) ")
				.append(sqlAdd)
				.append("group by dep.id, dep.name order by dep.name");
			List<Object[]> resultList = service.executeNativeSqlGetObj(sql.toString());
			if (!resultList.isEmpty()) {
				for (Object[] res : resultList) {
					table.append("<tr>");
					int i = 0, fl = 0;

					for (Object td : res) {
						table.append("<td");
						if (i > 1) {
							table.append(" onclick=\"goToPage('diet_dietJournal.do?start=").append(dateFrom).append("&typeReestr=1").append("&department=").append(res[0])
								.append("&")
								.append(fl < dietsLink.length ? dietsLink[fl]:"").append("')\">");
							fl++;
						} else {
							table.append(">");
						}
						i++;
						table.append(td).append("</td>");
					}
					table.append("</tr>");
				}
				table.append(tableBottom);
				table.append("</table>");
				aRequest.setAttribute("tableList", table.toString());
			} else {
				aRequest.setAttribute("tableList", "Данных не найдено");
			}
	    }
	}
	return aMapping.findForward(SUCCESS);
    }
}
