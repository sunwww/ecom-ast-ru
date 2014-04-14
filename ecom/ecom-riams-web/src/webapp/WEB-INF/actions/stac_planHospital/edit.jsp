<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">
	<tiles:put name="style" type="string">
        <style type="text/css">            
            .protocols {
				left:0px;  width:60em; 
				top:0px;  height:55em;
				overflow: auto;
			}
			.operationText {
			width:100%;
			}
			.histologicalStudy {
			width:100%;
			}
        </style>

    </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - Хирургическая операция
    	  -->

    <msh:form action="/entitySaveGoView-stac_planHospital.do" defaultField="patientName" guid="137f576d-2283-4edd-9978-74290e04b873" editRoles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Edit" createRoles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Create">
      <msh:panel guid="80209fa0-fbd4-45d0-be90-26ca4219af2e">
        <msh:hidden property="id" guid="95d2afaa-1cdb-46a9-bb71-756352439795" />
        <msh:hidden property="saveType" guid="c409dfd8-f4e7-469f-9322-1982b666a087" />
        <msh:row guid="1e6e2aa0-b434-4025-b877-58993d9b320d">
          	<msh:autoComplete showId="false" vocName="patient" property="patient" viewOnlyField="false"  label="Персона" guid="e4a238b3-0a19-4405-adcf-8b7ef8fad03f" horizontalFill="true" viewAction="entityView-mis_patient.do" fieldColSpan="2"/>
			<td align="right" width="1px"><div id="personButton"></div></td>
        </msh:row>
        <msh:row>
        	<msh:textField property="phone" label="Телефон" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:separator label="Параметры госпитализации" colSpan="4"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="orderLpu" label="Направлен из ЛПУ" fieldColSpan="3" horizontalFill="true" vocName="mainLpu"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="department" label="Отделение" fieldColSpan="3" horizontalFill="true" vocName="lpu"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="bedType" label="Профиль коек" fieldColSpan="3" horizontalFill="true" vocName="vocBedType"/>
        </msh:row>
        
        <msh:row>
        	<msh:autoComplete property="bedSubType" label="Тип коек" fieldColSpan="3" horizontalFill="true" vocName="vocBedSubType"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="hospitalRoom" parentAutocomplete="department" label="Забронировано место в палате:" vocName="hospitalRoomByLpu" fieldColSpan="2" labelColSpan="2" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="dateFrom" label="с"/>
        	<msh:textField property="dateTo" label="по"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="cntDays" label="дней"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete vocName="vocServiceStream" property="serviceStream" label="Поток обслуживания" horizontalFill="true"/>
        	<msh:checkBox property="isOperation" label="Операция?"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="idc10" vocName="vocIdc10" label="Код МКБ" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:textField horizontalFill="true" property="diagnosis" fieldColSpan="3" label="Диагноз"/>
        </msh:row>
        <msh:row>
        	<msh:textArea property="comment" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:separator label="Фактическая госпитализация" colSpan="4"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="medCase" parentAutocomplete="patient" fieldColSpan="3" label="Госпитализация" vocName="slsByPatient" horizontalFill="true"
        	/>
        </msh:row>
        <msh:row>
          <msh:autoComplete viewAction="entitySubclassView-work_workFunction.do" vocName="workFunction" property="workFunction" label="Функция" guid="010e3a75-ba7e-45da-a82a-9c618a0ffcd2" fieldColSpan="3" horizontalFill="true" viewOnlyField="true" />
        </msh:row>
        <msh:row>
        	<msh:separator label="Дополнительная информация" colSpan="4"/>
        </msh:row>
        <msh:row>
        	<msh:label property="createDate" label="Дата создания"/>
        	<msh:label property="createTime" label="время"/>
        </msh:row>
        <msh:row>
        	<msh:label property="createUsername" label="пользователь"/>
        </msh:row>
        <msh:row>
        	<msh:label property="editDate" label="Дата редактирования"/>
        	<msh:label property="editTime" label="время"/>
        </msh:row>
        <msh:row>
        	<msh:label property="editUsername" label="пользователь"/>
        </msh:row>                

                <msh:submitCancelButtonsRow guid="submitCancel" colSpan="3" />
      </msh:panel>
      
    </msh:form>
    <tags:mis_patient_new divForButton="personButton" name='Person' title='Создание новой персоны' autoComplitePatient="patient"/>

</tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="StacJournal" beginForm="stac_planHospitalForm" guid="fb43e71c-1ba9-4e61-8632-a6f4a72b461c" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="stac_planHospitalForm" guid="c7cae1b4-31ca-4b76-ab51-7f75b52d11b6">
      <msh:sideMenu title="Планирование госпитализаций" guid="edd9bfa6-e6e7-4998-b4c2-08754057b0aa">
        <msh:sideLink key="ALT+2" params="id" action="/entityEdit-stac_planHospital" name="Изменить" roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Edit" guid="5a1450f5-7629-4458-b5a5-e5566af6a914" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-stac_planHospital" name="Удалить" roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Delete" guid="7767f5b6-c131-47f4-b8a0-2604050c450f" />
      </msh:sideMenu>
      <msh:sideMenu title="Печать">
      	<msh:sideLink key="CTRL+2" params="id" action="/print-planHospital.do?m=printPlanHospital&s=VisitPrintService" name="Предварительной госпитализации"/>
      </msh:sideMenu>
      <msh:sideMenu title="Дополнительно">
      	<msh:sideLink name="Журнал по предварительной госпитализации" action="/stac_planning_hospitalizations.do"/>
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  
  <tiles:put name="javascript" type="string">
  	<msh:ifFormTypeIsNotView formName="stac_planHospitalForm">
  	<script type="text/javascript">
  		initPersonPatientDialog()</script>
  		</msh:ifFormTypeIsNotView>
  </tiles:put>
</tiles:insert>

