<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="side" type="string">
    <msh:ifFormTypeIsView formName="preg_pregHistoryForm" guid="e20545-4285-a21c-3bb9b4569efc">
      <msh:sideMenu guid="9ec15353-1f35-4c18-b99d-e2b63ecc60c9" title="История родов">
        <msh:sideLink key="ALT+2" params="id" action="/entityParentEdit-preg_pregHistory" name="Изменить" roles="/Policy/Mis/Pregnancy/Edit" guid="5a1450f5-7629-4458-b5a5-e5566af6a914" />
        <msh:sideLink key="ALT+DEL" confirm="Удалить?" params="id" action="/entityParentDeleteGoSubclassView-preg_pregHistory" name="Удалить" roles="/Policy/Mis/Pregnancy/Delete" guid="7767f5b6-c131-47f4-b8a0-2604050c450f" />
      </msh:sideMenu>
      <msh:sideMenu title="Осмотры" guid="e4e647a3-9dc6-45f6-8047-c61bc0c8acfb" />
      <msh:sideMenu title="Печать">
      	<msh:sideLink action="/print-preghistory.do?s=HospitalPrintService&amp;m=printPregHistory" params="id" name="История родов" title="Печать истории родов"/>
      </msh:sideMenu>
    </msh:ifFormTypeIsView>
  </tiles:put>
  <tiles:put name="body" type="string">
    <!-- 
    	  - История родов
    	  -->
    <msh:form action="/entityParentSaveGoView-preg_pregHistory.do" defaultField="pregnancy" guid="be2c889f-ed1d-4a2b-9cda-9127e9d94885">
      <msh:hidden property="id" guid="d10f460a-e434-45a5-90f0-b0a7aed00ec6" />
      <msh:hidden property="medCase" guid="9d908e88-e051-4d0a-8da6-3f5f4b226493" />
      <msh:hidden property="saveType" guid="bd322f07-c944-4587-a963-a09db2b93caf" />
      <msh:panel guid="d1cd0310-bf53-4ce1-9dd5-06388b51ec01">
        <msh:autoComplete property="pregnancy" label="Беременность" horizontalFill="true" vocName="pregnancyByWoman" guid="ad40d1b0-84f7-4d41-bbbe-5ae0d458844a" viewAction="entityParentView-preg_pregnancy.do" size="150" />
        <msh:ifFormTypeIsCreate formName="preg_pregHistoryForm" guid="90a9cef4-6832-459d-b670-ddb45e9359a2">
          <td>
            <a href="javascript:createNewPregnancy()" title="Завести новую беременность по пациентке">Новая беременность</a>
          </td>
        </msh:ifFormTypeIsCreate>
        <msh:submitCancelButtonsRow colSpan="" guid="6bece8ec-9b93-4faf-b729-851f1447d54f" />
      </msh:panel>
    </msh:form>
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail mainMenu="Patient" beginForm="preg_pregHistoryForm" guid="fb43e71c-1ba9-4e61-8632-a6f4a72b461c" />
  </tiles:put>
  <tiles:put name="javascript" type="string">
    <msh:ifFormTypeIsCreate formName="preg_pregHistoryForm">
    <script type='text/javascript' src="./dwr/interface/HospitalMedCaseService.js" >
    /**/
    </script>
    <script type='text/javascript'>
    function createNewPregnancy() {
  		//alert('Создание новой беременности для СМО ${param.id}') ;
  		HospitalMedCaseService.getPatient('${param.id}',
  			{
  				callback: function(aIdPat){
  					//alert("Пациент="+aIdPat) ;
  					window.location.href = 'entityParentPrepareCreate-preg_pregnancy.do?medCase=${param.id}&id='+aIdPat ;
  				}
  			})
  		
  		//window.location.href = 'entityParentPrepareCreate-preg_pregnancy.do?medCase=${param.id}&id=' ;
  	}
  </script>
    </msh:ifFormTypeIsCreate>
    <msh:ifFormTypeIsNotView formName="preg_pregHistoryForm">
    <script type='text/javascript'>
    	//alert($('medCase').value) ;
	    pregnancyAutocomplete.setParentId($('medCase').value) ;
  </script>
  </msh:ifFormTypeIsNotView>
  </tiles:put>
</tiles:insert>

