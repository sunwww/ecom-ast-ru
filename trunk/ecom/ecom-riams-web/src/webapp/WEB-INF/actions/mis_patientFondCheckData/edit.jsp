<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page = '/WEB-INF/tiles/mainLayout.jsp' flush = 'true'>

  <tiles:put name="body" type="string">
    <msh:form action="entitySaveGoView-mis_patientFondCheckData.do" defaultField="id" guid="10826cd9-7e71-480c-9d53-c96e6805ce24">
      <msh:hidden property="id" guid="332d968f-182e-4108-b1de-df71738d7b8a" />
      <msh:panel guid="070b9d1e-c50f-4423-9d72-274f6b1dc045">
        <msh:row guid="numberRow123">
          <msh:textField property="comment" label="Примечание" guid="9f8be45a-773a-4ca4-a12c-8f50d63e3ffc" />
        </msh:row>
        <msh:row>
          <msh:textField property="startDate" label="Дата проверки"/>
        </msh:row>
        <msh:row>
        <msh:section>
        <ecom:webQuery name="dataList" nameFldSql="dataListSQL" nativeSql="select pf.id, pf.lastname ||' '|| pf.firstname||' '||pf.middlename||' г.р. '||to_char(pf.birthday,'dd.MM.yyyy') as f2
        ,'ЕНП '||case when pf.commonnumber!='' then pf.commonnumber else '' end || case when pf.snils!='' then ', СНИЛС '||pf.snils else '' end as f3_fondPat
        ,'ЕНП '||case when p.commonnumber!='' then p.commonnumber else '' end|| case when p.snils!='' then ', СНИЛС '||p.snils else '' end as f4_medPat
      , (select v.name from vocidentitycard v where v.omccode=pf.documenttype)||' '|| pf.documentseries||' '||pf.documentnumber as f5_fondDoc
