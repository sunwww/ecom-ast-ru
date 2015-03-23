<%@page import="ru.ecom.web.util.ActionUtil"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true" >

  <tiles:put name="title" type="string">
    <msh:title mainMenu="Journals" title="Журнал для отправки данных в фонд"/>
  </tiles:put>
  <tiles:put name="side" type="string">
  </tiles:put>
  <tiles:put name="body" type="string">
  <%
  
	String typeView=ActionUtil.updateParameter("HospitalDirectDataInFond","typeView","1", request) ;
	String typeView1=ActionUtil.updateParameter("HospitalDirectDataInFond","typeView1","1", request) ;
	String typeDate=ActionUtil.updateParameter("HospitalDirectDataInFond","typeDate","1", request) ;
	String typeMode=ActionUtil.updateParameter("HospitalDirectDataInFond","typeMode","1", request) ;
	String typeEmergency=ActionUtil.updateParameter("HospitalDirectDataInFond","typeEmergency","3", request) ;
	String typeLpu=ActionUtil.updateParameter("HospitalDirectDataInFond","typeLpu","3", request) ;
  %>
  
    <msh:form action="/stac_direct_in_fond.do" defaultField="lpu" disableFormDataConfirm="true" method="GET" guid="d7b31bc2-38f0-42cc-8d6d-19395273168f">
    <msh:panel>
   
      <msh:row>
        <msh:separator label="Параметры поиска" colSpan="7" />
      </msh:row>
      <msh:row>
      	<msh:textField property="lpu"/>
        <msh:textField property="numberReestr" label="Реестровый номер" guid="f54568f6-b5b8-4d48-a045-ba7b9f875245" />
      </msh:row>
      <msh:row>
        <msh:textField  property="period" label="Период с" />
         <msh:textField  property="periodTo" label="по" /> 
      </msh:row>
      <msh:row>
      	<td class="label" title="Режим  (typeMode)" colspan="1"><label for="typeModeName" id="typeModeLabel">Режим:</label></td>
        <td onclick="this.childNodes[1].checked='checked';checkMode()" colspan="2">
        	<input type="radio" name="typeMode" value="1"> Формирования Xml
        </td>
	        <td onclick="this.childNodes[1].checked='checked';checkMode()" colspan="2">
	        	<input type="radio" name="typeMode" value="2"> Импорт результата
	        </td>
	   </msh:row>
      <msh:row>
      	<td></td>
	        <td onclick="this.childNodes[1].checked='checked';checkMode()" colspan="2">
	        	<input type="radio" name="typeMode" value="3"> Работа с данными
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';checkMode()" colspan="8">
	        	<input type="radio" name="typeMode" value="4"> Обновить соответствия по направлениям
	        </td>
      </msh:row>
      </msh:panel>
      <msh:panel styleId="updateData">
             <msh:row>
           <td colspan="11">
            <input type="submit" value="Обновить соответствия по направлениям" />
          </td>
      </msh:row>
            </msh:panel>
      <msh:panel styleId="exportXml">
      <msh:row>
        <td class="label" title="Тип xml  (typeView)" colspan="1"><label for="typeViewName" id="typeViewLabel">Тип xml:</label></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="1"> (N1) направления на госп.
        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeView" value="2"> (N2) госпитализации по направлению
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeView" value="3"> (N3) экстр. госпитализация
	        </td>
       </msh:row>
      <msh:row>
        <td></td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="4"> (N4) аннулирование направ. на госп.
        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeView" value="5"> (N5) выбывшие из стац.
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="2">
	        	<input type="radio" name="typeView" value="6"> (N6) наличие своб. мест
	        </td>
       </msh:row>
       <msh:row>
        <td></td>
        
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="7">  список неопред. по N1
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="8">  N1 + N3 таблицы
        </td>
        <td onclick="this.childNodes[1].checked='checked';">
        	<input type="radio" name="typeView" value="9">  N4 + N5 таблицы
        </td>
       </msh:row>
       <msh:row>
       	<msh:hidden property="filename"/>
       	<td colspan="4">
       		Файл <span id='aView'></span>
       	</td>
       </msh:row>
      <msh:row>
           <td colspan="11">
            <input type="submit" value="Сформировать файлы" />
          </td>
      </msh:row>
          </msh:panel>
          <msh:panel styleId="workData">
      <msh:row>
        <td class="label" title="Искать по дате (typeDate)" colspan="1"><label for="typeDateName" id="typeDateLabel">Искать по дате:</label></td>
        <td class='tdradio' onclick="this.childNodes[1].checked='checked';" colspan="2">
        	<input type="radio" name="typeDate" value="1"> поступления  
        </td>
        <td class='tdradio' onclick="this.childNodes[1].checked='checked';" colspan="3">
        	<input type="radio" name="typeDate" value="2">  выписки
        </td>
      </msh:row>      
     <msh:row>
        <td class="label" title="Поиск по показаниям поступления (typeEmergency)" colspan="1"><label for="typeEmergencyName" id="typeEmergencyLabel">Показания:</label></td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeEmergency" value="1">  экстренные
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeEmergency" value="2" >  плановые
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeEmergency" value="3">  все
        </td>
      </msh:row>
           <msh:row>
        <td class="label" title="Тип ЛПУ (typeLpu)" colspan="1"><label for="typeLpuName" id="typeLpuLabel">Тип ЛПУ:</label></td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeLpu" value="1">  направленных из текущего ЛПУ
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeLpu" value="2" >  направленных из другого ЛПУ
        </td>
        <td onclick="this.childNodes[1].checked='checked';"  colspan="2">
        	<input type="radio" name="typeLpu" value="3">  без учета ЛПУ
        </td>
      </msh:row>                
           
           
           <msh:row>
            <td class="label" title="Список  (typeView1)" colspan="1"><label for="typeView1Name" 
               id="typeView1Label">Список:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="4">
	        	<input type="radio" name="typeView1" value="1"> госпитализации без направлений 
	        </td>
	   </msh:row>
	   <msh:row>
	        <td></td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="4">
	        	<input type="radio" name="typeView1" value="2">  направления, по которым не определены госпитализации 
	        </td>
	   </msh:row>
	   <msh:row>
	        <td></td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="4">
	        	<input type="radio" name="typeView1" value="3">  неопред. госп. и направления 
	        </td>
	   </msh:row>
	   <msh:row>
	        <td></td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="4">
	        	<input type="radio" name="typeView1" value="4">  отделения, по которым не проставлен диагноз 
	        </td>
       	</msh:row>
       	<msh:row>
	        <td></td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="8">
	        	<input type="radio" name="typeView1" value="5">  СНИЛС врача для ген. плановых направлений для госпит. 
	        </td>
	   </msh:row>
             <msh:row>
           <td colspan="11">
            <input type="submit" value="Отобразить данные" />
          </td>
      </msh:row>
          </msh:panel>
          
    </msh:form>
    
       <msh:form action="stac_direct_in_fond_import.do"  defaultField="isClear" fileTransferSupports="true">
			            <msh:hidden property="saveType"/>
			 			<msh:panel styleId="importXml">
			                <msh:row>
			                    <td>Файл *.xml</td>
			                    <td colspan="1">
			                        <input type="file" name="file" id="file" size="50" value="" onchange="">
			                        <input type="button" name="run_import" value="Импорт"  onclick="this.form.submit()" />
			                    </td>
			                </msh:row>
			                	<msh:row>
			                	<td colspan="4" align="center">
			                		
			                	</td>
			                	</msh:row>
			                	
			            </msh:panel>
			        </msh:form>
	<tags:hosp_263 name="Diag"></tags:hosp_263>
