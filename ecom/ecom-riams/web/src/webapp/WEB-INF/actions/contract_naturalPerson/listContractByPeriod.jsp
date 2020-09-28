<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Contract" title="Списки договор по физическим лицам"/>
  </tiles:put>
  <tiles:put name="side" type="string">
    	<tags:contractMenu currentAction="naturalPerson"/>
  </tiles:put>
  <tiles:put name="body" type="string">
  <%
	String typeDtype =ActionUtil.updateParameter("Contract_NaturalPerson","typeView","3", request) ;
  %>
  
    <msh:form action="/contract_journal_natural_person_by period.do" defaultField="dateBegin" disableFormDataConfirm="true" method="GET">
    <msh:panel>
    <input type="hidden" name="s" id="s" value="HospitalPrintService" />
    <input type="hidden" name="m" id="m" value="printReestrByDay" />
    <input type="hidden" name="id" id="id" value=""/>
    
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
        <msh:row>
	        <td class="label" title="Просмотр данных (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Отобразить:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
	        	<input type="radio" name="typeView" value="1">  реестр
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';"  colspan="4">
	        	<input type="radio" name="typeView" value="2">  свод по отделениям 
	        </td>
        </msh:row>
      <msh:row>
        <msh:textField fieldColSpan="2" property="dateFrom" label="Период с" />
        <msh:textField property="dateTo" label="по" />
      </msh:row>
      <msh:row>
           <td colspan="11">
            <input type="submit" onclick="find()" value="Найти" />
          </td>
      </msh:row>
      
    </msh:panel>
    </msh:form>
    <script type='text/javascript'>
    
    //checkFieldUpdate('typeEmergency','${typeEmergency}',3) ;
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
    	frm.action='contract_journal_natural_person_by period.do' ;
    }
    function print() {
    	var frm = document.forms[0] ;
    	frm.m.value="printCriminalMessage" ;
    	frm.s.value="HospitalPrintReport" ;
    	frm.target='_blank' ;
    	frm.action='print-contract_journal_natural_person_by period.do' ;
    	
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
    	String department="" ;
    	String dep = request.getParameter("department") ;
    	if (dep!=null && !dep.equals("") && !dep.equals("0")) {
    		department= " and ml.id='"+dep+"'" ;
    	}
    	request.setAttribute("department", department) ;
    	
    	%>
 <%
    if (view!=null && (view.equals("1"))) {%>
    
    <msh:section title="Реестр за период ${param.dateBegin}-${param.dateEnd}">
    <ecom:webQuery nameFldSql="journal_expert_sql" name="journal_expert" nativeSql="
select 
cec.id,to_char(expertDate,'dd.mm.yyyy')
,ovwf.name||' '||owp.lastname||' '||owp.firstname||' '||owp.middlename as workfunction
,p.lastname||' '||p.firstname||' '||p.middlename as patient
, case when p.address_addressId is not null 
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
,vemc.code||coalesce(', № Л/Н'||dd.number,'')||', д. '||(cec.orderDate-cec.disabilityDate+1)||', '||ves.code as disability
,veds.name||' '||cec.deviationStandardsText as deviationStandards
,cec.defects as defects,cec.resultStep as resultStep
,cec.orderHADate as orderHADate,cec.conclusionHA as conlusionHA
,cec.receiveHADate as receiveHADate,cec.additionInfoHA as addtionInfoHA
from ClinicExpertCard cec
left join MedCase slo on slo.id=cec.medCase_id
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
left join Address2 a on a.addressId=p.address_addressId
left join Omc_KodTer okt on okt.id=p.territoryRegistrationNonresident_id
left join Omc_Qnp oq on oq.id=p.TypeSettlementNonresident_id
left join Omc_StreetT ost on ost.id=p.TypeStreetNonresident_id

    where cec.expertDate between to_date('${param.dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')  
${emerIs} ${department}
    order by cec.expertDate
    " />
    <msh:sectionTitle>
    
    <form action="print-contract_naturalPerson.do" method="post" target="_blank">
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
    viewUrl="entityParentView-contract_naturalPerson.do?short=Short" 
     action="entityParentView-contract_naturalPerson.do" idField="1" >
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
    <msh:sectionTitle>Свод по отделениям за период ${param.dateBegin}-${dateEnd}.</msh:sectionTitle>
    <msh:sectionContent>
    <ecom:webQuery name="journal_militia" nativeSql="
   select 
'&department='||ml.id as id,ml.name as mlname,count(*)
,count(case when veds.code='1' then cec.id else null end) as cntVeds1
from ClinicExpertCard cec
left join MedCase slo on slo.id=cec.medCase_id
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

    where cec.expertDate between to_date('${param.dateBegin}','dd.mm.yyyy')  and to_date('${dateEnd}','dd.mm.yyyy')  
${emerIs} ${department}
	group by ml.id,ml.name
    order by ml.name
    " />
    <msh:table name="journal_militia"
    viewUrl="contract_journal_natural_person_by period.do?short=Short&dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&typeView=1&typeEmergency=${typeEmergency}" 
     action="contract_journal_natural_person_by period.do?dateBegin=${param.dateBegin}&dateEnd=${param.dateEnd}&typeView=1&typeEmergency=${typeEmergency}" idField="1" >
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