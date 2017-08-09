<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh"%>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom"%>


<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
	<tiles:put name="body" type="string">
		<msh:form guid="formHello" action="/report_timeexecute.do" defaultField="hello">
			<msh:panel guid="panel">
				<msh:row>
					<msh:row>
						<msh:autoComplete vocName="vocWorkFunctionShort" property="building" label="Отделение" fieldColSpan="4" size="60" />
					</msh:row>
<msh:row>
			<msh:textField property="beginDate" label="c"/>
			<msh:textField property="endDate" label="по"/>
</msh:row>
					<td><input type="submit" value="Найти" /></td>
				</msh:row>
			</msh:panel>
		</msh:form>
		
		
		 <%
		 String SQL = "",SQL2="";
		 String dep = request.getParameter("building");
		 
		 if(dep!=null && !dep.equals("")){
		     SQL+=" and vwf.id="+dep;
		     request.setAttribute("SQL", SQL);
		 }
		 
     String beginDate = request.getParameter("beginDate") ;
	if (beginDate!=null && !beginDate.equals("")) {
		String finishDate = request.getParameter("endDate") ;		
		if (finishDate==null || finishDate.equals("")) {
			finishDate=beginDate ;
		}
		 request.setAttribute("dateStart", beginDate);
		 request.setAttribute("dateFinish", finishDate) ;
%>
		
		<ecom:webQuery name = "elnList" nativeSql="
select
vwf.name,
p.lastname||' '||p.firstname||' '||p.middlename as fio,
wcd.calendardate||' '||wct.timefrom as onRec, 
m.datestart||' '||m.timeexecute as fromRec,
CAST(CAST(m.datestart+m.timeexecute AS timestamp) -CAST(wcd.calendardate + wct.timefrom AS timestamp) as text)
from workcalendartime  wct
left join workcalendarday wcd on wcd.id = wct.workcalendarday_id
left join medcase m on m.id = wct.medcase_id
left join patient p on p.id = m.patient_id
left join workcalendar wc on wc.id = wcd.workcalendar_id
left join workfunction wf on wf.id = wc.workfunction_id
left join vocworkfunction vwf on vwf.id = wf.workfunction_id
where
p.lastname is not null and
wct.createdateprerecord between to_date('${dateStart}','dd.MM.yyyy') and to_date('${dateFinish}','dd.MM.yyyy')
${SQL}
" />
    <msh:section>
    <msh:sectionContent>
				<msh:table name="elnList" action="" idField="1">
					<msh:tableColumn columnName="Рабочая функция" property="1" />
					<msh:tableColumn columnName="ФИО" property="2" />
					<msh:tableColumn columnName="На какую дату/время записан" property="3" />
					<msh:tableColumn columnName="Когда было исполнено" property="4" />
					<msh:tableColumn columnName="Разница времени" property="5" />
				</msh:table>
				</msh:sectionContent>
			</msh:section>
					 <%} else { %>
			    	<p>Укажите период!</p>
			    	<%}%>
			
	</tiles:put>
</tiles:insert>