<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@page import="ru.ecom.web.login.LoginInfo"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@page import="java.util.List"%>
<%@page import="ru.ecom.ejb.services.query.WebQueryResult"%>
<%@page import="ru.ecom.ejb.services.query.IWebQueryService"%>
<%@page import="java.util.Collection"%>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >
	<tiles:put name="style" type="string">
		<style>
			h2 {display:none;}
			@media print {
			h2 {display:inline;}
			img,div#copyright,h1,ul#ideModeMainMenu, div#ideModeMainMenuClose {display:none;}
			input#beginDate{display:inline;}
			div.x-box-mc{display:none;}
			div#header{display:none;}
			}
		</style>
	</tiles:put>
  <tiles:put name="title" type="string">
    <msh:title mainMenu="Diet" guid="65127a6f-d6d3-4b8e-b436-c6aeeaea35ae" title="Журнал диет" />
  </tiles:put>
  
  
<tiles:put name="body" type="string">
<h2> Сводные  сведения по  наличию  больных, состоящих  на  питании <br></h2>
<h2>на <%=request.getParameter("beginDate")%></h2>
  
 <%
	String typeState = ActionUtil.updateParameter("PrescriptJournal", "typeState", "3", request);
	String typeGroup = ActionUtil.updateParameter("PrescriptJournal", "typeGroup", "1", request);
	String typeView = ActionUtil.updateParameter("PrescriptJournal", "typeView", "1", request);
	String typeReestr = request.getParameter("typeReestr");
	
	String start = request.getParameter("start");
	request.setAttribute("start", start);
	String depId = request.getParameter("depId");
	request.setAttribute("depId", depId);
	
	String dietId = request.getParameter("dietId");
	request.setAttribute("dietId", dietId);
	
	if (request.getParameter("short") == null || request.getParameter("short").equals("")) { %>
	
	<msh:form action="/diet_dietJournal.do" defaultField="beginDate" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
		<msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
			<msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
				<msh:separator label="Параметры поиска" colSpan="7" />
			</msh:row>
			<msh:row>
				<td class="label" title="Отображать (typeView)" colspan="1">
				<label for="typeViewName" id="typeViewLabel">Отображать:</label></td>
				<td onclick="this.childNodes[1].checked='checked';">
				<input type="radio" name="typeView" value="1"> Всех пациентов</td>
				<td onclick="this.childNodes[1].checked='checked';">
				<input type="radio" name="typeView" value="2"> Поступивших после12 часов</td>
	
			</msh:row>
			<msh:row>
				<msh:autoComplete property="department" fieldColSpan="4" horizontalFill="true" label="Отделение" vocName="lpu" size="50" />
			</msh:row>
			<msh:row>
				<msh:autoComplete property="diet" fieldColSpan="4" horizontalFill="true" label="Диета" vocName="Diet" size="50" />
			</msh:row>
			<msh:row>
				<msh:textField property="beginDate" label="Период" />
				<td><input type="submit" value="Найти" /></td>
			</msh:row>
		</msh:panel>
	</msh:form>
		<%
		}
  if (typeReestr != null && (typeReestr.equals("1"))) {
      String SQL="";
      if(dietId != null){
	  SQL = " and diet_id="+dietId;
      }
      request.setAttribute("SQL", SQL);
		%>
	<msh:section>
		<msh:sectionContent>
			<ecom:webQuery maxResult="1500" name="journal_ticket" nativeSql="SELECT  pat.id ,pat.lastname||' '||pat.firstname||' '||pat.middlename as fio, dep.name, d.name  as diet,p.planstartdate,p.planEndDate
			from prescription p
			left join prescriptionList pl on pl.id=p.prescriptionList_id 
			left join diet d on d.id=p.diet_id
			left join medcase slo  on slo.id=pl.medcase_id 
			left join mislpu dep on dep.id=slo.department_id 
			left join patient pat on pat.id = slo.patient_id
			where  
			p.dtype='DietPrescription' 
			and to_date('${start}','dd.MM.yyyy') 
			between p.planStartDate and coalesce(p.planEndDate, current_date) and dep.id=${depId} ${SQL}
			and (p.planEndTime is null or p.planEndTime>=current_time)
			order by d.name" /> 
			<msh:table name="journal_ticket" action="entityView-mis_patient.do" idField="1" noDataMessage="Нет данных">
				<msh:tableColumn columnName="#" property="sn" />
				<msh:tableColumn columnName="ФИО пациента" property="2" />            
				<msh:tableColumn columnName="Отделение" property="3"/>
				<msh:tableColumn columnName="Диета" property="4"/>
			</msh:table>
		</msh:sectionContent>
	</msh:section>
    <%
        }
		String dateFrom = request.getParameter("beginDate");
  		if (dateFrom != null && !dateFrom.equals("")) { out.print(request.getAttribute("tableList"));}
    %>
    <script type="text/javascript" src="./dwr/interface/PrescriptionService.js"></script>
    <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js"></script>
    <script type='text/javascript'>
    
    function checkFieldUpdate(aField,aValue,aDefault) {
    	eval('var chk =  document.forms[0].'+aField) ;
    	var max = chk.length ;
    	if ((+aValue)>max) {
    		chk[+aDefault-1].checked='checked' ;
    	} else {
    		chk[+aValue-1].checked='checked' ;
    	}
    }
	checkFieldUpdate('typeView','${typeView}',1);
  </script>
  <br><h2>Составила: медицинская  сестра  диетическая___________________ Шеф-повар______________ </h2>
  </tiles:put>
</tiles:insert>

