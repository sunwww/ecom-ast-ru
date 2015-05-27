<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Сервис мед.услуг
    	  -->
    <msh:form action="/entityParentSaveGoSubclassView-smo_medService.do" defaultField="dateStart" guid="58611d9a-bd7a-465f-8d35-a1d6d03d0257">
      <msh:hidden property="id" guid="0144d27c-76ff-41a2-9bab-52844196a1cc" />
      <msh:hidden property="saveType" guid="63c88f1b-4cc9-493e-90ad-984cfafeb17d" />
      <msh:hidden property="parent" guid="c6ff741b-f806-46cb-af60-37434635bd3c" />
      <msh:hidden property="patient" guid="ef57d35d-e9a0-48ba-a00c-b77676505ab2" />
      <msh:hidden property="printDate"/>
      <msh:hidden property="printTime"/>
        
      <msh:panel guid="ff562351-b995-43c7-871f-aca0b9977b65" colsWidth="5%,20%,5%,30%">
        <msh:row guid="bb138544-81d9-4339-b3a7-cab980708336">
          <msh:textField property="dateStart" label="Дата" guid="b2d29f22-2b89-4b43-a6af-ef7c8b8c5fb3" />
          <msh:textField property="timeExecute" label="Время" guid="8d583c3f-dda1-43a9-8417-5a2d43a6cd40" />
        </msh:row>
        <msh:row guid="348da311-be75-4c71-86c5-3fbf138985dc">
          <msh:autoComplete property="serviceStream" label="Поток обслуживания" vocName="vocServiceStream" guid="13482ebc-6632-411c-a30d-11315eb7410c" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        
        <msh:row>
          <msh:autoComplete  property="medService" label="Медицинская услуга" vocName="medServiceForCategory" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row>
          <msh:textField property="medServiceAmount" label="Количество" guid="b2d298c5fb3" />
          <msh:textField property="uet" label="Усл.един.трудоем."/>
        </msh:row>
        
        <msh:row>
        	<msh:ifInRole roles="/Policy/Mis/MedCase/MedService/EditWorker">
		          <msh:autoComplete property="workFunctionExecute" label="Исполнитель" vocName="workFunction" guid="13482ebc-6632-411c-a410c" fieldColSpan="3" horizontalFill="true" />
        	</msh:ifInRole>
        	<msh:ifNotInRole roles="/Policy/Mis/MedCase/MedService/EditWorker">
		          <msh:autoComplete viewOnlyField="true" property="workFunctionExecute" label="Исполнитель" vocName="workFunction" guid="13482ebc-6632-411c-a410c" fieldColSpan="3" horizontalFill="true" />
        	</msh:ifNotInRole>
        </msh:row>
        <msh:row guid="35d14f6d-41be-46bc-8f53-b929b8f04789">
          <msh:textField property="username" label="Пользователь" viewOnlyField="true" guid="4a9364d8-150d-465e-bc4f-5a08f2547136" />
          <msh:textField property="createDate" label="Дата создания" guid="cad233e1-9064-491b-8b15-f757f63657af" viewOnlyField="true" />
        </msh:row>
        <msh:row>
        	<msh:textField property="printDate" label="Дата печати" viewOnlyField="true"/>
        	<msh:textField property="printTime" label="Время" viewOnlyField="true"/>
        </msh:row>
        <msh:row guid="9fa28d40-dd32-4c15-9b76-7d41f31e6a29">
          <msh:checkBox property="noActuality" viewOnlyField="false" label="Недействительность услуги" guid="6573be39-9a16-4a7c-bdef-5ca915d669c2" horizontalFill="false" fieldColSpan="2" labelColSpan="2" />
        </msh:row>
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="8" />
      </msh:panel>
    </msh:form>
    
    <msh:ifFormTypeIsNotView formName="smo_medServiceForm">
    	<tags:mis_double name='MedService' title='Данная услуга оказана:'/>
    </msh:ifFormTypeIsNotView>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Patient" beginForm="smo_medServiceForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView guid="ifFormTypeIsView" formName="smo_medServiceForm">
      <msh:sideMenu guid="sideMenu-123" title="Услуга">
        <msh:sideLink guid="sideLinkEdit" key="ALT+2" params="id" action="/entityParentEdit-smo_medService" name="Изменить" roles="/Policy/Mis/MedCase/MedService/Edit" />
        <msh:sideLink guid="sideLinkDelete" key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-smo_medService" name="Удалить" roles="/Policy/Mis/MedCase/MedService/Delete" />
      </msh:sideMenu>
      <msh:sideMenu title="Добавить" guid="d2c3339b-5d34-41a4-8e29-c91aafc9f483">
        
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
    <script type="text/javascript">// <![CDATA[//
    	
    	var oldaction = document.forms[0].action ;
    	document.forms[0].action = 'javascript:isExistMedService()';
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
    		
    	//]]>
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

