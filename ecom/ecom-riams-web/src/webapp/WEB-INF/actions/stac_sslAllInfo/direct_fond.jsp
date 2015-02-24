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
	String typeEmergency=ActionUtil.updateParameter("HospitalDirectDataInFond","typeEmergency","1", request) ;
	String typeLpu=ActionUtil.updateParameter("HospitalDirectDataInFond","typeLpu","1", request) ;
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
        <td onclick="this.childNodes[1].checked='checked';checkMode()">
        	<input type="radio" name="typeMode" value="1"> Формирования Xml
        </td>
	        <td onclick="this.childNodes[1].checked='checked';checkMode()" colspan="2">
	        	<input type="radio" name="typeMode" value="2"> Импорт результата
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';checkMode()" colspan="2">
	        	<input type="radio" name="typeMode" value="3"> Работа с данными
	        </td>
	        <td onclick="this.childNodes[1].checked='checked';checkMode()" colspan="2">
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
	        	<input type="radio" name="typeView1" value="1"> СЛС, по которым не сформировано направление и  направления, по которым не определены госпитализации 
	        </td>
	   </msh:row>
	   <msh:row>
	        <td></td>
	        <td onclick="this.childNodes[1].checked='checked';" colspan="4">
	        	<input type="radio" name="typeView1" value="2">  отделения, по которым не проставлен диагноз 
	        </td>
	        <!-- 
	        <td onclick="this.childNodes[1].checked='checked';" colspan="3">
	        	<input type="radio" name="typeView1" value="3">  ошибочные направления (изменен поток или тип стационара) 
	        </td> -->
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
    	   	//alert(aField+" "+aValue+" "+aMax+" "+chk) ;
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
		   //alert("exportXml") ;
		   showTable("exportXml", true ) ; 
		   showTable("importXml", false ) ; 
		   showTable("workData", false ) ; 
		   showTable("updateData", false ) ; 
		   
	   } else if (chk[1].checked) {
		   //alert("importXml") ;
		   showTable("exportXml", false ) ; 
		   showTable("importXml", true ) ; 
		   showTable("workData", false ) ; 
		   showTable("updateData", false ) ; 
	   } else if (chk[2].checked){
		   //alert("workData") ;
		   showTable("exportXml", false ) ; 
		   showTable("importXml", false ) ; 
		   showTable("workData", true ) ; 
		   showTable("updateData", false ) ; 
	   } else {
		   //alert("workData") ;
		   showTable("exportXml", false ) ; 
		   showTable("importXml", false ) ; 
		   showTable("workData", false ) ; 
		   showTable("updateData", false ) ; 
	   }
   }
   function showTable(aTableId, aCanShow ) {
		//alert(aTableId) ;
		try {
			//alert( aCanShow ? 'table' : 'none') ;
			$(aTableId).style.display = aCanShow ? 'table' : 'none' ;
		} catch (e) {
			// for IE
			//alert(aCanShow ? 'block' : 'none') ;
			try{
			$(aTableId).style.display = aCanShow ? 'block' : 'none' ;
			}catch(e) {}
		}	
	}
	checkMode() ;
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
	} else if (typeLpu!=null && typeLpu.equals("2")) {
		request.setAttribute("lpuSql", " and hdf.directlpucode!='"+numberReestr+"'");
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
	<%     if (typeView1!=null && typeView1.equals("1")) {
		%>
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
		"/>
		<ecom:webQuery name="table2" nativeSql="
	select hdf.numberfond,hdf.lastname,hdf.firstname,hdf.middlename,hdf.birthday,hdf.emergency
,hdf.profile,hdf.prehospdate,hdf.hospdate,hdf.directdate,hdf.snils,hdf.diagnosis
,hdf.phone,hdf.statcard,hdf.seriespolicy,hdf.numberpolicy,hdf.formhelp,hdf.orderlpucode
,hdf.directlpucode
from HospitalDataFond hdf
where hdf.hospitalMedCase_id is null and (case when  hdf.isTable4 ='1' then '1' when hdf.IsTable4='1' then '1' else null end) is null
${lpuSql}
	"/>
	<table>
	<tr>
	<th>БЕЗ НАПРАВЛЕНИЙ</th><th>НАПРАВЛЕНИЯ БЕЗ ГОСПИТАЛИЗАЦИЙ</th>
	</tr> 
	<tr>
		<td valign="top">
		<msh:table name="table1" action="javascript:void" idField="1">
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
	</td>
	<td valign="top">
	
		<msh:table name="table2" action="javascript:void" idField="1">
			<msh:tableColumn property="sn" columnName="#"/>
			<msh:tableColumn property="2" columnName="№напр. фонда"/>
			<msh:tableColumn property="3" columnName="Фамилия"/>
			<msh:tableColumn property="4" columnName="Имя"/>
			<msh:tableColumn property="5" columnName="Отчество"/>
			<msh:tableColumn property="6" columnName="Дата рождения"/>
			<msh:tableColumn property="7" columnName="Профиль"/>
			<msh:tableColumn property="8" columnName="Дата пред."/>
			<msh:tableColumn property="9" columnName="Дата госп."/>
			<msh:tableColumn property="10" columnName="Дата направления"/>
			<msh:tableColumn property="11" columnName="СНИЛС врача"/>
			<msh:tableColumn property="13" columnName="Телефон пациента"/>
			<msh:tableColumn property="12" columnName="Диагноз"/>
			<msh:tableColumn property="14" columnName="Напр.ЛПУ"/>
			<msh:tableColumn property="15" columnName="КУда напр.ЛПУ"/>
		</msh:table>	
		</td>
		</tr>
		</table>
		<%       }  else  if (typeView1!=null && typeView1.equals("2")) {%>
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
${lpuSql}
group by sls.id,slo.id,oml.name ,ss.code ,pat.lastname ,pat.firstname 
,pat.middlename , sls.emergency,sls.dateStart ,sls.dateFinish
,ml.name ,vbt.name ,vss.name ,vbst.name
having count(case when (vdrt.code='3' or vdrt.code='4') and (vpd.code='1') and d.idc10_id is not null then 1 else null end)=0
		"/>
		<msh:table name="table1" action="javascript:void" idField="1">
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
		<%       } %>
	<% } %>
  </tiles:put>
</tiles:insert>