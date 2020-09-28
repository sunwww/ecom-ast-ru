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
    <msh:form action="/entitySaveGoView-stac_planHospital.do" defaultField="patientName" editRoles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Edit" createRoles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Create">
      <msh:panel>
        <msh:hidden property="id" />
        <msh:hidden property="saveType" />
        <msh:row>
          	<msh:autoComplete showId="false" vocName="patient" property="patient" viewOnlyField="false"  label="Персона" horizontalFill="true" viewAction="entityView-mis_patient.do" fieldColSpan="2"/>
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
        	<msh:autoComplete vocName="vocServiceStream" property="serviceStream" label="Поток обслуживания" horizontalFill="true"/>
        	<msh:checkBox property="isOperation" label="Операция?"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="department" label="Отделение" fieldColSpan="3" horizontalFill="true" vocName="vocLpuHospOtdAll"/>
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
          <msh:autoComplete viewAction="entitySubclassView-work_workFunction.do" vocName="workFunction" property="workFunction" label="Функция" fieldColSpan="3" horizontalFill="true" viewOnlyField="true" />
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

                <msh:submitCancelButtonsRow colSpan="3" />
      </msh:panel>
      
    </msh:form>
    <tags:mis_patient_new divForButton="personButton" name='Person' title='Создание новой персоны' autoComplitePatient="patient"/>

</tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="StacJournal" beginForm="stac_planHospitalForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="stac_planHospitalForm">
      <msh:sideMenu title="Планирование госпитализаций">
        <msh:sideLink key="ALT+2" params="id" action="/entityEdit-stac_planHospital" name="Изменить" roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityDelete-stac_planHospital" name="Удалить" roles="/Policy/Mis/MedCase/Stac/Ssl/SurOper/Delete" />
      </msh:sideMenu>
      <msh:sideMenu title="Печать">
      	<msh:sideLink key="CTRL+2" params="id" action="/print-documentDirection1.do?m=printPlanHospital&s=VisitPrintService" name="Предварительной госпитализации"/>
      </msh:sideMenu>
      <msh:sideMenu title="Дополнительно">
      	<msh:sideLink name="Журнал по предварительной госпитализации" action="/stac_planning_hospitalizations.do"/>
      	<msh:sideLink name="Госпитализировать" action="/javascript:createHosp()"/>
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  
  <tiles:put name="javascript" type="string">
      <script type="text/javascript">
          function createHosp() {
              window.document.location='entityParentPrepareCreate-stac_sslAdmission.do?id='+$('patient').value+'&preHosp=${param.id}';
          }
      </script>
  	<msh:ifFormTypeIsNotView formName="stac_planHospitalForm">
  	<script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js"></script>
  	<script type="text/javascript" src="./dwr/interface/PatientService.js"></script>

  	<script type="text/javascript">

  		initPersonPatientDialog();
  		patientAutocomplete.addOnChangeCallback(function() {
  		    if ($('patient').value) {
                PatientService.getPhoneByPatientId($('patient').value, {
                    callback: function(phone) {
                        $('phone').value=phone;
                    }
                });
            }
        });
        departmentAutocomplete.addOnChangeCallback(function() {
			HospitalMedCaseService.getDefaultBedTypeByDepartment(
					 $('department').value, $('serviceStream').value
      				, $('dateFrom').value,{
      			callback: function(aResult) {
      				var res = aResult.split('#') ;

      				if (+res[0]>0) {
      					$('bedType').value = res[0] ; 
      					$('bedTypeName').value = res[1] ; 
      					$('bedSubType').value = res[2] ; 
      					$('bedSubTypeName').value = res[3] ; 
      				} else {
      		      	 	$('bedType').value='0';
      		      	 	$('bedTypeName').value='';
      		      	 	$('bedSubType').value='0';
      		      	 	$('bedSubTypeName').value='';
      				}
      			}
      		}) ;  
  		});
      		</script>
  		</msh:ifFormTypeIsNotView>
  </tiles:put>
</tiles:insert>

