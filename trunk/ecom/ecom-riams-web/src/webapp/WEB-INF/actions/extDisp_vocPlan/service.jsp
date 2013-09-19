<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Collection"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
	<tiles:put name="body" type="string">
		
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Voc" beginForm="extDisp_vocPlanForm" />
	</tiles:put>
	<tiles:put name="side" type="string">
		<ecom:webQuery name="ageGroup"
		nativeSql="select vag.id,vag.name from VocExtDispAgeGroup vag
		left join ExtDispPlan edp on edp.dispType_id=vag.dispType_id
		where edp.id=${param.id}
		"
		/>
		<%
		List colAgeGroup = (List)request.getAttribute("ageGroup") ;
		int cntAgeGroup = colAgeGroup.size() ;
		StringBuilder ageSql = new StringBuilder() ;
		for (int i=0;i<cntAgeGroup;i++) {
			WebQueryResult wqr = (WebQueryResult) colAgeGroup.get(i) ;
			ageSql.append(",max(case when edps.ageGroup_id='").append(wqr.get1()).append("' and edps.sex_id is null or vs.omcCode='1' then 1 else 0 end)") ; 
			ageSql.append("||''||max(case when edps.ageGroup_id=").append(wqr.get1()).append(" and edps.sex_id is null or vs.omcCode='2' then 1 else 0 end) as SA").append(wqr.get1()).append("G") ;
		}
		request.setAttribute("ageGroupSexSql", ageSql.toString()) ;
		%>
		
		<ecom:webQuery name="result" nativeSql="select veds.id,veds.code,veds.name
${ageGroupSexSql}
from vocExtDispService veds
left join ExtDispPlanService edps on edps.serviceType_id=veds.id
left join VocSex vs on vs.id=edps.sex_id
where (edps.plan_id=1 or edps.id is null)
group by veds.id,veds.code,veds.name"/>
<table>
<tr>
	<th>ИД</th>
	<th>Код</th>
	<th>Наименование услуги</th>
	<% 
	for (int i=0;i<cntAgeGroup;i++) {
		WebQueryResult wqr = (WebQueryResult) colAgeGroup.get(i) ;
		out.print("<th>");out.print(wqr.get2());out.println("</th>") ;
	}	%>
</tr>
<%
List result = (List) request.getAttribute("result") ;
int cntResult = result.size() ;
for (int i=0;i<cntResult;i++) {
	WebQueryResult wqr = (WebQueryResult) result.get(i) ;
	%>
	<tr>
	<%
		out.print("<td>");out.print(wqr.get1()) ;out.println("</td>");
		out.print("<td>");out.print(wqr.get2()) ;out.println("</td>");
		out.print("<td>");out.print(wqr.get3()) ;out.println("</td>");
		for (int ai=0;ai<cntAgeGroup;ai++) {
			out.print("<th>");out.print(wqr.get2());out.println("</th>") ;
		}
		%>
	%>
	</tr>
	<%
}
%>
</table>
        <tags:voc_menu currentAction="extDisp"/>
	</tiles:put>
	
</tiles:insert>
