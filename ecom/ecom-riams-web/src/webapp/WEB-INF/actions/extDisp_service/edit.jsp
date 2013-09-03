<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="java.util.Collection"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">
	<tiles:put name="body" type="string">
	<ecom:webQuery name="getService" nativeSql="
	select eds.id as edsid
, veds.id as vedsid,veds.name as vedsname 
, to_char(eds.serviceDate,'dd.mm.yyyy') as servicedate
,case when eds.isPathology='1' then 'checked' else '' end
from extdispservice eds
left join VocExtDispService veds on veds.id=eds.servicetype_id
where card_id='${param.id}'
	"/>
	<%Collection<WebQueryResult> list1 = (Collection<WebQueryResult>)request.getAttribute("getService") ;
	if (list1!=null && !list1.isEmpty()) {
		
	} else {
		%>
<ecom:webQuery name="servicePlan"
nativeSql="
select * from ExtDispCard edc
left join Patient pat on pat.id=edc.patient_id
left join ExtDispPlan edp on edp.id=edc.dispType_id
left join ExtDispPlanService edps on edps.plan_id=edp.id
where edc.id=2 and (edps.sex_id=pat.sex_id or edps.sex_id is null)
and edps.serviceType_id is not null
"
/>		
		<%
	}
		
	%>
		<form action="saveExtDisp_Service.do">
			
		</form>

	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Patient" beginForm="extDisp_cardForm" title="Услуги" />
	</tiles:put>

</tiles:insert>
