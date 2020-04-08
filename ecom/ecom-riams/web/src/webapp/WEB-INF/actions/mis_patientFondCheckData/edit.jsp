<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page = '/WEB-INF/tiles/mainLayout.jsp' flush = 'true'>
  <tiles:put name="body" type="string">
    <msh:form action="entitySaveGoView-mis_patientFondCheckData.do" defaultField="id">
      <msh:hidden property="id" />
      <msh:panel>
        <msh:ifFormTypeIsCreate formName="mis_patientFondCheckDataForm">
        <msh:row>
	        <td class="label" title="Обновлять данных пациентов (typePatient)" colspan="1"><label for="typePatientName" id="typePatientLabel">Обновлять данные:</label></td>
	        <td onclick="this.childNodes[1].checked='checked';showHideDiv('divPatientListDiv',0);">
	        	<input type="radio" name="typePatient" value="1">  всех пациентов
	        </td>	        
	        <td onclick="this.childNodes[1].checked='checked';showHideDiv('divPatientListDiv',0);">
	        	<input type="radio" name="typePatient" value="2"> только прикрепленных к ЛПУ
	        </td>
	        <td onclick="this.childNodes[1].checked='checked'; showHideDiv('divPatientListDiv',1);">
	        	<input type="radio" name="typePatient" value="3"> конкретных пациентов
	        </td>
        </msh:row>
       
        <msh:row>
        
        
        	<td colspan="5">
        	 <div id='divPatientListDiv' style='display: none'>
        	Введите ID пациентов (через запятую)
        	
        		<input type='text' name='patientList' id='patientList' size="50" >
        	</div>	
        	</td>
        	 
        </msh:row>
       
        <msh:row>
        <msh:checkBox property="needUpdatePatient" label="Автоматически обновлять данные пациента"/>
        <msh:checkBox property="needUpdateDocument" label="Автоматически обновлять данные документов"/>
        </msh:row>
        <msh:row>
        <msh:checkBox property="needUpdatePolicy" label="Автоматически обновлять данные мед. полиса"/>
        <msh:checkBox property="needUpdateAttachment" label="Автоматически обновлять данные прикрепления"/>
        </msh:row>
        </msh:ifFormTypeIsCreate>
        <msh:ifFormTypeAreViewOrEdit formName="mis_patientFondCheckDataForm">
        <msh:row>
        <msh:checkBox property="needUpdatePatient" label="Автоматически обновлять данные пациента" viewOnlyField="true"/>
        <msh:checkBox property="needUpdateDocument" label="Автоматически обновлять данные документов" viewOnlyField="true"/>
        </msh:row>
        <msh:row>
        <msh:checkBox property="needUpdatePolicy" label="Автоматически обновлять данные мед. полиса" viewOnlyField="true"/>
        <msh:checkBox property="needUpdateAttachment" label="Автоматически обновлять данные прикрепления" viewOnlyField="true"/>
        </msh:row>
        
        <msh:row>
       
          <msh:textField property="startDate" label="Дата проверки" viewOnlyField="true"/>
        
        </msh:row>
        
        <msh:row>
      	    <td onclick="this.childNodes[1].checked='checked';checkMode()" colspan="2">
	        	<input type="radio" name="typeView" value="1"> Список с расхождениями
	        </td>
      	    <td onclick="this.childNodes[1].checked='checked';checkMode()" colspan="2">
	        	<input type="radio" name="typeView" value="2"> Список неидентифицированных
	        </td>
      </msh:row>
    </msh:ifFormTypeAreViewOrEdit>
      <msh:ifFormTypeIsCreate formName="mis_patientFondCheckDataForm">
       	<msh:submitCancelButtonsRow colSpan="2" />
       </msh:ifFormTypeIsCreate>
       </msh:panel>
    </msh:form>
<msh:ifFormTypeIsView formName="mis_patientFondCheckDataForm">
       <div id="diffRecords">
       <section title="Список пациентов с расхождениями">
        <ecom:webQuery name="dataList" nameFldSql="dataListSQL" nativeSql="select pf.id, pf.lastname ||' '|| pf.firstname||' '||pf.middlename||' г.р. '||to_char(pf.birthday,'dd.MM.yyyy') as f2
        ,'ЕНП '||case when pf.commonnumber!='' then pf.commonnumber else '' end || case when pf.snils!='' then ', СНИЛС '||pf.snils else '' end as f3_fondPat
        ,'ЕНП '||case when p.commonnumber!='' then p.commonnumber else '' end|| case when p.snils!='' then ', СНИЛС '||p.snils else '' end as f4_medPat
      , (select v.name from vocidentitycard v where v.omccode=pf.documenttype)||' '|| pf.documentseries||' '||pf.documentnumber as f5_fondDoc