, vic.name||' '|| p.passportseries||' '||p.passportnumber as f6_medDoc
,case when (pf.policyseries is not null and pf.policyseries!='')then 'Серия '||pf.policyseries else ''end  
||case when (pf.policynumber is not null and pf.policynumber!='') then ' №'||pf.policynumber end ||' СК '||case when pf.companycode='15' then 'МАКС' when pf.companycode='7' then 'СОГАЗ' else (select s.name from reg_ic s where omccode=pf.companycode) end as f7_fondPolicy
,case when (mp.series is not null and mp.series!='')then 'Серия '||mp.series else ''end 
||case when (mp.polnumber is not null and mp.polnumber!='') then ' №'||mp.polnumber end ||' СК '||case when ri.omccode='15' then 'МАКС' when ri.omccode='7' then 'СОГАЗ' else ri.name end as f8_medPolicy
,'Тип '|| pf.attachedtype ||' c '|| to_char(pf.attacheddate,'dd.MM.yyyy') as f9_fondAtt
,'Тип '|| vat.code ||' c '|| to_char(att.dateFrom,'dd.MM.yyyy') as f10_medAtt
,case when (select v.name from vocidentitycard v where v.omccode=pf.documenttype)||' '|| pf.documentseries||' '||pf.documentnumber=vic.name||' '|| p.passportseries||' '||p.passportnumber then null else pf.id end as f11_changeDoc
,case when case when (pf.policyseries is not null and pf.policyseries!='')then pf.policyseries else ''end 
||'#'||case when (pf.policynumber is not null and pf.policynumber!='') then pf.policynumber else '' end ||'#'|| pf.companycode 
=case when (mp.series is not null and mp.series!='')then mp.series else '' end 
||'#'||case when (mp.polnumber is not null and mp.polnumber!='') then mp.polnumber end ||'#'|| ri.omccode then null else pf.id end as f12_changePolicy
,case when case when (pf.commonnumber is not null) then pf.commonnumber else '' end ||'#'|| case when pf.snils is not null then pf.snils else '' end=case when p.commonnumber is not null then p.commonnumber else '' end||'#'|| case when p.snils is not null then p.snils else '' end then null else pf.id end as f13_changePat
,case when pf.lpuattached=(select sc.keyvalue from softconfig sc where key='DEFAULT_LPU_OMCCODE') and (pf.attacheddate||'#'||pf.attachedtype)!=(att.dateFrom||'#'||vat.code) then pf.id else null end as f14_changeAttachment
from patientfond pf 
left join patient p on p.id=pf.patient_id
left join vocidentitycard vic on vic.id=p.passporttype_id
left join medpolicy mp on mp.patient_id=p.id
left join vocmedpolicyomc vmpo on vmpo.id=mp.type_id
left join reg_ic ri on ri.id=mp.company_id
left join lpuattachedbydepartment att on att.patient_id=p.id and att.dateto is null
left join vocattachedtype vat on vat.id=att.attachedtype_id
where pf.checkTime_id=${param.id}
and (mp.id is null or (mp.dtype='MedPolicyOmc' and mp.id=(select max(id) from medpolicy where patient_id=p.id)))
and (att.id is null or att.id=(select max(id) from lpuattachedbydepartment where patient_id=pf.patient_id)) 
order by pf.lastname, pf.firstname, pf.middlename
 "/>
        	<msh:table name="dataList" action="javascript:void(0)" idField="1">
        		<msh:tableColumn columnName="#" property="sn"/>
	        	<msh:tableColumn columnName="Пациент" property="2"/>
	        	<msh:tableButton property="13" hideIfEmpty="true" buttonFunction="updatePatient" addParam="1,0,0,0" buttonShortName="O" buttonName="Обновить данные пациента" />
	        	<msh:tableColumn columnName="Данные МедОС" property="4"/>
	        	<msh:tableColumn columnName="Данные ФОМС" property="3"/>
	        	<msh:tableButton property="11" hideIfEmpty="true" buttonFunction="updatePatient" addParam="0,1,0,0" buttonShortName="O" buttonName="Обновить данные документа" />
	        	<msh:tableColumn columnName="Пасп. данные МедОС" property="6"/>  	
	        	<msh:tableColumn columnName="Пасп. данные ФОМС" property="5"/>  	
	        	<msh:tableButton property="12" hideIfEmpty="true" buttonFunction="updatePatient" addParam="0,0,1,0" buttonShortName="O" buttonName="Обновить данные полиса ОМС" />
	        	<msh:tableColumn columnName="Полис ОМС МедОС" property="8"/>  	
	        	<msh:tableColumn columnName="Полис ОМС ФОМС" property="7"/>  	
	        	<msh:tableButton property="14" hideIfEmpty="true" buttonFunction="updatePatient" addParam="0,0,0,1" buttonShortName="O" buttonName="Обновить данные прикрепления" />
	        	<msh:tableColumn columnName="Прикрепление МедОС" property="10"/>  	
	        	<msh:tableColumn columnName="Прикрепление ФОМС"  property="9"/>  	
        	</msh:table>
        </msh:section>
        </msh:row>
        <msh:row>
        </msh:row>
      </msh:panel>
       <msh:submitCancelButtonsRow colSpan="2" guid="a332e241-83f4-4e61-ad6f-d0f69cc6076e" />
    </msh:form>

  </tiles:put>
  <script type="text/javascript" src="./dwr/interface/PatientService.js"></script>
    <tiles:put name="javascript" type="string">
    <script type="text/javascript">
	    function updatePatient(aPatientFondId, updatePat, updateDoc, updatePol, updateAtt) {
	    	PatientService.updatePatientByAutoFondOneRecord(aPatientFondId, $('id').value, ""+updatePat=='1'?true:false, ""+updateDoc=='1'?true:false, ""+updatePol=='1'?true:false, ""+updateAtt=='1'?true:false,{
	    		callback: function(aResult) {
	    			alert(aResult);
	    			window.document.location.reload();
	    		}
	    	});
	    }
	    </script>
	    </tiles:put>
</tiles:insert>

