<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <%
  	String typeOrder =ActionUtil.updateParameter("ExtDispAction","typeOrder","1", request) ;
  	String typeDate =ActionUtil.updateParameter("ExtDispAction","typeDate","1", request) ;
    	 %>
    <msh:form guid="formHello" action="/mis_archive_journal.do" defaultField="hello">
      
      <msh:panel guid="panel">
        <msh:row guid="b5f456eb-b971-441e-9a90-5194a8019c07">
          <msh:autoComplete vocName="vocLpuHospOtdAll" property="department" size="50" fieldColSpan="3" label="Отделение выписки" guid="5dda7fb6-b2c3-44be-9d10-d8d428637b32" />
        </msh:row>
        <msh:row>
			<msh:textField property="dateBegin" label="c"/>
			<msh:textField property="dateEnd" label="по"/>
		</msh:row>
		<msh:row>
	        <td class="label" title="Поиск по дате (typeDate)" colspan="1"><label for="typeDateName" id="typeDateLabel">Поиск по:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeDate" value="1"> дате выписки
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeDate" value="2"> дате передачи истории в архив
	        </td>
	        
        </msh:row>
        <msh:row>
	        <td class="label" title="Сортировать (typeOrder)" colspan="1"><label for="typeOrderName" id="typeOrderLabel">Сортировать по:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeOrder" value="1"> ФИО пациента
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeOrder" value="2"> Отделению
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeOrder" value="3"> Дате выписки
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';">
	        	<input type="radio" name="typeOrder" value="4"> Дате передачи истории в архив
	        </td>
	        
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" labelSave="Поиск" labelSaving="Поиск..." colSpan="4" />
      </msh:panel>
    </msh:form>
    <%
    String beginDate = request.getParameter("dateBegin") ;
	if (beginDate!=null && !beginDate.equals("")) {
		String finishDate = request.getParameter("dateEnd") ;		
		if (finishDate==null || finishDate.equals("")) {
			finishDate=beginDate ;
		}
		request.setAttribute("dateStart", beginDate) ;
		request.setAttribute("dateFinish", finishDate) ;
		
    String dep = request.getParameter("department");
    String orderBySql  = "pat.lastname, pat.firstname, pat.middlename" ; 
    String depSql = "";
    if (dep!=null&&!dep.equals("")&&!dep.equals("0")) {
    	depSql+=" and slo.department_id='"+dep+"'";
    }
    if (typeDate!=null&&typeDate.equals("1")) {
    	request.setAttribute("typeDateSql", "sls.datefinish");
    } else {
    	request.setAttribute("typeDateSql", "ac.createDate");
    }
    if (typeOrder.equals("2")) {
    	orderBySql = "dep.name, pat.lastname, pat.firstname, pat.middlename";
    }  else if (typeOrder.equals("3")) {
    	orderBySql = "sls.datefinish, pat.lastname, pat.firstname, pat.middlename";
    } else if (typeOrder.equals("4")) {
    	orderBySql = "ac.createDate, pat.lastname, pat.firstname, pat.middlename";	
    }
    request.setAttribute("depSql",depSql);
    request.setAttribute("orderBySql",orderBySql);
    %>
    
    <ecom:webQuery name = "archivesList" nativeSql="
    select ss.id as ssid, sls.id as slsid, ss.code as code, pat.patientinfo as pat
    , to_char (sls.dateStart,'dd.MM.yyyy') as dateStart, to_char (sls.dateFinish,'dd.MM.yyyy') as dateFinish
    , to_char(ac.createDate,'dd.MM.yyyy') as transfedDate
    ,dep.name as department
from archiveCase ac
left join statisticstub ss on ss.id=ac.statcard 
left join medcase sls on sls.id=ss.medcase_id
left join medcase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase' and slo.datefinish is not null
left join mislpu dep on dep.id=slo.department_id
left join patient pat on pat.id=sls.patient_id 

where  ${typeDateSql} between to_date('${dateStart}','dd.MM.yyyy') and to_date('${dateFinish}','dd.MM.yyyy') 
${depSql}
order by ${orderBySql}
    " />
    <msh:section>
    <msh:sectionContent>
				<msh:table name="archivesList" action="javascript:void()" idField="1">
					<msh:tableColumn columnName="Номер ИБ" property="3" />
					<msh:tableColumn columnName="ФИО пациента" property="4" />
					<msh:tableColumn columnName="Отделение" property="8" />
					<msh:tableColumn columnName="Дата начала госпитализации" property="5" />
					<msh:tableColumn columnName="Дата окончания госпитализации" property="6" />
					<msh:tableColumn columnName="Дата передачи в архив" property="7" />
				</msh:table>
				</msh:sectionContent>
			</msh:section>
    <%} %>
  </tiles:put>
    <tiles:put name='side' type='string'> 
  <msh:sideLink key="ALT+1" params="" roles="/Policy/Mis/ArchiveCase/Create" action="/move_to_archive" name="Передача карт в архив" guid="31e83931-c7ab-4040-8f46-3306ac07cb26" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
  	<script type="text/javascript">
  	checkFieldUpdate("typeOrder",'${typeOrder}',1);
    function checkFieldUpdate(aField,aValue,aDefault) {
    	
    	eval('var chk =  document.forms[0].'+aField) ;
    	var max = chk.length ;
    	if ((+aValue)>max) {
    		chk[+aDefault-1].checked='checked' ;
    	} else {
    		chk[+aValue-1].checked='checked' ;
    	}
    }
  	</script>
  
  </tiles:put>
</tiles:insert>

