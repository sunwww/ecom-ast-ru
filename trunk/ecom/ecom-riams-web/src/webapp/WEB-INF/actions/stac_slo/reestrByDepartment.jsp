<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="StacJournal">Журнал обращений по отделению</msh:title>
  </tiles:put>
  <tiles:put name="side" type="string">
  	<tags:stac_journal currentAction="stac_reestrByDepartment"/>
  </tiles:put>
  <tiles:put name="body" type="string">
    <msh:form action="/stac_reestrByDepartment.do" defaultField="department" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
      <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Journal/ShowInfoAllDepartments">
	      <msh:row>
	      	<msh:autoComplete property="department" vocName="lpu" label="Отделение" fieldColSpan="6" horizontalFill="true"/>
	      </msh:row>
      </msh:ifInRole>
      <msh:row guid="7d80be13-710c-46b8-8503-ce0413686b69">
        <msh:checkBox property="dischargeIs" label="Искать по дате выписки" guid="f5458f6-b5b8-4d48-a045-ba7b9f875245" />
        <msh:textField property="dateBegin" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
        <msh:textField property="dateEnd" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
           <td>
            <input type="submit" onclick="find()" value="Найти" />
          </td>
           <td>
            <input type="submit" onclick="print()" value="Печать" />
          </td>
                  </msh:row>
                  <%-- 
        <msh:row>
        <td class="label" title="Период (period)" colspan="1"><label for="periodName" id="periodLabel">Период:</label></td>
        <td onclick="this.childNodes[1].checked='checked';changePeriod()">
        	<input type="radio" name="period" value="1"> Неделя
        </td>
        <td onclick="this.childNodes[1].checked='checked';changePeriod()">
        	<input type="radio" name="period" value="2"> Месяц
        </td>

      </msh:row>
      --%>
    </msh:panel>
    </msh:form>
    
    <%
    String date = (String)request.getParameter("dateBegin") ;
    Long department = (Long)request.getAttribute("department") ;
    if (date!=null && !date.equals("") && department!=null && department.intValue()>0 )  {
    	%>
    
    <msh:section>
    <msh:sectionTitle>Результаты поиска обращений в отделение  ${departmentInfo}. Период с ${param.dateBegin} по ${param.dateEnd}. ${infoSearch}</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_priem" nativeSql="select m.id,m.dateStart as mdateStart,m.dateFinish,m.transferDate,pat.lastname ||' ' ||pat.firstname|| ' ' || pat.middlename,pat.birthday,sc.code as sccode
    ,vas.name as vasname,vbst.name as vbstname,vss.name as vssname
    ,case when m.prevMedCase_id is not null then 'Да' else 'Нет' end,vhr.name,sls.dateStart as slsdateStart
    from medCase m 
    
    left join MedCase as sls on sls.id = m.parent_id
    left join BedFund bf on m.bedfund_id=bf.id
    left join VocBedSubType vbst on vbst.id=bf.bedSubType_id
    left join VocServiceStream vss on vss.id=bf.serviceStream_id
    left join VocHospitalizationResult vhr on vhr.id=sls.result_id
    left join StatisticStub as sc on sc.medCase_id=sls.id 
    left outer join Patient pat on m.patient_id = pat.id 
    left join VocAdditionStatus vas on pat.additionStatus_id=vas.id
    where m.DTYPE='DepartmentMedCase' and m.department_id='${department}'  and m.${dateSearch}  between to_date('${param.dateBegin}','dd.mm.yyyy')  and to_date('${param.dateEnd}','dd.mm.yyyy')
    order by vbst.name, m.${dateSearch}
    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="journal_priem" action="entityParentView-stac_slo.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d919b45bd6">
      <msh:tableColumn property="sn" columnName="#"/>
      <msh:tableColumn columnName="Стат.карта" property="7" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Доп.статус" property="8"/>
      <msh:tableColumn columnName="Перевод из др. отд." property="11"/>
      <msh:tableColumn columnName="Фамилия имя отчество пациента" property="5" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Год рождения" property="6" guid="34a9f56a-2b47-4feb-a3fa-5c1afdf6c41d" />
      <msh:tableColumn columnName="Дата поступления в стационар" property="13" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
      <msh:tableColumn columnName="Дата поступления в отделение" property="2" guid="3cf775aa-e94d-4393-a489-b83b2be02d60" />
      <msh:tableColumn columnName="Дата перевода" property="4" guid="e29229e1-d243-47d6-a5c7-997df74eaf73" />
      <msh:tableColumn columnName="Дата выписки" property="3" guid="d9642df9-5653-4920-bb78-1622cbeefa34" />
      <msh:tableColumn columnName="Тип коек" property="9"/>      
      <msh:tableColumn columnName="Поток обслуживания" property="10"/>  
      <msh:tableColumn columnName="Результат госпитализации" property="12"/>  
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    
    <% } else {%>
    	<i>Нет данных </i>
    	<% }   %>
    <%-- 
    <script type='text/javascript' src='/skin/ext/jscalendar/calendar.js'></script> 
    <script type='text/javascript' src='/skin/ext/jscalendar/calendar-setup.js'></script> 
    <script type='text/javascript' src='/skin/ext/jscalendar/calendar-ru.js'></script> 
    <style type="text/css">@import url(/skin/ext/jscalendar/css/calendar-blue.css);</style>
    --%>
    <script type='text/javascript'>
    /*
    var period = document.forms[0].period ;
    if ((+'${param.period}')==2) {
    	period[1].checked='checked' ;
    } else {
    	period[0].checked='checked' ;
    }*/
    function find() {
    	var frm = document.forms[0] ;
    	frm.target='' ;
    	frm.action='stac_reestrByDepartment.do' ;
    }
    function print() {
    	var frm = document.forms[0] ;
    	frm.target='_blank' ;
    	frm.action='stac_print_reestrByDepartment.do' ;
    }
    /*
    function getPeriod() {
    	//var period = document.forms[0].period ;
    	for (i=0;i<period.length;i++) {
    		
    		if (period[i].checked) return +period[i].value ;
    	}
    	return 1 ;
    }
    function changePeriod() {
    	
    	var field1 = document.getElementById("dateBegin").value;
    	var field2 = document.getElementById("dateEnd");
    	var date2 ;
    	var date = new Date(field1.substring(0,4),(+field1.substring(5,7)-1),field1.substring(8)) ;
    	if (getPeriod()==1) {
		 	date2 = new Date(date.getFullYear(),date.getMonth(),date.getDate()+6) ;
		 	//time += Date.WEEK; // substract one week
		 } else {
		 	date2=new Date(date.getFullYear(),date.getMonth()+1,date.getDate()-1) ;
		 }
		field2.value = date2.print("%Y-%m-%d");
    }
    function catcalc(cal) {
			var date = cal.date;
			var time = date.getTime() ;
			 // use the _other_ field
			 var field = document.getElementById("dateEnd");
			 var date2 ;
			 if (field == cal.params.inputField) {
				 field = document.getElementById("dateBegin");
				 if (getPeriod()==1) {
				 	date2 = new Date(date.getFullYear(),date.getMonth(),date.getDate()-6) ;
				 	//date2 = new Date(time) ;
				 } else {
				 	date2=new Date(date.getFullYear(),date.getMonth()-1,date.getDate()+1) ;
				 	
				 }
			 } else {
				 if (getPeriod()==1) {
				 	date2 = new Date(date.getFullYear(),date.getMonth(),date.getDate()+6) ;
				 	//time += Date.WEEK; // substract one week
				 } else {
				 	date2=new Date(date.getFullYear(),date.getMonth()+1,date.getDate()-1) ;
				 }
			 }
			 //var date2 = new Date(time);
			 field.value = date2.print("%Y-%m-%d");
	}
			 Calendar.setup({
				 inputField : "dateBegin", // id of the input field
				 ifFormat : "%Y-%m-%d", // format of the input field
				 showsTime : false,
				 timeFormat : "24",
				 eventName: "focus",
				 onUpdate : catcalc
			 });
			 Calendar.setup({
				 inputField : "dateEnd",
				 ifFormat : "%Y-%m-%d",
				 showsTime : false,
				 timeFormat : "24",
				 eventName: "focus",
				 onUpdate : catcalc
 			});*/
    </script>
  </tiles:put>
</tiles:insert>

