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
    	  <tags:templateProtocol version="Visit" idSmo="smo_medServiceForm.parent" voc="protocolVisitByPatient" />
    <msh:form action="/entityParentSaveGoSubclassView-smo_medService.do" defaultField="dateExecute" guid="58611d9a-bd7a-465f-8d35-a1d6d03d0257">
      <msh:hidden property="id" guid="0144d27c-76ff-41a2-9bab-52844196a1cc" />
      <msh:hidden property="saveType" guid="63c88f1b-4cc9-493e-90ad-984cfafeb17d" />
      <msh:hidden property="parent" guid="c6ff741b-f806-46cb-af60-37434635bd3c" />
      <msh:hidden property="patient" guid="ef57d35d-e9a0-48ba-a00c-b77676505ab2" />
      <msh:hidden property="printDate"/>
      <msh:hidden property="printTime"/>
        
      <msh:panel guid="ff562351-b995-43c7-871f-aca0b9977b65" colsWidth="5%,20%,5%,30%">
        <msh:row guid="bb138544-81d9-4339-b3a7-cab980708336">
          <msh:textField property="dateExecute" label="Дата" guid="b2d29f22-2b89-4b43-a6af-ef7c8b8c5fb3" />
          <msh:textField property="timeExecute" label="Время" guid="8d583c3f-dda1-43a9-8417-5a2d43a6cd40" />
        </msh:row>
        <msh:row guid="348da311-be75-4c71-86c5-3fbf138985dc">
          <msh:autoComplete property="serviceStream" label="Поток обслуживания" vocName="vocServiceStream" guid="13482ebc-6632-411c-a30d-11315eb7410c" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="ba482184-7fcc-4a7f-97a3-124fc3d51391">
          <msh:autoComplete parentId="smo_medServiceForm.dateExecute" property="categoryMedService" label="Категория услуги" vocName="medService" guid="4f0640d9-5cb8-4347-818a-b130d593ca0d" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="ba482184-7fcc-4a7f-97a3-124fc3d51391">
          <msh:autoComplete parentId="5#12.02.2010" property="medService" label="Медицинская услуга" 
          vocName="medServiceForCategory" guid="4f0640d9-5cb8-4347-818a-b130d593ca0d" fieldColSpan="3" horizontalFill="true" />
        </msh:row>
        <msh:row guid="bb138544-81d9980708336">
          <msh:textField property="medServiceAmount" label="Количество" guid="b2d298c5fb3" />
          <msh:textField property="uet" label="Усл.един.трудоем."/>
        </msh:row>
        <msh:ifFormTypeIsCreate formName="smo_medServiceForm">
                <msh:row>
                    <msh:textArea property="record" label="Текст протокола:"
                                      horizontalFill="true" rows="30" fieldColSpan="6"  guid="b6ehb-b971-441e-9a90-519c07" />
                    
                    <msh:ifFormTypeIsNotView formName="smo_medServiceForm">
                    
                    <td colspan="2">
                        <input type="button" value="Шаблон" onClick="showTemplateProtocol()"/>
                    </td>
                    
                    <tags:keyWord name="record" service="KeyWordService" methodService="getDecryption"/>
                    </msh:ifFormTypeIsNotView>
                    <msh:ifFormTypeIsView formName="smo_medServiceForm">
                    <td></td>
                    </msh:ifFormTypeIsView>
                </msh:row>
        </msh:ifFormTypeIsCreate>
        <msh:row guid="348da311-be75-4c71-86c5-3fbf138985dc">
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
    <msh:ifFormTypeIsView formName="smo_medServiceForm" guid="cef9c7b8-d384-4170-b7f7-dfff71b6d337">
      <msh:ifInRole roles="/Policy/Mis/MedCase/Protocol/View" guid="141a017a-569a-4fa2-9a0e-37093a8f69da">
        <ecom:parentEntityListAll formName="smo_visitProtocolForm" attribute="protocols" guid="307a660c-7369-4ec7-a67c-888f8c6aabcf" />
        <msh:tableNotEmpty name="protocols" guid="8fbc57e0-234a-426d-be88-632a2f5e1f69">
          <msh:section title="Заключение" guid="1fbe1294-8ea0-4b66-a0f3-6c99bcea13c1">
            <msh:table hideTitle="false" disableKeySupport="false" idField="id" name="protocols" action="entityParentView-smo_visitProtocol.do" disabledGoFirst="false" disabledGoLast="false" guid="d0e60067-9aec-4ee0-b20a-4f4b5aea9b37">
              <msh:tableColumn columnName="Дата" property="dateRegistration" guid="c16dd9e1-4534-44db-8b0a-972e2c67dd87" />
                <msh:tableColumn columnName="Запись" identificator="false" property="record" guid="bd9f7fe4-b1cb-4320-aa85-03952b4abd26" cssClass="preCell" />
              <msh:tableColumn columnName="Специалист" property="specialistInfo" guid="96c6570b-360d-46a5-9fc5-2f9c63ad1912" />
            </msh:table>
          </msh:section>
        </msh:tableNotEmpty>
      </msh:ifInRole>
    </msh:ifFormTypeIsView>
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
        <msh:sideLink params="id" action="/entityParentPrepareCreate-smo_visitProtocol" name="Заключение" title="Добавить протокол" guid="2209b5f9-4b4f-4ed5-b825-b66f2ac57e87" roles="/Policy/Mis/MedCase/Protocol/Create" key="ALT+6" />
      </msh:sideMenu>
      <msh:sideMenu title="Печать" guid="cdf02c63-67bc-4542-a68d-38398f5059bd">
        <msh:sideLink action="/javascript:printVisit('.do')" name="Мед.услуги по поликлинике" title="Печать мед.услуги и заключения" 
        	guid="2156670f-b32c-4634-942b-2f8a4467567c" params="" roles="/Policy/Mis/MedCase/Visit/PrintVisit" />
        <msh:sideLink action="/javascript:printHospital('.do')" name="Мед.услуги по стационару" title="Печать мед.услуги и заключения" 
        	guid="2156670f-b32c-4634-942b-2f8a4467567c" params="" roles="/Policy/Mis/MedCase/Stac/Ssl/PrintProtocol" />
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="javascript" type="string">
  <msh:ifFormTypeIsView formName="smo_medServiceForm">
  <msh:tableEmpty name="protocols">
    <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js"></script>
    <script type="text/javascript">
    	//if (confirm('Вы хотите создать заключение по услуге?')) {
    	//	window.location = "entityParentPrepareCreate-smo_visitProtocol.do?id=${param.id}";
    	//}
    </script>  
    </msh:tableEmpty>
 <script type='text/javascript' src='./dwr/interface/TemplateProtocolService.js'></script>
 <msh:ifInRole roles="/Policy/Mis/MedCase/Stac/Ssl/PrintProtocol">
  <script type="text/javascript">
  
  function printHospital() {
		window.location.href = "print-protocol.do?m=printProtocol&s=HospitalPrintService&id=${param.id}" ;
  }
  </script>  
  </msh:ifInRole>
  <msh:ifNotInRole roles="/Policy/Mis/MedCase/Visit/PrintVisit">
 <script type="text/javascript">
  
  function printVisit() {
  	TemplateProtocolService.getCountSymbolsInProtocol ('${param.id}'  , {
  	 callback: function(aCount) {
			 	//alert(aCount) ;
			     if (aCount>2760) {
			          window.location.href = "print-visit1.do?s=VisitPrintService&m=printVisit&id=${param.id}" ;
			     } else {
			     	 window.location.href = "print-visit.do?s=VisitPrintService&m=printVisit&id=${param.id}" ;
			     }
			    
			}}) ;
  }
  </script>
    </msh:ifNotInRole>
  </msh:ifFormTypeIsView>
  <msh:ifFormTypeIsNotView formName="smo_medServiceForm">
    <script type="text/javascript" src="./dwr/interface/HospitalMedCaseService.js"></script>
    <script type="text/javascript">// <![CDATA[//
    	
    	var oldaction = document.forms[0].action ;
    	document.forms[0].action = 'javascript:isExistMedService()';
    	function isExistMedService() {
    		 
    		HospitalMedCaseService.findDoubleServiceByPatient($('id').value,$('patient').value,$('medService').value, $('dateExecute').value
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
  
    	<msh:ifFormTypeIsNotView formName="smo_medServiceForm">
  	<script type="text/javascript" >
  	var oldValue = $('dateExecute').value ;
  	medServiceAutocomplete.setParentId($('categoryMedService').value+'#'+$('dateExecute').value) ;
  	//$('dateExecute').addOnChangeCallback(function() {alert(2)}) ;
  	eventutil.addEventListener($('dateExecute'), "change", 
  	function() {
  		changeParentMedService() ;
  	}) ;
  	eventutil.addEventListener($('dateExecute'),'blur',function(){
  		if (oldValue!=$('dateExecute').value) {
  			changeParentMedService() ;
  		}
  	}) ;
  	categoryMedServiceAutocomplete.addOnChangeCallback(function() {
  		changeParentMedService() ;
  	}) ;
  	function changeParentMedService() {
  		medServiceAutocomplete.setParentId($('categoryMedService').value+'#'+$('dateExecute').value) ;
  		$('medService').value='' ;
  		$('medServiceName').value='' ;
  		oldValue = $('dateExecute').value ;
  	}
  	</script>
  	</msh:ifFormTypeIsNotView>
  	
  	

    	<msh:ifFormTypeIsCreate formName="smo_medServiceForm">
    		<script type="text/javascript">
    			var focusRecord = false ;
    			eventutil.addEventListener($('record'),'focus',function(){
    				if (!focusRecord) {
		    			if (confirm("Вы хотите создать протокол на основе шаблона?")) {
		    				showTemplateProtocol() ;
		    			} 
	    			}
	    			focusRecord = true ;
    			})
    		</script>
    	</msh:ifFormTypeIsCreate>

    	<msh:ifFormTypeAreViewOrEdit formName="smo_medServiceForm"><msh:ifFormTypeIsNotView formName="smo_medServiceForm">
    		<script type="text/javascript">
    		TemplateProtocolService.getUsername(
    			{
                    callback: function(aString) {
                        if($('username').value != aString){
                         	alert('Вы можете редактировать только зарегистрированную Вами мед.услугу!!!');
                         	window.location.href= "entityParentView-smo_visitProtocol.do?id=${param.id}";
                         }
                     }
                 }
    		);
    		</script>
    	</msh:ifFormTypeIsNotView>
    	
    	</msh:ifFormTypeAreViewOrEdit>


  </tiles:put>
</tiles:insert>

