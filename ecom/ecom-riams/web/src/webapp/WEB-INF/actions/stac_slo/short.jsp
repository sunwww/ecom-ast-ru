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
    <msh:form action="/entityParentSaveGoView-stac_slo.do" title="<a href='entityParentView-stac_slo.do?id=${param.id}'>Случай лечения в отделении</a>" defaultField="transferDate">
      <msh:hidden property="id" />
      <msh:hidden property="prevMedCase" />
      <msh:hidden property="parent" />
      <msh:hidden property="patient" />
      <msh:hidden property="saveType" />
      <msh:hidden property="lpuAndDate" />
      <msh:hidden property="lpu" />
      <msh:hidden property="dateFinish"/>
      <msh:hidden property="dischargeTime"/>
      <msh:panel colsWidth="5%,5%,5%">
      <msh:ifFormTypeAreViewOrEdit formName="stac_sloForm">
      	<msh:row >
      		<msh:label property="statCardBySLS" label="Номер стат.карты" labelColSpan="1"/>
      		<td colspan="2">
      		<msh:link action="entityParentListRedirect-stac_slo.do?id=${param.id}" roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/View">⇧Cписок СЛО</msh:link>
      		</td>
      	</msh:row>
      	</msh:ifFormTypeAreViewOrEdit>
        <msh:separator label="Переведен из отделения" colSpan="6" />
        <msh:row>
          <msh:autoComplete viewAction="entityParentView-stac_slo.do" shortViewAction="entityShortView-stac_slo.do" parentId="stac_sloForm.parent" viewOnlyField="true"  vocName="sloBySls" property="prevMedCase" label="СЛО" fieldColSpan="6" horizontalFill="true" size="30" />
        </msh:row>
        <msh:separator label="Поступление в отделение" colSpan="6" />
        <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/NotEditAdmissionTime">
	        <msh:row>
	          <msh:textField property="dateStart" label="Дата поступления" viewOnlyField="true" />
	          <msh:textField property="entranceTime" label="время" viewOnlyField="true" />
	        </msh:row>
        </msh:ifInRole>
        <msh:ifNotInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Slo/NotEditAdmissionTime">
	        <msh:row>
	          <msh:textField  property="dateStart" label="Дата поступления" viewOnlyField="false" />
	          <msh:textField property="entranceTime" label="время" viewOnlyField="false" />
	        </msh:row>
        </msh:ifNotInRole>
        <msh:row>
          <msh:autoComplete  viewOnlyField="true"  vocName="lpu" property="department" label="Отделение" fieldColSpan="6" horizontalFill="true" size="30" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="serviceStreamByDepAndDate" property="serviceStream" label="Поток обслуживания" fieldColSpan="6" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="bedFundByDepAndStreamAndDate" property="bedFund" label="Профиль коек" fieldColSpan="6" horizontalFill="true" size="30" />
        </msh:row>
        <msh:row>
          <msh:autoComplete property="roomNumber" vocName="hospitalRoomByLpu" label="№палаты" parentId="stac_sloForm.department"/>
          <msh:autoComplete property="bedNumber" vocName="hospitalBedByRoom" label="№ койки" parentAutocomplete="roomNumber"/>
        </msh:row>
        <msh:row>
        	<msh:checkBox label="Экстренно" property="emergency" viewOnlyField="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="workFunction" property="ownerFunction" label="Лечащий врач" fieldColSpan="6" horizontalFill="true" viewAction="entitySubclassView-work_workFunction.do" size="30" />
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="omcStandart" fieldColSpan="6" label="ОМС стандарт (врач)" horizontalFill="true" vocName="omcStandart"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="omcStandartExpert" fieldColSpan="6" label="ОМС стандарт (эксперт)" horizontalFill="true" vocName="omcStandart"/>
        </msh:row>
       
             
        <msh:separator label="Перевод в другое отделение" colSpan="" />
        <msh:row>
          <msh:textField property="transferDate" label="Дата" />
          <msh:textField property="transferTime" label="Время" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocLpuOtd" property="transferDepartment" label="Отделение" fieldColSpan="6" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:autoComplete vocName="vocHospType" property="targetHospType" label="Куда переведен" fieldColSpan="6" horizontalFill="true" />
        </msh:row>
        <msh:separator label="Выписка" colSpan=""/>
        <msh:row>
          <msh:textField property="dateFinish" label="Дата" viewOnlyField="true" />
          <msh:textField property="dischargeTime" label="Время" viewOnlyField="true" />
        </msh:row>
        <msh:row>
        	<msh:separator label="Дополнительно" colSpan="4"/>
        </msh:row>
                <msh:row>
        	<msh:label property="createDate" label="Дата создания"/>
          <msh:label property="username" label="Оператор" />
        </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редак."/>
          	<msh:label property="editUsername" label="Оператор" />
        </msh:row>

      </msh:panel>
    </msh:form>
          <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/Diagnosis/View">
      
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
		
        <msh:section title="Диагнозы. <a href='entityParentPrepareCreate-stac_diagnosis.do?id=${param.id }'> Добавить новый диагноз</a>">
          <msh:table name="diagnosis" action="entityParentView-stac_diagnosis.do" idField="1" styleRow="8">
          	<msh:tableColumn property="sn" columnName="#"/>
            <msh:tableColumn columnName="Тип регистрации" property="3" />
            <msh:tableColumn columnName="Приоритет" property="4" />
            <msh:tableColumn columnName="Наименование" property="5" />
            <msh:tableColumn columnName="Код МКБ" property="6" />
            <msh:tableColumn columnName="Специалист" property="7" />
          </msh:table>
        </msh:section>
      </msh:ifInRole>
    <msh:ifFormTypeIsView formName="stac_sloForm">
      <msh:ifInRole roles="/Policy/Mis/MedCase/Protocol/View">
        <msh:section title="Дневники специалистов (последние 50). 
        <a href='entityParentPrepareCreate-smo_visitProtocol.do?id=${param.id }'>Добавить</a>&nbsp;&nbsp;
        <a href='entityParentList-smo_visitProtocol.do?id=${param.id }'>Полный список дневников</a>&nbsp;&nbsp;
        <a href='printProtocolsBySLO.do?medcase=${param.id }&id=${param.id}&stAll=selected'>Печать (весь список)</a>&nbsp;&nbsp;
        <a href='printProtocolsBySLO.do?medcase=${param.id }&id=${param.id}&stNoPrint=selected'>Печать (список нераспеч.)</a>
        ">
         
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

          <msh:table hideTitle="false" idField="1" name="protocols" action="entityParentView-smo_visitProtocol.do">
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
    <ecom:titleTrail mainMenu="Patient" beginForm="stac_sloForm" />
  </tiles:put>
</tiles:insert>