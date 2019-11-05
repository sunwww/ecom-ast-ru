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

    <msh:form action="/entityParentSaveGoView-smo_planHospitalByHosp.do" defaultField="phone" editRoles="/Policy/Mis/MedCase/Stac/Ssl/Planning/Edit" createRoles="/Policy/Mis/MedCase/Stac/Ssl/Planning/Create">
      <msh:panel colsWidth="15px,250px,15px">
        <msh:hidden property="id" />
        <msh:hidden property="visit" />
        <msh:hidden property="patient" />
        <msh:hidden property="saveType" />
          <msh:hidden property="internalCode" />
          <msh:ifFormTypeIsView formName="smo_planHospitalByHospForm">
              <msh:row>
                  <msh:textField property="internalCode" label="Внутренний номер направления" horizontalFill="true" fieldColSpan="3"/>
              </msh:row>
          </msh:ifFormTypeIsView>
        <msh:row>
        	<msh:textField property="phone" label="Телефон" horizontalFill="true" fieldColSpan="3"/>
        </msh:row>
        <msh:row>
        	<msh:separator label="Параметры госпитализации" colSpan="4"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="orderLpu" label="Направлен из" fieldColSpan="3" horizontalFill="true" vocName="mainLpu"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="directLpu" label="Направлен в" fieldColSpan="3" horizontalFill="true" vocName="mainLpu"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete vocName="vocServiceStream" property="serviceStream" label="Поток обслуживания" horizontalFill="true"/>
         </msh:row>
        <msh:row>
        	<msh:autoComplete property="bedType" label="Профиль коек" fieldColSpan="3" horizontalFill="true" vocName="vocBedType"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="bedSubType" label="Тип коек" fieldColSpan="3" horizontalFill="true" vocName="vocBedSubType"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="dateFrom" label="с"/>
        	<msh:textField property="dateTo" label="по"/>
        </msh:row>
        <msh:row>
        	<msh:textField property="cntDays" label="дней"/>
        </msh:row>
  <msh:row>
  	<msh:autoComplete property="indicationToHosp" vocName="vocIndicationHospital" label="Показания к госпитализации" fieldColSpan="3" horizontalFill="true"/>
  </msh:row>
        <msh:row>
        	<msh:autoComplete property="idc10" vocName="vocIdc10" label="Код МКБ" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
       <msh:row>
          <msh:autoComplete viewAction="entitySubclassView-work_workFunction.do" vocName="workFunction" property="workFunction" label="Функция" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
        	<msh:textField horizontalFill="true" property="diagnosis" fieldColSpan="3" label="Диагноз"/>
        </msh:row>
        <msh:ifFormTypeIsCreate formName="smo_planHospitalByHospForm">
        <msh:row>
        	<td colspan="3" align="center">
        	<input type="button" onclick="getTextDiaryByMedCase(this);return false;" value="Вставить данные дневниковой записи"/>
        	</td>
        </msh:row>
        </msh:ifFormTypeIsCreate>
        <msh:row>
        	<msh:textArea property="comment" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        <msh:checkBox property="isOperation" label="Назначить операцию?"/>
        </msh:row>
        </msh:panel>
        <msh:panel>
        <msh:row>
        	<msh:separator label="Фактическая госпитализация" colSpan="4"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="medCase" parentId="smo_planHospitalByHospForm.patient" fieldColSpan="3" label="Госпитализация" vocName="slsByPatient" horizontalFill="true"
        	/>
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
        
      </msh:panel>
     
        <msh:panel> 
		<msh:submitCancelButtonsRow colSpan="3" />
        </msh:panel>
    </msh:form>

</tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="smo_planHospitalByHospForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="smo_planHospitalByHospForm">
      <msh:sideMenu title="Планирование госпитализаций">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-smo_planHospitalByHosp" name="Изменить" roles="/Policy/Mis/MedCase/Stac/Ssl/Planning/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-smo_planHospitalByHosp" name="Удалить" roles="/Policy/Mis/MedCase/Stac/Ssl/Planning/Delete" />
      </msh:sideMenu>
      <msh:sideMenu title="Печать">
      	<msh:sideLink key="CTRL+2" params="id" action="/print-documentDirection1.do?m=printPlanHospital&s=VisitPrintService" name="Предварительной госпитализации"/>
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  
  <tiles:put name="javascript" type="string">
  <msh:ifFormTypeIsView formName="smo_planHospitalByHospForm">
  <msh:ifInRole roles="/Policy/Mis/MedCase/Visit/PrintNotView">
  </msh:ifInRole>
  <msh:ifNotInRole roles="/Policy/Mis/MedCase/Visit/PrintNotView">
  </msh:ifNotInRole>
  </msh:ifFormTypeIsView>
  <msh:ifFormTypeIsNotView formName="smo_planHospitalByHospForm">
  	<script type="text/javascript">
	
  	eventutil.addEventListener($('isOperation'), 'click', function () {alert('Создание назначения на операцию перенесено в лист назначения!');}) ;

  		//initPersonPatientDialog();
  		function getTextDiaryByMedCase(aElement) {
  			HospitalMedCaseService.getTextDiaryByMedCase(
					 $('visit').value,{
    			callback: function(aResult) {
    				$('comment').value=$('comment').value+"\n"+aResult ;
    				
    			}
    		}) ;
  			aElement.style.display="none" ;
  		} 

      		</script>  
  </msh:ifFormTypeIsNotView>
  </tiles:put>

</tiles:insert>

