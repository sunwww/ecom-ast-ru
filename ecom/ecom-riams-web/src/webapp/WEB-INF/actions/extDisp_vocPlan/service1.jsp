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
		String plan = request.getParameter("id") ;
		List colAgeGroup = (List)request.getAttribute("ageGroup") ;
		int cntAgeGroup = colAgeGroup.size() ;
		StringBuilder ageSql1 = new StringBuilder() ;
		StringBuilder ageSql2 = new StringBuilder() ;
		//out.print("cntAge="+cntAgeGroup) ;
		for (int i=0;i<cntAgeGroup && i<22;i++) {
			WebQueryResult wqr = (WebQueryResult) colAgeGroup.get(i) ;
			ageSql1.append(",max(case when edps.plan_id='").append(plan).append("' and edps.ageGroup_id='").append(wqr.get1()).append("' and (edps.sex_id is null or vs.omcCode='1') then 1 else 0 end)") ; 
			ageSql1.append("||''||max(case when edps.plan_id='").append(plan).append("' and edps.ageGroup_id=").append(wqr.get1()).append(" and (edps.sex_id is null or vs.omcCode='2') then 1 else 0 end) as SA").append(wqr.get1()).append("G") ;
		}
		request.setAttribute("ageGroupSex1Sql", ageSql1.toString()) ;
		if (cntAgeGroup>=22) {request.setAttribute("ageGroupSex2SqlAdd", "left join ExtDispPlanService edps on edps.serviceType_id=veds.id left join VocSex vs on vs.id=edps.sex_id") ;}
		for (int i=22;i<cntAgeGroup && i<44;i++) {
			WebQueryResult wqr = (WebQueryResult) colAgeGroup.get(i) ;
			ageSql2.append(",max(case when edps.plan_id='").append(plan).append("' and edps.ageGroup_id='").append(wqr.get1()).append("' and (edps.sex_id is null or vs.omcCode='1') then 1 else 0 end)") ; 
			ageSql2.append("||''||max(case when edps.plan_id='").append(plan).append("' and edps.ageGroup_id=").append(wqr.get1()).append(" and (edps.sex_id is null or vs.omcCode='2') then 1 else 0 end) as SA").append(wqr.get1()).append("G") ;
		}
		request.setAttribute("ageGroupSex2Sql", ageSql2.toString()) ;
		%>
		
		<ecom:webQuery name="result1" nativeSql="select veds.id,veds.code,veds.name
${ageGroupSex1Sql}
from vocExtDispService veds
left join ExtDispPlanService edps on edps.serviceType_id=veds.id
left join VocSex vs on vs.id=edps.sex_id
group by veds.id,veds.code,veds.name
order by veds.code,veds.name"/>
		<ecom:webQuery name="result2" nativeSql="select veds.id,veds.code,veds.name
${ageGroupSex2Sql}
from vocExtDispService veds
${ageGroupSex2SqlAdd}
group by veds.id,veds.code,veds.name
order by veds.code,veds.name"/>
<table class='servicetbl' border="1">
<tr>
	<th rowspan="2">ИД</th>
	<th rowspan="2">Код</th>
	<th rowspan="2">Наименование услуги</th>
	<th rowspan="2">Пол</th>
	
	<% 
	StringBuilder ageSB = new StringBuilder() ;
	StringBuilder ageNameSB = new StringBuilder() ;
	out.print("<th colspan='"+cntAgeGroup+"'>Возрастные группы</th></tr><tr>") ;
	for (int i=0;i<cntAgeGroup;i++) {
		WebQueryResult wqr = (WebQueryResult) colAgeGroup.get(i) ;
		out.print("<th colspan='1'>");out.print(wqr.get2());out.println("</th>") ;
		ageSB.append(wqr.get1()).append(",") ;
		ageNameSB.append(wqr.get2()).append("###") ;
		
	}	
	String[] ageList = ageSB.toString().split("," ) ;
	String[] ageNameList = ageNameSB.toString().split("###" ) ;
	%>
</tr>
	<% 
	/*for (int i=0;i<cntAgeGroup;i++) {
		out.print("<th class='manTd'>М</th>") ;
		out.print("<th>Ж</th>") ;
	}*/	%>
