<%@page import="ru.ecom.mis.web.action.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title guid="helloItle-123" mainMenu="Journals" title="Журнал учета больных в соответствие с поступлением (добровольно, недобровольно)"/>
  </tiles:put>
  <tiles:put name="side" type="string">
  	
    
  </tiles:put>
  <tiles:put name="body" type="string">
  <%
  
	String typeDate =ActionUtil.updateParameter("Hospital_Reestr_Psych","typeDate","1", request) ;
	String typeDirect =ActionUtil.updateParameter("Hospital_Reestr_Psych","typeAdmissionOrder","2", request) ;
  %>
  
    <msh:form action="/stac_journal_direct_psych.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <msh:panel guid="6ae283c8-7035-450a-8eb4-6f0f7da8a8ff">
    
      <msh:row guid="53627d05-8914-48a0-b2ec-792eba5b07d9">
        <msh:separator label="Параметры поиска" colSpan="7" guid="15c6c628-8aab-4c82-b3d8-ac77b7b3f700" />
      </msh:row>
      <msh:row>
        <td class="label" title="Поиск по дате (typeDate)" colspan="1"><label for="typeDateName" id="typeDateLabel">Дата:</label></td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeDate" value="1">  поступления
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeDate" value="2" >  выбытия
        </td>
      </msh:row>
      <msh:row>
        <td class="label" title="Поиск по показаниям поступления (typeAdmissionOrder)" colspan="1"><label for="typeAdmissionOrderName" id="typeAdmissionOrderLabel">Поступил:</label></td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeAdmissionOrder" value="1">  добровольное+согласия не требуется
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeAdmissionOrder" value="2" >  недобровольное
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeAdmissionOrder" value="3"> добровольное
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeAdmissionOrder" value="4"> согласия не требуется
        </td>
      </msh:row>

        <msh:row>
	        <td class="label" title="Просмотр данных (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отобразить:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
	        	<input type="radio" name="typeView" value="1">  реестр
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="4">
	        	<input type="radio" name="typeView" value="2">  свод по отделениям 
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="4">
	        	<input type="radio" name="typeView" value="3">  свод по решениям судьи по ст.35 
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="4">
	        	<input type="radio" name="typeView" value="4">  свод по порядку поступления  (статьи 29 и др)
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="4">
	        	<input type="radio" name="typeView" value="5">  кем направлен
	        </td>
        </msh:row>
      <msh:row>
        <msh:textField fieldColSpan="2" property="dateBegin" label="Период с" guid="8d7ef035-1273-4839-a4d8-1551c623caf1" />
        <msh:textField property="dateEnd" label="по" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
      </msh:row>
        <msh:row>
        	<msh:autoComplete property="department" fieldColSpan="7" horizontalFill="true" label="Отделение" vocName="lpu"/>
        </msh:row>
      <msh:row>
           <td colspan="11">
            <input type="submit" onclick="find()" value="Найти" />
          </td>
      </msh:row>
      
    </msh:panel>
    </msh:form>
    <script type='text/javascript'>
    
    checkFieldUpdate('typeEmergency','${typeEmergency}',3) ;
    checkFieldUpdate('typeView','${typeView}',1) ;
  
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
			 
    function find() {
    	var frm = document.forms[0] ;
    	frm.target='' ;
    	frm.action='expert_journal_ker.do' ;
    }
    function print() {
    	var frm = document.forms[0] ;
    	frm.m.value="printCriminalMessage" ;
    	frm.s.value="HospitalPrintReport" ;
    	frm.target='_blank' ;
    	frm.action='print-stac_criminalMessage.do' ;
    	
    }
    </script>

    
    <%
    String date = (String)request.getParameter("dateBegin") ;
    String date1 = (String)request.getParameter("dateEnd") ;
    
    if (date!=null && !date.equals(""))  {
    	if (date1==null ||date1.equals("")) {
    		request.setAttribute("dateEnd", date);
    	} else {
    		request.setAttribute("dateEnd", date1) ;
    	}
    	String view = (String)request.getAttribute("typeView") ;
    	/*
    	if (typeEmergency!=null && typeEmergency.equals("1")) {
    		request.setAttribute("emergencySql", " and slo.emergency='1' ") ;
    		request.setAttribute("emergencyInfo", ", поступивших по экстренным показаниям") ;
    	} else if (typeEmergency!=null && typeEmergency.equals("2")) {
    		request.setAttribute("emergencySql", " and (slo.emergency is null or slo.emergency='0') ") ;
    		request.setAttribute("emergencyInfo", ", поступивших по плановым показаниям") ;
    	} 
    	*/
    	ActionUtil.setParameterFilterSql("department","ml.id", request) ;

    	%>
 <%
    if (view!=null && (view.equals("1"))) {%>
    
    <msh:section title="Реестр за период ${param.dateBegin}-${param.dateEnd} ${emergencyInfo}">
    <ecom:webQuery nameFldSql="journal_expert_sql" name="journal_expert" nativeSql="
select 
cec.id,to_char(expertDate,'dd.mm.yyyy')
,ovwf.name||' '||owp.lastname||' '||owp.firstname||' '||owp.middlename as workfunction
,p.lastname||' '||p.firstname||' '||p.middlename as patient
,case when p.address_addressId is not null 
          then coalesce(a.fullname,a.name) 
               ||case when p.houseNumber is not null and p.houseNumber!='' then ' д.'||p.houseNumber else '' end
               ||case when p.houseBuilding is not null and p.houseBuilding!='' then ' корп.'|| p.houseBuilding else '' end
	       ||case when p.flatNumber is not null and p.flatNumber!='' then ' кв. '|| p.flatNumber else '' end
       when p.territoryRegistrationNonresident_id is not null
	  then okt.name||' '||p.RegionRegistrationNonresident||' '||oq.name||' '||p.SettlementNonresident
	       ||' '||ost.name||' '||p.StreetNonresident
               ||case when p.HouseNonresident is not null and p.HouseNonresident!='' then ' д.'||p.HouseNonresident else '' end
	       ||case when p.BuildingHousesNonresident is not null and p.BuildingHousesNonresident!='' then ' корп.'|| p.BuildingHousesNonresident else '' end
	       ||case when p.ApartmentNonresident is not null and p.ApartmentNonresident!='' then ' кв. '|| p.ApartmentNonresident else '' end
       else  p.foreignRegistrationAddress end as address
,to_char(p.birthday,'dd.mm.yyyy') as birthday
,vs.name as vsname,veps.code||', '||cec.profession as job
,mkb.code as mkbcode
,vepc.code as vepccode
,veds.name||' '||cec.deviationStandardsText as deviationStandards
,cec.defects as defects,cec.resultStep as resultStep
,vec.code||' '||coalesce(to_char(cec.conclusionDate,'dd.mm.yyyy'),'')||' '||coalesce(cec.additionInfo,'') as conclusion
,cec.orderHADate as orderHADate,cec.conclusionHA as conlusionHA
,cec.receiveHADate as receiveHADate,cec.additionInfoHA as addtionInfoHA
from MedCase sls 
left join MedCase slo on slo.id=sls.parent_id
left join MisLpu ml on ml.id=slo.department_id
left join WorkFunction owf on owf.id=cec.orderFunction_id
left join Worker ow on ow.id=owf.worker_id
left join Patient owp on owp.id=ow.person_id
left join VocWorkFunction ovwf on ovwf.id=owf.workFunction_id
left join Patient p on p.id=cec.patient_id
left join VocSex vs on vs.id=p.sex_id
left join VocExpertPatientStatus veps on veps.id=cec.patientStatus_id
left join VocIdc10 mkb on mkb.id=cec.mainDiagnosis_id
left join VocExpertPatternCase vepc on vepc.id=cec.patternCase_id
left join DisabilityDocument dd on dd.id=cec.disabilityDocument_id
left join VocExpertModeCase vemc on vemc.id=cec.modeCase_id
left join VocExpertSubject ves on ves.id=cec.subjectCase_id
left join VocExpertDeviationStandards veds on veds.id=cec.deviationStandards_id
left join VocExpertConclusion vec on vec.id=cec.conclusion_id
left join Address2 a on a.addressId=p.address_addressId
left join Omc_KodTer okt on okt.id=p.territoryRegistrationNonresident_id
left join Omc_Qnp oq on oq.id=p.TypeSettlementNonresident_id
left join Omc_StreetT ost on ost.id=p.TypeStreetNonresident_id

    where cec.expertDate between to_date('${param.dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')  
${emergencySql} ${departmentSql}
    order by cec.expertDate
    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:sectionTitle>
    
    <form action="print-expert_journalKer.do" method="post" target="_blank">
    Реестр с ${param.dateBegin} по ${dateEnd}.
    <input type='hidden' name="sqlText" id="sqlText" value="${journal_expert_sql}"> 
    <input type='hidden' name="sqlInfo" id="sqlInfo" value="Период с ${param.dateBegin} по ${param.dateEnd}.">
    <input type='hidden' name="sqlColumn" id="sqlColumn" value="">
    <input type='hidden' name="s" id="s" value="PrintService">
    <input type='hidden' name="m" id="m" value="printNativeQuery">
    <input type="submit" value="Печать"> 
    </form>
    </msh:sectionTitle>
    <msh:sectionContent>
    <msh:table name="journal_expert"
    viewUrl="entityParentView-expert_ker.do?short=Short" 
     action="entityParentView-expert_ker.do" idField="1" >
      <msh:tableColumn columnName="#" property="sn" />
      <msh:tableColumn columnName="Дата экспертизы" property="2" />
      <msh:tableColumn columnName="ФИО врача" property="3" />
      <msh:tableColumn columnName="ФИО пациента" property="4" />
      <msh:tableColumn columnName="Адрес" property="5" />
      <msh:tableColumn columnName="Дата рождения" property="6" />
      <msh:tableColumn columnName="Пол" property="7" />
      <msh:tableColumn columnName="Соц.статус, профессия" property="8" />
      <msh:tableColumn columnName="Диагноз" property="9" />
      <msh:tableColumn columnName="Характеристика случая экспертизы" property="10" />
      <msh:tableColumn columnName="Вид, предмет экспертизы" property="11" />
      <msh:tableColumn columnName="Отклонения от стандарта" property="12" />
      <msh:tableColumn columnName="Дефекты" property="13" />
      <msh:tableColumn columnName="Достижения ЛПМ" property="14" />
      <msh:tableColumn columnName="Обоснование заключения" property="15" />
      <msh:tableColumn columnName="Дата направ. в бюро МСЭ" property="16" />
      <msh:tableColumn columnName="Заключеие МСЭ" property="17" />
      <msh:tableColumn columnName="Дата получения закл. МСЭ" property="18" />
      <msh:tableColumn columnName="Доп. инф. по закл. др. учреж." property="19" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>

    <%} %>
       	<%if (view!=null && (view.equals("2"))) {%>
    
    <msh:section>
    <msh:sectionTitle>Свод по отделениям за период ${param.dateBegin}-${dateEnd} ${emergencyInfo}.</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_militia" nativeSql="
    " guid="4a720225-8d94-4b47-bef3-4dbbe79eec74" />
    <msh:table name="journal_militia"
    viewUrl="expert_journal_ker.do?short=Short&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&typeView=1&typeEmergency=${typeEmergency}" 
     action="expert_journal_ker.do?dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&typeView=1&typeEmergency=${typeEmergency}" idField="1" >
      <msh:tableColumn columnName="Отделение" property="2" />
      <msh:tableColumn columnName="Кол-во направ. на ВК" property="3" />
      <msh:tableColumn columnName="Кол-во ВК с откл. от станд." property="4" />
    </msh:table>
    </msh:sectionContent>
    </msh:section>
    <% }  %>
    <% 
    } else {%>
    	<i>Нет данных </i>
    	<% 
    	}%>
    
  </tiles:put>
</tiles:insert>