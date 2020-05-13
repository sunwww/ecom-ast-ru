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
    <msh:form action="/entityParentSaveGoView-smo_planHospitalByVisit.do" defaultField="phone" editRoles="/Policy/Mis/MedCase/Stac/Ssl/Planning/Edit" createRoles="/Policy/Mis/MedCase/Stac/Ssl/Planning/Create">
      <msh:panel colsWidth="15px,250px,15px">
        <msh:hidden property="id" />
        <msh:hidden property="visit" />
        <msh:hidden property="patient" />
        <msh:hidden property="saveType" />
        <msh:hidden property="internalCode" />
          <msh:ifFormTypeIsView formName="smo_planHospitalByVisitForm">
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
        	<msh:autoComplete property="orderLpu" label="Направлен из ЛПУ" fieldColSpan="3" horizontalFill="true" vocName="mainLpu"/>
        </msh:row>
          <msh:row>
              <msh:autoComplete property="directLpu" label="Направлен в" fieldColSpan="3" horizontalFill="true" vocName="mainLpu"/>
          </msh:row>
        <msh:row>
        	<msh:autoComplete vocName="vocServiceStream" property="serviceStream" label="Поток обслуживания" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="department" label="Отделение" fieldColSpan="3" horizontalFill="true" vocName="vocLpuHospOtdAll"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete parentAutocomplete="department" property="bedType" label="Профиль коек" fieldColSpan="3" horizontalFill="true" vocName="vocBedTypeByDep"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="bedSubType" label="Тип коек" fieldColSpan="3" horizontalFill="true" vocName="vocBedSubTypeByDep"/>
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
  	<msh:autoComplete property="indicationToHosp" vocName="vocIndicationHospital" label="Показания к госпитализации" fieldColSpan="3" horizontalFill="true"/>
  </msh:row>
        <msh:row>
        	<msh:autoComplete property="idc10" vocName="vocIdc10" label="Код МКБ" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        	<msh:textField horizontalFill="true" property="diagnosis" fieldColSpan="3" label="Диагноз"/>
        </msh:row>
        <msh:ifFormTypeIsNotView formName="smo_planHospitalByVisitForm">
            <msh:row><td></td>
        	<td colspan="3" align="center">
                <input type="button" onclick="getTextDiaryByMedCase(this);return false;"
                       value="Вставить данные дневниковой записи"/>
                <input type="button" value="Вставить данные из шаблона" onClick="showtmpTemplateProtocol()"/>
        	</td>
        </msh:row>  
        </msh:ifFormTypeIsNotView>
        <msh:row>
        	<msh:textArea property="comment" fieldColSpan="3" horizontalFill="true"/>
        </msh:row>
        <msh:row>
        <msh:checkBox property="isOperation" label="Назначить операцию?"/>
        </msh:row>
        </msh:panel>

        <msh:panel>
        <msh:row>
        	<td colspan="3" align="center">
        	<input type="button" onclick="showinfoPlanHospitalCloseDocument()" value="Другие предварительные госпитализации"/>
        	</td>
        </msh:row>
        <msh:row>
        	<msh:separator label="Фактическая госпитализация" colSpan="4"/>
        </msh:row>
        <msh:row>
        	<msh:autoComplete property="medCase" parentId="smo_planHospitalByVisitForm.patient" fieldColSpan="3" label="Госпитализация" vocName="slsByPatient" horizontalFill="true"
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
        
      </msh:panel>
     
        <msh:panel> 
		<msh:submitCancelButtonsRow colSpan="3" />
        </msh:panel>
    </msh:form>

</tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="smo_planHospitalByVisitForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="smo_planHospitalByVisitForm">
      <msh:sideMenu title="Планирование госпитализаций">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-smo_planHospitalByVisit" name="Изменить" roles="/Policy/Mis/MedCase/Stac/Ssl/Planning/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-smo_planHospitalByVisit" name="Удалить" roles="/Policy/Mis/MedCase/Stac/Ssl/Planning/Delete" />
      </msh:sideMenu>
      <msh:sideMenu title="Печать">
      	<msh:sideLink key="CTRL+2" params="id" action="/print-documentDirection1.do?m=printPlanHospital&s=VisitPrintService" name="Предварительной госпитализации"/>
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
      <tags:infoPlanHospital name="infoPlanHospital" />
      <tags:templateProtocol idSmo="smo_planHospitalByVisit.visit"
                             version="Visit" name="tmp" property="comment"
                             voc="protocolVisitByPatient" />
  </tiles:put>
  
  <tiles:put name="javascript" type="string">
  <msh:ifFormTypeIsView formName="smo_planHospitalByVisitForm">
  <msh:ifInRole roles="/Policy/Mis/MedCase/Visit/PrintNotView">
  <script type="text/javascript"> 
  
    function printDocument() {
      	if (confirm('Распечатать документ?')) {
      		window.location.href = "print-documentDirection1.do?next=entityParentView-smo_visit.do__id="+$('visit').value+"&s=VisitPrintService&m=printPlanHospital&id=${param.id}" ;
      	}
  }
    printDocument();
    </script>
  </msh:ifInRole>
  <msh:ifNotInRole roles="/Policy/Mis/MedCase/Visit/PrintNotView">
  <script type="text/javascript">
    function printDocument() {
      	if (confirm('Распечатать документ?')) {
      		window.location.href = "print-documentDirection1.do?next=entityParentView-smo_visit.do__id="+$('visit').value+"&s=VisitPrintService&m=printPlanHospital&id=${param.id}" ;
      	}
      }
    printDocument();
    </script>
  </msh:ifNotInRole>
  </msh:ifFormTypeIsView>
  <msh:ifFormTypeIsNotView formName="smo_planHospitalByVisitForm">
<script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js">/**/</script>
<script type="text/javascript" src="./dwr/interface/WorkCalendarService.js"></script>
  	
  	<script type="text/javascript">
  	eventutil.addEventListener($('isOperation'), 'click', function () {alert('Создание назначения на операцию перенесено в лист назначения!');}) ;

  	//Milamesher 20.04.2017 проверка даты - текущий и следующий года только
  	eventutil.addEventListener($('dateFrom'),'blur', function() {checkDate();}); 
  	function checkDate() {
  		var date = $(dateFrom).value;
  		if (date.length==10) {
  			//прошлые даты - минимум сегодня
  			var d=$(dateFrom).value.substring(0,2); 
 			var m=$(dateFrom).value.substring(3,5); 
 			var y=date.substring(6); 
 			date=y.toString()+m.toString()+d.toString();
 			
 			var now=new Date();
 			year=now.getFullYear();
            var today=year.toString();
 			var month=now.getMonth()+1;
			if (month<10) month="0"+month; 
			var day=now.getDate();
			if (day<10) day="0"+day;
			today=today+month.toString()+day.toString(); 
			
			if (date<today) {
				$(dateFrom).value=day+'.'+month+'.'+year;
			}
			else {
				if (y-year>1) {  //больше чем год разницы
					$(dateFrom).value=$(dateFrom).value.replace(y,year);
				}
				
			} 
  		}
  	}
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
  		departmentAutocomplete.addOnChangeCallback(function() {
			HospitalMedCaseService.getDefaultBedTypeByDepartment(
					 $('department').value, $('serviceStream').value
      				, $('dateFrom').value,{
      			callback: function(aResult) {
      				var res = aResult.split('#') ;

      				if (+res[0]!=0) {
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
      				bedSubTypeAutocomplete.setParentId($('department').value+'#'+$('bedType').value) ;
      				
      			}
      		}) ;  
  		});
  		bedTypeAutocomplete.addOnChangeCallback(function() {
  			HospitalMedCaseService.getDefaultBedSubTypeByDepartment(
					 $('department').value, $('serviceStream').value
					 ,$('bedType').value
     				, $('dateFrom').value,{
     			callback: function(aResult) {
     				var res = aResult.split('#') ;

     				if (+res[0]>0) {
     					$('bedSubType').value = res[0] ; 
     					$('bedSubTypeName').value = res[1] ;
     				} else {
     		      	 	$('bedSubType').value='0';
     		      	 	$('bedSubTypeName').value='';
     				}
     				bedSubTypeAutocomplete.setParentId($('department').value+'#'+$('bedType').value) ;
     				
     			}
     		}) ;
      	 });
  		bedSubTypeAutocomplete.setParentId((+$('department').value>0?$('department').value:"0")+'#'+$('bedType').value) ;

      		</script> 

  </msh:ifFormTypeIsNotView>
  </tiles:put>

</tiles:insert>

