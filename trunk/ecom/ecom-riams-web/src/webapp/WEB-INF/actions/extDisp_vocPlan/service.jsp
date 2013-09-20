<%@page import="java.util.ArrayList"%>
<%@page import="ru.nuzmsh.util.PropertyUtil"%>
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
			ageSql.append(",max(case when edps.ageGroup_id='").append(wqr.get1()).append("' and (edps.sex_id is null or vs.omcCode='1') then 1 else 0 end)") ; 
			ageSql.append("||''||max(case when edps.ageGroup_id=").append(wqr.get1()).append(" and (edps.sex_id is null or vs.omcCode='2') then 1 else 0 end) as SA").append(wqr.get1()).append("G") ;
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
<table class='servicetbl' border="1">
<tr>
	<th rowspan="2">ИД</th>
	<th rowspan="2">Код</th>
	<th rowspan="2">Наименование услуги</th>
	<% 
	StringBuilder ageSB = new StringBuilder() ;
	for (int i=0;i<cntAgeGroup;i++) {
		WebQueryResult wqr = (WebQueryResult) colAgeGroup.get(i) ;
		out.print("<th colspan='2'>");out.print(wqr.get2());out.println("</th>") ;
		ageSB.append(wqr.get1()).append(",") ;
	}	
	String[] ageList = ageSB.toString().split("," ) ;
	%>
</tr>
<tr>
	<% 
	for (int i=0;i<cntAgeGroup;i++) {
		out.print("<th class='manTd'>М</th>") ;
		out.print("<th>Ж</th>") ;
	}	%>
</tr>
<%
List result = (List) request.getAttribute("result") ;
int cntResult = result.size() ;
for (int i=0;i<cntResult;i++) {
	WebQueryResult wqr = (WebQueryResult) result.get(i) ;
	%>
	<tr class='dataTr'>
	<%
		out.print("<td>");out.print(wqr.get1()) ;out.println("</td>");
		out.print("<td>");out.print(wqr.get2()) ;out.println("</td>");
		out.print("<td>");out.print(wqr.get3()) ;out.println("</td>");
		for (int ai=0;ai<cntAgeGroup;ai++) {
			String obj = (String)PropertyUtil.getPropertyValue(wqr, ""+(ai+4)) ;
			String age = ageList[ai] ;
			if (obj!=null&&obj.length()==2) {
				out.print("<td class='manTd'>") ;
				//out.println(obj.substring(0,1)) ;
				if (obj.substring(0,1).equals("1")) {
					out.print("<a class='adivs' href='javascript:void(0)' id='service");out.print(wqr.get1()) ;
					out.print("_"+age+"_1' onclick=\"updatePlanService('");out.print(wqr.get1());out.print("','"+age+"','1')\">У</a>");
				} else {
					out.print("<a class='adivs' href='javascript:void(0)' id='service");out.print(wqr.get1());
					out.print("_"+age+"_1' onclick=\"updatePlanService('");out.print(wqr.get1());out.print("','"+age+"','1')\">Д</a>");
				}
				out.print("</td>") ;
				out.print("<td>") ;
				//out.println(obj.substring(1,2)) ;
				if (obj.substring(1,2).equals("1")) {
					out.print("<a class='adivs' href='javascript:void(0)' id='service"+wqr.get1()+"_"+age+"_2' onclick=\"updatePlanService('"+wqr.get1()+"','"+age+"','2')\">У</a>");
				} else {
					out.print("<a class='adivs' href='javascript:void(0)' id='service"+wqr.get1()+"_"+age+"_2' onclick=\"updatePlanService('"+wqr.get1()+"','"+age+"','2')\">Д</a>");
				}
				out.print("</td>") ;
			} else {
				out.println("<td>Д</td>") ;
				out.println("<td>Д</td>") ;
			}
			
			
			
		}
		%>
	
	</tr>
	<%
}
%>
</table>		
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Voc" beginForm="extDisp_vocPlanForm" />
	</tiles:put>
	<tiles:put name="side" type="string">

        <tags:voc_menu currentAction="extDisp"/>
	</tiles:put>
	<tiles:put name="javascript" type="string">
    <script type="text/javascript" src="./dwr/interface/ContractService.js"></script>
	<script type="text/javascript">
		function updatePlanService(aServiceId,aAgeGroup,aSex) {
			
			ContractService.updateExtDispPlanService('${param.id}',$('service'+aServiceId+"_"+aAgeGroup+"_"+aSex).innerHTML
					,aServiceId,aAgeGroup,aSex,
			{
			 callback: function(aResult) {
				 $('service'+aServiceId+"_"+aAgeGroup+"_"+aSex).innerHTML=aResult ;                
			  }});			
		}
		</script>
	</tiles:put>
  <tiles:put name="style" type="string">
  	<style type="text/css">
	
  	.manTd {
  		background-color: yellow;
  	}
  	table.servicetbl tr td {
  		cursor: pointer;
  		font-weight: bolder;
  		text-align: center;  
  		
  		padding: 5px;
  		margin: 5px;
  		border: 1px black outset;
  		
  	}
  	table.servicetbl tr.dataTr {
  		background-color: white;
  	}
  	table.servicetbl tr.dataTr:HOVER {
  		background-color: #369;
  	}
  	</style>
  </tiles:put>	
 </tiles:insert>