, vic.name||' '|| p.passportseries||' '||p.passportnumber as f6_medDoc
,case when (pf.policyseries is not null and pf.policyseries!='')then 'Серия '||pf.policyseries else ''end  
||case when (pf.policynumber is not null and pf.policynumber!='') then ' №'||pf.policynumber end ||' СК '||case when pf.companycode='15' then 'МАКС' when pf.companycode='7' then 'СОГАЗ' else (select s.name from reg_ic s where omccode=pf.companycode) end as f7_fondPolicy
,case when (mp.series is not null and mp.series!='')then 'Серия '||mp.series else ''end 
||case when (mp.polnumber is not null and mp.polnumber!='') then ' №'||mp.polnumber end ||' СК '||case when ri.omccode='15' then 'МАКС' when ri.omccode='7' then 'СОГАЗ' else ri.name end as f8_medPolicy
,case when pf.lpuattached=(select sc.keyvalue from softconfig sc where key='DEFAULT_LPU_OMCCODE') then 'Тип '|| pf.attachedtype ||' c '|| to_char(pf.attacheddate,'dd.MM.yyyy') else 'ЛПУ # '||pf.lpuattached||' тип '|| pf.attachedtype ||' c '|| to_char(pf.attacheddate,'dd.MM.yyyy') end as f9_fondAtt
,'Тип '|| vat.code ||' c '|| to_char(att.dateFrom,'dd.MM.yyyy') as f10_medAtt
,case when  pf.documenttype||'#'|| pf.documentseries||'#'||pf.documentnumber=vic.omccode||'#'|| p.passportseries||'#'||p.passportnumber then null else pf.id end as f11_changeDoc
,case when case when (pf.policyseries is not null and pf.policyseries!='')then pf.policyseries else ''end 
||'#'||case when (pf.policynumber is not null and pf.policynumber!='') then pf.policynumber else '' end ||'#'|| pf.companycode 
=case when (mp.series is not null and mp.series!='')then mp.series else '' end 
||'#'||case when (mp.polnumber is not null and mp.polnumber!='') then mp.polnumber end ||'#'|| ri.omccode then null else pf.id end as f12_changePolicy
,case when ((pf.commonnumber is null or pf.commonnumber='') and p.commonnumber!='') then null when ((pf.snils is null or pf.snils='') and p.snils!='') then null when case when (pf.commonnumber is not null) then pf.commonnumber else '' end ||'#'|| case when pf.snils is not null then pf.snils else '' end=case when p.commonnumber is not null then p.commonnumber else '' end||'#'|| case when p.snils is not null then p.snils else '' end then null else pf.id end as f13_changePat
,case when pf.lpuattached=(select sc.keyvalue from softconfig sc where key='DEFAULT_LPU_OMCCODE') and ((pf.attacheddate||'#'||pf.attachedtype)!=(att.dateFrom||'#'||vat.code) or att.id is null) then pf.id else null end as f14_changeAttachment
from patientfond pf 
left join patient p on p.id=coalesce(pf.patient_id,pf.patient)
left join vocidentitycard vic on vic.id=p.passporttype_id
left join medpolicy mp on mp.patient_id=p.id
left join vocmedpolicyomc vmpo on vmpo.id=mp.type_id
left join reg_ic ri on ri.id=mp.company_id
left join lpuattachedbydepartment att on att.patient_id=p.id and att.dateto is null
left join vocattachedtype vat on vat.id=att.attachedtype_id
where pf.checkTime_id=${param.id} 
and pf.isDifference='1' 
and (mp.id is null or (mp.dtype='MedPolicyOmc' and mp.id=(select max(id) from medpolicy where patient_id=p.id)))
and (att.id is null or att.id=(select max(id) from lpuattachedbydepartment where patient_id=pf.patient_id)) 
order by pf.lastname, pf.firstname, pf.middlename
 "/>
        	<msh:table name="dataList" action="javascript:void(0)" idField="1">
        		<msh:tableColumn columnName="#" property="sn"/>
	        	<msh:tableColumn columnName="Пациент" property="2"/>
	        	<msh:tableButton property="13" hideIfEmpty="true" buttonFunction="updatePatient" addParam="1,0,0,0);this.style.display='none';javascript:void(0" buttonShortName="O" buttonName="Обновить данные пациента" />
	        	<msh:tableColumn columnName="Данные МедОС" property="4"/>
	        	<msh:tableColumn columnName="Данные ФОМС" property="3"/>
	        	<msh:tableButton property="11" hideIfEmpty="true" buttonFunction="updatePatient" addParam="0,1,0,0);this.style.display='none';javascript:void(0" buttonShortName="O" buttonName="Обновить данные документа" />
	        	<msh:tableColumn columnName="Пасп. данные МедОС" property="6"/>  	
	        	<msh:tableColumn columnName="Пасп. данные ФОМС" property="5"/>  	
	        	<msh:tableButton property="12" hideIfEmpty="true" buttonFunction="updatePatient" addParam="0,0,1,0);this.style.display='none';javascript:void(0" buttonShortName="O" buttonName="Обновить данные полиса ОМС" />
	        	<msh:tableColumn columnName="Полис ОМС МедОС" property="8"/>  	
	        	<msh:tableColumn columnName="Полис ОМС ФОМС" property="7"/>  	
	        	<msh:tableButton property="14" hideIfEmpty="true" buttonFunction="updatePatient" addParam="0,0,0,1);this.style.display='none';javascript:void(0" buttonShortName="O" buttonName="Обновить данные прикрепления" />
	        	<msh:tableColumn columnName="Прикрепление МедОС" property="10"/>  	
	        	<msh:tableColumn columnName="Прикрепление ФОМС"  property="9"/>  	
        	</msh:table>
        </section>
  </div>
  <div id='notFoundRecords'>
            <msh:section title="Пациенты, не найденные в базе ФОМС">
        <ecom:webQuery name="dataList" nameFldSql="dataListSQL" nativeSql="
        select jpfc.patient_id, jpfc.lastname,jpfc.firstname,jpfc.middlename,to_char(jpfc.birthday,'dd.MM.yyyy')