<script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js">/**/</script>
      <script type="text/javascript">
      checkFieldUpdate('typeLpu','${typeLpu}',1) ;
      checkFieldUpdate('typeEmergency','${typeEmergency}',1) ;
      checkFieldUpdate('typeView1','${typeView1}',1) ;
      checkFieldUpdate('typeView','${typeView}',1) ;
      checkFieldUpdate('typeDate','${typeDate}',1) ;
      checkFieldUpdate('typeMode','${typeMode}',1) ;
      function checkFieldUpdate(aField,aValue,aDefaultValue) {
    	   	eval('var chk =  document.forms[0].'+aField) ;
    	   	var aMax=chk.length ;
    	   	if ((+aValue)==0 || (+aValue)>(+aMax)) {
    	   		chk[+aDefaultValue-1].checked='checked' ;
    	   	} else {
    	   		chk[+aValue-1].checked='checked' ;
    	   	}
    	   }
     
  $('aView').innerHTML=$('filename').value ;
  
   function checkMode() {
	   var chk =  document.forms[0].typeMode ;
	   if (chk[0].checked) {
		   showTable("exportXml", true ) ; 
		   showTable("importXml", false ) ; 
		   showTable("workData", false ) ; 
		   showTable("updateData", false ) ; 
		   
	   } else if (chk[1].checked) {
		   showTable("exportXml", false ) ; 
		   showTable("importXml", true ) ; 
		   showTable("workData", false ) ; 
		   showTable("updateData", false ) ; 
	   } else if (chk[2].checked){
		   showTable("exportXml", false ) ; 
		   showTable("importXml", false ) ; 
		   showTable("workData", true ) ; 
		   showTable("updateData", false ) ; 
	   } else {
		   showTable("exportXml", false ) ; 
		   showTable("importXml", false ) ; 
		   showTable("workData", false ) ; 
		   showTable("updateData", true ) ; 
	   }
   }
   function showTable(aTableId, aCanShow ) {
		try {
			$(aTableId).style.display = aCanShow ? 'table' : 'none' ;
		} catch (e) {
			try{
			$(aTableId).style.display = aCanShow ? 'block' : 'none' ;
			}catch(e) {}
		}	
	}
	checkMode() ;
	function setSnilsDoctor(aIdBedFund,aSnils) {
		var idBed = aIdBedFund.split("#") ;
		if (+idBed[0]>0) {} else {
			alert("Не заведен СЛО по госпитализации!!!");
			return "" ;
		}
		var snils = idBed.length>1 ? idBed[1]:aSnils!=null?aSnils:""; 
		snils = prompt("Введите СНИЛС врача:",snils!=null?snils:"");
		if (snils!=null && snils.length==14) {
			HospitalMedCaseService.updateTable('BedFund','id',idBed[0],'snilsDoctorDirect263',snils,'',{
	  			callback: function(aResult) {
	  				window.location.reload() ;
	  			}
	  		}) ;
		} else {
			if (confirm("Вы хотите ввести СНИЛС врача еще раз?")) {
				setSnilsDoctor(aIdBedFund,snils) ;
			}
		}
	}
	function setDeniedByHDF(aHDFid,aFrm,aDenied) {
		var frm=document.forms[aFrm] ;
		var val = getCheckedRadio(frm,aDenied) ;
		HospitalMedCaseService.updateTable('HospitalDataFond','id',aHDFid,'deniedHospital',val,'',{
  			callback: function(aResult) {
  				window.location.reload() ;
  			}
  		}) ;
	}
	function setHospByHDF(aHDFid,aSls) {
		HospitalMedCaseService.updateTable('HospitalDataFond','id',aHDFid,'hospitalMedCase_id',aSls,'',{
  			callback: function(aResult) {
  				window.location.reload() ;
  			}
  		}) ;
	}
    </script>
	<% 
	String period = request.getParameter("period") ;
	String periodTo = request.getParameter("periodTo") ;
	String numberReestr = request.getParameter("numberReestr") ;
	if (periodTo==null ||periodTo.equals("")) periodTo=period ;
	request.setAttribute("periodTo", periodTo) ;
	boolean isCkeck = true ;
	if (period==null ||period.equals("")) isCkeck=false ;
	if (typeLpu!=null && typeLpu.equals("1")) {
		request.setAttribute("lpuSql", " and hdf.directlpucode='"+numberReestr+"'");
		request.setAttribute("lpuSlsSql", " and lpu.codef='"+numberReestr+"'");
	} else if (typeLpu!=null && typeLpu.equals("2")) {
		request.setAttribute("lpuSql", " and hdf.directlpucode!='"+numberReestr+"'");
		request.setAttribute("lpuSlsSql", " and lpu.codef!='"+numberReestr+"'");
	}
	if (typeEmergency!=null && typeEmergency.equals("1")) {
		request.setAttribute("emergencySql", " and (hdf.formHelp='1' or hdf.formHelp='2')");
		request.setAttribute("emergencySlsSql", " and sls.emergency='1'");
	} else if (typeEmergency!=null && typeEmergency.equals("2")) {
		request.setAttribute("emergencySql", " and (hdf.formHelp='3')");
		request.setAttribute("emergencySlsSql", " and (sls.emergency is null or sls.emergency='0')");
	}
	if (typeMode!=null && typeMode.equals("1")) {if (isCkeck && request.getAttribute("listError")!=null){%>
	<msh:table name="listError" action="javascript:void(0)" idField="1">
			<msh:tableColumn property="sn" columnName="#"/>
			<msh:tableColumn property="1" columnName="Дата напр."/>
			<msh:tableColumn property="2" columnName="Форма помощи."/>
			<msh:tableColumn property="3" columnName="Код ЛПУ напр."/>
			<msh:tableColumn property="4" columnName="Код ЛПУ куда напр."/>
			<msh:tableColumn property="5" columnName="Вид полиса"/>
			<msh:tableColumn property="6" columnName="Серия полиса"/>
			<msh:tableColumn property="7" columnName="Номер полиса"/>
			<msh:tableColumn property="8" columnName="СМО"/>
			<msh:tableColumn property="9" columnName="ОГРН"/>
			<msh:tableColumn property="10" columnName="ОКАТО"/>
			<msh:tableColumn property="11" columnName="Фамилия"/>
			<msh:tableColumn property="12" columnName="Имя"/>
			<msh:tableColumn property="13" columnName="Отчество"/>
			<msh:tableColumn property="14" columnName="Пол"/>
			<msh:tableColumn property="15" columnName="Дата рождения"/>
			<msh:tableColumn property="16" columnName="Телефон"/>
			<msh:tableColumn property="17" columnName="Диагноз"/>
			<msh:tableColumn property="18" columnName="Профиль коек"/>
			<msh:tableColumn property="19" columnName="СНИЛС врача"/>
	</msh:table>
	
	<% }
	} else if (typeMode!=null && typeMode.equals("3") && isCkeck) {%>
	<%     if (typeView1!=null && (typeView1.equals("1") || typeView1.equals("2")||typeView1.equals("3"))) {
		
		%>
			<table>
	<tr>
		
		<%if (typeView1!=null && (typeView1.equals("1") || typeView1.equals("3"))) {
			out.println("<th>БЕЗ НАПРАВЛЕНИЙ ГОСПИТАЛИЗАЦИИ</th>");
		%>
		
		<ecom:webQuery name="table1" nativeSql="select sls.id as slsid
,oml.name as omlname		
,ss.code as sscode,pat.lastname||' '||pat.firstname||' '||pat.middlename as patmiddlename
,pat.birthday as birthday
, case when sls.emergency='1' then 'экстренно' else 'планово' end as emergency
,sls.dateStart as slsdatestart,sls.dateFinish as slsdatefinish
,ml.name as mlname
,vbt.name as vbtname
,vss.name as vssname
,(select list(mkb.code) from diagnosis diag 
left join VocIdc10 mkb on mkb.id=diag.idc10_id 
left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationtype_id
where diag.medcase_id=slo.id and vpd.code='1' and vdrt.code = '4')
,bf.id||''','''||coalesce(bf.snilsDoctorDirect263,'') as bfid
,bf.snilsDoctorDirect263
,sls.id||''','''||to_char(sls.dateStart,'dd.mm.yyyy')||''','''||pat.lastname||''','''||pat.firstname||''','''||pat.middlename
	||''','''||to_char(pat.birthday,'dd.mm.yyyy')||''',''1'',''1' as snarp
from medcase sls 
left join MisLpu lpu on lpu.id=sls.lpu_id
left join MedCase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase'
left join BedFund bf on bf.id=slo.bedFund_id
left join MisLpu ml on ml.id=slo.department_id
left join VocBedType vbt on vbt.id = bf.bedType_id
left join VocBedSubType vbst on vbst.id=bf.bedSubType_id
left join HospitalDataFond hdf on hdf.hospitalMEdCase_id=sls.id
left join StatisticStub ss on ss.id=sls.statisticStub_id
left join Patient pat on pat.id=sls.patient_id
left join VocServiceStream vss on vss.id=sls.serviceStream_id
left join MisLpu oml on oml.id=sls.orderLpu_id
where sls.dtype='HospitalMedCase' and sls.datestart between to_date('${param.period}','dd.mm.yyyy') and to_date('${periodTo}','dd.mm.yyyy')
and sls.deniedHospitalizating_id is null
and (slo.id is null or slo.prevMedCase_id is null) and (vbst.id is null or vbst.code='1')
and vss.code in ('OBLIGATORYINSURANCE','OTHER')
and hdf.hospitalmedcase_id is null

${lpuSlsSql} ${emergencySlsSql}
		"/>
		<% }
		if (typeView1!=null && (typeView1.equals("2")||typeView1.equals("3"))) {
			out.print("<th>НАПРАВЛЕНИЯ БЕЗ ГОСПИТАЛИЗАЦИЙ</th>") ;
		%>
		<ecom:webQuery name="table2" nativeSql="
	select hdf.id,hdf.numberfond,hdf.lastname||' '||hdf.firstname||' '||hdf.middlename as f3io
	,hdf.birthday,hdf.formHelp
,hdf.profile,hdf.prehospdate,hdf.hospdate,hdf.directdate,hdf.snils as f10snils
,hdf.phone,hdf.diagnosis
,hdf.orderlpucode
,hdf.directlpucode
,hdf.statcard as f15
,hdf.deniedHospital
,hdf.id||''','''||coalesce(''||hdf.deniedHospital,'') as idden
,hdf.id||''','''||to_char(coalesce(hdf.prehospdate,hdf.hospdate),'dd.mm.yyyy')||''','''||hdf.lastname||''','''||hdf.firstname||''','''||hdf.middlename
	||''','''||to_char(hdf.birthday,'dd.mm.yyyy')||''',''1'',''1' as idforsls

from HospitalDataFond hdf
where hdf.hospitalMedCase_id is null and (case when  hdf.isTable4 ='1' then '1' when hdf.IsTable4='1' then '1' else null end) is null
${lpuSql} ${emergencySql}

order by hdf.lastname,hdf.firstname,hdf.middlename,hdf.id
	"/>
	<%} %>
	
	</tr> 
	<tr>
	<%if (typeView1!=null && (typeView1.equals("1") || typeView1.equals("3"))) {
			
		%>
		<td valign="top">
		<msh:table name="table1" action=" javascript:void(0)" idField="1">
			<msh:tableButton property="15" buttonFunction="showDiag263napr" buttonName="Установить соответствие с неопределенными направлениями" buttonShortName="Направление"/>
			<msh:tableButton property="13" buttonFunction="setSnilsDoctor" buttonName="Установить СНИЛС врача" buttonShortName="Уст. СНИЛС"/>
			<msh:tableColumn property="14" columnName="СНИЛС врача для генерации напр. для план. СЛС"/>
			<msh:tableColumn property="sn" columnName="#"/>
			<msh:tableColumn property="2" columnName="Напр. ЛПУ"/>
			<msh:tableColumn property="3" columnName="№ИБ"/>
			<msh:tableColumn property="4" columnName="ФИО пациента"/>
			<msh:tableColumn property="5" columnName="Дата рождения"/>
			<msh:tableColumn property="6" columnName="Показания"/>
			<msh:tableColumn property="7" columnName="Дата госп."/>
			<msh:tableColumn property="8" columnName="Дата выписки"/>
			<msh:tableColumn property="9" columnName="Отделение"/>
			<msh:tableColumn property="10" columnName="Профиль коек"/>
			<msh:tableColumn property="11" columnName="Поток обслуживания"/>
			<msh:tableColumn property="12" columnName="Диагноз"/>
		</msh:table>
	</td>
	<% }
		if (typeView1!=null && (typeView1.equals("2")||typeView1.equals("3"))) {
		%>
	<td valign="top">
	
		<msh:table name="table2" action=" javascript:void(0)" idField="1">
			<msh:tableButton property="18" buttonFunction="showDiag263sls" buttonName="Установить соответствие с неопределенной госпитализацией" buttonShortName="СЛС"/>
			<msh:tableButton property="17" buttonFunction="showDiag263denied" buttonName="Установить отказ" buttonShortName="Отказ"/>
			<msh:tableColumn property="16" columnName="Код отказа"/>
			<msh:tableColumn property="sn" columnName="#"/>
			<msh:tableColumn property="2" columnName="№напр. фонда"/>
			<msh:tableColumn property="3" columnName="ФИО"/>
			<msh:tableColumn property="4" columnName="Дата рождения"/>
			<msh:tableColumn property="5" columnName="Показания"/>
			<msh:tableColumn property="6" columnName="Профиль"/>
			<msh:tableColumn property="7" columnName="Дата пред."/>
			<msh:tableColumn property="8" columnName="Дата госп."/>
			<msh:tableColumn property="9" columnName="Дата направления"/>
			<msh:tableColumn property="10" columnName="СНИЛС врача"/>
			<msh:tableColumn property="11" columnName="Телефон пациента"/>
			<msh:tableColumn property="12" columnName="Диагноз"/>
			<msh:tableColumn property="13" columnName="Напр.ЛПУ"/>
			<msh:tableColumn property="14" columnName="Куда напр.ЛПУ"/>
			
		</msh:table>	
		</td>
		<%} %>
		</tr>
		</table>
		<%       }  else  if (typeView1!=null && typeView1.equals("4")) {%>
		<ecom:webQuery name="table1" nativeSql="select sls.id as slsid
,oml.name as omlname		
,ss.code as sscode,pat.lastname as patlastname,pat.firstname as patfirstname
,pat.middlename as patmiddlename
, case when sls.emergency='1' then 'экстренно' else 'планово' end as emergency
,sls.dateStart as slsdatestart,sls.dateFinish as slsdatefinish
,ml.name as mlname
,vbt.name as vbtname
,vss.name as vssname
,(select list(mkb.code) from diagnosis diag 
left join VocIdc10 mkb on mkb.id=diag.idc10_id 
left join VocPriorityDiagnosis vpd on vpd.id=diag.priority_id
left join VocDiagnosisRegistrationType vdrt on vdrt.id=diag.registrationtype_id
where diag.medcase_id=slo.id and vpd.code='1' and vdrt.code = '4')
,vbst.name as vbstname
,sls.id||''','''||coalesce(slo.id,0) ss 
from medcase sls 
left join MisLpu lpu on lpu.id=sls.lpu_id
left join MedCase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase'
left join diagnosis d on slo.id = d.medcase_id
left join vocdiagnosisregistrationtype vdrt on vdrt.id=d.registrationtype_id
left join vocprioritydiagnosis vpd on vpd.id=d.priority_id
left join BedFund bf on bf.id=slo.bedFund_id
left join MisLpu ml on ml.id=slo.department_id
left join VocBedType vbt on vbt.id = bf.bedType_id
left join VocBedSubType vbst on vbst.id=bf.bedSubType_id
left join HospitalDataFond hdf on hdf.hospitalMEdCase_id=sls.id
left join StatisticStub ss on ss.id=sls.statisticStub_id
left join Patient pat on pat.id=sls.patient_id
left join VocServiceStream vss on vss.id=sls.serviceStream_id
left join MisLpu oml on oml.id=sls.orderLpu_id
where sls.dtype='HospitalMedCase' and sls.datestart between to_date('${param.period}','dd.mm.yyyy') and to_date('${periodTo}','dd.mm.yyyy')
and sls.deniedHospitalizating_id is null
and (slo.id is null or slo.prevMedCase_id is null) and (vbst.id is null or vbst.code='1')
and vss.code in ('OBLIGATORYINSURANCE','OTHER')

and (case when  hdf.isTable3 ='1' then '1' when hdf.IsTable1='1' then '1' else null end) is not null
and (case when  hdf.isTable2 ='1' then '1' else null end) is null
${lpuSlsSql} ${emergencySqlSql}
group by sls.id,slo.id,oml.name ,ss.code ,pat.lastname ,pat.firstname 
,pat.middlename , sls.emergency,sls.dateStart ,sls.dateFinish
,ml.name ,vbt.name ,vss.name ,vbst.name
having count(case when (vdrt.code='3' or vdrt.code='4') and (vpd.code='1') and d.idc10_id is not null then 1 else null end)=0
		"/>
		<msh:table name="table1" action="javascript:void" idField="1">
			<msh:tableButton property="14" buttonFunction="alert" buttonName="Открыть в новой вкладке на редактирование" buttonShortName="СЛО"/>
			<msh:tableColumn property="sn" columnName="#"/>
			<msh:tableColumn property="2" columnName="№ИБ"/>
			<msh:tableColumn property="3" columnName="Фамилия"/>
			<msh:tableColumn property="4" columnName="Имя"/>
			<msh:tableColumn property="5" columnName="Отчество"/>
			<msh:tableColumn property="6" columnName="Показания"/>
			<msh:tableColumn property="7" columnName="Дата госп."/>
			<msh:tableColumn property="8" columnName="Дата выписки"/>
			<msh:tableColumn property="9" columnName="Отделение"/>
			<msh:tableColumn property="10" columnName="Профиль коек"/>
			<msh:tableColumn property="11" columnName="Поток обслуживания"/>
			<msh:tableColumn property="12" columnName="Диагноз"/>
		</msh:table>	
		<%       }  else  if (typeView1!=null && typeView1.equals("5")) {%>
		<ecom:webQuery name="table1" nativeSql="select bf.id||''','''||coalesce(bf.snilsDoctorDirect263,'') as id,bf.snilsDoctorDirect263
,list(distinct oml.name) as omlname		
,ml.name as mlname
,vbt.name as vbtname,count(sls.id) 
from medcase sls 
left join MisLpu lpu on lpu.id=sls.lpu_id
left join MedCase slo on slo.parent_id=sls.id and slo.dtype='DepartmentMedCase'
left join BedFund bf on bf.id=slo.bedFund_id
left join MisLpu ml on ml.id=slo.department_id
left join VocBedType vbt on vbt.id = bf.bedType_id
left join VocBedSubType vbst on vbst.id=bf.bedSubType_id
left join HospitalDataFond hdf on hdf.hospitalMEdCase_id=sls.id
left join StatisticStub ss on ss.id=sls.statisticStub_id
left join Patient pat on pat.id=sls.patient_id
left join VocServiceStream vss on vss.id=sls.serviceStream_id
left join MisLpu oml on oml.id=sls.orderLpu_id
where sls.dtype='HospitalMedCase' and sls.datestart between to_date('${param.period}','dd.mm.yyyy') and to_date('${periodTo}','dd.mm.yyyy')
and sls.deniedHospitalizating_id is null
and (slo.id is null or slo.prevMedCase_id is null) and (vbst.id is null or vbst.code='1')
and vss.code in ('OBLIGATORYINSURANCE','OTHER')

and (case when  hdf.isTable3 ='1' then '1' when hdf.IsTable1='1' then '1' else null end) is null
${lpuSlsSql} ${emergencySlsSql}
group by bf.id,bf.snilsDoctorDirect263,ml.name,vbt.name
		"/>
		<msh:table name="table1" action="javascript:void" idField="1">
			<msh:tableButton property="1" buttonFunction="setSnilsDoctor" buttonName="Установить СНИЛС врача" buttonShortName="Уст. СНИЛС"/>
			<msh:tableColumn property="sn" columnName="#"/>
			<msh:tableColumn property="2" columnName="СНИЛС врача"/>
			<msh:tableColumn property="3" columnName="Направившие ЛПУ"/>
			<msh:tableColumn property="4" columnName="Отделение"/>
			<msh:tableColumn property="5" columnName="Профиль"/>
			<msh:tableColumn property="6" columnName="Кол-во случаев"/>
		</msh:table>	
		<%       } %>
	<% } %>
  </tiles:put>
</tiles:insert>