<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@page import="ru.ecom.web.login.LoginInfo"%>
<%
    String username = LoginInfo.find(request.getSession()).getUsername();
    request.setAttribute("username", username);
%>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Сервис мед.услуг
    	  -->
    <msh:form action="/entityParentSaveGoSubclassView-smo_medService.do" defaultField="dateStart">
      <msh:hidden property="id" />
      <msh:hidden property="saveType" />
      <msh:hidden property="parent" />
      <msh:hidden property="patient" />
      <msh:hidden property="printDate"/>
      <msh:hidden property="printTime"/>
        
      <msh:panel colsWidth="5%,20%,5%,30%">
        <msh:row>
          <msh:textField property="dateStart" label="Дата" />
          <msh:textField property="timeExecute" label="Время" />
          
        </msh:row>
         <msh:row>
          <msh:textField property="dateFinish" label="Дата окончания" />
         
        </msh:row>
        <msh:row>
          <msh:autoComplete property="serviceStream" label="Поток обслуживания" vocName="vocServiceStream" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        
        <msh:row>
          <msh:autoComplete  property="medService" label="Медицинская услуга" vocName="medService" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="medServiceAmount" label="Количество" />
          <msh:textField property="uet" label="Усл.един.трудоем."/>
        </msh:row>
        
        <msh:row>
        	<msh:ifInRole roles="/Policy/Mis/MedCase/MedService/EditWorker">
		          <msh:autoComplete property="workFunctionExecute" label="Исполнитель" vocName="workFunction" fieldColSpan="3" horizontalFill="true" />
        	</msh:ifInRole>
        	<msh:ifNotInRole roles="/Policy/Mis/MedCase/MedService/EditWorker">
		          <msh:autoComplete viewOnlyField="true" property="workFunctionExecute" label="Исполнитель" vocName="workFunction" fieldColSpan="3" horizontalFill="true" />
        	</msh:ifNotInRole>
        </msh:row>
        <msh:row>
          <msh:textField property="username" label="Пользователь" viewOnlyField="true" />
          <msh:textField property="createDate" label="Дата создания" viewOnlyField="true" />
        </msh:row>
        <msh:row>
        	<msh:textField property="printDate" label="Дата печати" viewOnlyField="true"/>
        	<msh:textField property="printTime" label="Время" viewOnlyField="true"/>
        </msh:row>
        <msh:row>
          <msh:checkBox property="noActuality" viewOnlyField="false" label="Недействительность услуги" horizontalFill="false" fieldColSpan="2" labelColSpan="2" />
        </msh:row>
        <msh:submitCancelButtonsRow colSpan="8" />
      </msh:panel>

      <div id="addOut"></div>
    </msh:form>
    
    <msh:ifFormTypeIsNotView formName="smo_medServiceForm">
    	<tags:mis_double name='MedService' title='Данная услуга оказана:'/>
    </msh:ifFormTypeIsNotView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="smo_medServiceForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="smo_medServiceForm">
      <msh:sideMenu title="Услуга">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-smo_medService" name="Изменить" roles="/Policy/Mis/MedCase/MedService/Edit" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-smo_medService" name="Удалить" roles="/Policy/Mis/MedCase/MedService/Delete" />
      </msh:sideMenu>
      <msh:sideMenu title="Добавить">
        
      </msh:sideMenu>
     
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="javascript" type="string">
 
  <msh:tableEmpty name="protocols">
    <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js"></script>
    <script type="text/javascript">
    
    </script>  
    </msh:tableEmpty>
 <script type='text/javascript' src='./dwr/interface/TemplateProtocolService.js'></script>
 
  <script type="text/javascript">
  
  function printHospital() {
		window.location.href = "print-protocol.do?m=printProtocol&s=HospitalPrintService&id=${param.id}" ;
  }
  </script>  
 
  <msh:ifFormTypeIsNotView formName="smo_medServiceForm">
    <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js"></script>
    <script type="text/javascript" src="./dwr/interface/PharmnetService.js"></script>
    <script type="text/javascript">
    	
    	var oldaction = document.forms[0].action ;
    	document.forms[0].action = 'javascript:isExistMedService()';

