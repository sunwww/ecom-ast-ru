<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib uri="/WEB-INF/mis.tld" prefix="mis" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainShortLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Случай стационарного лечения в отделении
    	  -->
    	  <style type="text/css">


            #clinicalDiagnosLabel, #clinicalMkbLabel, #clinicalActuityLabel {
                color: blue ;
            }
            #concomitantDiagnosLabel, #concomitantMkbLabel {
                color: green ;
            }

            #concludingDiagnosLabel, #concludingMkbLabel {
                color: black ;
            }
            #pathanatomicalDiagnosLabel, #pathanatomicalMkbLabel {
                color: red ;
            }

        </style>
    <msh:form action="/entityParentSaveGoView-stac_slo.do" title="<a href='entityParentView-stac_slo.do?id=${param.id}'>Случай лечения в отделении</a>" defaultField="transferDate" guid="be2c889f-ed1d-4a2b-9cda-9127e9d94885">
      <msh:hidden property="id" guid="d10f460a-e434-45a5-90f0-b0a7aed00ec6" />
      <msh:hidden property="prevMedCase" guid="710eb92b-fc3f-4390-b32df6837280" />
      <msh:hidden property="parent" guid="710eb92b-fc3f-4b44-9390-b32df6837280" />
      <msh:hidden property="patient" guid="9d908e88-e051-4d0a-8da6-3f5f4b226493" />
      <msh:hidden property="saveType" guid="bd322f07-c944-4587-a963-a09db2b93caf" />
      <msh:hidden property="lpuAndDate" guid="9cc5ff9f-b68c-423a-be34-50ebeecf4b18" />
      <msh:hidden property="lpu" guid="756525c0-3c91-41da-b2ba-27ebdbdc001b" />
      <msh:hidden property="dateFinish"/>
      <msh:hidden property="dischargeTime"/>
      <msh:panel guid="d1cd0310-bf53-4ce1-9dd5-06388b51ec01" colsWidth="5%,5%,5%">
      <msh:ifFormTypeAreViewOrEdit formName="stac_sloForm">
      	<msh:row >
      		<msh:label property="statCardBySLS" label="Номер стат.карты" labelColSpan="1"/>
      		<td colspan="2">
      		<msh:link action="entityParentListRedirect-stac_slo.do?id=${param.id}" roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/View">⇧Cписок СЛО</msh:link>
      		</td>
      	</msh:row>
      	</msh:ifFormTypeAreViewOrEdit>
        <msh:separator label="Переведен из отделения" colSpan="6" guid="d4313623-45ca-43cc-826d-bc1b66526744" />
        <msh:row guid="f244aba5-68fb-4ccc-9982-7b4480cca147">
          <msh:autoComplete viewAction="entityParentView-stac_slo.do" shortViewAction="entityShortView-stac_slo.do" parentId="stac_sloForm.parent" viewOnlyField="true"  vocName="sloBySls" property="prevMedCase" label="СЛО" fieldColSpan="6" horizontalFill="true" guid="109f7264-23b2-42c0-ba47-65d90747816c" size="30" />
        </msh:row>
        <msh:separator label="Поступление в отделение" colSpan="6" guid="d4313623-45ca-43cc-826d-bc1b66526744" />
        <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/NotEditAdmissionTime">
	        <msh:row guid="d6321f29-4e95-42a5-9063-96df480e55a8">
	          <msh:textField property="dateStart" label="Дата поступления" viewOnlyField="true" />
	          <msh:textField property="entranceTime" label="время" viewOnlyField="true" />
	        </msh:row>
        </msh:ifInRole>
        <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/NotEditAdmissionTime">
	        <msh:row guid="d6321f29-4e95-42a5-9063-96df480e55a8">
	          <msh:textField  property="dateStart" label="Дата поступления" viewOnlyField="false" />
	          <msh:textField property="entranceTime" label="время" viewOnlyField="false" />
	        </msh:row>
        </msh:ifNotInRole>
        <msh:row guid="f244aba5-68fb-4ccc-9982-7b4480cca147">
          <msh:autoComplete  viewOnlyField="true"  vocName="lpu" property="department" label="Отделение" fieldColSpan="6" horizontalFill="true" guid="109f7264-23b2-42c0-ba47-65d90747816c" size="30" />
        </msh:row>
        <msh:row guid="f2-68fb-4ccc-9982-7b4480cca147">
          <msh:autoComplete vocName="serviceStreamByDepAndDate" property="serviceStream" label="Поток обслуживания" fieldColSpan="6" horizontalFill="true" guid="109f7264-23b216c" />
        </msh:row>
        <msh:row guid="f2aba5-68fb-4ccc-9982-7b4480cmca147">
          <msh:autoComplete vocName="bedFundByDepAndStreamAndDate" property="bedFund" label="Профиль коек" fieldColSpan="6" horizontalFill="true" guid="1064-23b2-42c0-ba47-65d90747816c" size="30" />
        </msh:row>
        <msh:row guid="9b781235-66ad-4f9d-991b-afb9aedfb7a8">
          <msh:autoComplete property="roomNumber" vocName="hospitalRoomByLpu" label="№палаты" parentId="stac_sloForm.department"/>
          <msh:autoComplete property="bedNumber" vocName="hospitalBedByRoom" label="№ койки" parentAutocomplete="roomNumber"/>
        </msh:row>
        <msh:row>
        	<msh:checkBox label="Провизорность" property="provisional" guid="dh88d59-3adb-4485-af94-cahb04f82b" />
        	<msh:checkBox label="Экстренно" property="emergency" guid="dhcahb04f82b" />
        </msh:row>
        <msh:row guid="1d32ce64-883b-4be9-8db1-a421709f4470">
          <msh:autoComplete vocName="workFunction" property="ownerFunction" label="Лечащий врач" fieldColSpan="6" horizontalFill="true" guid="968469ce-dd95-40f4-af14-deef6cd3e4f3" viewAction="entitySubclassView-work_workFunction.do" size="30" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="omcStandart" fieldColSpan="6" label="ОМС стандарт (врач)" horizontalFill="true" vocName="omcStandart"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="omcStandartExpert" fieldColSpan="6" label="ОМС стандарт (эксперт)" horizontalFill="true" vocName="omcStandart"/>
        </msh:row>
       
             
        <msh:separator label="Перевод в другое отделение" colSpan="" guid="dd7185d0-e499-4307-9e58-6ef41d83c2b0" />
        <msh:row guid="a3509d1f-9324-4997-a7c3-6ca8f12a9347">
          <msh:textField property="transferDate" label="Дата" guid="f8f5c912-00b8-4fd8-87b9-abe417212d78" />
          <msh:textField property="transferTime" label="Время" guid="c04ab410-42df-4f5b-b365-b4acf17a2616" />
        </msh:row>
        <msh:row guid="72adfc11-ef9b-47c0-8eb4-a23ee9e84ed8">
          <msh:autoComplete vocName="vocLpuOtd" property="transferDepartment" label="Отделение" fieldColSpan="6" horizontalFill="true" guid="f793944a-6afe-4c26-82f3-50532049a8bc" />
        </msh:row>
        <msh:row guid="f2a5-68fb-4ccc-9982-7b4447">
          <msh:autoComplete vocName="vocHospType" property="targetHospType" label="Куда переведен" fieldColSpan="6" horizontalFill="true" guid="10964-23b2-42c0-ba47-6547816c" />
        </msh:row>
        <msh:separator label="Выписка" colSpan=""/>
        <msh:row guid="21b4ac48-1773-410d-b85f-537680420aa4">
          <msh:textField property="dateFinish" label="Дата" viewOnlyField="true" guid="bb7b87a8-c542-47ef-93b6-91106abf9f19" />
          <msh:textField property="dischargeTime" label="Время" viewOnlyField="true" guid="a8bfc5ac-8d19-4656-a30b-bd87da1918df" />
        </msh:row>
        <msh:row>
        	<msh:separator label="Дополнительно" colSpan="4"/>
        </msh:row>
                <msh:row>
        	<msh:label property="createDate" label="Дата создания"/>
          <msh:label property="username" label="Оператор" guid="2258d5ca-cde5-46e9-a1cc-3ffc278353fe" />
        </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редак."/>
          	<msh:label property="editUsername" label="Оператор" guid="2258d5ca-cde5-46e9-a1cc-3ffc278353fe" />
        </msh:row>

      </msh:panel>
    </msh:form>
          <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Diagnosis/View" guid="b0ceb3e4-a6a2-41fa-be6b-ea222196a33d">
      
		<ecom:webQuery name='diagnosis' nativeSql="select d.id as did, d.establishDate as destablishDate, vrt.name as vrtinfo
		, vpd.name as vpdname, d.name as dname, mkb.code
		,vwf.name|| ' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as doctor
		,case 
		when vpd.code='4' then 'color: purple;'
		when vpd.code='3' then 'color: green;'
		when vpd.code='1' then 'color: blue;'
		 end as style
		from Diagnosis d
		left join VocDiagnosisRegistrationType vrt on vrt.id=d.registrationType_id
		left join VocPriorityDiagnosis vpd on vpd.id=d.priority_id
		left join VocIdc10 mkb on mkb.id=d.idc10_id
		left join WorkFunction wf on wf.id=d.medicalWorker_id
		left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
		left join Worker w on w.id=wf.worker_id
		left join Patient wp on wp.id=w.person_id
		where d.medcase_id='${param.id}'
		order by vpd.code,mkb.code
		"/>
		
        <msh:section title="Диагнозы. <a href='entityParentPrepareCreate-stac_diagnosis.do?id=${param.id }'> Добавить новый диагноз</a>" guid="1f214-8ea0-4b66-a0f3-62713c1">
          <msh:table name="diagnosis" action="entityParentView-stac_diagnosis.do" idField="1" guid="b621e361-1e0b-4ebd-9f58-b7d916" styleRow="8">
          	<msh:tableColumn property="sn" columnName="#"/>
            <msh:tableColumn columnName="Тип регистрации" property="3" guid="6682eeef-105f-43a0-be61-30a865f27972" />
            <msh:tableColumn columnName="Приоритет" property="4" guid="6682eeef-12" />            
            <msh:tableColumn columnName="Наименование" property="5" guid="6682eeef-105f-43a0-be61-30a865f27972" />
            <msh:tableColumn columnName="Код МКБ" property="6" guid="f34e1b12-3392-4978-b31f-5e54ff2e45bd" />
            <msh:tableColumn columnName="Специалист" property="7" guid="-3392-4978-b31f-5e54ff2e45" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
    <msh:ifFormTypeIsView formName="stac_sloForm" guid="48eb9700-d07d-4115-a476-a5a5e">
      <msh:ifInRole roles="/Policy/Mis/MedCase/Protocol/View" guid="932601e0-0d99-4b63-8f44-2466f6e91c0f">
        <msh:section title="Дневники специалистов (последние 50). 
        <a href='entityParentPrepareCreate-smo_visitProtocol.do?id=${param.id }'>Добавить</a>&nbsp;&nbsp;
        <a href='entityParentList-smo_visitProtocol.do?id=${param.id }'>Полный список дневников</a>&nbsp;&nbsp;
        <a href='printProtocolsBySLO.do?medcase=${param.id }&id=${param.id}&stAll=selected'>Печать (весь список)</a>&nbsp;&nbsp;
        <a href='printProtocolsBySLO.do?medcase=${param.id }&id=${param.id}&stNoPrint=selected'>Печать (список нераспеч.)</a>
        " guid="1f21294-8ea0-4b66-a0f3-62713c1">
         
      <ecom:webQuery maxResult="50" name="protocols"  nativeSql="
      select d.id as did, d.dateRegistration as ddateRegistration
      , d.timeRegistration as dtimeRegistration, d.record as drecord 
     , vwf.name||' '||pw.lastname||' '||pw.firstname||' '||pw.middlename as doctor
      from MedCase slo
      left join MedCase aslo on aslo.parent_id=slo.parent_id
      left join Diary as d on aslo.id=d.medCase_id
      left join WorkFunction wf on wf.id=d.specialist_id
      left join Worker w on w.id=wf.worker_id
      left join Patient pw on pw.id=w.person_id
      
      left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
            	where slo.id='${param.id}' and d.DTYPE='Protocol'
            	order by  d.dateRegistration desc,  d.timeRegistration desc"/>

          <msh:table hideTitle="false" idField="1" name="protocols" action="entityParentView-smo_visitProtocol.do" guid="d0267-9aec-4ee0-b20a-4f26b37">
                    <msh:tableColumn columnName="#" property="sn"/>
                    <msh:tableColumn columnName="Дата" property="2"/>
                    <msh:tableColumn columnName="Время" property="3"/>
                    <msh:tableColumn columnName="Протокол" property="4" cssClass="preCell"/>
                    <msh:tableColumn columnName="Специалист" property="5"/>
                </msh:table>
          
        </msh:section>
        
      </msh:ifInRole>

      <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/View">
          <ecom:webQuery name="allSurgOper" nativeSql="select so.id
          ,so.operationDate as sooperationDate,so.operationTime as soperationTime,coalesce(vo.code,'')||' '||vo.name as voname
          , d.name as whoIs  
          , vwf.name||' '||wp.lastname||' '||wp.firstname||' '||wp.middlename as doctor
          from SurgicalOperation as so 
          left join MedService vo on vo.id=so.medService_id 
          left join medcase parent on parent.id=so.medcase_id 
          left join MisLpu d on d.id=so.department_id 
          left join WorkFunction wf on wf.id=so.surgeon_id
          left join Worker w on w.id=wf.worker_id
          left join VocWorkFunction vwf on vwf.id=wf.workFunction_id
          left join Patient wp on wp.id=w.person_id
          where  
           so.medCase_id=${param.id}
          order by so.operationDate
          "/>
    <msh:tableNotEmpty name="allSurgOper">
	    <msh:section title="Хирургические операции ">
	    	<msh:table name="allSurgOper" action="entityParentView-stac_surOperation.do" idField="1">
	    		<msh:tableColumn columnName="#" property="sn"/>
	    		<msh:tableColumn columnName="Отделение" property="5"/>
	    		<msh:tableColumn columnName="Дата" property="2"/>
	    		<msh:tableColumn columnName="Время" property="3"/>
	    		<msh:tableColumn columnName="Операция" property="4"/>
	    		<msh:tableColumn columnName="Хирург" property="6"/>
	    	</msh:table>
	    </msh:section>
    </msh:tableNotEmpty>
      </msh:ifInRole>
      </msh:ifFormTypeIsView>
    
  </tiles:put>
    <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="stac_sloForm" guid="fb43e71c-1ba9-4e61-8632-a6f4a72b461c" />
  </tiles:put>
</tiles:insert>