from journalpatientfondcheck jpfc where jpfc.checkTime_id=${param.id} and jpfc.isFound='0' and jpfc.patient_id is not null 
order by jpfc.lastname, jpfc.firstname, jpfc.middlename
 "/>
        	<msh:table name="dataList" action="entityView-mis_patient.do" idField="1">
        		<msh:tableColumn columnName="#" property="sn"/>
	        	<msh:tableColumn columnName="Фамилия" property="2"/>
	        	<msh:tableColumn columnName="Имя" property="3"/>
	        	<msh:tableColumn columnName="Отчетство" property="4"/>
	        	<msh:tableColumn columnName="Дата рождения" property="5"/>
	        </msh:table>
        </msh:section>
  </div>
</msh:ifFormTypeIsView>
  </tiles:put>
<tiles:put name="javascript" type="string">
    
 <script type="text/javascript" src="./dwr/interface/PatientService.js"></script>
    <msh:ifFormTypeIsView formName="mis_patientFondCheckDataForm">
    <script type='text/javascript'>
     checkFieldUpdate('typeView','${typeView}',1) ;
     checkMode();
    function checkFieldUpdate(aField,aValue,aDefaultValue) {
  	   	eval('var chk =  document.forms[0].'+aField) ;
  	   	var aMax=chk.length ;
  	   	if ((+aValue)==0 || (+aValue)>(+aMax)) {
  	   		chk[+aDefaultValue-1].checked='checked' ;
  	   	} else {
  	   		chk[+aValue-1].checked='checked' ;
  	   	}
  	   }
    function checkMode() {
 	   var chk =  document.forms[0].typeView ;
 	   if (chk[0].checked) {
 		   showTable("diffRecords", true ) ;
 		   showTable("notFoundRecords", false ) ;
 		 } else if (chk[1].checked) {
 		   showTable("diffRecords", false ) ;
 		   showTable("notFoundRecords", true ) ;
 		 } 
 		 else {
 			 showTable("diffRecords", false ) ;
 	 		 showTable("notFoundRecords", false ) ;
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
    
    function updatePatient(aPatientFondId, updatePat, updateDoc, updatePol, updateAtt) {
	    	PatientService.updateDataByFondAutomatic(aPatientFondId, $('id').value, +updatePat==1, +updateDoc==1
                , +updatePol==1, +updateAtt==1);
	    }
	    </script>
 	</msh:ifFormTypeIsView>
    <msh:ifFormTypeIsCreate formName="mis_patientFondCheckDataForm">
    <script type="text/javascript">
    document.forms['mis_patientFondCheckDataForm'].action="javascript:checkAllPatients()";
	
     function showHideDiv (aName, aStatus) {
    	 if ($(aName)) {
             $(aName).style.display=aStatus==1 ? 'block' : 'none';
    	 }
     }
	 function checkAllPatients() {
		 var typePat= jQuery('input:radio[name=typePatient]:checked').val();

	    	PatientService.checkAllPatients($('needUpdatePatient').checked, 
	    		$('needUpdateDocument').checked, $('needUpdatePolicy').checked, 
	    		$('needUpdateAttachment').checked, typePat, $('patientList').value, {
	    		callback: function () {
	    			document.location="mis_patientFondCheckList.do";
	    		}
	    	});
	    		
	    	}
          checkFieldUpdate('typePatient','${typePatient}',2) ;
   function checkFieldUpdate(aField,aValue,aDefaultValue) {
  	   	eval('var chk =  document.forms[0].'+aField) ;
  	   	var aMax=chk.length ;
  	   	if ((+aValue)==0 || (+aValue)>(+aMax)) {
  	   		chk[+aDefaultValue-1].checked='checked' ;
  	   	} else {
  	   		chk[+aValue-1].checked='checked' ;
  	   	}
  	   }
   
  
    </script>
</msh:ifFormTypeIsCreate> 
	    </tiles:put>
</tiles:insert>