<%
List result1 = (List) request.getAttribute("result1") ;
List result2 = (List) request.getAttribute("result2") ;
int cntResult = result1.size() ;
for (int i=0;i<cntResult;i++) {
	WebQueryResult wqr1 = (WebQueryResult) result1.get(i) ;
	WebQueryResult wqr2 = (WebQueryResult) result2.get(i) ;
	%>
	<tr class='dataTr'>
	<%
		out.print("<td rowspan='2'>");out.print(wqr1.get1()) ;out.println("</td>");
		out.print("<td rowspan='2'>");
		out.print(wqr1.get2()) ;out.println("</td>");
		out.print("<td rowspan='2' style='text-align: left;'>");
		out.print("<a href='javascript:void(0)' class='adivs' onclick=\"updateExtDispPlanServiceAllAge('");out.print(wqr1.get1());out.print("',this)\">-</a>") ;
		out.print("<a href='javascript:void(0)' class='adivs' onclick=\"updateExtDispPlanServiceAllAge('");out.print(wqr1.get1());out.print("',this)\">+</a>") ;
		out.print(wqr1.get3()) ;out.println("</td>");
		StringBuilder strM = new StringBuilder() ;
		StringBuilder strW = new StringBuilder() ;
		strM.append("<th>М</th>") ;
		strW.append("<th>Ж</th>") ;
		for (int ai=0;ai<cntAgeGroup ;ai++) {
			String obj = "" ;
			if (ai<22) {
				obj = (String)PropertyUtil.getPropertyValue(wqr1, ""+(ai+4)) ;
			} else if (ai<44) {
				obj = (String)PropertyUtil.getPropertyValue(wqr2, ""+(ai-18)) ;
			}
			String age = ageList[ai] ;
			if (obj!=null&&obj.length()==2) {
				strW.append("<td class='womenTd'>") ;
				strM.append("<td class='manTd'>") ;
				//out.println(obj.substring(0,1)) ;
				if (obj.substring(0,1).equals("1")) {
					strM.append("<a title='"+ageNameList[ai]+"' class='adivs service"+wqr1.get1()+"' href='javascript:void(0)' id='service");
					strM.append(wqr1.get1()) ;
					strM.append("_"+age+"_1' onclick=\"updatePlanService('");
					strM.append(wqr1.get1());
					strM.append("','"+age+"','1')\">+</a>");
				} else {
					strM.append("<a title='"+ageNameList[ai]+"' class='adivs service"+wqr1.get1()+"' href='javascript:void(0)' id='service");
					strM.append(wqr1.get1());
					strM.append("_"+age+"_1' onclick=\"updatePlanService('");
					strM.append(wqr1.get1());
					strM.append("','"+age+"','1')\">-</a>");
				}
				//strW.append("</td><td>") ;
				//out.println(obj.substring(1,2)) ;
				if (obj.substring(1,2).equals("1")) {
					strW.append("<a title='"+ageNameList[ai]+"' class='adivs service"+wqr1.get1()+"' href='javascript:void(0)' id='service"+wqr1.get1()+"_"+age+"_2' onclick=\"updatePlanService('"+wqr1.get1()+"','"+age+"','2')\">+</a>");
				} else {
					strW.append("<a title='"+ageNameList[ai]+"' class='adivs service"+wqr1.get1()+"' href='javascript:void(0)' id='service"+wqr1.get1()+"_"+age+"_2' onclick=\"updatePlanService('"+wqr1.get1()+"','"+age+"','2')\">-</a>");
				}
				strW.append("</td>") ;
				strM.append("</td>") ;
			} else {
				strM.append("<td>Д</td>") ;
				strW.append("<td>Д</td>") ;
			}
			
			
		}
		out.print(strW.toString()) ;
		out.print("</tr><tr>");
		out.print(strM.toString()) ;
		%>
	
	</tr>
	<%
}
%>
</table>		
<br><b><u>Легенда:</u></b><br/>
<a href="javascript:void(0)" class="adivs" title="22">+</a> - услуга добавлена в план
<a href="javascript:void(0)" class="adivs" title="22">-</a> - услуга не добавлена в план
	</tiles:put>
	<tiles:put name="title" type="string">
		<ecom:titleTrail mainMenu="Voc" beginForm="extDisp_vocPlanForm" title="Настройка услуг по плану"/>
	</tiles:put>
	<tiles:put name="side" type="string">
		<msh:sideMenu title="Вид плана">
			<msh:sideLink name="МЖ" action="/js-extDisp_service-editPlan.do" params="id"/>
		</msh:sideMenu>
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
		function updateExtDispPlanServiceAllAge(aServiceId,aElement) {
			
			ContractService.updateExtDispPlanServiceAllAge('${param.id}',aElement.innerHTML
					,aServiceId,
			{
			 callback: function(aResult) {
				 var el=document.getElementsByClassName("service"+aServiceId) ;
				 alert("Сохранено");
				 for (var i=0;i<el.length;i++) {
					 el[i].innerHTML=aResult ;
				 }
			  }});			
		}		
		</script>
	</tiles:put>
  <tiles:put name="style" type="string">
  	<style type="text/css">
	a.adivs{
		font-size: xx-large;
		text-decoration: none ;
	}
  	.manTd {
  		background-color: #F1F1F1;
  	}
  	table.servicetbl tr td {
  		cursor: pointer;
  		font-weight: bolder;
  		text-align: center;  
  		
  		padding: 1px;
  		margin: 1px;
  		border: 1px black outset;
  		
  	}
  	table.servicetbl tr.dataTr {
  		background-color: white;
  	}
  	table.servicetbl tr.dataTr:HOVER {
  		background-color: #08C;
  	}
  	</style>
  </tiles:put>	
 </tiles:insert>
