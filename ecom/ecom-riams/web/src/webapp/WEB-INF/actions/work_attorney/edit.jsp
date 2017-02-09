<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://struts.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.nuzmsh.ru/tags/msh" prefix="msh" %>
<%@ taglib uri="http://www.ecom-ast.ru/tags/ecom" prefix="ecom" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<tiles:insert page="/WEB-INF/tiles/mainLayout.jsp" flush="true">

  <tiles:put name="body" type="string">
    <!-- 
    	  - Справочник рабочих функций
    	  -->
    <msh:form action="/entitySaveGoView-work_attorney.do" defaultField="type" guid="c8f2f6d6-5482-43a0-a137-1b22828b43f5">
      <msh:hidden guid="hiddenId" property="id" />
      <msh:hidden guid="hiddenSaveType" property="saveType" />
      <msh:panel guid="panel">
      <msh:row>
      <msh:autoComplete property="type" vocName="vocAttorneyType" fieldColSpan="4" size="50"/>
      </msh:row>
      <msh:row>
      <msh:autoComplete property="person" vocName="patient" fieldColSpan="4" size="50"/>
      </msh:row>
      <msh:row guid="232f0010-a709-4078-bb63-090b4e2aee95">
         <msh:textField property="altPersonInfo" label="ФИО в родительном падеже" fieldColSpan="4" size="50" guid="40f2cd0a-d466-4811-82fc-ed560c6ca164" />
      </msh:row>
        <msh:row guid="232f0010-a709-4078-bb63-090b4e2aee95">
          <msh:textField property="attorneyNumber" guid="40f2cd0a-d466-4811-82fc-ed560c6ca164" />
          <msh:textField property="attorneyStartDate" guid="7d51235b-c16b-4b60-8743-46e38c0c1ca0" />
        </msh:row>
        <msh:row>
        	<msh:checkBox property="isDefault"/>
        </msh:row>
        <msh:row>
        	<msh:checkBox property="isArchive"/>
        </msh:row>
       
       
        <msh:submitCancelButtonsRow guid="submitCancel" colSpan="2" />
      </msh:panel>
    </msh:form>
    
  </tiles:put>
  <tiles:put name="title" type="string">
    <ecom:titleTrail guid="titleTrail-123" mainMenu="Voc" beginForm="work_attorneyForm" />
  </tiles:put>
  <tiles:put name="side" type="string">
   <msh:sideMenu title="Перейти" guid="ded8e3d3-a6a8-4c76-b484-92dfe52cc1b7">
      <msh:sideLink roles="/Policy/Mis/Worker/Attorney/View" key="ALT+2" params="id" action="/entityList-work_attorney" name="Список доверенностей" title="Список доверенностей" guid="75bdfecf-3673-4e79-85d1-caa9ec490608" />
    </msh:sideMenu>
    <msh:sideMenu title="Доверенность" guid="ded8e3d3-a6a8-4c76-b484-92dfe52cc1b7">
      <msh:sideLink roles="/Policy/Mis/Worker/Attorney/Edit" key="ALT+2" params="id" action="/entityEdit-work_attorney" name="Изменить" title="Изменить " guid="75bdfecf-3673-4e79-85d1-caa9ec490608" />
      <msh:sideLink roles="/Policy/Mis/Worker/Attorney/Delete" confirm="Удалить?" key="ALT+DEL" params="id" action="/entityDelete-work_attorney" name="Удалить" title="Удалить " guid="90ae4764-8e8c-4c53-8648-2fffd15f4097" />
    </msh:sideMenu>
         
  </tiles:put>
  
<tiles:put name="javascript" type="string">
  <script type='text/javascript'>
    personAutocomplete.addOnChangeCallback(function() {
    	$('altPersonInfo').value = $('personName').value;
    })
    

  </script>
</tiles:put>
</tiles:insert>

