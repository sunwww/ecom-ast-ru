<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/main${param.short}Layout.jsp" flush="true">

    <tiles:put name='title' type='string'>
        <msh:title mainMenu="Disability">Открытые направления на ВК</msh:title>
    </tiles:put>

    <tiles:put name='side' type='string'>
        <tags:dis_menu currentAction="journalOpenKER"/>
    </tiles:put>

    <tiles:put name='body' type='string'>
    <%
	String typeJournal =ActionUtil.updateParameter("Expert_Ker_Open","typeJournal","2", request) ;
	String typeView = ActionUtil.updateParameter("Expert_Ker","typeView","3", request) ;
	String typeLpu = ActionUtil.updateParameter("Expert_Ker","typeLpu","3", request) ;
   %>
    <msh:form action="expert_kersopen.do" defaultField="1" >
    <msh:panel>
          <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
      <msh:row>
        <td class="label" title="Поиск по параметрам (typeJournal)" colspan="1"><label for="typeJournalName" id="typeJournalLabel">Группировать:</label></td>
        <td onclick="this.childNodes[1].checked='checked';document.location.href='expert_kersopen.do?typeJournal=1'"  colspan="2">
        	<input type="radio" name="typeJournal" value="1">  реестр
        </td>
        <td onclick="this.childNodes[1].checked='checked';document.location.href='expert_kersopen.do?typeJournal=2'"  colspan="2">
        	<input type="radio" name="typeJournal" value="2" >  по датам
        </td>
        <td onclick="this.childNodes[1].checked='checked';document.location.href='expert_kersopen.do?typeJournal=3'"  colspan="2">
        	<input type="radio" name="typeJournal" value="3">  по отделению
        </td>
        <td onclick="this.childNodes[1].checked='checked';document.location.href='expert_kersopen.do?typeJournal=4'"  colspan="2">
        	<input type="radio" name="typeJournal" value="4">  по специалистам
        </td>
	  <td onclick="this.childNodes[1].checked='checked';document.location.href='expert_kersopen.do?typeJournal=5'"  colspan="2">
		  <input type="radio" name="typeJournal" value="5">  по типу ВК
	  </td>
      </msh:row>
      <msh:row>
        <td class="label" title="Тип ЛПУ (typeLpu)" colspan="1"><label for="typeLpuName" id="typeLpuLabel">ЛПУ:</label></td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeLpu" value="1">  стационар
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeLpu" value="2" >  поликлиника
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeLpu" value="3">  все
        </td>
      </msh:row>
      </msh:panel>
      </msh:form>
          <script type='text/javascript'>
    
    checkFieldUpdate('typeJournal','${typeJournal}',1) ;
    checkFieldUpdate('typeLpu','${typeLpu}',3) ;
  
   function checkFieldUpdate(aField,aValue,aDefaultValue) {
   	eval('var chk =  document.forms[0].'+aField) ;
   	var aMax=chk.length ;
   	//alert(aField+" "+aValue+" "+aMax+" "+chk) ;
   	if ((+aValue)==0 || (+aValue)>(+aMax)) {
   		chk[+aDefaultValue-1].checked='checked' ;
   	} else {
   		chk[+aValue-1].checked='checked' ;
   	}
   }
      </script>
    	<%
    	if (typeLpu!=null && typeLpu.equals("1")) {
    		request.setAttribute("lpuSql", " and slo.dtype='DepartmentMedCase' ") ;
    		request.setAttribute("lpuInfo", ", стационар") ;

    	} else if (typeLpu!=null && typeLpu.equals("2")) {
    		request.setAttribute("lpuSql", " and slo.dtype!='DepartmentMedCase' ") ;
    		request.setAttribute("lpuInfo", ", поликлиника") ;
    		
    	}
    	String groupId = request.getParameter("groupId") ;
    	//String groupId = request.getParameter("groupId") ;
    	if (groupId==null && typeJournal!=null && 
    			(typeJournal.equals("2")||typeJournal.equals("3")||typeJournal.equals("4"))) {
    		if (typeJournal.equals("2")) {
    			request.setAttribute("queryGroup", "cec.orderDate");
    			request.setAttribute("queryOrder", "cec.orderDate");
    			request.setAttribute("queryId", "to_char(cec.orderDate,'dd.mm.yyyy')");
    			request.setAttribute("queryFld", "to_char(cec.orderDate,'dd.mm.yyyy')");
    			request.setAttribute("queryTitle", "Дата направления");
    		} else if (typeJournal.equals("3")) {
    			request.setAttribute("queryGroup", "ml.id,ml.name");
    			request.setAttribute("queryOrder", "ml.name");
    			request.setAttribute("queryId", "ml.id");
    			request.setAttribute("queryFld", "ml.name");
    			request.setAttribute("queryTitle", "Отделение");
    		} else if (typeJournal.equals("4")) {
    			request.setAttribute("queryGroup", "cec.orderFunction_id,ovwf.name,owp.lastname,owp.firstname,owp.middlename");
    			request.setAttribute("queryOrder", "ovwf.name,owp.lastname,owp.firstname,owp.middlename");
    			request.setAttribute("queryId", "cec.orderFunction_id");
    			request.setAttribute("queryFld", "ovwf.name||' '||owp.lastname||' '||owp.firstname||' '||owp.middlename");
    			request.setAttribute("queryTitle", "Специалист");
    		}
    		if (typeJournal.equals("5")) {
				request.setAttribute("queryOrder", "vet.name");
			}
    	%>
    	<ecom:webQuery name="list"
    	nativeSql="select 
			 '&groupId='||${queryId} as id,${queryFld} as orderDate, count(*) as cnt 
	    	from ClinicExpertCard cec 
	    	left join medcase slo on slo.id=cec.medcase_id
	    	left join mislpu ml on ml.id=slo.department_id
	    	left join WorkFunction owf on owf.id=cec.orderFunction_id
	    	left join VocWorkFunction ovwf on ovwf.id=owf.workFunction_id
	    	left join Worker ow on ow.id=owf.worker_id
	    	left join Patient owp on owp.id=ow.person_id
			where cec.expertdate is null ${lpuSql}
			group by ${queryGroup} 
			order by ${queryOrder}"
    	/>
        <msh:table name="list" viewUrl="expert_kersopen.do?typeJournal=${typeJournal}&short=Short" action="expert_kersopen.do?typeJournal=${typeJournal}" idField="1" noDataMessage="Не найдено">
            <msh:tableColumn columnName="#" property="sn"/>
            <msh:tableColumn columnName="${queryTitle}" property="2"/>
            <msh:tableColumn columnName="Кол-во" isCalcAmount="true" property="3"/>
        </msh:table>
        <% } else { 
        	
    		if (typeJournal.equals("2")) {
    			request.setAttribute("queryWhere", " and cec.orderDate=to_date('"+groupId+"','dd.mm.yyyy')");
    			request.setAttribute("queryOrder", "p.lastname||' '||p.firstname||' '||p.middlename");
    		} else if (typeJournal.equals("3")) {
    			request.setAttribute("queryWhere", " and slo.department_id='"+groupId+"'");
    			request.setAttribute("queryOrder", "p.lastname||' '||p.firstname||' '||p.middlename");
    		} else if (typeJournal.equals("4")) {
    			request.setAttribute("queryWhere", " and cec.orderFunction_id='"+groupId+"'");
    			request.setAttribute("queryOrder", "p.lastname||' '||p.firstname||' '||p.middlename");
    		} else {
    			request.setAttribute("queryOrder", "cec.orderDate,p.lastname||' '||p.firstname||' '||p.middlename");
    		}
			if (typeJournal.equals("5")) {
				request.setAttribute("queryOrder", "vet.name");
			}
        %>
	       	<ecom:webQuery name="list"
		    	nativeSql="select 
cec.id,cec.orderDate,cec.disabilitydate
,ovwf.name||' '||owp.lastname||' '||owp.firstname||' '||owp.middlename as workfunction
,p.lastname||' '||p.firstname||' '||p.middlename as fio
,(select max(cec1.conclusionDate) from clinicexpertcard cec1
left join medcase slo1 on slo1.id=cec1.medcase_id
left join medcase sls1 on sls1.id=slo1.parent_id
where sls1.id=sls.id) as con1Sls
,vet.name as vetname
from ClinicExpertCard cec
left join WorkFunction owf on owf.id=cec.orderFunction_id
left join Worker ow on ow.id=owf.worker_id
left join Patient owp on owp.id=ow.person_id
left join VocWorkFunction ovwf on ovwf.id=owf.workFunction_id
left join Patient p on p.id=cec.patient_id
			    	left join medcase slo on slo.id=cec.medcase_id
			    	left join mislpu ml on ml.id=slo.department_id
left join medcase sls on sls.id=slo.parent_id
left join vocexperttype vet on cec.type_id=vet.id
where cec.expertDate is null ${queryWhere} ${lpuSql}
order by ${queryOrder}
"
		    	/>
            <msh:table name="list" action="entityParentEdit-expert_ker.do" idField="1" noDataMessage="Не найдено">
		      <msh:tableColumn columnName="#" property="sn"/>
		      <msh:tableColumn columnName="Номер" property="1"/>
		      <msh:tableColumn columnName="Дата направления" property="2"/>
		      <msh:tableColumn columnName="Дата выхода на нетрудоспособность" property="3"/>
		      <msh:tableColumn columnName="Дата посл. продления по госпитализации" property="6"/>
		      <msh:tableColumn columnName="Специалист" property="4"/>
		      <msh:tableColumn columnName="Пациент" property="5"/>
				<msh:tableColumn columnName="Тип ВК" property="7"/>
		    </msh:table>
        <% 
        }
        %>
    </tiles:put>

</tiles:insert>