<msh:ifInRole roles="/Policy/Mis/Pharmacy/CreateOutcome">

        var count = "";
        var div = document.getElementById('addOut');

        medServiceAutocomplete.addOnChangeCallback(function(){
            var medserviceId = document.getElementById('medService');
            if(medserviceId.value!="") {
                viewComplect(medserviceId.value,count);
            }else div.innerHTML ="";
        });
        var countMedservice = document.getElementById('medServiceAmount');
        countMedservice.oninput = function() {
            count=countMedservice.value;
            var medserviceId = document.getElementById('medService');
            if(medserviceId.value!="") {
                viewComplect(medserviceId.value, count);
            }
        };

        function viewComplect(medserviceId,count) {
            var medcase = '${param.id}';
            PharmnetService.viewComplect(medserviceId,count,medcase, {
                callback: function (aResult) {
                    div.innerHTML = aResult;
                }
            });
        }

        window.onload = function() {
            var butn = document.getElementById('submitButton');
            butn.onclick = function() {

                var medService = document.getElementById('medService').value;
                var medcase = '${param.id}';
                var count = document.getElementById('medServiceAmount').value;
                if(medService!="" && count!=""){
                    createOut(medService,count,medcase);
                }

                this.value='Создание ...';
                this.disabled=true;
                this.form.submit();
                return true ;
            };
        };

        function createOut(medserviceId,count,medcaseId) {
            var userNames= "${username}";
            PharmnetService.createOutcomeByMedservice(medserviceId,count,medcaseId,userNames, {
                callback : function(aResult) {
                    //alert("Списание выполнено!");
                }
            });
        }

        </msh:ifInRole>


    	function isExistMedService() {
    		 
    		HospitalMedCaseService.findDoubleServiceByPatient($('id').value,$('patient').value,$('medService').value, $('dateStart').value
    		, {
                   callback: function(aResult) {
                   		//alert(aResult);
                      if (aResult) {
				    		showMedServiceDouble(aResult) ;
                       } else {
                       		document.forms[0].action = oldaction ;
				    		document.forms[0].submit() ;
                       }
                   }
	        	}
	        	);
    	}
    	eventutil.addEventListener($('dateFinish'),'blur',function(){setCountByDays();}) ;
    	function setCountByDays() {
    		if ($('dateFinish').value!=''&&$('dateFinish').value.length==10) {
    			var cnt="1";
    			var startDateT = $('dateStart').value
    			var finishDateT = $('dateFinish').value;
    			if (startDateT==finishDateT) {}
    			else {
					var startDate = new Date;    			
					var finishDate = new Date;    			
    				
    				startDateT=startDateT.substr(6,4)+'-'+startDateT.substr(3,2)+'-'+startDateT.substr(0,2);
    				finishDateT=finishDateT.substr(6,4)+'-'+finishDateT.substr(3,2)+'-'+finishDateT.substr(0,2);
    				startDate.setTime (Date.parse(startDateT));
    				
    				finishDate.setTime(Date.parse(finishDateT));
    				if (finishDate.getTime() >startDate.getTime()){
    				cnt= ""+(finishDate.getTime() - startDate.getTime()) / (1000*60*60*24);
    				} else {
    					cnt="0";
    				}
    			}
    			$('medServiceAmount').value=cnt;
    		}
    	}
    	</script>
    	
    	
  </msh:ifFormTypeIsNotView>
    	<msh:ifFormTypeAreViewOrEdit formName="smo_medServiceForm"><msh:ifFormTypeIsNotView formName="smo_medServiceForm">
    		<script type="text/javascript">
    		TemplateProtocolService.getUsername(
    			{
                    callback: function(aString) {
                       // if($('username').value != aString){
                       //  	alert('Вы можете редактировать только зарегистрированную Вами мед.услугу!!!');
                       //  	window.location.href= "entityParentView-smo_visitProtocol.do?id=${param.id}";
                        // }
                     }
                 }
    		);
    		</script>
    	</msh:ifFormTypeIsNotView>
    	
    	</msh:ifFormTypeAreViewOrEdit>


  </tiles:put>
</tiles:insert>

